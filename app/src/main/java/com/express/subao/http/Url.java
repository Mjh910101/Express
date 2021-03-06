package com.express.subao.http;

public class Url {

    /**
     * 服务器地址ַ
     */
    private static String RootIndex = "http://sub-ao-app-services.leanapp.cn";
//    private static String RootIndex = "http://dev.sub-ao-app-services.avosapps.com";

    public static String getIndex() {
        return RootIndex;
    }

    public static String getHomeUrl(String s) {
        return getIndex() + "/api/v1/home?area=" + s;
    }

    public static String getArea() {
        return getIndex() + "/api/v1/area";
    }

    public static String getSignUp() {
        return getIndex() + "/api/v1/signUp";
    }

    public static String getSignUpVerify() {
        return getIndex() + "/api/v1/signUp/verify";
    }

    public static String getLogin() {
        return getIndex() + "/api/v1/login";
    }

    public static String getForgotSendVerify() {
        return getIndex() + "/api/v1/forgot/sendVerify";
    }

    public static String getForgotReset() {
        return getIndex() + "/api/v1/forgot/reset";
    }

    public static String getSignUpSendVerify() {
        return getIndex() + "/api/v1/signUp/sendVerify";
    }

    public static String getCategory(String id) {
        return getIndex() + "/api/v1/category" + "?id=" + id;
    }

    public static String getTag(String type) {
        return getIndex() + "/api/v1/tag" + "?type=" + type;
    }

    public static String getDiscount() {
        return getIndex() + "/api/v1/discount";
    }

    public static String getDiscount(String id) {
        return getDiscount() + "/" + id;
    }

    public static String getQueryExpress() {
        return getIndex() + "/api/v1/queryexpress";
    }

    public static String getExpress() {
        return getIndex() + "/api/v1/express";
    }

    public static String getUserOrder() {
        return getIndex() + "/api/v1/user/order";
    }

    public static String getItemTag(String id) {
        return getIndex() + "/api/v1/store/" + id + "/itemtag";
    }

    public static String getItem(String id) {
        return getIndex() + "/api/v1/store/" + id + "/items";
    }

    public static String getSdyBoxes() {
        return getIndex() + "/api/v1/sdy/boxes";
    }

    public static String getUserModify() {
        return getIndex() + "/api/v1/user/modify";
    }

    public static String getUserOrderDetail() {
        return getUserOrder()+"/detail";
    }
}
