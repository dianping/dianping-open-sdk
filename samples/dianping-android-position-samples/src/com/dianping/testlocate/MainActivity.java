package com.dianping.testlocate;

import java.util.HashMap;

import com.dianping.locate.utils.DPLocation;
import com.dianping.locate.utils.DPLocationClient;
import com.dianping.locate.utils.DPLocationClientOption;
import com.dianping.locate.utils.DPLocationClientOption.Offset_type;
import com.dianping.locate.utils.DPLocationListener;
import com.dianping.locate.utils.DemoApiTool;
import com.hamber.testlocate.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
	
	public static final String TAG = MainActivity.class.getSimpleName();

    private Button mBtnLocate;
    
    private TextView mTvResult;
    
    private DPLocationClient locClient;
    
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);

	        mBtnLocate = (Button) findViewById(R.id.btn_locate);
	        mBtnLocate.setOnClickListener(this);

	        mTvResult = (TextView) findViewById(R.id.tv_result);
	        
	        locClient = new DPLocationClient(this);
	        MyLocationListener listener = new MyLocationListener();
	        locClient.registerLocationListener(listener);
	        
//	        mTvResult.setText(testSign());
	    }

	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        locClient.cancleLocation();
	    }

	    @Override
	    public void onClick(View v) {
	        switch (v.getId()) {
	            case R.id.btn_locate:
	            	if (locClient != null)
	            	{
	            		mTvResult.setText("");
	            		DPLocationClientOption clientOption = new DPLocationClientOption();
	            		clientOption.setOffset_type(Offset_type.GAODE);
	            		locClient.setClientOption(clientOption);
	            		locClient.requestLocation();
	            	}
	            	else
	            	{
	            		Log.d("DPLocSDK", "locClient is null or not started");
	            	}
	                break;

	            default:
	                break;
	        }
	    }
	    
	    public class MyLocationListener implements DPLocationListener {
	    	@Override
	    	public void onReceiveLocation(DPLocation location) {
	    		if (location == null)
	    	          return ;
	    	      StringBuffer sb = new StringBuffer(256);
	    	      sb.append("status:").append(location.getStatus());
	    	      sb.append("\n city:").append(location.getCity());
	    	      sb.append("\n latitude:").append(location.getLatitude());
	    	      sb.append("\n longitude:").append(location.getLongitude());
	    	      mTvResult.setText(sb.toString());
	    	}
	    }
}
