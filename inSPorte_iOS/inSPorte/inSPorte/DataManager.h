//
//  DataManager.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <sqlite3.h>

typedef sqlite3_stmt * Statement;
typedef sqlite3 * Database;

@interface DataManager : NSObject

+ (BOOL)dataBaseExists;
+ (void)prepareDB;
+ (void)eraseDB;

- (id)init;
- (Database)database;
- (void)dealloc;

- (Statement *)prepareQuery:(NSString *)q, ...;
- (BOOL)hasNextStepWithStatement:(Statement *)stmt;
- (int)intAtColumn:(unsigned)idx ofStatement:(Statement *)stmt;
- (NSString *)stringAtColumn:(unsigned)idx ofStatement:(Statement *)stmt;
- (void)finalizeQueryWithStatement:(Statement *)stmt;
- (BOOL)tryToPerformInsertStatement:(NSString *)i, ...;

@end

