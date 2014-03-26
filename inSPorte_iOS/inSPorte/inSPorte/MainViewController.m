//
//  MainViewController.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "MainViewController.h"
#import <Social/Social.h>
#import <Accounts/Accounts.h>

@interface MainViewController ()

@property (weak, atomic) IBOutlet UITextView *newsFeed;
@property (weak, nonatomic) IBOutlet UIImageView *imageView;

@end

@implementation MainViewController

- (BOOL)prefersStatusBarHidden {
    return NO;
}

//
// Metodo abaixo baseado no codigo de Jeroen van Rijn
//

- (void) getInfo
{
    // Request access to the Twitter accounts
    ACAccountStore *accountStore = [[ACAccountStore alloc] init];
    ACAccountType *accountType = [accountStore accountTypeWithAccountTypeIdentifier:ACAccountTypeIdentifierTwitter];
    [accountStore requestAccessToAccountsWithType:accountType options:nil completion:^(BOOL granted, NSError *error){
        if (granted) {
            NSArray *accounts = [accountStore accountsWithAccountType:accountType];
            // Check if the users has setup at least one Twitter account
            if (accounts.count > 0)
            {
                ACAccount *twitterAccount = [accounts objectAtIndex:0];
                // Creating a request to get the info about a user on Twitter
                SLRequest *twitterInfoRequest = [SLRequest requestForServiceType:SLServiceTypeTwitter requestMethod:SLRequestMethodGET URL:[NSURL URLWithString:@"https://api.twitter.com/1.1/users/show.json"] parameters:[NSDictionary dictionaryWithObject:@"sptrans_" forKey:@"screen_name"]];
                
                [twitterInfoRequest setAccount:twitterAccount];
                
                // Making the request
                [twitterInfoRequest performRequestWithHandler:^(NSData *responseData, NSHTTPURLResponse *urlResponse, NSError *error) {
                    dispatch_async(dispatch_get_main_queue(), ^{
                        // Check if we reached the reate limit
                        if ([urlResponse statusCode] == 429) {
                            NSLog(@"Rate limit reached");
                            return;
                        }
                        // Check if there was an error
                        if (error) {
                            NSLog(@"Error: %@", error.localizedDescription);
                            return;
                        }
                        // Check if there is some response data
                        if (responseData) {
                            
                            NSError *error = nil;
                            NSArray *TWData = [NSJSONSerialization JSONObjectWithData:responseData options:NSJSONReadingMutableLeaves error:&error];

                            // Update the interface with the loaded data
                            NSString *lastTweet = [[(NSDictionary *)TWData objectForKey:@"status"] objectForKey:@"text"];
                            
                            if(error == nil) {
                            
                                self.newsFeed.text = lastTweet;
                            }
                            
                            else {
                                
                                self.newsFeed.text = @"Não foi possível obter as últimas notícias...";
                            }
                            
                            self.newsFeed.textColor = [UIColor colorWithRed:0.812 green:0.169 blue:0.184 alpha:1.0];
                        }
                    });
                }];
            }
            
        } else {
            
            NSLog(@"No access granted");
            
            self.newsFeed.text = @"Não foi possível obter as últimas notícias...";
            self.newsFeed.textColor = [UIColor colorWithRed:0.812 green:0.169 blue:0.184 alpha:1.0];
        }
    }];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (IBAction)selectAction:(id)sender {
    
    [self performSegueWithIdentifier:@"MainToSelectSegue" sender:self];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    
    [self getInfo];
}

- (void)viewDidAppear:(BOOL)animated {
    
    /* Parallax */
    UIInterpolatingMotionEffect *verticalMotionEffect = [[UIInterpolatingMotionEffect alloc] initWithKeyPath:@"center.y"
                                                                                                        type:UIInterpolatingMotionEffectTypeTiltAlongVerticalAxis];
    
    verticalMotionEffect.minimumRelativeValue = @(-20);
    verticalMotionEffect.maximumRelativeValue = @(20);
    
    UIInterpolatingMotionEffect *horizontalMotionEffect = [[UIInterpolatingMotionEffect alloc] initWithKeyPath:@"center.x"
                                                                                                          type:UIInterpolatingMotionEffectTypeTiltAlongHorizontalAxis];
    horizontalMotionEffect.minimumRelativeValue = @(-15);
    horizontalMotionEffect.maximumRelativeValue = @(15);
    
    UIMotionEffectGroup *group = [UIMotionEffectGroup new];
    
    group.motionEffects = @[horizontalMotionEffect, verticalMotionEffect];
    
    [self.imageView addMotionEffect:group];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
