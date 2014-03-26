//
//  DataManager.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "DataManager.h"
#import "OfflineManager.h"

#include "Constants.h"

@interface DataManager()

@property (nonatomic) sqlite3 * db;

@end

@implementation DataManager

+ (NSString *)dataBasePath {
    
    NSString *documentsDirectory = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0];
    
    return [documentsDirectory stringByAppendingPathComponent:DB_NAME];
}

+ (BOOL)dataBaseExists {
    
    return [[NSFileManager defaultManager] fileExistsAtPath:[DataManager dataBasePath]];
}

+ (void)prepareDB {
    
    if(![DataManager dataBaseExists]) {
        
        NSString * destination = [OfflineManager pathToFileInDocuments:DB_NAME];
        
        NSString * path = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:DB_NAME];
        
        NSError * err = nil;
        
        if(![[NSFileManager defaultManager] copyItemAtPath:path toPath:destination error:&err]) {
            
            NSLog(@"Não moveu a base de dados!");
        }
    }
}

+ (void)eraseDB {
    
    if([DataManager dataBaseExists]) {
        
        [[NSFileManager defaultManager] removeItemAtPath:[DataManager dataBasePath] error:nil];
    }
}

+ (Database)openDatabaseConnectionWithPath:(NSString *)path {
    
    sqlite3 * database;
    
    if(sqlite3_open([path UTF8String], &database) != SQLITE_OK) {
        
        sqlite3_close(database);
        
        NSLog(@"Não abriu base de dados!");
        
        return NULL;
    }
    
    return database;
}

+ (void)closeDatabaseConnection:(sqlite3 *)db {
    
    if(db != NULL)
        sqlite3_close(db);
}

- (id)init {
    
    self = [super init];
    
    if(self != nil) {
        
        [DataManager prepareDB];
        
        _db = [DataManager openDatabaseConnectionWithPath:[DataManager dataBasePath]];
    }
    
    return self;
}

- (Database)database {
    
    return self.db;
}

- (void)dealloc {
    
    [DataManager closeDatabaseConnection:_db];
}

- (Statement *)prepareQuery:(NSString *)q, ... {
    
    va_list args;
    va_start(args, q);
    
    NSString * query = [[NSString alloc] initWithFormat:q arguments:args];
    
    va_end(args);
    
    Statement * ret;
    
    if (self.db != NULL && (ret = (Statement *)malloc(sizeof(Statement))) != NULL) {
        
        if (sqlite3_prepare_v2(self.db, [query UTF8String], -1, ret, NULL) == SQLITE_OK) {
            
            return ret;
        }
        
        else {
            
            free(ret);
            return NULL;
        }
        
    } else
        return NULL;
    
    return ret;
}

- (BOOL)hasNextStepWithStatement:(Statement *)stmt {
    
    return (stmt != NULL) ? (sqlite3_step(*stmt) == SQLITE_ROW):NO;
}

- (int)intAtColumn:(unsigned)idx ofStatement:(Statement *)stmt {
    
    if(stmt != NULL && *stmt != NULL)
        return sqlite3_column_int(*stmt, idx);
    
    return 0;
}

- (NSString *)stringAtColumn:(unsigned)idx ofStatement:(Statement *)stmt {
    
    if(stmt == NULL || *stmt == NULL)
        return @"";
    
    const char * ret = (const char *)sqlite3_column_text(*stmt, idx);
    
    if(ret != NULL)
        return [NSString stringWithUTF8String:ret];
    
    return @"";
}

- (void)finalizeQueryWithStatement:(Statement *)stmt {
    
    if(stmt != NULL) {
        
        sqlite3_finalize(*stmt);
        free(stmt);
    }
}

- (BOOL)tryToPerformInsertStatement:(NSString *)i, ... {
    
    Statement * stmt;
    
    va_list args;
    va_start(args, i);
    
    NSString * insert = [[NSString alloc] initWithFormat:i arguments:args];
    
    va_end(args);
    
    if (self.db != NULL && (stmt = (Statement *)malloc(sizeof(Statement))) != NULL) {
        
        if(sqlite3_prepare_v2(self.db, [insert UTF8String], -1, stmt, NULL) == SQLITE_OK) {
            
            if(sqlite3_step(*stmt) != SQLITE_DONE) {
                
                [self finalizeQueryWithStatement:stmt];
                return NO;
            }
            
            else {
                
                [self finalizeQueryWithStatement:stmt];
                return YES;
            }
        }
        
        [self finalizeQueryWithStatement:stmt];
    }
    
    return NO;
}

@end
