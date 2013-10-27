package br.com.badrequest.insporte.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import br.com.badrequest.insporte.util.Util;

public class NetworkChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(Util.haveNetworkConnection(context)) {
			Intent serviceIntent = new Intent(context, br.com.badrequest.insporte.service.SyncUserDataService_.class);
			context.startService(serviceIntent);
//            if(!Util.isStatusPushIDSent(context) || Util.getRegistrationId(context).length() == 0 || !(new PDVDataSource(context)).isBaseRegistrada()) {
//                Intent serviceGCM = new Intent(context, com.luxfacta.ipdv.service.RegisterGCMService_.class);
//                context.startService(serviceGCM);
//            }
		}
	}
	
}
