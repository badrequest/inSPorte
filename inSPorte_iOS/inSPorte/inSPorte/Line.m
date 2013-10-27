//
//  Line.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "Line.h"

@implementation Line

- (id)initWithId:(unsigned)idLine andCode:(NSString *)code andText:(NSString *)text {
    
    self = [super init];
    
    if(self != nil) {
        
        _idLine = idLine;
        _code = code;
        _text = text;
    }
    
    return self;
}

@end
