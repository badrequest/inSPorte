//
//  Answer.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "Answer.h"

@interface Answer()

@property (strong, nonatomic) NSMutableDictionary * info;
@property (nonatomic) unsigned line;
@property (nonatomic) double lat;
@property (nonatomic) double lon;
@property (strong, nonatomic) NSDate * dt;

@end

@implementation Answer

- (id)initAnswerWithId:(unsigned)line andLat:(double)lat andLon:(double)lon {
    
    self = [super init];
    
    if(self != nil) {
    
        NSMutableDictionary * inf = [[NSMutableDictionary alloc] init];
        
        [inf setObject:[NSNumber numberWithUnsignedInt:line] forKey:@"id"];
        [inf setObject:[NSNumber numberWithDouble:lat] forKey:@"lat"];
        [inf setObject:[NSNumber numberWithDouble:lon] forKey:@"lon"];
        
        NSDateFormatter * df = [[NSDateFormatter alloc] init];
        NSTimeZone *timeZone = [NSTimeZone timeZoneWithName:@"UTC"];
        
        [df setTimeZone:timeZone];
        [df setDateFormat:@"dd/MM/yyyy HH:mm:ss"];
        
        [inf setObject:[df stringFromDate:[NSDate date]] forKey:@"data"];
        
        _info = inf;
    }
    
    return self;
}

- (NSDictionary *)jsonData {
    
    NSMutableDictionary * jsonData = [[NSMutableDictionary alloc] init];
    
    [jsonData setObject:[Auth getAuthDict] forKey:@"auth"];
    [jsonData setObject:@"resposta" forKey:@"request"];
    [jsonData setObject:self.info forKey:@"info"];
    
    if(self.questionnaire != nil) {
        
        [jsonData setObject:[self.questionnaire jsonData] forKey:@"resposta"];
    }
    
    if(self.comment != nil) {
        
        [jsonData setObject:self.comment forKey:@"comentario"];
    }
    
    return jsonData;
}

@end
