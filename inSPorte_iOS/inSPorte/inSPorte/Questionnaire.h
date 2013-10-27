//
//  Questionnaire.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Question.h"

@interface Questionnaire : NSObject

@property (strong, nonatomic) NSString * text;
@property (nonatomic) unsigned idQuestionnaire;

- (id)initWithText:(NSString *)text andId:(unsigned)idQuestionnaire;
- (void)addQuestion:(Question *)q;
- (Question *)retrieveQuestionWithIndex:(unsigned)i;
- (unsigned)countOfQuestions;

@end
