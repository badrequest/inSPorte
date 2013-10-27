//
//  Question.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "Question.h"

@interface Question()

@property (strong, nonatomic) NSMutableArray * options; // of Option

@end

@implementation Question

- (id)initWithText:(NSString *)text andId:(unsigned)idQuestion {
 
    self = [super init];
    
    if(self != nil) {
    
        _text = text;
        _idQuestion = idQuestion;
        
        _options = [[NSMutableArray alloc] init];
    }
    
    return self;
}

- (void)addOption:(Option *)o {
    
    [self.options addObject:o];
}

- (Option *)retrieveOptionWithIndex:(unsigned)i {
    
    if(i >= self.options.count)
        return nil;
    
    return [self.options objectAtIndex:i];
}

- (unsigned)countOfOptions {
    
    return [self.options count];
}

@end
