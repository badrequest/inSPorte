//
//  MenuViewController.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "MenuViewController.h"
#import "inSPorte.h"
#import "Comment.h"
#import "DAO.h"
#import "Network.h"
#include "Constants.h"

@interface MenuViewController ()

@property (strong, nonatomic) UIAlertView * loading;

@property (weak, nonatomic) IBOutlet UIButton *likeButton;
@property (weak, nonatomic) IBOutlet UIButton *dislikeButton;

@property (weak, nonatomic) IBOutlet UILabel *lineText;

@property (strong, nonatomic) NSArray * questionnaires;

@property (nonatomic) BOOL previousLike;
@property (nonatomic) BOOL previousDislike;

@property (nonatomic) BOOL like;
@property (nonatomic) BOOL dislike;

@property (strong, nonatomic) Network * conn;

@property (strong, nonatomic) NSString * nextSegue;

@property (weak, nonatomic) IBOutlet UIImageView *imageView;

@end

@implementation MenuViewController

- (BOOL)prefersStatusBarHidden {
    return NO;
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];

    [self.lineText setText:[[inSPorte sharedInstance] selectedLineText]];
    
    self.questionnaires = [[[DAO alloc] init] requestQuestionnaires];
    
    self.like = NO;
    self.dislike = NO;
    
    self.conn = [[Network alloc] initWithURL:WEB_SERVICE_SERVER];
    
    [self updateLikes];
}

- (void)viewDidAppear:(BOOL)animated {
    
    /* Parallax */
    UIInterpolatingMotionEffect *verticalMotionEffect = [[UIInterpolatingMotionEffect alloc] initWithKeyPath:@"center.y"
                                                                                                        type:UIInterpolatingMotionEffectTypeTiltAlongVerticalAxis];
    
    verticalMotionEffect.minimumRelativeValue = @(-15);
    verticalMotionEffect.maximumRelativeValue = @(15);
    
    UIInterpolatingMotionEffect *horizontalMotionEffect = [[UIInterpolatingMotionEffect alloc] initWithKeyPath:@"center.x"
                                                                                                          type:UIInterpolatingMotionEffectTypeTiltAlongHorizontalAxis];
    horizontalMotionEffect.minimumRelativeValue = @(-20);
    horizontalMotionEffect.maximumRelativeValue = @(20);
    
    UIMotionEffectGroup *group = [UIMotionEffectGroup new];
    
    group.motionEffects = @[horizontalMotionEffect, verticalMotionEffect];
    
    [self.imageView addMotionEffect:group];
}

- (void)viewDidLayoutSubviews {
    
    [super viewDidLayoutSubviews];
}

- (void)updateLikes {
    
    if(SYSTEM_VERSION < 7) {
        
        self.loading = [[UIAlertView alloc] initWithTitle:@"Acessando..."
                                                  message:@"\n"
                                                 delegate:nil
                                        cancelButtonTitle:nil
                                        otherButtonTitles:nil];
        
        UIActivityIndicatorView *spinner = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
        
        spinner.center = CGPointMake(139.5, 75.5);
        [self.loading addSubview:spinner];
        [spinner startAnimating];
        [self.loading show];
    }
    
    else {
        
        self.loading = [[UIAlertView alloc] initWithTitle:@""
                                                  message:@"Acessando..."
                                                 delegate:nil
                                        cancelButtonTitle:nil
                                        otherButtonTitles:nil];
        
        [self.loading show];
    }
    
    [self.conn requestGetLikeWithId:[[[inSPorte sharedInstance] selectedLine] idLine]
                      andWithTarget:self
                        andResponse:@selector(requestLikeOk:)
                           andError:@selector(requestLikeError:)];
}

- (void)requestLikeOk:(id)jsonResponse {
    
    [self.loading dismissWithClickedButtonIndex:0 animated:NO];
    
    self.previousDislike = NO;
    self.previousLike = NO;
    
    if(jsonResponse == nil || ![jsonResponse isKindOfClass:[NSDictionary class]]) {
        
        return;
    }
    
    if([(NSDictionary *)jsonResponse objectForKey:@"ans"] == nil) {
        
        return;
    }
    
    if([[(NSDictionary *)jsonResponse objectForKey:@"ans"] isEqualToString:@"ok"]) {
        
        BOOL likeDislike = [[jsonResponse objectForKey:@"like"] boolValue];
        
        if(likeDislike) {
            
            [self likeAction:nil];
            
            self.previousLike = YES;
            self.previousDislike = NO;
        }
        
        else {
         
            [self dislikeAction:nil];
            
            self.previousLike = NO;
            self.previousDislike = YES;
        }
    }
}

- (void)requestLikeError:(NSError *)err {
    
    [self.loading dismissWithClickedButtonIndex:0 animated:NO];
    
    self.previousLike = NO;
    self.previousDislike = NO;
}

- (IBAction)busAction:(id)sender {
    
    [[inSPorte sharedInstance] setSelectedQuestionnaire:[self.questionnaires objectAtIndex:0]];
    
    [self performSegue:@"MenuToQuestionnaireSegue"];
}

- (IBAction)driverAction:(id)sender {
    
    [[inSPorte sharedInstance] setSelectedQuestionnaire:[self.questionnaires objectAtIndex:1]];
    
    [self performSegue:@"MenuToQuestionnaireSegue"];
}

- (IBAction)busStopAction:(id)sender {
    
    [[inSPorte sharedInstance] setSelectedQuestionnaire:[self.questionnaires objectAtIndex:2]];
    
    [self performSegue:@"MenuToQuestionnaireSegue"];
}

- (IBAction)incidentAction:(id)sender {
    
    [[inSPorte sharedInstance] setSelectedQuestionnaire:[self.questionnaires objectAtIndex:3]];
    
    [self performSegue:@"MenuToQuestionnaireSegue"];
}

- (IBAction)commentAction:(id)sender {
    
    [self performSegue:@"MenuToCommentSegue"];
}

- (IBAction)backAction:(id)sender {

    [[inSPorte sharedInstance] deleteSelectedLine];
    
    // Neste caso nao envia o like novo!
    [self performSegueWithIdentifier:@"MenuToSelectSegue" sender:self];
}

- (IBAction)likeAction:(id)sender {
    
    if(!self.like) {
        
        self.like = YES;
        [self.likeButton setBackgroundImage:[UIImage imageNamed:@"like-ativo.png"] forState:UIControlStateNormal];
        
        self.dislike = NO;
        [self.dislikeButton setBackgroundImage:[UIImage imageNamed:@"not-like.png"] forState:UIControlStateNormal];
    }
}

- (IBAction)dislikeAction:(id)sender {
    
    if(!self.dislike) {
        
        self.like = NO;
        [self.likeButton setBackgroundImage:[UIImage imageNamed:@"like.png"] forState:UIControlStateNormal];
        
        self.dislike = YES;
        [self.dislikeButton setBackgroundImage:[UIImage imageNamed:@"not-like-ativo.png"] forState:UIControlStateNormal];
    }
}

- (void)performSegue:(NSString *)segueId {
    
    self.nextSegue = segueId;
    
    if(self.like != self.previousLike || self.dislike != self.previousDislike) {
        
        if(SYSTEM_VERSION < 7) {
            
            self.loading = [[UIAlertView alloc] initWithTitle:@"Acessando..."
                                                      message:@"\n"
                                                     delegate:nil
                                            cancelButtonTitle:nil
                                            otherButtonTitles:nil];
            
            UIActivityIndicatorView *spinner = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
            
            spinner.center = CGPointMake(139.5, 75.5);
            [self.loading addSubview:spinner];
            [spinner startAnimating];
            [self.loading show];
        }
        
        else {
            
            self.loading = [[UIAlertView alloc] initWithTitle:@""
                                                      message:@"Acessando..."
                                                     delegate:nil
                                            cancelButtonTitle:nil
                                            otherButtonTitles:nil];
            
            [self.loading show];
        }
        
        [self.conn requestSetLikeWithId:[[[inSPorte sharedInstance] selectedLine] idLine]
                                andLike:self.like
                          andWithTarget:self
                            andResponse:@selector(setLikeResponse:)
                               andError:@selector(setLikeError:)];
    }
    
    else {
    
        [self performSegueWithIdentifier:segueId sender:self];
    }
}

- (void)setLikeResponse:(id)jsonResponse {
    
    [self.loading dismissWithClickedButtonIndex:0 animated:NO];
    
    if(jsonResponse == nil || ![jsonResponse isKindOfClass:[NSDictionary class]]) {
        
        NSLog(@"Erro ao alterar o \'like\'");
        
        return;
    }
    
    if([(NSDictionary *)jsonResponse objectForKey:@"ans"] == nil) {
        
        NSLog(@"Erro ao alterar o \'like\'");
        
        return;
    }
    
    if([[(NSDictionary *)jsonResponse objectForKey:@"ans"] isEqualToString:@"ok"]) {
        
        NSLog(@"\'Like\' modificado!");
    }
    
    [self performSegueWithIdentifier:self.nextSegue sender:self];
}

- (void)setLikeError:(NSError *)err {
    
    [self.loading dismissWithClickedButtonIndex:0 animated:NO];

    NSLog(@"Erro ao alterar o \'like\'");
    
    [self performSegueWithIdentifier:self.nextSegue sender:self];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
