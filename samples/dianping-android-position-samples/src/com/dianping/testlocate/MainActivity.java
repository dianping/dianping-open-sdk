package com.dianping.testlocate;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dianping.locate.geo.GeoFactory;
import com.dianping.locate.geo.GeoListener;
import com.dianping.locate.geo.GeoService;
import com.dianping.testlocate.utils.*;
import com.hamber.testlocate.R;

public class MainActivity extends Activity implements View.OnClickListener, GeoListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Button mBtnLocate;
    private TextView mTvResult;

    /** 获取位置参数信息的接口服务 */
    private GeoService mGeoService;

    private Handler mMainHandler;
    private HandlerThread mHandlerThread;
    private Handler mWorkHandler;

    private Map<String, String> mParams = new HashMap<String, String>();
    private String mGeoInfo = "";

    private static final int MSG_DP_LOCATE = 1001;
    private static final int MSG_UPDATE_UI = 2001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 下面两部需要在带有Looper的线程中执行
        // 创建接口实例
        mGeoService = GeoFactory.createGeoService(this);
        // 添加监听器，用于异步获取位置参数信息
        mGeoService.addListener(this);

        mBtnLocate = (Button) findViewById(R.id.btn_locate);
        mBtnLocate.setOnClickListener(this);

        mTvResult = (TextView) findViewById(R.id.tv_result);

        mMainHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_UPDATE_UI:
                        updateUI();
                        break;

                    default:
                        break;
                }
            }
        };

        mHandlerThread = new HandlerThread("work");
        mHandlerThread.start();
        mWorkHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_DP_LOCATE:
                        dpLocate();
                        mMainHandler.sendEmptyMessage(MSG_UPDATE_UI);
                        break;

                    default:
                        break;
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 取消请求
        mGeoService.cancelRequest();
        // 移除监听器
        mGeoService.removeListener(this);

        mHandlerThread.quit();
        mMainHandler.removeMessages(MSG_UPDATE_UI);
        mWorkHandler.removeMessages(MSG_DP_LOCATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_locate:
                locate();
                break;

            default:
                break;
        }
    }

    private void locate() {
        mTvResult.setText("");

        // 请求位置参数信息，异步
        mGeoService.requestGeoParams();
    }

    private void dpLocate() {
        // TODO 这里写http请求的逻辑
        // 使用mParams获取点评定位
        // 根据点评定位结果拼装mGeoInfo
    	String apiUrl = "http://api.dianping.com/v1/position/get_coordinate";
    	//填写开发者自己的appKey 和 secret
        String appKey = "7******6";
        String secret = "a**********************8";

        Map<String, String> paramMap = new HashMap<String, String>();
        String gsmInfo = mParams.get("gsmInfo");
        String cdmaInfo = mParams.get("cdmaInfo");
        String wifiInfo = mParams.get("wifiInfo");
        String coordGps = mParams.get("coordGps");
        String coordNetwork = mParams.get("coordNetwork");
        if(!"".equals(gsmInfo) && gsmInfo != null)
        {
        	paramMap.put("gsm_info",gsmInfo);
        }
        if(!"".equals(cdmaInfo) && cdmaInfo != null)
        {
        	paramMap.put("cdma_info",cdmaInfo);
        }
        if(!"".equals(wifiInfo) && wifiInfo != null)
        {
        	paramMap.put("wifi_info",wifiInfo);
        }
        if(!"".equals(coordGps) && coordGps != null)
        {
        	paramMap.put("gps_info", coordGps);
        }
        if(!"".equals(coordNetwork) && coordNetwork != null)
        {
        	paramMap.put("network_info",coordNetwork);
        }
        paramMap.put("offset_type","1");
        paramMap.put("format", "json");
        this.mGeoInfo = DemoApiTool.requestApi(apiUrl, appKey, secret, paramMap);
    }

    private void updateUI() {
        // TODO 这里可以补充更新UI的逻辑
        mTvResult.setText(mGeoInfo);
    }

    @Override
    public void onRequestGeoParamsFinish(Map<String, String> params) {
        // 从该Map中能获取5个信息
        // gsmInfo：GSM基站信息，如果有
        // cdmaInfo：CDMA基站信息，如果有
        // wifiInfo：WIFI信息，需要打开WIFI
        // coordGps：系统GPS定位信息
        // coordNetwork：系统Network定位信息
        String gsmInfo = params.get("gsmInfo");
        String cdmaInfo = params.get("cdmaInfo");
        String wifiInfo = params.get("wifiInfo");
        String coordGps = params.get("coordGps");
        String coordNetwork = params.get("coordNetwork");

        StringBuilder builder = new StringBuilder();
        builder.append("gsmInfo: " + gsmInfo + "\n");
        builder.append("cdmaInfo: " + cdmaInfo + "\n");
        builder.append("wifiInfo: " + wifiInfo + "\n");
        builder.append("coordGps: " + coordGps + "\n");
        builder.append("coordNetwork: " + coordNetwork + "\n");

        mTvResult.setText(builder.toString());

        mParams.clear();
        mParams.putAll(params);

        mWorkHandler.sendEmptyMessage(MSG_DP_LOCATE);
    }
}