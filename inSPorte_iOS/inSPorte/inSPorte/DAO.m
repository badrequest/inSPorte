//
//  DAO.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "DAO.h"
#include "Constants.h"

#define SQL(query, ...) [self prepareQuery:query, ##__VA_ARGS__]

#define ITERATE_SQL(X) while([self hasNextStepWithStatement:X]){
#define STEP_SQL(X) if([self hasNextStepWithStatement:X]){
#define FAIL_STEP_SLQ } else {
#define END_SQL(X) }[self finalizeQueryWithStatement:X];

@implementation DAO

- (NSArray *)requestQuestionnaires {
    
    NSMutableArray * quest = [[NSMutableArray alloc] init];
    
    Statement * stmt = SQL(@"select id, nome from questionario order by id");
    
    ITERATE_SQL(stmt)
    
        Questionnaire * q = [[Questionnaire alloc] initWithText:[self stringAtColumn:1 ofStatement:stmt] andId:[self intAtColumn:0 ofStatement:stmt]];
        
        [quest addObject:q];
    
    END_SQL(stmt)
    
    unsigned nQuestionnaires = [quest count];
    
    for(unsigned i = 0; i < nQuestionnaires; i++) {
        
        Questionnaire * qAux = [quest objectAtIndex:i];
        
        NSArray * questions = [self requestQuestionsForQuestionnaireWithId:[qAux idQuestionnaire]];
        
        unsigned nQuestions = [questions count];
        
        for(unsigned j = 0; j < nQuestions; j++) {
            
            [qAux addQuestion:[questions objectAtIndex:j]];
        }
    }
    
    return quest;
}

- (NSArray *)requestQuestionsForQuestionnaireWithId:(unsigned)idQuestionnaire {
    
    NSMutableArray * questions = [[NSMutableArray alloc] init];
    
    Statement * stmt = SQL(@"select id, texto from pergunta where id_questionario = %d order by id", idQuestionnaire);
    
    ITERATE_SQL(stmt)
    
        Question * q = [[Question alloc] initWithText:[self stringAtColumn:1 ofStatement:stmt] andId:[self intAtColumn:0 ofStatement:stmt]];
        
        [questions addObject:q];
    
    END_SQL(stmt)
    
    unsigned nQuestions = [questions count];
    
    for(unsigned i = 0; i < nQuestions; i++) {
        
        Question * auxQ = [questions objectAtIndex:i];
        NSArray * opts = [self requestOptionsForQuestionWithId:[auxQ idQuestion]];
        
        unsigned nOptions = [opts count];
        
        for(unsigned j = 0; j < nOptions; j++) {
            
            [auxQ addOption:[opts objectAtIndex:j]];
        }
    }
    
    return questions;
}

- (NSArray *)requestOptionsForQuestionWithId:(unsigned)idQuestion {
    
    NSMutableArray * opts = [[NSMutableArray alloc] init];
    
    Statement * stmt = SQL(@"select id, texto from opcao where id_pergunta = %d order by id", idQuestion);
    
    ITERATE_SQL(stmt)
    
        Option * opt = [[Option alloc] initWithText:[self stringAtColumn:1 ofStatement:stmt] andId:[self intAtColumn:0 ofStatement:stmt]];
        
        [opts addObject:opt];
    
    END_SQL(stmt)
    
    return opts;
}

- (NSArray *)requestLinesWithNumberOrName:(NSString *)data {
    
    Statement * stmt;
    
    if(data == nil || [data isEqualToString:@""]) {
        
        stmt = SQL(@"select id, codigo, nome from linha order by id limit 100");
    }
    
    else {
        
        stmt = SQL(@"select id, codigo, nome from linha where codigo like \'%%%@%%\' or nome like \'%%%@%%\'", data, data);
    }
    
    NSMutableArray * arr = [[NSMutableArray alloc] init];
    
    ITERATE_SQL(stmt)
    
    Line * l = [[Line alloc] initWithId:[self intAtColumn:0 ofStatement:stmt]
                                andCode:[self stringAtColumn:1 ofStatement:stmt]
                                andText:[self stringAtColumn:2 ofStatement:stmt]];
    
    [arr addObject:l];
    
    END_SQL(stmt)
    
    return arr;
}

@end
