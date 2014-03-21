package br.com.badrequest.insporte.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import br.com.badrequest.insporte.util.Util;

public class NetworkChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(Util.haveNetworkConnection(context)) {
			Intent serviceIntent = new Intent(context, br.com.badrequest.insporte.service.SyncUserDataService_.class);
			context.startService(serviceIntent);
            Log.d("INSPORTE", "NetworkChangeReceiver");
//            if(!Util.isStatusPushIDSent(context) || Util.getRegistrationId(context).length() == 0 || !(new InsporteDataSource(context)).isBaseRegistrada()) {
//                Intent serviceGCM = new Intent(context, br.com.badrequest.insporte.service.RegisterGCMService_.class);
//                context.startService(serviceGCM);
//            }
		}
	}
	
}
