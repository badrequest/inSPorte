//
//  NSString+base64.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSString (base64)

+ (NSString *)base64StringFromData:(NSData *)data length:(int)length;

@end
