//
//  Answer.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Auth.h"
#import "Questionnaire.h"
#import "QuestionnaireAnswer.h"

@interface Answer : NSObject

@property (strong, nonatomic) QuestionnaireAnswer * questionnaire;
@property (strong, nonatomic) NSMutableDictionary * comment;

- (id)initAnswerWithId:(unsigned)line andLat:(double)lat andLon:(double)lon;
- (NSDictionary *)jsonData;

@end
