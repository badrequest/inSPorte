//
//  QuestionAnswer.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "QuestionAnswer.h"

@implementation QuestionAnswer

- (id)initWithId:(unsigned)idQuestion {
    
    self = [super init];
    
    if(self != nil) {
        
        _idQuestion = idQuestion;
        _options = nil;
    }
    
    return self;
}

- (NSDictionary *)jsonData {
    
    NSMutableDictionary * json = [[NSMutableDictionary alloc] init];
    
    [json setObject:[NSNumber numberWithUnsignedInt:self.idQuestion] forKey:@"idPergunta"];
    
    NSMutableArray * arr = [[NSMutableArray alloc] init];
    
    if(self.options != nil) {
    
        unsigned nOptions = [self.options count];
        
        for(unsigned i = 0; i < nOptions; i++) {
            
            [arr addObject:[(OptionAnswer *)[self.options objectAtIndex:i] jsonData]];
        }
    }
    
    [json setObject:arr forKey:@"opcoes"];
    
    [json setObject:[NSNumber numberWithBool:NO] forKey:@"imagem"];
    
    return json;
}

@end
