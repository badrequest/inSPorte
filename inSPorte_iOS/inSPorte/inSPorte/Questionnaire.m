//
//  Questionnaire.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "Questionnaire.h"

@interface Questionnaire()

@property (strong, nonatomic) NSMutableArray * questions; // of Question

@end

@implementation Questionnaire

- (id)initWithText:(NSString *)text andId:(unsigned)idQuestionnaire {
    
    self = [super init];
    
    if(self != nil) {
        
        _text = text;
        _idQuestionnaire = idQuestionnaire;
        
        _questions = [[NSMutableArray alloc] init];
    }
    
    return self;
}

- (void)addQuestion:(Question *)q {
    
    [self.questions addObject:q];
}

- (Question *)retrieveQuestionWithIndex:(unsigned)i {
    
    if(i >= self.questions.count)
        return nil;
    
    return [self.questions objectAtIndex:i];
}

- (unsigned)countOfQuestions {
    
    return [self.questions count];
}

@end
