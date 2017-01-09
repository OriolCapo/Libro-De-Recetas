package com.example.android.myapplication.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.myapplication.R;
import com.example.android.myapplication.Data.Fotos;
import com.example.android.myapplication.Data.ReceptesDAO;

import java.util.ArrayList;

/**
 * Created by Oriolcapo on 04/01/2017.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<ListItem> listItemList = new ArrayList<ListItem>();
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }

    private void populateListItem() {
        ReceptesDAO rDAO = new ReceptesDAO(context);
        ArrayList<String> mThumbNoms = rDAO.getAllReceptesNames();
        for (int i = 0; i < mThumbNoms.size(); i++) {
            ListItem listItem = new ListItem();
            listItem.nomRecepta = mThumbNoms.get(i);
            listItem.hola = "";
            listItemList.add(listItem);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_list_row);
        ListItem listItem = listItemList.get(i);
        remoteView.setTextViewText(R.id.nomR, listItem.nomRecepta.replace('_',' '));
        remoteView.setImageViewBitmap(R.id.imageView, Fotos.loadImageFromStorage(context,listItem.nomRecepta));

        Intent toRecepta = new Intent();
        toRecepta.putExtra("nomRecepta", listItem.nomRecepta);
        remoteView.setOnClickFillInIntent(R.id.layoutWidget,toRecepta);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
