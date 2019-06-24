package org;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.StringDef;

import org.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class NotificationManager {
    private static final String GROUP_TED_PARK = "tedPark";



    public static void createChannel(Context context) {


        NotificationChannelGroup group1 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            group1 = new NotificationChannelGroup(GROUP_TED_PARK, GROUP_TED_PARK);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getManager(context).createNotificationChannelGroup(group1);
        }


        NotificationChannel channelMessage = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channelMessage = new NotificationChannel(Channel.MESSAGE,

                    "알림설정", android.app.NotificationManager.IMPORTANCE_DEFAULT);
            channelMessage.setDescription("알림설정하기");

            channelMessage.setGroup(GROUP_TED_PARK);

            channelMessage.setLightColor(Color.GREEN);

            channelMessage.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager(context).createNotificationChannel(channelMessage);


            NotificationChannel channelComment = new NotificationChannel(Channel.COMMENT,

                    "알림설정", android.app.NotificationManager.IMPORTANCE_DEFAULT);

            channelComment.setDescription("알림설정하기");

            channelComment.setGroup(GROUP_TED_PARK);

            channelComment.setLightColor(Color.BLUE);

            channelComment.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            getManager(context).createNotificationChannel(channelComment);


            NotificationChannel channelNotice = new NotificationChannel(Channel.NOTICE,

                    "알림설정", android.app.NotificationManager.IMPORTANCE_HIGH);

            channelNotice.setDescription("알림설정하기");

            channelNotice.setGroup(GROUP_TED_PARK);

            channelNotice.setLightColor(Color.RED);

            channelNotice.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            getManager(context).createNotificationChannel(channelNotice);


        }

    }



    private static android.app.NotificationManager getManager(Context context) {

        return (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    }



    public static void deleteChannel(Context context, @Channel String channel) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getManager(context).deleteNotificationChannel(channel);
        }


    }



    public static void sendNotification(Context context, int id, @Channel String channel, String title, String body) {



        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, channel)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.kakao_login_symbol)
                    .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(), getSmallIcon()))
                    .setAutoCancel(true);


            getManager(context).notify(id, builder.build());
        }
    }



    private static int getSmallIcon() {

        return R.drawable.dog1;

    }



    @Retention(RetentionPolicy.SOURCE)

    @StringDef({

            Channel.MESSAGE,

            Channel.COMMENT,

            Channel.NOTICE

    })

    public @interface Channel {

        String MESSAGE = "message";

        String COMMENT = "comment";

        String NOTICE = "notice";

    }



}
