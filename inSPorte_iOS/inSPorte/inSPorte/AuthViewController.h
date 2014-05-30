//
//  AuthViewController.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <UIKit/UIKit.h>

@class Network;

@interface AuthViewController : UIViewController <UITextFieldDelegate>

@property (weak, nonatomic) IBOutlet UITextField * ticketField;
@property (weak, nonatomic) IBOutlet UITextField * documentField;

@property (strong, nonatomic) Network * network;
@property (strong, nonatomic) UIAlertView * loading;
@property (strong, nonatomic) NSString * ticketValue;
@property (strong, nonatomic) NSString * documentValue;

- (IBAction) authorizeAction: (id) sender;

- (BOOL) prefersStatusBarHidden;
- (void) viewDidLoad;
- (id) initWithNibName: (NSString *) nibNameOrNil bundle: (NSBundle *) nibBundleOrNil;
- (void) didReceiveMemoryWarning;
- (BOOL) textField: (UITextField *) textField shouldChangeCharactersInRange: (NSRange) range replacementString: (NSString *) string;

- (void) responseFromAuthRequest: (id) jsonResponse;
- (void) errorFromAuthRequest: (id) err;
- (void) prepareFormFields;
- (void) prepareNetworkConnection;
- (BOOL) validateFormFields;

@end
