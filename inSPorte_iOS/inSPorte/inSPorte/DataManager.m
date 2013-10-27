//
//  DataManager.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "DataManager.h"

@implementation DataManager

+ (NSString *)getDatabasePathWithName:(NSString *)name {
    
    NSError *error;
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory , NSUserDomainMask, YES);
    NSString *documentsDir = [paths objectAtIndex:0];
    NSString *databaseFileDir = [documentsDir stringByAppendingPathComponent:name];
    NSFileManager *fileManager = [NSFileManager defaultManager];
    BOOL success = [fileManager fileExistsAtPath:databaseFileDir];
    
    if (!success) {
        
        NSLog(@"BD Transferido");
        NSString *defaultDBPath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:name];
        NSLog(@"Database file resource dir: %@", defaultDBPath);
        
        success = [fileManager fileExistsAtPath:defaultDBPath];
        
        if (success) {
            success = [fileManager copyItemAtPath:defaultDBPath toPath:databaseFileDir error:&error];
            
            if (!success) NSAssert1(0, @"Failed to create writable database file with message '%@'.", [error localizedDescription]);
            
        }
    }
    
    return databaseFileDir;
}

+ (sqlite3 *)openDatabaseConnectionWithName:(NSString *)name {
    
    sqlite3 *database;
    NSString * path = [self getDatabasePathWithName:name];
    
    if (sqlite3_open([path UTF8String], &database) != SQLITE_OK) {
        sqlite3_close(database);
        NSAssert1(0, @"Failed to open database with message '%s'.", sqlite3_errmsg(database));
    }
    return database;
}

+ (void)closeDatabaseConnection:(sqlite3 *)db {
    
    if(db != NULL) {
        sqlite3_close(db);
    }
}

@end
