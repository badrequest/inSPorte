package br.com.badrequest.insporte.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class LocationService extends Service {

    private final IBinder mBinder = new LocationBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final Random mGenerator = new Random();

    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

    public class LocationBinder extends Binder {
        LocationService getService() {
            return LocationService.this;
        }
    }

    public static class LocationServiceConnection implements ServiceConnection {
        LocationService mService;
        boolean mBound = false;

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            LocationBinder binder = (LocationBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            mBound = false;
        }
    }

}
