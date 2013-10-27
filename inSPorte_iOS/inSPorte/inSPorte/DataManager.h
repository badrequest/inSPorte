//
//  DataManager.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <sqlite3.h>

@interface DataManager : NSObject

+ (NSString *)getDatabasePathWithName:(NSString *)name;
+ (sqlite3 *)openDatabaseConnectionWithName:(NSString *)name;
+ (void)closeDatabaseConnection:(sqlite3 *)db;

@end
