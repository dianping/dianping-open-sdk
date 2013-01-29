//
//  DPViewController.h
//  apidemo
//
//  Created by ZhouHui on 13-1-28.
//  Copyright (c) 2013年 Dianping. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface DPViewController : UIViewController
@property (unsafe_unretained, nonatomic) IBOutlet UITextField *keyInput;
@property (unsafe_unretained, nonatomic) IBOutlet UITextField *secretInput;
@property (unsafe_unretained, nonatomic) IBOutlet UIButton *urlButton;
@property (unsafe_unretained, nonatomic) IBOutlet UITextField *paramInput;
@property (unsafe_unretained, nonatomic) IBOutlet UITextView *resultTextView;

- (IBAction)resignAction:(id)sender;
- (IBAction)urlAction:(id)sender;
- (IBAction)queryAction:(id)sender;

@end
