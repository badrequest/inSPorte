//
//  CheckBox.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "CheckBox.h"

@interface CheckBox()

@property (strong, nonatomic) UIImage * checkYes;
@property (strong, nonatomic) UIImage * checkNo;

@end

@implementation CheckBox

- (id)initWithFrame:(CGRect)frame andLabel:(NSString *)label {
    
    self = [super initWithFrame:frame];
    if (self) {
        
        _checkYes = [UIImage imageNamed:@"checkbox-ativo.png"];
        _checkNo = [UIImage imageNamed:@"checkbox.png"];
        
        [self addTarget:self action:@selector(clickCheckbox) forControlEvents:UIControlEventTouchDown];
        
        _label = label;
        
        self.option = NO;
    }
    return self;
}

- (void)clickCheckbox {
    
    self.option = (self.option == NO);
    [self setNeedsDisplay];
}

- (void)unset {
    
    self.option = NO;
    [self setNeedsDisplay];
}

- (void)drawRect:(CGRect)rect {
    
    CGRect auxRect = CGRectMake(rect.origin.x, rect.origin.y, self.checkNo.size.width-5, self.checkNo.size.height-5);
    
    if(self.option == YES) {
        
        [[UIColor colorWithRed:0.812 green:0.169 blue:0.184 alpha:1.0] set];
        [self.checkYes drawInRect:auxRect];
    }
    
    else {
        
        [[UIColor colorWithRed:0.0 green:0.0 blue:0.0 alpha:1.0] set];
        [self.checkNo drawInRect:auxRect];
    }
    
    if(self.label) {
        
        auxRect.origin.x += rect.origin.x + self.checkNo.size.width + 5;
        auxRect.origin.y += 5;
        auxRect.size.width = rect.size.width - self.checkNo.size.width - 5;
        
        [self.label drawInRect:auxRect withFont:[UIFont fontWithName:@"Helvetica" size:12.0]];
    }
}

@end
