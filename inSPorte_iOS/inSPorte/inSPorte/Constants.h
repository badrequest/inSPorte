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

#define GPS_TIMEOUT 15.0
#define MAX_GPS_TRIES 5
#define MAX_GPS_ACCURACY 200.0

#define DB_NAME @"local.db"

#warning TROCAR ESTA URL COM A URL DO SEU SERVIDOR!
#define WEB_SERVICE_SERVER @"http://172.20.10.7/insporte-rest"

#endif