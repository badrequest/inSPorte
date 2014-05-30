//
//  InitialViewController.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "InitialViewController.h"
#import "Auth.h"

@implementation InitialViewController

// View controller inicialization.
- (id) initWithNibName: (NSString *) nibNameOrNil bundle: (NSBundle *) nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self)
    {
        // Custom initialization
    }
    return self;
}

// Hide status bar of this view.
- (BOOL) prefersStatusBarHidden
{
    return YES;
}

// Capture the view load.
- (void) viewDidLoad
{
    [super viewDidLoad];
    
    [self proceedToApplication];
}

// Proceed to application after to configure storyboard.
- (void) proceedToApplication
{
    
    UIStoryboard * storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    UIViewController * viewController;
    
    if ([Auth isAuthenticated])
    {
        viewController = [storyboard instantiateViewControllerWithIdentifier:@"MainViewController"];
    }
    else
    {
        viewController = [storyboard instantiateViewControllerWithIdentifier:@"AuthViewController"];
    }

    UIWindow * windowRef = [UIApplication sharedApplication].delegate.window;
    windowRef.rootViewController = viewController;
    [windowRef makeKeyAndVisible];
}

// Capture receiving warnings about memory.
- (void) didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
