package br.com.badrequest.insporte.activity.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import br.com.badrequest.insporte.service.LocationService;

/**
 * Created by gmarques on 5/3/14.
 */
public class InsporteBaseActivity extends ActionBarActivity {

    protected LocationServiceConnection mLocationServiceConnection = new  LocationServiceConnection();
    protected LocationService mLocationService;
    protected boolean locationServiceBound = false;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, mLocationServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (locationServiceBound) {
            unbindService(mLocationServiceConnection);
            locationServiceBound = false;
        }
    }

    protected class LocationServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            LocationService.LocationBinder binder = (LocationService.LocationBinder) service;
            mLocationService = binder.getService();
            locationServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            locationServiceBound = false;
        }
    }


}
