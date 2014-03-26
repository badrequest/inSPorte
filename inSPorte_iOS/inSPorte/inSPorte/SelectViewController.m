//
//  SelectViewController.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "SelectViewController.h"
#import "DAO.h"
#import "inSPorte.h"

@interface SelectViewController ()

@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) NSArray * lines; // of Line

@property (weak, nonatomic) IBOutlet UITextField *searchField;

@end

@implementation SelectViewController

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

    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    
    self.lines = [[[DAO alloc] init] requestLinesWithNumberOrName:nil];
    
    self.searchField.delegate = self;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    [[inSPorte sharedInstance] setSelectedLine:[self.lines objectAtIndex:indexPath.row]];
    [self performSegueWithIdentifier:@"SelectToMenuSegue" sender:self];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    UITableViewCell * cell = [self.tableView dequeueReusableCellWithIdentifier:[NSString stringWithFormat:@"%d", indexPath.row]];
    
    if(cell == nil)
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle
                                      reuseIdentifier:[NSString stringWithFormat:@"%d", indexPath.row]];

    cell.textLabel.text = [(Line *)[self.lines objectAtIndex:indexPath.row] text];
    cell.textLabel.font = [UIFont fontWithName:@"Helvetica" size:16.0];
    
    cell.detailTextLabel.text = [(Line *)[self.lines objectAtIndex:indexPath.row] code];
    cell.detailTextLabel.font = [UIFont fontWithName:@"Helvetica" size:12.0];
    
    return cell;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    if(section == 0) {
        
        return [self.lines count];
    }
    
    return 0;
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    
    self.lines = [[[DAO alloc] init] requestLinesWithNumberOrName:textField.text];
    
    [self.tableView reloadData];
    
    [textField resignFirstResponder];
    [textField setNeedsDisplay];
    
    return NO;
}

@end
