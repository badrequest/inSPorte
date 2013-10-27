//
//  Network.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Answer.h"

@interface Network : NSObject

- (id)initWithURL:(NSString *)serverURL;
- (void)cancelRequests;
- (void)requestAuthWithBU:(NSString *)bu andCPF:(NSString *)cpf andWithTarget:(id)target andResponse:(SEL)action andError:(SEL)errAction;
- (void)requestSendAnswer:(Answer *)ans andWithTarget:(id)target andResponse:(SEL)action andError:(SEL)errAction;
- (void)requestGetLikeWithId:(unsigned)idLine andWithTarget:(id)target andResponse:(SEL)action andError:(SEL)errAction;
- (void)requestSetLikeWithId:(unsigned)idLine andLike:(BOOL)like andWithTarget:(id)target andResponse:(SEL)action andError:(SEL)errAction;

@end
