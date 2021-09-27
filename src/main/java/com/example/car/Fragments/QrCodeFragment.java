package com.example.car.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.example.car.R;
import com.google.zxing.WriterException;

import java.util.HashMap;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static com.example.car.ConstantsUtility.Constants.MARQUE_KEY;
import static com.example.car.ConstantsUtility.Constants.MATRICULE_KEY;
import static com.example.car.ConstantsUtility.Constants.MODEL_KEY;
import static com.example.car.ConstantsUtility.Constants.PHONE_KEY_WITH_PLUS;
import static com.example.car.ConstantsUtility.Constants.PRIVATE_CODE_KEY;


public class QrCodeFragment extends DialogFragment {

    ImageView qrImage;
    String data;
    private HashMap<String ,String> carDataMap ;

    public QrCodeFragment(HashMap<String,String> carData) {
        // Required empty public constructor
        this.carDataMap = carData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_qr_code, container, false);

        interpretateData();

        qrImage = v.findViewById(R.id.qrPlaceHolder);
        QRGEncoder qrgEncoder = new QRGEncoder(data,null, QRGContents.Type.TEXT,500);
        try {
            Bitmap qrBits = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(qrBits);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return v;
    }

    private void interpretateData() {
        this.data = carDataMap.get(MARQUE_KEY)+" ; "+
                carDataMap.get(MODEL_KEY)+" ; "+
                carDataMap.get(MATRICULE_KEY)+" ; "+
                carDataMap.get(PRIVATE_CODE_KEY)+" ; "+
                carDataMap.get(PHONE_KEY_WITH_PLUS);
    }
}