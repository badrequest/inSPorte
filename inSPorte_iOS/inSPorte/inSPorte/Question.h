//
//  Question.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Option.h"

@interface Question : NSObject

@property (strong, nonatomic) NSString * text;
@property (nonatomic) unsigned idQuestion;

- (id)initWithText:(NSString *)text andId:(unsigned)idQuestion;
- (void)addOption:(Option *)o;
- (Option *)retrieveOptionWithIndex:(unsigned)i;
- (unsigned)countOfOptions;

@end
