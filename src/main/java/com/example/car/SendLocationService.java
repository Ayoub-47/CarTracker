package com.example.car;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SendLocationService extends Service {

    private String phone;
    private LocationListener listener;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        phone= intent.getStringExtra("phone"); // receiving sender phone number from ReceiverSMS
        sendCurrentLocation();                       // getting current location and send it through SMS
        stopSelf();                                 // finishing the service
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @SuppressLint("MissingPermission")
    private void sendCurrentLocation(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if(location != null)
                    {
                        try {
                            Toast.makeText(SendLocationService.this, "msg is sent to "+ phone
                                    , Toast.LENGTH_SHORT).show();
                            SmsManager smsManager =  SmsManager.getDefault();
                            smsManager.sendTextMessage(phone , null , location.getLatitude()
                                    +","+location.getLongitude() , null ,null);
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();

                                try {
                                    SmsManager smsManager =  SmsManager.getDefault();
                                    smsManager.sendTextMessage(phone , null , location.getLatitude()+","+location.getLongitude() , null ,null);
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                }

                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest , locationCallback , Looper.myLooper());
                    }
                }
            });
        }
        else
        {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}

