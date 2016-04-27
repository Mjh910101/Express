package com.express.subao.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.express.subao.R;
import com.express.subao.adaptera.QueryExpresAdaper;
import com.express.subao.box.ExpresObj;
import com.express.subao.box.handlers.ExpresObjHandler;
import com.express.subao.handlers.ColorHandler;
import com.express.subao.handlers.JsonHandle;
import com.express.subao.handlers.MessageHandler;
import com.express.subao.handlers.TextHandeler;
import com.express.subao.http.HttpUtilsBox;
import com.express.subao.http.Url;
import com.express.subao.tool.Passageway;
import com.express.subao.views.InsideListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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
 * Created by Hua on 16/1/29.
 */
public class BoxExpressListActivity extends BaseActivity {

    private final static int NO_RECEIVED = 1;
    private final static int RECEIVED = 2;

    @ViewInject(R.id.title_back)
    private ImageView backIcon;
    @ViewInject(R.id.title_name)
    private TextView titleName;
    @ViewInject(R.id.boxExpress_progress)
    private ProgressBar progress;
    @ViewInject(R.id.boxExpress_notReceovedText)
    private TextView notReceivedText;
    @ViewInject(R.id.boxExpress_receovedText)
    private TextView receivedText;
    @ViewInject(R.id.boxExpress_notReceovedDataList)
    private InsideListView notReceovedDataList;
    @ViewInject(R.id.boxExpress_receovedDataList)
    private InsideListView receovedDataList;
    @ViewInject(R.id.boxExpress_notReceovedDataText)
    private TextView notReceovedDataText;
    @ViewInject(R.id.boxExpress_receovedDataText)
    private TextView receovedDataText;

    private List<ExpresObj> notReceivedList;
    private List<ExpresObj> receivedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_express_list);

        context = this;
        ViewUtils.inject(this);

        initActivity();
        downloadData();
    }

    @OnClick({R.id.title_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

    private void initActivity() {
        backIcon.setVisibility(View.VISIBLE);
        titleName.setText(TextHandeler.getText(context, R.string.box_in_express_text));

        notReceivedList = new ArrayList<ExpresObj>();
        receivedList = new ArrayList<ExpresObj>();

        setTitleListSize();
    }

    private void setTitleListSize() {
        notReceivedText.setText(TextHandeler.getText(context, R.string.not_received_text).replace("?", String.valueOf(notReceivedList.size())));
        receivedText.setText(TextHandeler.getText(context, R.string.received_text).replace("?", String.valueOf(receivedList.size())));

        notReceovedDataText.setVisibility(View.GONE);
        notReceovedDataList.setVisibility(View.GONE);
        receovedDataText.setVisibility(View.GONE);
        receovedDataList.setVisibility(View.GONE);
        if (notReceivedList.size() > 0) {
            notReceovedDataList.setVisibility(View.VISIBLE);
        } else {
            notReceovedDataText.setVisibility(View.VISIBLE);
        }
        if (receivedList.size() > 0) {
            receovedDataList.setVisibility(View.VISIBLE);
        } else {
            receovedDataText.setVisibility(View.VISIBLE);
        }
    }

    private void finishingList(List<ExpresObj> list) {
        for (ExpresObj obj : list) {
            if (obj.getStatus() == 1) {
                receivedList.add(obj);
            } else {
                notReceivedList.add(obj);
            }
        }
        setTitleListSize();

        receovedDataList.setAdapter(new QueryExpresAdaper(context, receivedList, true));
        notReceovedDataList.setAdapter(new QueryExpresAdaper(context, notReceivedList, true));
    }

    private void downloadData() {
        progress.setVisibility(View.VISIBLE);

        String url = Url.getExpress();

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
                                    List<ExpresObj> list = ExpresObjHandler.getExpresObjList(array);
                                    finishingList(list);
                                }
                            }

                        }
                    }

                });
    }


}
