package br.com.badrequest.insporte.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import java.util.Random;

public class LocationService extends Service implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener {

    LocationClient mLocationClient;
    LocationRequest mLocationRequest;

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationClient = new LocationClient(this, this, this);
        mLocationClient.connect();
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(4000);
        mLocationRequest.setFastestInterval(4000);

    }

    @Override
    public void onDestroy() {
        mLocationClient.disconnect();
        super.onDestroy();
    }

    private final IBinder mBinder = new LocationBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final Random mGenerator = new Random();

    public Location getLastLocation() {
        return mLocationClient.getLastLocation();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    public class LocationBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationClient.requestLocationUpdates(mLocationRequest, this);
    }

    @Override
    public void onDisconnected() {
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
    }


}
