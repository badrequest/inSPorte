//
//  QuestionnaireAnswer.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "QuestionnaireAnswer.h"
#import "QuestionAnswer.h"

@interface QuestionnaireAnswer()

@property (nonatomic) unsigned idQuestionnaire;

@end

@implementation QuestionnaireAnswer

- (id)initWithId:(unsigned)idQuestionnaire {
    
    self = [super init];
    
    if(self != nil) {
        
        _idQuestionnaire = idQuestionnaire;
        _questions = nil;
    }
    
    return self;
}

- (NSDictionary *)jsonData {
    
    NSMutableDictionary * json = [[NSMutableDictionary alloc] init];
    
    [json setObject:[NSNumber numberWithUnsignedInt:self.idQuestionnaire] forKey:@"idQuestionario"];
    
    NSMutableArray * arr = [[NSMutableArray alloc] init];
    
    if(self.questions != nil) {
        
        unsigned nQuestions = [self.questions count];
        
        for(unsigned i = 0; i < nQuestions; i++) {
         
            [arr addObject:[(QuestionAnswer *)[self.questions objectAtIndex:i] jsonData]];
        }
    }
    
    [json setObject:arr forKey:@"perguntas"];
    
    return json;
}

@end
