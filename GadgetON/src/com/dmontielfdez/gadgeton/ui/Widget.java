package com.dmontielfdez.gadgeton.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.ddbb.ProductCRUD;
import com.dmontielfdez.gadgeton.model.Product;

public class Widget extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context,
			AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		final int N = appWidgetIds.length;
		

		for (int i=0; i<N; i++) {
			final Product p = ProductCRUD.getProductImportant();
			Intent intent = new Intent(context, ProductActivity.class);
			intent.putExtra("id", p.getId());
			int appWidgetId = appWidgetIds[i];
	
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
			views.setOnClickPendingIntent(R.id.layout_widget, pendingIntent);
			views.setTextViewText(R.id.name_widget, p.getName());

			appWidgetManager.updateAppWidget(appWidgetId, views);
		}

	}
}