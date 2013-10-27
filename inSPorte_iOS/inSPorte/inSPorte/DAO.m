//
//  DAO.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "DAO.h"
#import "DataManager.h"
#include "Constants.h"

@interface DAO()

@property (nonatomic) sqlite3 * db;

@end

@implementation DAO

- (id)init {
    
    self = [super init];
    
    if(self) {
        
        self.db = [DataManager openDatabaseConnectionWithName:DB_NAME];
    }
    
    return self;
}

- (NSArray *)requestQuestionnaires {
    
    NSMutableArray * quest = [[NSMutableArray alloc] init];
    
    char * query = "select id, nome from questionario order by id";
    
    sqlite3_stmt * selectStatement;
    
    if(sqlite3_prepare_v2(self.db, query, -1, &selectStatement, NULL) == SQLITE_OK) {
        
        while(sqlite3_step(selectStatement) == SQLITE_ROW) {
            
            unsigned idQuestionnaire = sqlite3_column_int(selectStatement, 0);
            char * text = (char *)sqlite3_column_text(selectStatement, 1);
            
            Questionnaire * q = [[Questionnaire alloc] initWithText:[NSString stringWithUTF8String:text] andId:idQuestionnaire];
            
            [quest addObject:q];
        }
    }
    
    else {
        
        NSLog(@"Erro: \'%s\'", sqlite3_errmsg(self.db));
    }
    
    sqlite3_finalize(selectStatement);
    
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
    
    NSString * nsQuery = [NSString stringWithFormat:@"select id, texto from pergunta where id_questionario = %d order by id", idQuestionnaire];
    
    const char * query = [nsQuery UTF8String];
    
    sqlite3_stmt * selectStatement;
    
    if(sqlite3_prepare_v2(self.db, query, -1, &selectStatement, NULL) == SQLITE_OK) {
        
        while(sqlite3_step(selectStatement) == SQLITE_ROW) {
            
            unsigned idQuestion = sqlite3_column_int(selectStatement, 0);
            char * text = (char *)sqlite3_column_text(selectStatement, 1);
            
            Question * q = [[Question alloc] initWithText:[NSString stringWithUTF8String:text] andId:idQuestion];
            
            [questions addObject:q];
        }
    }
    
    else {
        
        NSLog(@"Erro: \'%s\'", sqlite3_errmsg(self.db));
    }
    
    unsigned nQuestions = [questions count];
    
    for(unsigned i = 0; i < nQuestions; i++) {
        
        Question * auxQ = [questions objectAtIndex:i];
        NSArray * opts = [self requestOptionsForQuestionWithId:[auxQ idQuestion]];
        
        unsigned nOptions = [opts count];
        
        for(unsigned j = 0; j < nOptions; j++) {
            
            [auxQ addOption:[opts objectAtIndex:j]];
        }
    }
    
    sqlite3_finalize(selectStatement);
    
    return questions;
}

- (NSArray *)requestOptionsForQuestionWithId:(unsigned)idQuestion {
    
    NSMutableArray * opts = [[NSMutableArray alloc] init];
    
    NSString * nsQuery = [NSString stringWithFormat:@"select id, texto from opcao where id_pergunta = %d order by id", idQuestion];
    
    const char * query = [nsQuery UTF8String];
    
    sqlite3_stmt * selectStatement;
    
    if(sqlite3_prepare_v2(self.db, query, -1, &selectStatement, NULL) == SQLITE_OK) {
        
        while(sqlite3_step(selectStatement) == SQLITE_ROW) {
            
            unsigned idOption = sqlite3_column_int(selectStatement, 0);
            char * text = (char *)sqlite3_column_text(selectStatement, 1);
            
            Option * opt = [[Option alloc] initWithText:[NSString stringWithUTF8String:text] andId:idOption];
            
            [opts addObject:opt];
        }
    }
    
    else {
        
        NSLog(@"Erro: \'%s\'", sqlite3_errmsg(self.db));
    }
    
    sqlite3_finalize(selectStatement);
    
    return opts;
}

- (NSArray *)requestLines {
    
    NSMutableArray * arr = [[NSMutableArray alloc] init];
    
    char * query = "select id, codigo, nome from linha order by id";
    
    sqlite3_stmt * selectStatement;
    
    if(sqlite3_prepare_v2(self.db, query, -1, &selectStatement, NULL) == SQLITE_OK) {
        
        while(sqlite3_step(selectStatement) == SQLITE_ROW) {
            
            unsigned idLine = sqlite3_column_int(selectStatement, 0);
            char * code = (char *)sqlite3_column_text(selectStatement, 1);
            NSString * codeString = [NSString stringWithUTF8String:code];
            
            char * text = (char *)sqlite3_column_text(selectStatement, 2);
            NSString * textString = [NSString stringWithUTF8String:text];
            
            Line * l = [[Line alloc] initWithId:idLine
                                        andCode:codeString
                                        andText:textString];
            
            [arr addObject:l];
        }
    }
    
    else {
        
        NSLog(@"Erro: \'%s\'", sqlite3_errmsg(self.db));
    }
    
    sqlite3_finalize(selectStatement);
    
    return arr;
}

- (void)dealloc {
    
    [DataManager closeDatabaseConnection:self.db];
}

@end
