//
//  QuestionnaireViewController.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "QuestionnaireViewController.h"
#import "CheckBox.h"
#import "inSPorte.h"
#import "Constants.h"

#import "Answer.h"
#import "QuestionAnswer.h"
#import "QuestionnaireAnswer.h"
#import "OptionAnswer.h"

#import "Network.h"

@interface QuestionnaireViewController () {
    CLLocationManager * locManager;
    unsigned nTries;
}

@property (weak, nonatomic) IBOutlet UILabel *questionnaireText;
@property (weak, nonatomic) IBOutlet UIScrollView *questionnaireArea;

@property (strong, nonatomic) UIAlertView * loading;

@property (nonatomic) double lat;
@property (nonatomic) double lon;

@property (strong, nonatomic) NSMutableArray * questionsCheckBoxArray;

@property (strong, nonatomic) Network * conn;

@end

@implementation QuestionnaireViewController

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
    
    Questionnaire * quest = [[inSPorte sharedInstance] selectedQuestionnaire];
    
    [self.questionnaireText setText:[quest text]];
}

- (IBAction)sendAction:(id)sender {
    
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
    
    Questionnaire * quest = [[inSPorte sharedInstance] selectedQuestionnaire];
    
    NSMutableArray * questArray = [[NSMutableArray alloc] init];
    
    unsigned nQuestions = [self.questionsCheckBoxArray count];
    
    for(unsigned i = 0; i < nQuestions; i++) {
        
        unsigned nCount = [(NSMutableArray *)[self.questionsCheckBoxArray objectAtIndex:i] count], n = 0;
        
        for(unsigned j = 0; j < nCount; j++) {
            
            if([[[self.questionsCheckBoxArray objectAtIndex:i] objectAtIndex:j] option])
                n++;
        }
        
        if(n > 0) {
        
            Question * q = [quest retrieveQuestionWithIndex:i];
            
            NSMutableArray * optionArray = [[NSMutableArray alloc] init];
            
            NSMutableArray * checkBoxes = [self.questionsCheckBoxArray objectAtIndex:i];
            unsigned nOptions = [checkBoxes count];
            
            for(unsigned j = 0; j < nOptions; j++) {
                
                CheckBox * c = [checkBoxes objectAtIndex:j];
                
                if([c option]) {
                    
                    Option * opt = [q retrieveOptionWithIndex:j];
                    
                    OptionAnswer * optAns = [[OptionAnswer alloc] initWithId:[opt idOption]];
                    
                    [optionArray addObject:optAns];
                }
            }
        
            QuestionAnswer * q_a = [[QuestionAnswer alloc] initWithId:[q idQuestion]];
            [q_a setOptions:optionArray];
            [questArray addObject:q_a];
        }
    }
    
    QuestionnaireAnswer * qa = [[QuestionnaireAnswer alloc] initWithId:[quest idQuestionnaire]];
    [qa setQuestions:questArray];
    
    [ans setQuestionnaire:qa];

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
    
    [self performSegueWithIdentifier:@"QuestionnaireToMainSegue" sender:self];
}

- (void)sendError:(NSError *)err {
    
    [self.loading dismissWithClickedButtonIndex:0 animated:NO];
    
    ERROR_ALERT(@"Não foi possível fazer o envio!");
    
    [self performSegueWithIdentifier:@"QuestionnaireToMainSegue" sender:self];
}

- (IBAction)cancelAction:(id)sender {

    [[inSPorte sharedInstance] deleteSelectedQuestionnaire];
    
    [self performSegueWithIdentifier:@"QuestionnaireToMenuSegue" sender:self];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewWillAppear:(BOOL)animated {
    
    [self loadQuestionnaireScrollView];
}


- (void)loadQuestionnaireScrollView {
    
    [[self.questionnaireArea subviews] makeObjectsPerformSelector:@selector(removeFromSuperview)];
    
    double imgDelta = 0.0;
    double contentHeight = 0.0;
    
    Questionnaire * quest = [[inSPorte sharedInstance] selectedQuestionnaire];
    
    unsigned count = [quest countOfQuestions], i;
    
    self.questionsCheckBoxArray = [[NSMutableArray alloc] init];
    
    for(i = 0; i < count; i++) {
        
        Question * q = [quest retrieveQuestionWithIndex:i];
        
        imgDelta = 0;
        
        contentHeight += imgDelta;
        
        UILabel * questionText = [[UILabel alloc] initWithFrame:CGRectMake(5, contentHeight + 5, self.view.frame.size.width - 25 - 15, 50)];
        
        [questionText setBackgroundColor:[UIColor colorWithRed:1.0 green:1.0 blue:1.0 alpha:0.0]];
        [questionText setText:q.text];
        [questionText setFont:[UIFont fontWithName:@"Helvetica" size:14.0]];
        [questionText setTextColor:[UIColor grayColor]];
        
        questionText.lineBreakMode = NSLineBreakByWordWrapping;
        questionText.numberOfLines = 0;
        questionText.preferredMaxLayoutWidth = self.view.frame.size.width - 25 - 15;
        
        [questionText sizeToFit];
        
        if(questionText.frame.size.height < 40)
            questionText.frame = CGRectMake(questionText.frame.origin.x, questionText.frame.origin.y, questionText.frame.size.width, 40);
        
        UIButton * photoButton;
        UIButton * galleryButton = [[UIButton alloc] init];
        
//        if([[quest retrieveQuestionWithIndex:i] hasPhoto]) {
//            
//            photoButton = [[UIButton alloc] initWithFrame:CGRectMake(self.view.frame.size.width - 25 - 5, contentHeight + (questionText.frame.size.height + 10)/2.0 - 25, 25, 25)];
//            galleryButton.frame = CGRectMake(self.view.frame.size.width - 25 - 5, contentHeight + (questionText.frame.size.height + 10)/2.0, 25, 25);
//            
//            [galleryButton setBackgroundImage:[UIImage imageNamed:@"gallery.png"] forState:UIControlStateNormal];
//            [galleryButton setTag:(NSInteger)i];
//            [galleryButton addTarget:self action:@selector(galleryButtonAction:) forControlEvents:UIControlEventTouchDown];
//        }
        
//        else {
        
            photoButton = [[UIButton alloc] initWithFrame:CGRectMake(self.view.frame.size.width - 25 - 5, contentHeight + (questionText.frame.size.height + 10)/2.0 - 12.5, 25, 25)];
            galleryButton.hidden = YES;
//        }
        
        [photoButton setBackgroundImage:[UIImage imageNamed:@"camera_red.png"] forState:UIControlStateNormal];
        [photoButton setTag:(NSInteger)i];
//        [photoButton addTarget:self action:@selector(photoButtonAction:) forControlEvents:UIControlEventTouchDown];
        
        galleryButton.titleLabel.text = @"gallery";
        galleryButton.titleLabel.hidden = YES;
        
        photoButton.titleLabel.text = @"photo";
        photoButton.titleLabel.hidden = YES;
        
        UIView * background = [[UIView alloc] initWithFrame:CGRectMake(0, contentHeight, self.view.frame.size.width, imgDelta + (10 + questionText.frame.size.height))];
        [background setBackgroundColor:[UIColor colorWithRed:224.0/255.0 green:224.0/255.0 blue:224.0/255.0 alpha:1.0]];
        
        [self.questionnaireArea addSubview:background];
        [self.questionnaireArea addSubview:questionText];
        [self.questionnaireArea addSubview:photoButton];
        [self.questionnaireArea addSubview:galleryButton];
        
        contentHeight += (questionText.frame.size.height + 10);
        
        unsigned nOptions = [q countOfOptions];
        
        NSMutableArray * checkBoxes = [[NSMutableArray alloc] init];
        
        for (unsigned j = 0; j < nOptions; j++) {
            
            Option * opt = [q retrieveOptionWithIndex:j];
            
            CheckBox * c = [[CheckBox alloc] initWithFrame:CGRectMake(5, contentHeight + 2, self.view.frame.size.width - 5, 70) andLabel:[opt text]];
            
            [c setIdxQuestion:i];
            [c setIdxOption:j];
            
            contentHeight += 37;
            
            [self.questionnaireArea addSubview:c];
            [checkBoxes addObject:c];
        }
        
        [self.questionsCheckBoxArray addObject:checkBoxes];
    }
    
    [self.questionnaireArea setContentSize:CGSizeMake(self.view.frame.size.width, contentHeight)];
}

@end
