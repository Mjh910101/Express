package com.express.subao.box.handlers;

import android.content.Context;

import com.express.subao.box.UserObj;
import com.express.subao.handlers.JsonHandle;
import com.express.subao.handlers.SystemHandle;

import org.json.JSONObject;

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
 * Created by Hua on 15/12/22.
 */
public class UserObjHandler {

    public static UserObj getUserObj(JSONObject json) {
        UserObj obj = new UserObj();

        obj.setObjectId(JsonHandle.getString(json, UserObj.OBJECT_ID));
        obj.setUsername(JsonHandle.getString(json, UserObj.USER_NAME));
        obj.setSessionToken(JsonHandle.getString(json, UserObj.SESSION_TOKEN));

        return obj;
    }

    private static UserObj mUserObj;

    public static void saveUserObj(UserObj obj) {
        if (mUserObj != null) {
            mUserObj = null;
        }
        mUserObj = obj;
    }

    public static UserObj getUserObj() {
        return mUserObj;
    }

    private final static String KEY = "user_";

    public static void saveUserObj(Context context, UserObj obj) {
        SystemHandle.saveStringMessage(context, KEY + UserObj.SESSION_TOKEN, obj.getSessionToken());
        SystemHandle.saveStringMessage(context, KEY + UserObj.USER_NAME, obj.getUsername());
        SystemHandle.saveStringMessage(context, KEY + UserObj.OBJECT_ID, obj.getObjectId());
    }

    public static boolean isLigon(Context context) {
        return !getSaveSessionToken(context).equals("");
    }

    private static String getSaveSessionToken(Context context) {

        return SystemHandle.getString(context, KEY + UserObj.SESSION_TOKEN);
    }

    public static void deleteUser(Context context) {
        SystemHandle.saveStringMessage(context, KEY + UserObj.SESSION_TOKEN, "");
        SystemHandle.saveStringMessage(context, KEY + UserObj.USER_NAME, "");
        SystemHandle.saveStringMessage(context, KEY + UserObj.OBJECT_ID, "");
    }

    public static String getUserName(Context context) {
        return SystemHandle.getString(context,KEY+UserObj.USER_NAME);
    }

    public static String getUserTel(Context context) {
        return SystemHandle.getString(context,KEY+UserObj.USER_NAME);
    }

    public static String getUserId(Context context) {
        return SystemHandle.getString(context,KEY+UserObj.OBJECT_ID);
    }
}
