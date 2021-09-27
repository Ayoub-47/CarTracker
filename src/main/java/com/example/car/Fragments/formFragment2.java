package com.example.car.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.car.R;

import static com.example.car.ConstantsUtility.Constants.PRIVATE_CODE_KEY;
import static com.example.car.PrivateCodeUtility.CodeControl.displayPrivateCodeErrors;
import static com.example.car.PrivateCodeUtility.CodeControl.randPrivateCode;

/**
 * A simple {@link Fragment} subclass.

 */
public class formFragment2 extends Fragment {

    private Button button_annuler ;
    private Button button_suivant ;
    private ImageButton button_retour ;
    //text edits
    EditText privateCode;
    EditText privateCodeConfirm;
    TextView randCode;
    TextView privateCodeErrors;
    private formFragment formFrag;
    private formFragment bottomSheetFragment;
    private String matchError = "";

    public formFragment2(formFragment formFrag) {
        this.formFrag = formFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            bottomSheetFragment = bundle.getParcelable("this"); // Key
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_form2, container, false);
        findViews(v);
        configureRandPrivateCode();
        button_suivant.setEnabled(false);
        annulerButton(v);
        suivantButton(v);
        retourButton(v);
        configureErrors();
        setPrevAttrForModf();

        return v;
    }

    private void setPrevAttrForModf() {
        if (formFrag.PRIVATE_CODE_KEY_MOD != "DEFAULT") privateCode.setText(formFrag.PRIVATE_CODE_KEY_MOD);
        if (formFrag.PRIVATE_CODE_KEY_MOD != "DEFAULT") privateCodeConfirm.setText(formFrag.PRIVATE_CODE_KEY_MOD);
    }

    private void configureErrors() {
        privateCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                button_suivant.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(privateCodeConfirm.getText().toString())){
                    matchError = "";
                }else {
                    matchError = "Les deux codes privés ne correspondent pas!";
                }
                String errors = displayPrivateCodeErrors(s.toString());
                privateCodeErrors.setText(errors+"\n"+ matchError);

                if(errors.isEmpty() && matchError.isEmpty()){
                    button_suivant.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        privateCodeConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                button_suivant.setEnabled(false);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(privateCode.getText().toString())){
                    matchError = "";
                }else {
                    matchError = "Les deux codes privés ne correspondent pas!";
                }
                String errors = displayPrivateCodeErrors(privateCode.getText().toString());
                privateCodeErrors.setText(errors+"\n"+ matchError);

                if(errors.isEmpty() && matchError.isEmpty()){
                    button_suivant.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void configureRandPrivateCode(){
        randCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = randPrivateCode();
                privateCode.setText(code);
                privateCodeConfirm.setText(code);
            }
        });
    }

    public void annulerButton (View v) {
        button_annuler = v.findViewById(R.id.annuler);

        button_annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bottomSheetFragment.dismiss();

            }
        });
    }

    public void suivantButton (View v) {

        button_suivant = v.findViewById(R.id.suivant);

        button_suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formFrag.intent.putExtra(PRIVATE_CODE_KEY, privateCode.getText().toString() );
                bottomSheetFragment.setFragment3(formFrag);


            }
        });
    }

    public void retourButton (View v) {

        button_retour = v.findViewById(R.id.retour1);

        button_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetFragment.setFragment1(formFrag);


            }
        });
    }

    private void findViews(View v){
        button_suivant = v.findViewById(R.id.suivant);
         privateCode = v.findViewById(R.id.code);
         privateCodeConfirm = v.findViewById(R.id.code2);
         randCode = v.findViewById(R.id.randCode_textView);
         privateCodeErrors = v.findViewById(R.id.private_code_errors);
    }
}