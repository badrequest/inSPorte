//
//  DAO.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Questionnaire.h"
#import "Line.h"

@interface DAO : NSObject

- (id)init;
- (NSArray *)requestQuestionnaires;
- (NSArray *)requestLines;
- (void)dealloc;

@end
