//
//  inSPorte.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "inSPorte.h"

@interface inSPorte()

@end

@implementation inSPorte

+ (id)sharedInstance {
    
    static inSPorte * shared = nil;
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^{
        shared = [[self alloc] init];
    });
    
    return shared;
}

- (id)init {
    
    self = [super init];
    
    if(self != nil) {
        
        _selectedLine = nil;
        _selectedQuestionnaire = nil;
    }
    
    return self;
}

- (void)deleteSelectedLine {
    
    _selectedLine = nil;
}

- (NSString *)selectedLineText {
    
    if(self.selectedLine != nil) {
        
        return [self.selectedLine text];
    }
    
    return @"";
}

- (void)deleteSelectedQuestionnaire {
    
    _selectedQuestionnaire = nil;
}


@end
