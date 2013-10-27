//
//  QuestionnaireAnswer.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface QuestionnaireAnswer : NSObject

@property (strong, nonatomic) NSMutableArray * questions; // of QuestionAnswer

- (id)initWithId:(unsigned)idQuestionnaire;
- (NSDictionary *)jsonData;

@end
