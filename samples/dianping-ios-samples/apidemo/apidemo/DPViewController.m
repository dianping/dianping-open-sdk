//
//  DPViewController.m
//  apidemo
//
//  Created by ZhouHui on 13-1-28.
//  Copyright (c) 2013年 Dianping. All rights reserved.
//

#import "DPViewController.h"
#import "DPAppDelegate.h"

@interface DPViewController () <UIActionSheetDelegate, DPRequestDelegate>

@end

@implementation DPViewController {
	NSArray *urlArray;
	NSArray *paramsArray;
	
	NSInteger index;
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
		urlArray = [NSArray arrayWithObjects:@"v1/business/find_businesses"
					, @"v1/deal/find_deals"
					, @"v1/deal/get_single_deal"
					, @"v1/review/get_recent_reviews"
					, nil];
		
		paramsArray = [NSArray arrayWithObjects:@"city=北京&region=海淀区&category=火锅&has_coupon=1&sort=2&limit=20"
					   , @"city=北京&region=海淀区&category=火锅&sort=2&limit=20"
					   , @"deal_id=1-72628"
					   , @"business_id=5429278", nil];
		
		index = 0;
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
	self.keyInput.text = [[DPAppDelegate instance] appKey];
	self.secretInput.text = [[DPAppDelegate instance] appSecret];
	[self.urlButton setTitle:[urlArray objectAtIndex:index] forState:UIControlStateNormal];
	self.paramInput.text = [paramsArray objectAtIndex:index];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    [self setUrlButton:nil];
    [self setKeyInput:nil];
    [self setSecretInput:nil];
    [self setParamInput:nil];
	[self setResultTextView:nil];
    [super viewDidUnload];
}

- (IBAction)resignAction:(id)sender {
	[self.keyInput resignFirstResponder];
	[self.secretInput resignFirstResponder];
	[self.paramInput resignFirstResponder];
	[self.resultTextView resignFirstResponder];
}

- (IBAction)urlAction:(id)sender {
	UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:@"API名称" delegate:self cancelButtonTitle:nil destructiveButtonTitle:nil otherButtonTitles:nil, nil];
	for (NSString *url in urlArray) {
		[sheet addButtonWithTitle:url];
	}
	[sheet addButtonWithTitle:@"取消"];
	[sheet showInView:self.view];
}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
	if (buttonIndex>=urlArray.count) {
		return;
	}
	
	index = buttonIndex;
	[self.urlButton setTitle:[urlArray objectAtIndex:index] forState:UIControlStateNormal];
	self.paramInput.text = [paramsArray objectAtIndex:index];
}

- (IBAction)queryAction:(id)sender {
	if (self.keyInput.text.length<1 || self.secretInput.text.length<1) {
		return;
	}
	
	[[DPAppDelegate instance] setAppKey:self.keyInput.text];
	[[DPAppDelegate instance] setAppSecret:self.secretInput.text];
	
	NSString *url = [urlArray objectAtIndex:index];
	NSString *params = [paramsArray objectAtIndex:index];
	[[[DPAppDelegate instance] dpapi] requestWithURL:url paramsString:params delegate:self];
}

- (void)request:(DPRequest *)request didFailWithError:(NSError *)error {
	self.resultTextView.contentOffset = CGPointZero;
	self.resultTextView.text = [error description];
}

- (void)request:(DPRequest *)request didFinishLoadingWithResult:(id)result {
	self.resultTextView.contentOffset = CGPointZero;
	self.resultTextView.text = [result description];
}

@end
