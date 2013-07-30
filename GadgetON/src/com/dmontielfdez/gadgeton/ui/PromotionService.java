package com.dmontielfdez.gadgeton.ui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.ddbb.ProductCRUD;
import com.dmontielfdez.gadgeton.model.Product;

public class PromotionService extends Service {

	private NotificationManager nm;
    private static final int ID_NOTIFICACION_PLAY = 1;
    Socket s;
    String idProduct;
    SharedPreferences pref;
    String serverDirection;
    
    boolean notification, vibrator, sound;
	@Override
	public void onCreate() {
		pref =
	            PreferenceManager.getDefaultSharedPreferences(
	                PromotionService.this);
		
		serverDirection = pref.getString("server_direction", "");
		notification = pref.getBoolean("notification", true);
		vibrator = pref.getBoolean("vibrator", true);
		sound = pref.getBoolean("sound", true);
		
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		new Thread(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				try {
		
					s = new Socket(serverDirection, 2222);
					DataOutputStream output = new DataOutputStream(s.getOutputStream());
					output.writeUTF("client");
					DataInputStream input = null; 
					while(true){
						input= new DataInputStream(s.getInputStream());
						idProduct = input.readUTF();
						
						ProductCRUD productCRUD = new ProductCRUD();
						Product p = productCRUD.findByPK(idProduct);
						
						if (notification) {
							Notification notification = new Notification(R.drawable.ic_launcher, p.getName(), System.currentTimeMillis());
							Intent intent = new Intent(PromotionService.this, ProductActivity.class);
							intent.putExtra("id", Integer.parseInt(idProduct));
							
							PendingIntent pendingIntent = PendingIntent.getActivity(PromotionService.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
							
							notification.setLatestEventInfo(PromotionService.this, "Promoción GadgetON!",p.getName(), pendingIntent);
							
							if (sound) {
								notification.defaults |= Notification.DEFAULT_SOUND;
							}
							
							if (vibrator) {
								notification.defaults |= Notification.DEFAULT_VIBRATE;
							}
							
							notification.flags |= Notification.FLAG_AUTO_CANCEL;
							
							nm.notify(ID_NOTIFICACION_PLAY, notification);
						}
						
						

						
						
					}
				} catch (ConnectException e){
					Log.e("servidor","servidor apagado");
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		
				
	}
	
	

	@Override
	public void onDestroy() {
		try {
			if (s!=null) {
				s.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
