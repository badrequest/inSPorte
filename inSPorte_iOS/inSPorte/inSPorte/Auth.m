//
//  Auth.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "Auth.h"

@implementation Auth

+ (BOOL)isAuthenticated {

    NSUserDefaults * defaults = [NSUserDefaults standardUserDefaults];
    
    if([defaults objectForKey:@"authenticated"] != nil) {
        
        return [(NSNumber *)[defaults objectForKey:@"authenticated"] boolValue];
    }
    
    else {
        
        [defaults setObject:[NSNumber numberWithBool:NO] forKey:@"authenticated"];
        return NO;
    }
    
}

+ (NSString *)getBU {
    
    if([Auth isAuthenticated]) {
        
        NSUserDefaults * defaults = [NSUserDefaults standardUserDefaults];
        
        return [defaults objectForKey:@"bu"];
    }
    
    else
        return nil;
}

+ (NSString *)getCPF {
    
    if([Auth isAuthenticated]) {
        
        NSUserDefaults * defaults = [NSUserDefaults standardUserDefaults];
        
        return [defaults objectForKey:@"cpf"];
    }
    
    else
        return nil;
}

+ (void)setAuthBU:(NSString *)bu andCPF:(NSString *)cpf {

    if(![Auth isAuthenticated]) {
        
        NSUserDefaults * defaults = [NSUserDefaults standardUserDefaults];
        
        [defaults setObject:bu forKey:@"bu"];
        [defaults setObject:cpf forKey:@"cpf"];
        [defaults setObject:[NSNumber numberWithBool:YES] forKey:@"authenticated"];
        [defaults synchronize];
    }
}

+ (NSDictionary *)getAuthDict {

    NSMutableDictionary * authDict = [[NSMutableDictionary alloc] init];
    
    if([Auth isAuthenticated]) {
        
        [authDict setObject:[Auth getBU] forKey:@"bu"];
        [authDict setObject:[Auth getCPF] forKey:@"id"];
    }
    
    return authDict;
}



@end
