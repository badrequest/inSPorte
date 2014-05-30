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
#import "DocumentValidator.h"
#import "Constants.h"

@implementation AuthViewController

// Hide status bar of this view.
- (BOOL) prefersStatusBarHidden
{
    return YES;
}

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

// Capture the view load.
- (void) viewDidLoad
{
    [super viewDidLoad];
    
    [self prepareFormFields];
    [self prepareNetworkConnection];
}

// Capture receiving warnings about memory.
- (void) didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

// Authorization button action.
- (IBAction) authorizeAction: (id) sender
{
    if (![self validateFormFields])
    {
        // Don't continue!
        return;
    }
    
    if (SYSTEM_VERSION < 7)
    {
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
    else
    {
        self.loading = [[UIAlertView alloc] initWithTitle:@""
                                                  message:@"Autorizando..."
                                                 delegate:nil
                                        cancelButtonTitle:nil
                                        otherButtonTitles:nil];
        
        [self.loading show];
    }
    
    [self.network requestAuthWithBU:self.ticketValue
                          andCPF:self.documentValue
                   andWithTarget:self
                     andResponse:@selector(responseFromAuthRequest:)
                        andError:@selector(errorFromAuthRequest:)];
    
}

// Check the response from webservice for authentication.
- (void) responseFromAuthRequest: (id) jsonResponse
{
    [self.loading dismissWithClickedButtonIndex:0 animated:NO];
    
    if (jsonResponse == nil || ![jsonResponse isKindOfClass:[NSDictionary class]])
    {
        ERROR_ALERT(@"Não foi possível autorizar o seu acesso. Falha ao tentar comunicar-se com o servidor.");
        return;
    }
    
    if ([(NSDictionary *) jsonResponse objectForKey:@"ans"] == nil)
    {
        ERROR_ALERT(@"Não foi possível autorizar o seu acesso. Falha ao tentar comunicar-se com o servidor.");
        
        return;
    }
    
    if ([[(NSDictionary *) jsonResponse objectForKey:@"ans"] isEqualToString:@"ok"])
    {
        [Auth setAuthBU:self.ticketValue andCPF:self.documentValue];
        [self performSegueWithIdentifier:@"AuthToMainSegue" sender:self];
    }
    else
    {
        ERROR_ALERT(@"Não foi possível autorizar o seu acesso. Falha ao tentar comunicar-se com o servidor.");
    }
}

// Error action from auth request.
- (void) errorFromAuthRequest: (id) err
{
    [self.loading dismissWithClickedButtonIndex:0 animated:NO];
    
    ERROR_ALERT(@"Não foi possível autorizar o seu acesso. Falha ao tentar comunicar-se com o servidor.");
}

// Prepare the formulary fields to validate.
- (void) prepareFormFields
{
    self.ticketField.keyboardType = UIKeyboardTypeNumberPad;
    self.ticketField.delegate = self;
    self.documentField.keyboardType = UIKeyboardTypeNumberPad;
    self.documentField.delegate = self;
}

// Prepare the network connection to request data.
- (void) prepareNetworkConnection
{
    self.network = [[Network alloc] initWithURL:WEB_SERVICE_SERVER];
}

// Validate the formulary fields values.
- (BOOL) validateFormFields
{
    self.ticketValue = self.ticketField.text;
    self.documentValue = self.documentField.text;
    
    if (![[DocumentValidator shared] verifyIfDocumentValueIsValid:self.ticketValue
                                                         andType:DocumentTypeTransportTicket])
    {
        ERROR_ALERT(@"Preencha o campo Bilhete Único com um número de documento válido.");
        return FALSE;
    }
    
    if (![[DocumentValidator shared] verifyIfDocumentValueIsValid:self.documentValue
                                                          andType:DocumentTypeCPF])
    {
        ERROR_ALERT(@"Preencha o campo CPF com um número de documento válido.");
        return FALSE;
    }
    
    return TRUE;
}

# pragma mark - UITextFieldDelegate implements

- (BOOL) textField: (UITextField *) textField shouldChangeCharactersInRange: (NSRange) range replacementString: (NSString *) string
{
    NSUInteger newLength = [textField.text length] - range.length + [string length];
    
    
    if ((textField == self.documentField && newLength > DocumentTypeCPF)
        || (textField == self.ticketField && newLength > DocumentTypeTransportTicketMaxLength))
    {
        return NO;
    }
    
    return YES;
}


@end
