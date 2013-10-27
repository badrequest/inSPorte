//
//  QuestionAnswer.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "OptionAnswer.h"

@interface QuestionAnswer : NSObject

@property (nonatomic) unsigned idQuestion;
@property (strong, nonatomic) NSMutableArray * options; // of OptionAnswer

- (id)initWithId:(unsigned)idQuestion;
- (NSDictionary *)jsonData;

@end
