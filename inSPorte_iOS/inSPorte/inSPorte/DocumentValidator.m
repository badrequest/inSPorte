//
//  DocumentValidator.m
//  inSPorte
//
//  Created by Danilo Carolino on 28/05/14.
//  Copyright (c) 2014 Bad Request. All rights reserved.
//

#import "DocumentValidator.h"

@implementation DocumentValidator

@synthesize keys;

// Class inicialization.
- (id) init
{
    if (self == [super init])
    {
        self.keys = [[NSMutableDictionary alloc] init];
    }
    return self;
}

// Get a unique instance of this class.
+ (DocumentValidator *) shared
{
    @synchronized(instance) {
        if (!instance || instance == NULL)
        {
            // Allocate the shared instance, because it hasn't been done yet.
            instance = [[DocumentValidator alloc] init];
        }
        
        return instance;
    }
}

// Verify if this document is valid using value and document type.
- (BOOL) verifyIfDocumentValueIsValid: (NSString *) value andType: (NSInteger) type
{
    BOOL response = FALSE;
    
    if (value == nil || [value length] == 0)
    {
        return FALSE;
    }
    
    switch (type)
    {
        case DocumentTypeCPF:
            response = [self verifyCPF:value];
            break;
        case DocumentTypeTransportTicket:            
            response = [self verifyTransportTicket:value];
            break;
    }
    
    return response;
}

// Verify if CPF document is valid.
- (BOOL) verifyCPF: (NSString *) value
{
    if ([value length] < DocumentTypeCPF)
    {
        return FALSE;
    }
    return TRUE;
}

// Verify if Transport Ticket document is valid.
- (BOOL) verifyTransportTicket: (NSString *) value
{
    if (([value length] > (DocumentTypeTransportTicketMinLength - 1))
        && ([value length] < (DocumentTypeTransportTicketMaxLength + 1)))
    {
        return TRUE;
    }
    
    return FALSE;
}

@end