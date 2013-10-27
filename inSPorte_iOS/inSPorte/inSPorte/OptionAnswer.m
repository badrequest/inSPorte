//
//  OptionAnswer.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "OptionAnswer.h"

@implementation OptionAnswer

- (id)initWithId:(unsigned)idOption {
    
    self = [super init];
    
    if(self != nil) {
        
        _idOption = idOption;
    }
    
    return self;
}

- (NSDictionary *)jsonData {
    
    NSMutableDictionary * json = [[NSMutableDictionary alloc] init];
    
    [json setObject:[NSNumber numberWithUnsignedInt:self.idOption] forKey:@"idOpcao"];
    
    return json;
}

@end
