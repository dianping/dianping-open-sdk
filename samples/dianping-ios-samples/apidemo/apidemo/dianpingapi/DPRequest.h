//
//  DPRequest.h
//  apidemo
//
//  Created by ZhouHui on 13-1-28.
//  Copyright (c) 2013年 Dianping. All rights reserved.
//

#import <Foundation/Foundation.h>

@class DPAPI;
@protocol DPRequestDelegate;

@interface DPRequest : NSObject

@property (nonatomic, unsafe_unretained) DPAPI *dpapi;
@property (nonatomic, strong) NSString *url;
@property (nonatomic, strong) NSDictionary *params;
@property (nonatomic, unsafe_unretained) id<DPRequestDelegate> delegate;

+ (DPRequest *)requestWithURL:(NSString *)url
					   params:(NSDictionary *)params
					 delegate:(id<DPRequestDelegate>)delegate;

+ (NSString *)getParamValueFromUrl:(NSString*)url paramName:(NSString *)paramName;

+ (NSString *)serializeURL:(NSString *)baseURL params:(NSDictionary *)params;

- (void)connect;

- (void)disconnect;

@end


@protocol DPRequestDelegate <NSObject>
@optional
- (void)request:(DPRequest *)request didReceiveResponse:(NSURLResponse *)response;
- (void)request:(DPRequest *)request didReceiveRawData:(NSData *)data;
- (void)request:(DPRequest *)request didFailWithError:(NSError *)error;
- (void)request:(DPRequest *)request didFinishLoadingWithResult:(id)result;
@end
