//
//  Option.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Option : NSObject

@property (strong, nonatomic) NSString * text;
@property (nonatomic) unsigned idOption;

- (id)initWithText:(NSString *)text andId:(unsigned)idOption;

@end
