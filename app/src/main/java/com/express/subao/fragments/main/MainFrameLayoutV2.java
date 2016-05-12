package com.express.subao.fragments.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.express.subao.R;
import com.express.subao.activitys.BoxExpressListActivity;
import com.express.subao.activitys.OrdinaryWebActivity;
import com.express.subao.activitys.QueryExpressActivity;
import com.express.subao.activitys.TopUpActivity;
import com.express.subao.activitys.WebActivity;
import com.express.subao.adaptera.CategoryAdapter;
import com.express.subao.adaptera.HelpAdapter;
import com.express.subao.box.CategoryObj;
import com.express.subao.box.HelpObj;
import com.express.subao.box.SliderObj;
import com.express.subao.box.handlers.AreaObjHandler;
import com.express.subao.box.handlers.CategoryObjHandler;
import com.express.subao.box.handlers.HelpObjHandler;
import com.express.subao.box.handlers.SliderObjHandler;
import com.express.subao.fragments.BaseFragment;
import com.express.subao.handlers.JsonHandle;
import com.express.subao.handlers.MessageHandler;
import com.express.subao.http.HttpUtilsBox;
import com.express.subao.http.Url;
import com.express.subao.tool.Passageway;
import com.express.subao.tool.WinTool;
import com.express.subao.views.InsideGridView;
import com.express.subao.views.InsideViewFlipper;
import com.express.subao.views.SliderView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * *
 * * ┏┓      ┏┓
 * *┏┛┻━━━━━━┛┻┓
 * *┃          ┃
 * *┃          ┃
 * *┃ ┳┛   ┗┳  ┃
 * *┃          ┃
 * *┃    ┻     ┃
 * *┃          ┃
 * *┗━┓      ┏━┛
 * *  ┃      ┃
 * *  ┃      ┃
 * *  ┃      ┗━━━┓
 * *  ┃          ┣┓
 * *  ┃         ┏┛
 * *  ┗┓┓┏━━━┳┓┏┛
 * *   ┃┫┫   ┃┫┫
 * *   ┗┻┛   ┗┻┛
 * Created by Hua on 15/12/21.
 */
public class MainFrameLayoutV2 extends BaseFragment {

    @ViewInject(R.id.main_progress)
    private ProgressBar progress;
    @ViewInject(R.id.ppt_images)
    private InsideViewFlipper mViewFlipper;
    @ViewInject(R.id.ppt_ball)
    private LinearLayout pptBallBox;
    @ViewInject(R.id.main_expressBox)
    private ImageView expressBox;
    @ViewInject(R.id.main_expressCheck)
    private ImageView expressCheck;
    @ViewInject(R.id.main_tellFriend)
    private ImageView tellFriend;
    @ViewInject(R.id.main_callMe)
    private ImageView callMe;
    @ViewInject(R.id.main_getExpress)
    private ImageView getExpress;
    @ViewInject(R.id.main_boxAddress)
    private ImageView boxAddress;

    private SliderView mSliderView;

    @Override
    public void onRestart() {
        pptBallBox.removeAllViews();
        mViewFlipper.removeAllViews();
        if (mSliderView != null) {
            mSliderView.stopFlish();
        }
        downloadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View contactsLayout = inflater.inflate(R.layout.layout_main_v2, container,
                false);
        ViewUtils.inject(this, contactsLayout);

        initImageView();
        downloadData();

        return contactsLayout;
    }

    private void initImageView() {
        double w = WinTool.getWinWidth(context);

        double eBW = w * 258 / 640;
        double eBH = eBW / 258 * 230;
        setImageParams(expressCheck, eBW, eBH);

        double eCW = w * 382 / 640;
        double eCH = eCW / 382 * 230;
        setImageParams(expressBox, eCW, eCH);

        double w2 = w / 2;

        double tFH = w2 / 320 * 177;
        setImageParams(tellFriend, w2, tFH);

        double cMH = w2 / 320 * 132;
        setImageParams(callMe, w2, cMH);

        double gEH = w2 / 320 * 133;
        setImageParams(getExpress, w2, gEH);

        double bAH = w2 / 320 * 176;
        setImageParams(boxAddress, w2, bAH);

    }

    private void setImageParams(ImageView view, double w, double h) {
        view.setLayoutParams(new LinearLayout.LayoutParams((int) w, (int) h));
    }

    @OnClick({R.id.main_expressBox, R.id.main_expressCheck})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_expressBox:
                Passageway.jumpActivity(context, BoxExpressListActivity.class);
                break;
            case R.id.main_expressCheck:
//                Passageway.jumpActivity(context, QueryExpressActivity.class);
                Bundle b = new Bundle();
//                b.putString(WebActivity.URL, "https://m.baidu.com/from=844b/s?word=%E5%BF%AB%E9%80%92%E5%8D%95%E5%8F%B7%E6%9F%A5%E8%AF%A2&ts=9273863&t_kt=0&ie=utf-8&rsv_iqid=15097551060046916401&rsv_t=72deMgPPNkm183EeiLmi7c4tOYHp0VHnafndB7E%252BxsATdrxKgzYXkgQVGw&sa=is_1&ms=1&rsv_pq=15097551060046916401&rsv_sug4=7846&ss=100&inputT=5348&rq=k");
//                b.putString(WebActivity.URL, "http://m.kuaidi100.com");
                b.putString(WebActivity.URL, "http://m.baidu.com");
//                b.putString(WebActivity.URL, "http://m.kuaidi100.com/result.jsp?nu=5124366058");
                Passageway.jumpActivity(context, WebActivity.class, b);
                break;
        }
    }

    @OnClick({R.id.main_tellFriend, R.id.main_getExpress, R.id.main_callMe, R.id.main_boxAddress})
    public void jumpWeb(View view) {
        Bundle b = new Bundle();
        switch (view.getId()) {
            case R.id.main_tellFriend:
                b.putString(WebActivity.URL, Url.getIndex() + "/html/11.html");
                break;
            case R.id.main_getExpress:
                b.putString(WebActivity.URL, Url.getIndex() + "/html/12.html");
                break;
            case R.id.main_callMe:
                b.putString(WebActivity.URL, Url.getIndex() + "/html/14.html");
                break;
            case R.id.main_boxAddress:
                b.putString(WebActivity.URL, Url.getIndex() + "/html/15.html");
                break;
        }
        Passageway.jumpActivity(context, WebActivity.class, b);
    }


    public void setSliderView(JSONArray array) {
        if (array != null) {
            setSliderView(SliderObjHandler.getSliderObjList(array));
        }
    }

    private void setSliderView(List<SliderObj> list) {
        mSliderView = SliderView.initSliderView(context, list, mViewFlipper, pptBallBox);
    }

    private void downloadData() {
        progress.setVisibility(View.VISIBLE);

        String url = Url.getIndex() + "/api/v1/slider";
//        String url = Url.getHomeUrl(AreaObjHandler.getAreaName(context));

        HttpUtilsBox.getHttpUtil().send(HttpMethod.GET, url,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException exception, String msg) {
                        progress.setVisibility(View.GONE);
                        MessageHandler.showFailure(context);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        progress.setVisibility(View.GONE);
                        String result = responseInfo.result;
                        Log.d("", result);

                        JSONObject json = JsonHandle.getJSON(result);
                        if (json != null) {

                            if (JsonHandle.getInt(json, "status") == 1) {
                                JSONArray sliderArray = JsonHandle.getArray(json, "results");
                                setSliderView(sliderArray);
                            }

                        }

                    }

                });
    }

}
