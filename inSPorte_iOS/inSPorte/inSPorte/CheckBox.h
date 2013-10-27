//
//  CheckBox.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CheckBox : UIButton

@property (strong, nonatomic) NSString * label;
@property (nonatomic) BOOL option;

@property (nonatomic) unsigned idxQuestion;
@property (nonatomic) unsigned idxOption;

- (id)initWithFrame:(CGRect)frame andLabel:(NSString *)label;

- (void)clickCheckbox;
- (void)unset;

@end
