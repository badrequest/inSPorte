//
//  AuthViewController.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "AuthViewController.h"
#import "Network.h"
#import "Auth.h"
#include "Constants.h"

@interface AuthViewController ()

@property (weak, nonatomic) IBOutlet UITextField *bu;
@property (weak, nonatomic) IBOutlet UITextField *cpf;

@property (strong, nonatomic) Network * conn;
@property (strong, nonatomic) UIAlertView * loading;

@end

@implementation AuthViewController

- (BOOL)prefersStatusBarHidden {
    return YES;
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
    
    self.bu.keyboardType = UIKeyboardTypeNumberPad;
    self.cpf.keyboardType = UIKeyboardTypeNumbersAndPunctuation;
    
    self.conn = [[Network alloc] initWithURL:WEB_SERVICE_SERVER];
    
#warning TODO Colocar limite no tamanho dos campos...
}

- (IBAction)authorizeAction:(id)sender {
    
    if(SYSTEM_VERSION < 7) {
        
        self.loading = [[UIAlertView alloc] initWithTitle:@"Autorizando..."
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
                                                  message:@"Autorizando..."
                                                 delegate:nil
                                        cancelButtonTitle:nil
                                        otherButtonTitles:nil];
        
        [self.loading show];
    }
    
    [self.conn requestAuthWithBU:self.bu.text
                          andCPF:self.cpf.text
                   andWithTarget:self
                     andResponse:@selector(responseFromAuthRequest:)
                        andError:@selector(errorFromAuthRequest:)];
    
}

- (void)responseFromAuthRequest:(id)jsonResponse {
    
    [self.loading dismissWithClickedButtonIndex:0 animated:NO];
    
    if(jsonResponse == nil || ![jsonResponse isKindOfClass:[NSDictionary class]]) {
        
        ERROR_ALERT(@"Não foi possível autorizar o seu acesso...");
        
        return;
    }
    
    if([(NSDictionary *)jsonResponse objectForKey:@"ans"] == nil) {
        
        ERROR_ALERT(@"Não foi possível autorizar o seu acesso...");
        
        return;
    }
    
    if([[(NSDictionary *)jsonResponse objectForKey:@"ans"] isEqualToString:@"ok"]) {
        
        [Auth setAuthBU:self.bu.text andCPF:self.cpf.text];
        
        [self performSegueWithIdentifier:@"AuthToMainSegue" sender:self];
    }
    
    else {
        
        ERROR_ALERT(@"Não foi possível autorizar o seu acesso...");
    }
}

- (void)errorFromAuthRequest:(id)err {
    
    [self.loading dismissWithClickedButtonIndex:0 animated:NO];
    
    ERROR_ALERT(@"Não foi possível autorizar o seu acesso...");
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
