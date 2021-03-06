package com.express.subao.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.express.subao.R;
import com.express.subao.box.ExpresObj;
import com.express.subao.box.SdyOrderObj;
import com.express.subao.box.handlers.ExpresObjHandler;
import com.express.subao.box.handlers.SdyOrderObjHandler;
import com.express.subao.box.handlers.UserObjHandler;
import com.express.subao.handlers.JsonHandle;
import com.express.subao.handlers.MessageHandler;
import com.express.subao.handlers.TextHandeler;
import com.express.subao.http.HttpUtilsBox;
import com.express.subao.http.Url;
import com.express.subao.tool.Passageway;
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
 * Created by Hua on 15/12/24.
 */
public class SdyOrderContentActivity extends BaseActivity {

    @ViewInject(R.id.title_back)
    private ImageView backIcon;
    @ViewInject(R.id.title_name)
    private TextView titleName;
    @ViewInject(R.id.expres_content_price)
    private TextView contentPrice;
    @ViewInject(R.id.expres_content_verify)
    private TextView contentVerify;
    @ViewInject(R.id.expres_content_tips)
    private TextView contentTips;
    @ViewInject(R.id.expres_content_area)
    private TextView contentArea;
    @ViewInject(R.id.expres_content_part)
    private TextView contentPart;
    @ViewInject(R.id.expres_content_code)
    private TextView contentCode;
    @ViewInject(R.id.expres_content_companyName)
    private TextView contentCompanyName;
    @ViewInject(R.id.expres_content_postman)
    private TextView contentPostman;
    @ViewInject(R.id.expres_content_expressAt)
    private TextView contentExpressAt;
    @ViewInject(R.id.expres_content_mobBox)
    private RelativeLayout mobBox;
    @ViewInject(R.id.expres_content_mobLine)
    private View mobLine;
    @ViewInject(R.id.expres_content_progress)
    private ProgressBar progress;

    private SdyOrderObj mSdyOrderObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expres_content);

        context = this;
        ViewUtils.inject(this);

        initActivity();
    }

    @OnClick({R.id.title_back, R.id.expres_content_openBtn, R.id.expres_content_check})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.expres_content_openBtn:
                Passageway.jumpActivity(context, PayActivity.class);
                break;
            case R.id.expres_content_check:
                if (mSdyOrderObj != null) {
                    jumpCheckActivity();
                }
                break;
        }
    }

    private void jumpCheckActivity() {
//        Bundle b = new Bundle();
//        b.putString(CheckExpressActivity.CODE_KEY, mExpresObj.getExpreser().getExpress_id());
//        Passageway.jumpActivity(context, CheckExpressActivity.class, b);
        Bundle b = new Bundle();
        b.putString(WebActivity.URL, "https://m.baidu.com/from=1013665e/s?word=" + "快递" + mSdyOrderObj.getMailno());
        Passageway.jumpActivity(context, WebActivity.class, b);
    }

    private void initActivity() {
        backIcon.setVisibility(View.VISIBLE);
        titleName.setText(TextHandeler.getText(context, R.string.sdy_detailed_text));

        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.getBoolean("isShowMob")) {
                mobBox.setVisibility(View.VISIBLE);
                mobLine.setVisibility(View.VISIBLE);
            }
            downloadData(b.getString(SdyOrderObj.SDY_ORDER_ID));
        }

    }

    public void setMessageView(SdyOrderObj obj) {
        contentPrice.setText("MOB");
        contentTips.setText("");
        contentArea.setText("位置：" + obj.getBoxAddress());
        contentPart.setText("");
        contentCode.setText("編號：" + obj.getBoxDeviceId());
        contentCompanyName.setText("快递单号 : " + obj.getMailno());
        contentPostman.setText("快遞員 : " + obj.getMailman());
        contentExpressAt.setText("投件時間 : " + obj.getCreatedAt());

        switch (obj.getStatus()) {
            case "1":
                contentVerify.setText(obj.getOpen_code());
                contentTips.setVisibility(View.VISIBLE);
                break;
            default:
                contentVerify.setText("已取件");
                contentTips.setVisibility(View.GONE);
                break;
        }
    }

    private void downloadData(String id) {
        progress.setVisibility(View.VISIBLE);

        String url = Url.getUserOrderDetail() + "?sessiontoken=" + UserObjHandler.getSessionToken(context) + "&sdy_order_id=" + id;

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
                                JSONArray array = JsonHandle.getArray(json, "results");
                                if (array != null) {
                                    mSdyOrderObj = SdyOrderObjHandler.getSdyOrderObj(JsonHandle.getJSON(json, "data"));
                                    setMessageView(mSdyOrderObj);
                                }
                            }

                        }
                    }

                });
    }

}
