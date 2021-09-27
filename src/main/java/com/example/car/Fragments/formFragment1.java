package com.example.car.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.car.R;
import com.hbb20.CountryCodePicker;

import static com.example.car.ConstantsUtility.Constants.PHONE_KEY_WITHOUT_PLUS;
import static com.example.car.ConstantsUtility.Constants.PHONE_KEY_WITH_PLUS;


public class formFragment1 extends Fragment {

    View thisFragment;
    private Button button_annuler ;
    private Button button_suivant;
    private EditText phone;
    CountryCodePicker ccp;
    TextView phoneErrors ;
    private formFragment bottomSheetFragment;
    private formFragment formFrag;

    public formFragment1(formFragment formFrag) {
        this.formFrag = formFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Recuperer l'objet de bottomSheetFragment pour pouvoir utiliser le bouton annuler.
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            bottomSheetFragment = bundle.getParcelable("this"); // Key

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisFragment = inflater.inflate(R.layout.fragment_form1, container, false);
        assignViews(thisFragment);
        button_suivant.setEnabled(false);
        getPhoneNumberEdit();
        ccp.registerCarrierNumberEditText(phone);
        ccp.setCountryForNameCode("DZ");
        annulerButton(thisFragment);
        suivantButton(thisFragment);
        setPrevAttrForModf();
        return thisFragment;
    }

    private void setPrevAttrForModf() {
        if (formFrag.PHONE_KEY_MOD != "DEFAULT") phone.setText(formFrag.PHONE_KEY_MOD);
    }

    public void annulerButton (View v){
        button_annuler = v.findViewById(R.id.annuler);
        button_annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetFragment.dismiss();

            }
        });
    }
    public void suivantButton (View v){

        button_suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formFrag.intent.putExtra(PHONE_KEY_WITH_PLUS, ccp.getFullNumberWithPlus());
                if(phone.getText().toString().toCharArray()[0]=='0'){
                    formFrag.intent.putExtra(PHONE_KEY_WITHOUT_PLUS, phone.getText().toString() );
                }else {
                    formFrag.intent.putExtra(PHONE_KEY_WITHOUT_PLUS, "0" + phone.getText().toString() );
                }
                bottomSheetFragment.setFragment2(formFrag);
            }
        });
    }

    public void getPhoneNumberEdit(){
            phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    button_suivant.setEnabled(false);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(ccp.isValidFullNumber()){
                        button_suivant.setEnabled(true);
                        phoneErrors.setTextColor(getResources().getColor(R.color.green));
                        phoneErrors.setText("Ce numéro de téléphone est valide en "+ ccp.getSelectedCountryName());
                    }else {
                        if(phone.getText().toString().length()>0){
                            phoneErrors.setTextColor(Color.RED);
                            phoneErrors.setText("Ce numéro de téléphone n'est pas valide en "+ ccp.getSelectedCountryName());
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
    }
    private void assignViews(View v){
        button_suivant = v.findViewById(R.id.suivant);
        phone=(EditText) v.findViewById(R.id.phone);
        ccp=(CountryCodePicker) v.findViewById(R.id.ccp);
        phoneErrors = (TextView) v.findViewById(R.id.phoneErrors);
    }
}