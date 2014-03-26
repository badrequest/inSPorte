//
//  CommentViewController.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "CommentViewController.h"
#import "Constants.h"
#import "Answer.h"
#import "Network.h"
#import "inSPorte.h"

@interface CommentViewController() {
    CLLocationManager * locManager;
    unsigned nTries;
}

#warning TODO Colocar limite de caracteres

@property (weak, nonatomic) IBOutlet UITextView *commentText;
@property (weak, nonatomic) IBOutlet UIButton *okButton;

@property (nonatomic) double lat;
@property (nonatomic) double lon;

@property (strong, nonatomic) UIAlertView * loading;

@property (strong, nonatomic) Network * conn;

@end

@implementation CommentViewController

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

    [self.commentText.layer setBorderColor:[UIColor blackColor].CGColor];
    [self.commentText.layer setBorderWidth:1.0];
    
    self.commentText.delegate = self;
}

- (IBAction)okAction:(id)sender {

    [UIView transitionWithView:self.okButton
                      duration:0.3
                       options:UIViewAnimationOptionTransitionCrossDissolve
                    animations:NULL
                    completion:NULL];
    
    self.okButton.hidden = YES;
    
    [self.commentText resignFirstResponder];
}

- (IBAction)sendAction:(id)sender {
    
    if([self.commentText.text isEqualToString:@""])
        return;
    
    if(locManager == nil) {
        
        if(SYSTEM_VERSION < 7) {
            
            self.loading = [[UIAlertView alloc] initWithTitle:@"Enviando..."
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
                                                      message:@"Enviando..."
                                                     delegate:nil
                                            cancelButtonTitle:nil
                                            otherButtonTitles:nil];
            
            [self.loading show];
        }
        
        // Timeout
        [self performSelector:@selector(timeOutGPS) withObject:self afterDelay:GPS_TIMEOUT];
        
        locManager = [[CLLocationManager alloc] init];
        locManager.delegate = self;
        nTries = 0;
        [locManager startUpdatingLocation];
    }
    
}

- (void)failGPS {
    
    [locManager stopUpdatingLocation];
    locManager = nil;
    nTries = 0;
    
    self.lat = 999.9;
    self.lon = 999.9;
}

- (void)timeOutGPS {
    
    if(locManager != nil) {
        
        NSLog(@"Timeout no GPS...");
        
        [self failGPS];
        
        [self proceedToSendAnswer];
    }
}

- (void)locationManager:(CLLocationManager *)manager didUpdateLocations:(NSArray *)locations {
    
    CLLocation * location = [locations lastObject];
    
    NSLog(@"Procurando... Lat:%f Long:%f Acc:%f", location.coordinate.latitude, location.coordinate.longitude, location.horizontalAccuracy);
    
    // 200 m de accuracy minima...
    if(location.horizontalAccuracy > 0.0 && location.horizontalAccuracy <= 200.0) {
        
        [manager stopUpdatingLocation];
        [NSObject cancelPreviousPerformRequestsWithTarget:self selector:@selector(timeOutGPS) object:self];
        locManager = nil;
        
        self.lat = location.coordinate.latitude;
        self.lon = location.coordinate.longitude;
        
        [self proceedToSendAnswer];
        
        return;
    }
    
    if(nTries == MAX_GPS_TRIES) {
        
        [self failGPS];
        [self proceedToSendAnswer];
        
        return;
    }
    
    nTries++;
}

- (void)locationManager:(CLLocationManager *)manager didFailWithError:(NSError *)error {
    
    [NSObject cancelPreviousPerformRequestsWithTarget:self selector:@selector(timeOutGPS) object:self];
    
    NSLog(@"Erro no GPS...");
    
    [self failGPS];
    [self proceedToSendAnswer];
}

- (void)proceedToSendAnswer {
    
    NSLog(@"Continuando com latitude %lf e longitude %lf", self.lat, self.lon);
    
    Answer * ans = [[Answer alloc] initAnswerWithId:[[[inSPorte sharedInstance] selectedLine] idLine]
                                             andLat:self.lat
                                             andLon:self.lon];
    
    NSMutableDictionary * commentDict = [[NSMutableDictionary alloc] init];
    
    [commentDict setObject:self.commentText.text forKey:@"texto"];
    [commentDict setObject:[NSNumber numberWithBool:NO] forKey:@"imagem"];
    
    [ans setComment:commentDict];
    
    self.conn = [[Network alloc] initWithURL:WEB_SERVICE_SERVER];
    
    [self.conn requestSendAnswer:ans
                   andWithTarget:self
                     andResponse:@selector(sendSuccess:)
                        andError:@selector(sendError:)];
}

- (void)sendSuccess:(id)jsonResponse {
    
    [self.loading dismissWithClickedButtonIndex:0 animated:NO];
    
    if(jsonResponse == nil || ![jsonResponse isKindOfClass:[NSDictionary class]]) {
        
        ERROR_ALERT(@"Não foi possível fazer o envio!");
    }
    
    else if([(NSDictionary *)jsonResponse objectForKey:@"ans"] == nil) {
        
        ERROR_ALERT(@"Não foi possível fazer o envio!");
    }
    
    else if([[(NSDictionary *)jsonResponse objectForKey:@"ans"] isEqualToString:@"ok"]) {
        
        ALERT(@"", @"Enviado com sucesso!");
    }
    
    else {
        
        ERROR_ALERT(@"Não foi possível fazer o envio!");
    }
    
    [self performSegueWithIdentifier:@"CommentToMainSegue" sender:self];
}

- (void)sendError:(NSError *)err {
    
    [self.loading dismissWithClickedButtonIndex:0 animated:NO];
    
    ERROR_ALERT(@"Não foi possível fazer o envio!");
    
    [self performSegueWithIdentifier:@"CommentToMainSegue" sender:self];
}

- (IBAction)cancelAction:(id)sender {
    
    [self performSegueWithIdentifier:@"CommentToMenuSegue" sender:self];
}

- (void)textViewDidBeginEditing:(UITextView *)textView {
    
    [UIView transitionWithView:self.okButton
                      duration:0.3
                       options:UIViewAnimationOptionTransitionCrossDissolve
                    animations:NULL
                    completion:NULL];
    
    self.okButton.hidden = NO;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
