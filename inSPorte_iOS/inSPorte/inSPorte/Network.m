//
//  Network.m
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import "Network.h"
#import "MKNetworkKit.h"

@interface Network()

@property (strong, nonatomic) NSString * serverURL;
@property (strong, nonatomic) MKNetworkEngine * eng;

@end

@implementation Network

- (id)initWithURL:(NSString *)serverURL {
    
    self = [super init];
    
    if(self) {
        
        _serverURL = [NSString stringWithString:serverURL];
        _eng = [[MKNetworkEngine alloc] initWithHostName:serverURL
                                      customHeaderFields:nil];
        
        if(_eng == nil)
            return nil;
    }
    
    return self;
}

- (void)cancelRequests {
    
    if(self.eng)
        [self.eng cancelAllOperations];
}

- (void)requestAuthWithBU:(NSString *)bu andCPF:(NSString *)cpf andWithTarget:(id)target andResponse:(SEL)action andError:(SEL)errAction {
    
    if([bu isEqualToString:@""] || [cpf isEqualToString:@""]) {
        
        [target performSelector:errAction withObject:[[NSError alloc] init]];
        return;
    }
    
    NSMutableDictionary * request = [[NSMutableDictionary alloc] init];
    
    [request setObject:@"auth" forKey:@"request"];
    
    NSMutableDictionary * auth = [[NSMutableDictionary alloc] init];
    
    [auth setObject:bu forKey:@"bu"];
    [auth setObject:cpf forKey:@"id"];
    
    [request setObject:auth forKey:@"auth"];
    
    NSLog(@"JSON: %@", [[NSString alloc] initWithData:[NSJSONSerialization dataWithJSONObject:request options:NSJSONWritingPrettyPrinted error:nil] encoding:NSUTF8StringEncoding]);
    
    MKNetworkOperation * netOp = [self.eng operationWithURLString:[NSString stringWithFormat:@"%@/auth", self.serverURL]
                                                           params:request
                                                       httpMethod:@"POST"];
    netOp.postDataEncoding = MKNKPostDataEncodingTypeJSON;
    
    [netOp addCompletionHandler:^(MKNetworkOperation *operation){
        [target performSelectorOnMainThread:action withObject:operation.responseJSON
                              waitUntilDone:NO];
    }
     
    errorHandler:^(MKNetworkOperation *errorOp, NSError* error){
        [target performSelectorOnMainThread:errAction withObject:error waitUntilDone:NO];
    }];
    
    [self.eng enqueueOperation:netOp];
}

- (void)requestSendAnswer:(Answer *)ans andWithTarget:(id)target andResponse:(SEL)action andError:(SEL)errAction {
    
    if(![Auth isAuthenticated]) {
    
        [target performSelectorOnMainThread:errAction withObject:nil waitUntilDone:NO];
        
        return;
    }

    NSDictionary * request = [ans jsonData];
    
    NSLog(@"JSON: %@", [[NSString alloc] initWithData:[NSJSONSerialization dataWithJSONObject:request options:NSJSONWritingPrettyPrinted error:nil] encoding:NSUTF8StringEncoding]);
    
    MKNetworkOperation * netOp = [self.eng operationWithURLString:[NSString stringWithFormat:@"%@/add", self.serverURL]
                                                           params:request
                                                       httpMethod:@"POST"];
    netOp.postDataEncoding = MKNKPostDataEncodingTypeJSON;
    
    [netOp addCompletionHandler:^(MKNetworkOperation *operation){
        [target performSelectorOnMainThread:action withObject:operation.responseJSON
                              waitUntilDone:NO];
    }
     
    errorHandler:^(MKNetworkOperation *errorOp, NSError* error){
        [target performSelectorOnMainThread:errAction withObject:error waitUntilDone:NO];
    }];
    
    [self.eng enqueueOperation:netOp];
}

- (void)requestGetLikeWithId:(unsigned)idLine andWithTarget:(id)target andResponse:(SEL)action andError:(SEL)errAction {
    
    if(![Auth isAuthenticated]) {
        
        [target performSelectorOnMainThread:errAction withObject:nil waitUntilDone:NO];
        
        return;
    }
    
    NSMutableDictionary * request = [[NSMutableDictionary alloc] init];
    
    [request setObject:[Auth getAuthDict] forKey:@"auth"];
    [request setObject:@"get-like" forKey:@"request"];
    [request setObject:[NSNumber numberWithUnsignedInt:idLine] forKey:@"id"];
    
    NSLog(@"JSON: %@", [[NSString alloc] initWithData:[NSJSONSerialization dataWithJSONObject:request options:NSJSONWritingPrettyPrinted error:nil] encoding:NSUTF8StringEncoding]);
    
    MKNetworkOperation * netOp = [self.eng operationWithURLString:[NSString stringWithFormat:@"%@/get-like", self.serverURL]
                                                           params:request
                                                       httpMethod:@"POST"];
    netOp.postDataEncoding = MKNKPostDataEncodingTypeJSON;
    
    [netOp addCompletionHandler:^(MKNetworkOperation *operation){
        [target performSelectorOnMainThread:action withObject:operation.responseJSON
                              waitUntilDone:NO];
    }
     
    errorHandler:^(MKNetworkOperation *errorOp, NSError* error){
        [target performSelectorOnMainThread:errAction withObject:error waitUntilDone:NO];
    }];
    
    [self.eng enqueueOperation:netOp];
}

- (void)requestSetLikeWithId:(unsigned)idLine andLike:(BOOL)like andWithTarget:(id)target andResponse:(SEL)action andError:(SEL)errAction {
    
    if(![Auth isAuthenticated]) {
        
        [target performSelectorOnMainThread:errAction withObject:nil waitUntilDone:NO];
        
        return;
    }
    
    NSMutableDictionary * request = [[NSMutableDictionary alloc] init];
    
    [request setObject:[Auth getAuthDict] forKey:@"auth"];
    [request setObject:@"set-like" forKey:@"request"];
    
    NSMutableDictionary * likeDict = [[NSMutableDictionary alloc] init];
    
    [likeDict setObject:[NSNumber numberWithBool:like] forKey:@"status"];
    [likeDict setObject:[NSNumber numberWithUnsignedInt:idLine] forKey:@"id"];
    
    [request setObject:likeDict forKey:@"like"];
    
    NSLog(@"JSON: %@", [[NSString alloc] initWithData:[NSJSONSerialization dataWithJSONObject:request options:NSJSONWritingPrettyPrinted error:nil] encoding:NSUTF8StringEncoding]);
    
    MKNetworkOperation * netOp = [self.eng operationWithURLString:[NSString stringWithFormat:@"%@/set-like", self.serverURL]
                                                           params:request
                                                       httpMethod:@"POST"];
    netOp.postDataEncoding = MKNKPostDataEncodingTypeJSON;
    
    [netOp addCompletionHandler:^(MKNetworkOperation *operation){
        [target performSelectorOnMainThread:action withObject:operation.responseJSON
                              waitUntilDone:NO];
    }

    errorHandler:^(MKNetworkOperation *errorOp, NSError* error){
        [target performSelectorOnMainThread:errAction withObject:error waitUntilDone:NO];
    }];
    
    [self.eng enqueueOperation:netOp];
}

@end
