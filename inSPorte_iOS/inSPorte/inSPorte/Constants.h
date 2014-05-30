//
//  Constants.h
//  inSPorte
//
//  Created by Fabio Dela Antonio on 10/26/13.
//  Copyright (c) 2013 Bad Request. All rights reserved.
//

#ifndef inSPorte_Constants_h
#define inSPorte_Constants_h

#define SYSTEM_VERSION \
[[[[UIDevice currentDevice].systemVersion componentsSeparatedByString:@"."] objectAtIndex:0] intValue]

#define ERROR_ALERT(X) \
[[[UIAlertView alloc] initWithTitle:@"Erro!" \
message:X \
delegate:nil \
cancelButtonTitle:@"OK" \
otherButtonTitles:nil] show]

#define ALERT(X, Y)  \
[[[UIAlertView alloc] initWithTitle:X \
message:Y \
delegate:nil \
cancelButtonTitle:@"OK" \
otherButtonTitles:nil] show]

#define EXCLUSIVE_TOUCH_BUTTONS(X) \
for (UIView * button in [X subviews]) {\
if([button isKindOfClass:[UIButton class]])\
[((UIButton *)button) setExclusiveTouch:YES];\
}

#define GPS_TIMEOUT 15.0
#define MAX_GPS_TRIES 5
#define MAX_GPS_ACCURACY 200.0

#define DB_NAME @"local.db"

#define WEB_SERVICE_SERVER @"http://insporte.com.br/rest"

#endif
