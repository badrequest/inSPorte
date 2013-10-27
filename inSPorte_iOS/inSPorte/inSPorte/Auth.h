//
//  Auth.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Auth : NSObject

+ (BOOL)isAuthenticated;
+ (NSString *)getBU;
+ (NSString *)getCPF;
+ (void)setAuthBU:(NSString *)bu andCPF:(NSString *)cpf;
+ (NSDictionary *)getAuthDict;

@end
