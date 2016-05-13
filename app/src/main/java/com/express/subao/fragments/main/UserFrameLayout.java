package com.express.subao.fragments.main;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.PushService;
import com.express.subao.R;
import com.express.subao.activitys.CollectionActivity;
import com.express.subao.activitys.ExpressListActivity;
import com.express.subao.activitys.FeedBackActivity;
import com.express.subao.activitys.LoginActivity;
import com.express.subao.activitys.ModifyUserActivity;
import com.express.subao.activitys.RegisterActivity;
import com.express.subao.activitys.RemainingMoneyListActivity;
import com.express.subao.activitys.ShareFriendsActivity;
import com.express.subao.activitys.UserExpressListActivity;
import com.express.subao.activitys.WebActivity;
import com.express.subao.adaptera.ExpresAdaper;
import com.express.subao.box.handlers.UserObjHandler;
import com.express.subao.download.DownloadImageLoader;
import com.express.subao.fragments.BaseFragment;
import com.express.subao.handlers.TextHandeler;
import com.express.subao.http.Url;
import com.express.subao.tool.Passageway;
import com.express.subao.tool.WinTool;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

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
public class UserFrameLayout extends BaseFragment {

    @ViewInject(R.id.user_loginMessage)
    private RelativeLayout loginMessage;
    @ViewInject(R.id.user_logoutMessage)
    private RelativeLayout logoutMessage;
    @ViewInject(R.id.user_logoutBtn)
    private TextView logoutBtn;
    @ViewInject(R.id.user_loginBox)
    private LinearLayout loginBox;
    @ViewInject(R.id.user_compileBox)
    private LinearLayout compileBox;
    @ViewInject(R.id.user_usetName)
    private TextView userName;
    @ViewInject(R.id.user_usetPic)
    private ImageView usetPic;


    @Override
    public void onRestart() {
        isLogin();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View contactsLayout = inflater.inflate(R.layout.layout_user, container,
                false);
        ViewUtils.inject(this, contactsLayout);
        isLogin();
        return contactsLayout;
    }

    @OnClick({R.id.user_item_help, R.id.user_item_about, R.id.user_item_inviteFriendsBox})
    public void onJumpWeb(View view) {
        Bundle b = new Bundle();
        switch (view.getId()) {
            case R.id.user_item_help:
                b.putString(WebActivity.URL, Url.getIndex() + "/html/12.html");
                break;
            case R.id.user_item_about:
                b.putString(WebActivity.URL, Url.getIndex() + "/html/22.html");
                break;
            case R.id.user_item_inviteFriendsBox:
                b.putString(WebActivity.URL, Url.getIndex() + "/html/23.html");
                break;
        }
        Passageway.jumpActivity(context, WebActivity.class, b);

    }

    @OnClick({R.id.user_registerBtn, R.id.user_loginBtn, R.id.user_logoutBtn, R.id.user_compileBox,
            R.id.user_item_feedbackBox, R.id.user_item_collectionBox, R.id.user_remainingMoneyBox,
            R.id.user_item_subaoBox})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_registerBtn:
                Passageway.jumpActivity(context, RegisterActivity.class, RegisterActivity.REQUEST_CODE);
                break;
            case R.id.user_loginBtn:
                Passageway.jumpActivity(context, LoginActivity.class, LoginActivity.REQUEST_CODE);
                break;
            case R.id.user_logoutBtn:
                logout();
                break;
            case R.id.user_compileBox:
                Passageway.jumpActivity(context, ModifyUserActivity.class, ModifyUserActivity.REQUEST_CODE);
                break;
            case R.id.user_item_feedbackBox:
                Passageway.jumpActivity(context, FeedBackActivity.class);
                break;
            case R.id.user_item_collectionBox:
                Passageway.jumpActivity(context, CollectionActivity.class);
                break;
            case R.id.user_remainingMoneyBox:
                Passageway.jumpActivity(context, RemainingMoneyListActivity.class);
                break;
            case R.id.user_item_subaoBox:
                Passageway.jumpActivity(context, UserExpressListActivity.class);
                break;
//            case R.id.user_item_inviteFriendsBox:
//                Passageway.jumpActivity(context, ShareFriendsActivity.class);
//                break;
        }
    }

    private void logout() {
        PushService.unsubscribe(context, UserObjHandler.getUserTel(context));
        PushService.unsubscribe(context, UserObjHandler.getUserId(context));
        AVInstallation.getCurrentInstallation().saveInBackground();//退订之后需要重新保存 Installation
        UserObjHandler.deleteUser(context);
        ((Activity) (context)).finish();
//                isLogin();
    }

    public void isLogin() {
//        if (UserObjHandler.isLigon(context)) {
        if (true) {
            loginMessage.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.VISIBLE);
            compileBox.setVisibility(View.VISIBLE);
            loginBox.setVisibility(View.GONE);
//            userName.setText(TextHandeler.getText(context, R.string.welcome_user_text).replace("?", "JamesBond"));
            DownloadImageLoader.loadImageForID(usetPic, R.drawable.james_bond_icon, WinTool.dipToPx(context, 30));
            userName.setText(TextHandeler.getText(context, R.string.welcome_user_text).replace("?", UserObjHandler.getUserName(context)));
        } else {
            loginMessage.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.GONE);
            compileBox.setVisibility(View.GONE);
            loginBox.setVisibility(View.VISIBLE);
            userName.setText(TextHandeler.getText(context, R.string.welcome_text));
        }
    }
}
