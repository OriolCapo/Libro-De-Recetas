package com.example.android.myapplication.Widget;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.android.myapplication.LlistatReceptesActivity;
import com.example.android.myapplication.R;
import com.example.android.myapplication.ReceptaActivity;

public class Widget extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        for (int i = 0; i < N; i++) {
            RemoteViews remoteViews = updateWidgetListView(context,appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_design);

        Intent intent = new Intent(context, LlistatReceptesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.textWidget, pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.imageWidget, pendingIntent);

        Intent intentRecepta = new Intent(context, ReceptaActivity.class);
        PendingIntent pendingIntentRecepta = PendingIntent.getActivity(context, 0, intentRecepta, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.listViewWidget, pendingIntentRecepta);

        Intent svcIntent = new Intent(context, WidgetService.class);
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        remoteViews.setRemoteAdapter(R.id.listViewWidget, svcIntent);
        return remoteViews;
    }

}