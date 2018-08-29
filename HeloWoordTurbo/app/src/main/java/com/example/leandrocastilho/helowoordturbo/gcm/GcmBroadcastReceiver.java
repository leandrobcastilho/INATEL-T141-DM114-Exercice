package com.example.leandrocastilho.helowoordturbo.gcm;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.leandrocastilho.helowoordturbo.MainActivity;
import com.example.leandrocastilho.helowoordturbo.R;
import com.example.leandrocastilho.helowoordturbo.models.InterestProduct;
import com.example.leandrocastilho.helowoordturbo.models.OrderInfo;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

public class GcmBroadcastReceiver extends BroadcastReceiver {

    private Context context;
    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID_ORDER = 1;
    public static final int NOTIFICATION_ID_INTEREST_PRODUCT = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this.context);
        String messageType = gcm.getMessageType(intent);
        if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            Bundle extras = intent.getExtras();
            Gson gson = new Gson();
            if (extras.containsKey("orderInfo")) {
                String strOrderInfo = extras.getString("orderInfo");
                if (strOrderInfo != null) {
                    OrderInfo orderInfo = gson.fromJson(strOrderInfo, OrderInfo.class);
                    sendOrderNotification(orderInfo);
                }
            } else if (extras.containsKey("productOfInterest")) {
                String strInterestProductInfo = extras.getString("productOfInterest");
                if (strInterestProductInfo != null) {
                    InterestProduct interestProduct = gson.fromJson(strInterestProductInfo, InterestProduct.class);
                    sendInterestProductNotification(interestProduct);
                }
            }
        }
        if (isOrderedBroadcast()) {
            setResultCode(Activity.RESULT_OK);
        }
    }

    private void sendOrderNotification(OrderInfo orderInfo) {
        this.mNotificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this.context, MainActivity.class);
        intent.putExtra("orderInfo", orderInfo);
        PendingIntent contentIntent = PendingIntent.getActivity(this.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder mBuilder = new Notification.Builder(this.context)
                .setSmallIcon(R.drawable.ic_event_available_white_24dp)
                .setAutoCancel(true).setContentTitle("Siecola Vendas")
                .setStyle(new Notification.BigTextStyle()
                        .bigText("Pedido:" + orderInfo.getId() + " - " + orderInfo.getStatus()))
                .setContentText(
                        "Pedido:" + orderInfo.getId() + " - " + orderInfo.getStatus());

        mBuilder.setContentIntent(contentIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder.setChannelId("1");
        }
        this.mNotificationManager.notify(NOTIFICATION_ID_ORDER, mBuilder.build());

    }

    private void sendInterestProductNotification(InterestProduct interestProduct) {
        this.mNotificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this.context, MainActivity.class);
        intent.putExtra("productOfInterest", interestProduct);
        PendingIntent contentIntent = PendingIntent.getActivity(this.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder mBuilder = new Notification.Builder(this.context)
                .setSmallIcon(R.drawable.ic_event_available_white_24dp)
                .setAutoCancel(true).setContentTitle("Siecola Message")
                .setStyle(new Notification.BigTextStyle()
                        .bigText("Interest Product:" + interestProduct.getCode() + " - " + interestProduct.getPrice()))
                .setContentText(
                        "Interest Product:" + interestProduct.getCode() + " - " + interestProduct.getPrice());

        mBuilder.setContentIntent(contentIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder.setChannelId("2");
        }
        this.mNotificationManager.notify(NOTIFICATION_ID_INTEREST_PRODUCT, mBuilder.build());

    }

}

