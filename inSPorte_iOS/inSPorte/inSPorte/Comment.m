//
//  Comment.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "Comment.h"

@implementation Comment

- (id)initWithComment:(NSString *)text andImage:(id)img {
    
    self = [super init];
    
    if(self != nil) {
    
        _text = text;
        _img = img;
    }
    
    return self;
}

- (NSDictionary *)jsonRepresentation {
    
    NSMutableDictionary * dict = [[NSMutableDictionary alloc] init];
    
    
    [dict setObject:self.text forKey:@"texto"];
    
    if(self.img != nil) {
        
#warning TODO adicionar imagem
    }
    
    return dict;
}

@end
