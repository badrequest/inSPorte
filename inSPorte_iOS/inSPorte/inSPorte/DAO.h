//
//  DAO.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "DataManager.h"
#import "Questionnaire.h"
#import "Line.h"

@interface DAO : DataManager

- (NSArray *)requestQuestionnaires;
- (NSArray *)requestLinesWithNumberOrName:(NSString *)data;

@end
