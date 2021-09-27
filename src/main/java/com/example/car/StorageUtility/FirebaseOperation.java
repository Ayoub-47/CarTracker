package com.example.car.StorageUtility;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.car.CarDataUtility.CarData;
import com.example.car.CarDataUtility.PositionData;
import com.example.car.ConstantsUtility.Constants;
import com.example.car.HashUtility.ShaHash;
import com.example.car.LocationService;
import com.example.car.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import static com.example.car.ConstantsUtility.Constants.DEFAULT_TEXT;
import static com.example.car.ConstantsUtility.Constants.FILE_NAME;
import static com.example.car.ConstantsUtility.Constants.MARQUE_KEY;
import static com.example.car.ConstantsUtility.Constants.MATRICULE_KEY;
import static com.example.car.ConstantsUtility.Constants.MODEL_KEY;
import static com.example.car.ConstantsUtility.Constants.PHONE_KEY_WITH_PLUS;
import static com.example.car.ConstantsUtility.Constants.PRIVATE_CODE_KEY;
import static com.example.car.HashUtility.ShaHash.sha256;
import static java.lang.System.currentTimeMillis;

public class FirebaseOperation {

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void saveCar(HashMap<String,String> carDataMap , Context context) throws NoSuchAlgorithmException {
        CarData car = new CarData(carDataMap.get(MARQUE_KEY) , carDataMap.get(MATRICULE_KEY) ,
                carDataMap.get(MODEL_KEY) , ShaHash.sha256(carDataMap.get(PRIVATE_CODE_KEY)));
        String phone;
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME , MODE_PRIVATE);

        phone = sharedPreferences.getString(PHONE_KEY_WITH_PLUS , DEFAULT_TEXT);
        Toast.makeText(context, "old phone "+phone, Toast.LENGTH_LONG).show();
        if(!carDataMap.get(PHONE_KEY_WITH_PLUS).equals(phone)  && !phone.isEmpty()){
            deleteCar(phone , context);
        }


        db.collection(Constants.CAR_FIRESTORE_COLLECTION).document(carDataMap.get(PHONE_KEY_WITH_PLUS)).set(car)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "success save car", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "success car error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void deleteCar(String phoneNumber, Context context){
        db.collection(Constants.CAR_FIRESTORE_COLLECTION).document(phoneNumber).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "deletion successful", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "deletion failure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static void savePosition(Context context , double longitude , double latitude){
        //SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        //sharedPreferences.getString(PHONE_KEY , DEFAULT_TEXT);
        //Toast.makeText(LocationService.this, Double.toString(latitude)+","+Double.toString(longitude), Toast.LENGTH_LONG).show();
        String PhoneNumber = LocalStorage.getPhoneNumber(context);
        PositionData position = new PositionData(longitude , latitude );
        db.collection(Constants.CAR_FIRESTORE_COLLECTION).document(PhoneNumber)
                .collection(Constants.POSITION_FIRESTORE_COLLECTION)
                .document(String.valueOf(currentTimeMillis())).set(position)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "success save", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "success erreur", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

