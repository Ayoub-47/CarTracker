package com.example.car;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.car.CarDataUtility.CarData;
import com.example.car.CarDataUtility.PositionData;
import com.example.car.ConstantsUtility.Constants;
import com.example.car.Fragments.CarInfoFragment;
import com.example.car.Fragments.SetupFragment;

import com.example.car.Fragments.formFragment;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.grpc.Context;

import static com.example.car.ConstantsUtility.Constants.DEFAULT_TEXT;
import static com.example.car.ConstantsUtility.Constants.FILE_NAME;
import static com.example.car.ConstantsUtility.Constants.LOCATION_REQUEST_FASTEST_INTERVAL;
import static com.example.car.ConstantsUtility.Constants.LOCATION_REQUEST_INTERVAL;
import static com.example.car.ConstantsUtility.Constants.MARQUE_KEY;
import static com.example.car.ConstantsUtility.Constants.MATRICULE_KEY;
import static com.example.car.ConstantsUtility.Constants.MODEL_KEY;
import static com.example.car.ConstantsUtility.Constants.PHONE_KEY_WITHOUT_PLUS;
import static com.example.car.ConstantsUtility.Constants.PHONE_KEY_WITH_PLUS;
import static com.example.car.ConstantsUtility.Constants.PRIVATE_CODE_KEY;
import static com.example.car.StorageUtility.FirebaseOperation.saveCar;
import static com.example.car.StorageUtility.LocalStorage.loadData;
import static com.example.car.StorageUtility.LocalStorage.saveData;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, String> carDataMap = new HashMap<String, String>();
    private LocationRequest locationRequest;
    FloatingActionButton configBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configBtn = (FloatingActionButton) findViewById(R.id.configure_btn);
        try {
            getDataFromIntet();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        checkingForPermissions();
        gpsState(locationRequest);
        setFragment();
    }


    private void getDataFromIntet() throws NoSuchAlgorithmException {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            carDataMap.put(PHONE_KEY_WITHOUT_PLUS, intent.getExtras().getString(PHONE_KEY_WITHOUT_PLUS));
            carDataMap.put(PHONE_KEY_WITH_PLUS, intent.getExtras().getString(PHONE_KEY_WITH_PLUS));
            carDataMap.put(PRIVATE_CODE_KEY, intent.getExtras().getString(PRIVATE_CODE_KEY));
            carDataMap.put(MARQUE_KEY, intent.getExtras().getString(MARQUE_KEY));
            carDataMap.put(MODEL_KEY, intent.getExtras().getString(MODEL_KEY));
            carDataMap.put(MATRICULE_KEY, intent.getExtras().getString(MATRICULE_KEY));
            saveCar(carDataMap, this);
            saveData(carDataMap, this);
        }
    }

    public void setFragment() {
        if (loadData(this, carDataMap)) {
            Toast.makeText(this, "car info fragment", Toast.LENGTH_SHORT).show();
            CarInfoFragment carInfoFragment = new CarInfoFragment(this, carDataMap);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, carInfoFragment).commit();
            hideConfigBtn();
        } else {
            Toast.makeText(this, "setup fragment", Toast.LENGTH_SHORT).show();
            SetupFragment setupFragment = new SetupFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, setupFragment).commit();
            setConfigBtn();
        }
    }

    void setConfigBtn() {
        configBtn.setVisibility(View.VISIBLE);
        configBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formFragment bottomSheetDialog = new formFragment(carDataMap);
                bottomSheetDialog.show(getSupportFragmentManager(), null);
            }
        });
    }

    void hideConfigBtn() {
        configBtn.setVisibility(View.INVISIBLE);
    }

    //************************************************************************************************//
//**************************************** GPS STATE *********************************************//
    public void gpsState(LocationRequest locationRequest) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
        locationRequest.setFastestInterval(LOCATION_REQUEST_FASTEST_INTERVAL);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext()).checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(MainActivity.this, "GPS is ON", Toast.LENGTH_LONG).show();
                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MainActivity.this, com.example.car.ConstantsUtility.Constants.REQUEST_CHECK_SETTING);
                            } catch (IntentSender.SendIntentException sendIntentException) {
                                sendIntentException.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;

                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == com.example.car.ConstantsUtility.Constants.REQUEST_CHECK_SETTING) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Toast.makeText(this, "GPS is turned on", Toast.LENGTH_LONG).show();
                    break;

                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "GPS is required to be turned ON", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    //********************************************************************************************//
    //************************* ASCKING AND CHECKING FOR REQUIRED PERMISSIONS ********************//
    public void checkingForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.READ_SMS,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.FOREGROUND_SERVICE}
                    , com.example.car.ConstantsUtility.Constants.PERMISSION_CODE);
        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == com.example.car.ConstantsUtility.Constants.PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[3] == PackageManager.PERMISSION_GRANTED
                    && grantResults[4] == PackageManager.PERMISSION_GRANTED) {
                // if the user give permissions to the application
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}