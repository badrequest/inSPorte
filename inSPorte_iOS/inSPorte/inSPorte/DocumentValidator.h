//
//  DocumentValidator.h
//  inSPorte
//
//  Created by Danilo Carolino on 28/05/14.
//  Copyright (c) 2014 Bad Request. All rights reserved.
//

#import <Foundation/Foundation.h>

@class DocumentValidator;

static DocumentValidator * instance;

@interface DocumentValidator : NSObject
{
    NSMutableDictionary * keys;
}

// Document types.
typedef enum
{
    DocumentTypeCPF = 11, // Fixed length, stored here.
    DocumentTypeTransportTicket
} DocumentType;

// Maximum and minimum length of a ticket value.
typedef enum
{
    DocumentTypeTransportTicketMaxLength = 12,
    DocumentTypeTransportTicketMinLength = 6
} DocumentTypeTransportTicketLimits;

@property (nonatomic, retain) NSMutableDictionary * keys;

+ (DocumentValidator *) shared;
- (id) init;
- (BOOL) verifyIfDocumentValueIsValid: (NSString *) value andType: (NSInteger) type;
- (BOOL) verifyCPF: (NSString *) value;
- (BOOL) verifyTransportTicket: (NSString *) value;

@end
