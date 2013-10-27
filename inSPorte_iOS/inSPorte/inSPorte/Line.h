//
//  Line.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Line : NSObject

@property (strong, nonatomic) NSString * text;
@property (strong, nonatomic) NSString * code;
@property (nonatomic) unsigned idLine;

- (id)initWithId:(unsigned)idLine andCode:(NSString *)code andText:(NSString *)text;

@end
