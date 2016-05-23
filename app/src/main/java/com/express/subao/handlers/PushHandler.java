package com.express.subao.handlers;

import android.content.Context;

import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.PushService;
import com.express.subao.activitys.MainActivity;
import com.express.subao.box.handlers.UserObjHandler;

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
 * Created by Hua on 16/5/23.
 */
public class PushHandler {

    private final static String PUSH_KEY = "IS_PUSH";

    public final static boolean STOP = true;
    public final static boolean START = false;

    public static void savePushState(Context context, boolean b) {
        SystemHandle.saveBooleanMessage(context, PUSH_KEY, b);
    }

    public static boolean isStop(Context context) {
        return SystemHandle.getBoolean(context, PUSH_KEY);
    }

    public static void startPush(Context context) {
        savePushState(context, START);
        PushService.subscribe(context, UserObjHandler.getUserTel(context), MainActivity.class);
        PushService.subscribe(context, UserObjHandler.getUserId(context), MainActivity.class);
        AVInstallation.getCurrentInstallation().saveInBackground();
    }

    public static void stopPush(Context context) {
        savePushState(context, STOP);
        PushService.unsubscribe(context, UserObjHandler.getUserTel(context));
        PushService.unsubscribe(context, UserObjHandler.getUserId(context));
        AVInstallation.getCurrentInstallation().saveInBackground();
    }

}
