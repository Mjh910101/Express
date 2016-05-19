package com.express.subao.adaptera;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.express.subao.R;
import com.express.subao.activitys.ExpresContentActivity;
import com.express.subao.activitys.SdyOrderContentActivity;
import com.express.subao.box.SdyOrderObj;
import com.express.subao.box.handlers.SdyOrderObjHandler;
import com.express.subao.download.DownloadImageLoader;
import com.express.subao.interfaces.CallbackForString;
import com.express.subao.tool.Passageway;
import com.express.subao.tool.WinTool;

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
 * Created by Hua on 15/12/25.
 */
public class SdyOrderAdaper extends BaseAdapter {

    private Context context;
    private List<SdyOrderObj> itemList;
    private LayoutInflater inflater;

    private CallbackForString callback;

    public SdyOrderAdaper(Context context, List<SdyOrderObj> itemList) {
        initBaseAdapter(context);
        this.itemList = itemList;
    }

    public void setCallback(CallbackForString callback) {
        this.callback = callback;
    }

    private void initBaseAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HolderView holder;
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.layout_query_expres_list_items, null);
            holder = new HolderView();

            holder.img = (ImageView) convertView.findViewById(R.id.query_express_item_img);
            holder.statusStr = (TextView) convertView.findViewById(R.id.query_express_item_statusStr);
            holder.companyName = (TextView) convertView.findViewById(R.id.query_express_item_companyName);
            holder.code = (TextView) convertView.findViewById(R.id.query_express_item_code);

            convertView.setTag(holder);
        } else {
            holder = (HolderView) convertView.getTag();
        }

        SdyOrderObj obj = itemList.get(position);
        setView(holder, obj);
        setOnClick(convertView, obj);

        return convertView;
    }


    private void setOnClick(View convertView, final SdyOrderObj obj) {
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SdyOrderObjHandler.saveSdyOrder(obj);
                Bundle b = new Bundle();
                b.putBoolean("isShowMob", false);
                b.putString(SdyOrderObj.SDY_ORDER_ID, obj.getSdy_order_id());
                Passageway.jumpActivity(context, SdyOrderContentActivity.class, b);
            }
        });
    }

    private void setView(HolderView holder, SdyOrderObj obj) {
//        setImage(holder.img, obj.getExpreser().getCompanyInfo().getIco());
//        holder.companyName.setText(obj.getExpreser().getCompanyInfo().getName() + " " + obj.getExpreser().getExpress_id());
        holder.statusStr.setVisibility(View.VISIBLE);
        holder.img.setVisibility(View.GONE);
        holder.statusStr.setText(obj.getStatus());
        switch (obj.getStatus()) {
            case "1":
                holder.statusStr.setBackgroundResource(R.color.green);
                break;
            default:
                holder.statusStr.setVisibility(View.INVISIBLE);
                break;
        }

        holder.companyName.setText("快递单号 " + obj.getSdy_order_id());

        switch (obj.getStatus()) {
            case "1":
                holder.code.setText("開箱碼 " + obj.getOpen_code());
                break;
            default:
                holder.code.setText(obj.getPickup_time());
                break;
        }
    }

    private void setImage(ImageView img, String ico) {
        int w = WinTool.getWinWidth(context) / 5;
        img.setLayoutParams(new LinearLayout.LayoutParams(w, w));
        DownloadImageLoader.loadImage(img, ico);
    }


    class HolderView {
        ImageView img;
        TextView companyName;
        TextView code;
        TextView statusStr;
    }

}
