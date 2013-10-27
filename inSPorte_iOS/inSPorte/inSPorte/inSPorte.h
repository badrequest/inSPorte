//
//  inSPorte.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "Line.h"
#import "Questionnaire.h"

@interface inSPorte : NSObject

@property (strong, nonatomic) Line * selectedLine;
@property (strong, nonatomic) Questionnaire * selectedQuestionnaire;

+ (id)sharedInstance;
- (void)deleteSelectedLine;
- (NSString *)selectedLineText;
- (void)deleteSelectedQuestionnaire;

@end
