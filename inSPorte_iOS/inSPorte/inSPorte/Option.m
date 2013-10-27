//
//  Option.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "Option.h"

@interface Option()

@end

@implementation Option

- (id)initWithText:(NSString *)text andId:(unsigned)idOption {
    
    self = [super init];
    
    if(self != nil) {
        
        _text = text;
        _idOption = idOption;
    }
    
    return self;
}

@end
