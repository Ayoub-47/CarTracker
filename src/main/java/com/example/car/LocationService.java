package com.example.car;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.car.CarDataUtility.CarData;
import com.example.car.CarDataUtility.PositionData;
import com.example.car.ConstantsUtility.Constants;
import com.example.car.StorageUtility.FirebaseOperation;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;

import static com.example.car.ConstantsUtility.Constants.DEFAULT_TEXT;
import static com.example.car.ConstantsUtility.Constants.FILE_NAME;
import static com.example.car.ConstantsUtility.Constants.MARQUE_KEY;
import static com.example.car.ConstantsUtility.Constants.MATRICULE_KEY;
import static com.example.car.ConstantsUtility.Constants.MODEL_KEY;
import static com.example.car.ConstantsUtility.Constants.PRIVATE_CODE_KEY;
import static java.lang.System.currentTimeMillis;

public class LocationService extends Service {

    double previousLatitude = 0;
    double previousLongitude = 0;

    public double setPrecesion(double b){
            return Double.parseDouble(String.format("%.5f",b));
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null && locationResult.getLastLocation() != null) {
                double latitude = locationResult.getLastLocation().getLatitude();
                double longitude = locationResult.getLastLocation().getLongitude();

                if(previousLatitude==0 && previousLongitude==0){
                    FirebaseOperation.savePosition(LocationService.this , longitude , latitude);
                    previousLatitude = latitude;
                    previousLongitude = longitude;
                }else{
                    if(setPrecesion(previousLatitude)!=setPrecesion(latitude) ||
                            setPrecesion(previousLongitude)!=setPrecesion(longitude)){
                        FirebaseOperation.savePosition(LocationService.this , longitude , latitude);
                        previousLatitude = latitude;
                        previousLongitude = longitude;
                        Toast.makeText(LocationService.this, latitude+" "+longitude, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @SuppressLint("MissingPermission")
    private void startLocationService() {
        String channelId = "location_modification_channel";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(),
                channelId
        );
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Location Service");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText("Running");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(false);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.getNotificationChannel(channelId) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        channelId,
                        "Location Service",
                        NotificationManager.IMPORTANCE_HIGH
                );
                notificationChannel.setDescription("this channel is used by location service");
                notificationManager.createNotificationChannel(notificationChannel);

            }
        }

        LocationRequest locationRequest = new  LocationRequest();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        startForeground(Constants.LOCATION_SERVICE_ID , builder.build());
    }


    private void stopLocationService(){
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback);
        stopForeground(true);
        stopSelf();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null)
        {
            String action = intent.getAction();
            if(action != null)
            {
                if(action.equals(Constants.ACTION_START_LOCATION_SERVICE))
                {
                    startLocationService();
                }
                else
                {
                    if(action.equals(Constants.ACTION_STOP_LOCATION_SERVICE))
                    {
                        stopLocationService();
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
