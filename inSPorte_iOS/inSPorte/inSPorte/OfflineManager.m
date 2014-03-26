//
//  OfflineManager.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 3/25/14.
//  Copyright (c) 2014 Bad Request. All rights reserved.
//

#import "OfflineManager.h"

@implementation OfflineManager

+ (NSString *)pathToFileInDocuments:(NSString *)file {
    
    NSArray * paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory , NSUserDomainMask, YES);
    
    NSString * documentsDir = [paths objectAtIndex:0];
    
    return [documentsDir stringByAppendingPathComponent:file];
}

@end
