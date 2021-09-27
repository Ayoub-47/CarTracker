package com.example.car.StorageUtility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.car.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.car.ConstantsUtility.Constants.DEFAULT_TEXT;
import static com.example.car.ConstantsUtility.Constants.FILE_NAME;
import static com.example.car.ConstantsUtility.Constants.MARQUE_KEY;
import static com.example.car.ConstantsUtility.Constants.MATRICULE_KEY;
import static com.example.car.ConstantsUtility.Constants.MODEL_KEY;
import static com.example.car.ConstantsUtility.Constants.PHONE_KEY_WITHOUT_PLUS;
import static com.example.car.ConstantsUtility.Constants.PHONE_KEY_WITH_PLUS;
import static com.example.car.ConstantsUtility.Constants.PRIVATE_CODE_KEY;
import static com.example.car.StorageUtility.FirebaseOperation.saveCar;

public class LocalStorage {


    public static Boolean loadData(Context context , HashMap<String ,String> carDataMap){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        List<String> data = new ArrayList<String>();
        String privateCode = sharedPreferences.getString(PRIVATE_CODE_KEY , DEFAULT_TEXT);
        data.add(privateCode);
        String phoneNumber = sharedPreferences.getString(PHONE_KEY_WITHOUT_PLUS , DEFAULT_TEXT);
        data.add(phoneNumber);
        String phoneNumberPlus = sharedPreferences.getString(PHONE_KEY_WITH_PLUS , DEFAULT_TEXT);
        data.add(phoneNumberPlus);

        String marque = sharedPreferences.getString(MARQUE_KEY , DEFAULT_TEXT);
        data.add(marque);
        String matricule = sharedPreferences.getString(MATRICULE_KEY , DEFAULT_TEXT);
        data.add(matricule);
        String model = sharedPreferences.getString(MODEL_KEY , DEFAULT_TEXT);
        data.add(model);
        for (String d:data){
            if (d == DEFAULT_TEXT) return false;
        }
        carDataMap.put(PRIVATE_CODE_KEY, privateCode);
        carDataMap.put(PHONE_KEY_WITHOUT_PLUS, phoneNumber);
        carDataMap.put(PHONE_KEY_WITH_PLUS, phoneNumberPlus);
        carDataMap.put(MARQUE_KEY, marque);
        carDataMap.put(MATRICULE_KEY, matricule);
        carDataMap.put(MODEL_KEY, model);
        return true;
    }

    public static void saveData(HashMap<String,String> dataMap , Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONE_KEY_WITHOUT_PLUS, dataMap.get(PHONE_KEY_WITHOUT_PLUS));
        editor.putString(PHONE_KEY_WITH_PLUS, dataMap.get(PHONE_KEY_WITH_PLUS));
        editor.putString(PRIVATE_CODE_KEY, dataMap.get(PRIVATE_CODE_KEY));
        editor.putString(MATRICULE_KEY, dataMap.get(MATRICULE_KEY));
        editor.putString(MODEL_KEY, dataMap.get(MODEL_KEY));
        editor.putString(MARQUE_KEY, dataMap.get(MARQUE_KEY));
        editor.commit();
    }

    public static void saveData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONE_KEY_WITHOUT_PLUS,DEFAULT_TEXT);
        editor.putString(PHONE_KEY_WITH_PLUS, DEFAULT_TEXT);
        editor.putString(PRIVATE_CODE_KEY, DEFAULT_TEXT);
        editor.putString(MATRICULE_KEY, DEFAULT_TEXT);
        editor.putString(MODEL_KEY, DEFAULT_TEXT);
        editor.putString(MARQUE_KEY, DEFAULT_TEXT);
        editor.commit();
    }

    public static String getPhoneNumber(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(PHONE_KEY_WITH_PLUS , DEFAULT_TEXT);
    }

    public static String getMatricule(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(MATRICULE_KEY , DEFAULT_TEXT);
    }

    public static String getModel(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(MODEL_KEY , DEFAULT_TEXT);
    }

    public static String getMarque(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(MARQUE_KEY , DEFAULT_TEXT);
    }

    public static String getPrivateCode(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(PRIVATE_CODE_KEY , DEFAULT_TEXT);
    }
}
