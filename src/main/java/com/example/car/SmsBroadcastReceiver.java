package com.example.car;


import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.car.ConstantsUtility.Constants;
import com.example.car.StorageUtility.LocalStorage;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    private String PrivateCode ;
    private String[] request;
    private ArrayList<String> phoneNumbers = new ArrayList<String>();



    @Override
    public void onReceive(Context context, Intent intent) {
        //SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.FILE_NAME, MODE_PRIVATE);
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from , msgBody;
            if(bundle != null)
            {
                try
                {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i=0 ; i< msgs.length ; i++)
                    {
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getDisplayOriginatingAddress().toString();
                        msgBody = msgs[i].getMessageBody().toString();

                        PrivateCode = LocalStorage.getPrivateCode(context);//sharedPreferences.getString(Constants.PRIVATE_CODE_KEY, "");

                        if(msgBody.equals(PrivateCode+Constants.SMS))
                        {

                                Toast.makeText(context, msg_from+" : "+ msgBody, Toast.LENGTH_LONG).show();
                                Intent sendLocationService = new Intent(context , SendLocationService.class);
                                sendLocationService.putExtra("phone" , msg_from);
                                context.startService(sendLocationService);

                        } else if(msgBody.equals(PrivateCode+Constants.ENABLE_4G)){//(Constants.ENABLE_4G.equals(request[1].trim().toLowerCase())){
                                phoneNumbers.add(msg_from);
                                startLocationService(context);
                            }
                            else if(msgBody.equals(PrivateCode+Constants.DISABLE_4G)){//(Constants.DISABLE_4G.equals(request[1].trim().toLowerCase())){
                                try{
                                    phoneNumbers.remove(msg_from);
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                if(phoneNumbers.isEmpty()){
                                    stopLocationService(context);
                                }
                            }


                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }



    private boolean isLocationServiceRunning(Context context){
        ActivityManager activityManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager != null)
        {
            for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)){
                if(LocationService.class.getName().equals(service.service.getClassName()))
                {
                    if(service.foreground){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void startLocationService(Context context){
        if(!isLocationServiceRunning(context)){
            Intent intent = new Intent(context , LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            context.startService(intent);
            Toast.makeText(context, "Location Service Started", Toast.LENGTH_LONG).show();
        }
    }

    private void stopLocationService(Context context){
        if(isLocationServiceRunning(context)){
            Intent intent = new Intent(context , LocationService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            context.startService(intent);
            Toast.makeText(context, "Location Service Stopped", Toast.LENGTH_LONG).show();
        }
    }
}


