package com.example.car.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.car.MainActivity;
import com.example.car.R;
import com.google.android.material.textfield.TextInputEditText;

import static com.example.car.ConstantsUtility.Constants.MARQUE_KEY;
import static com.example.car.ConstantsUtility.Constants.MATRICULE_KEY;
import static com.example.car.ConstantsUtility.Constants.MODEL_KEY;

public class formFragment3 extends Fragment {

    private Button button_annuler;
    private Button button_confirmer;
    private ImageButton button_retour;
    private formFragment bottomSheetFragment;
    private formFragment formFrag;
    TextInputEditText edtMarque;
    TextInputEditText edtModel;
    TextInputEditText edtMatricule;


    public formFragment3(formFragment formFrag) {
        // Required empty public constructor
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
        View v = inflater.inflate(R.layout.fragment_form3, container, false);
        findViews(v);
        button_confirmer.setEnabled(false);
        annulerButton(v);
        confirmerButton(v);
        retourButton(v);
        configureConfirmer();
        setPrevAttrForModf();

        return v;
    }


    private void setPrevAttrForModf() {
        if (formFrag.MARQUE_KEY_MOD != "DEFAULT") edtMarque.setText(formFrag.MARQUE_KEY_MOD);
        if (formFrag.MODEL_KEY_MOD != "DEFAULT") edtModel.setText(formFrag.MODEL_KEY_MOD);
        if (formFrag.MATRICULE_KEY_MOD != "DEFAULT") edtMatricule.setText(formFrag.MATRICULE_KEY_MOD);
    }


    public void annulerButton (View v) {
        button_annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetFragment.dismiss();

            }
        });
    }

    public void confirmerButton (View v) {
        button_confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // put extra information
                formFrag.intent.putExtra(MARQUE_KEY,edtMarque.getText().toString());
                formFrag.intent.putExtra(MATRICULE_KEY,edtMatricule.getText().toString());
                formFrag.intent.putExtra(MODEL_KEY,edtModel.getText().toString());
                formFrag.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(formFrag.intent);
                bottomSheetFragment.dismiss();

            }
        });
    }

    public void retourButton (View v) {

        button_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetFragment.setFragment2(formFrag);
            }
        });
    }

    void configureConfirmer(){
        edtMatricule.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(             !edtMarque.getText().toString().isEmpty()
                        && !edtModel.getText().toString().isEmpty()
                        && !s.toString().isEmpty()
                )button_confirmer.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtMarque.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(             !s.toString().isEmpty()
                        && !edtModel.getText().toString().isEmpty()
                        && !edtMatricule.getText().toString().isEmpty()
                )button_confirmer.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(             !edtMarque.getText().toString().isEmpty()
                        && !s.toString().isEmpty()
                        && !edtMatricule.getText().toString().isEmpty()
                )button_confirmer.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void findViews(View v){
        button_annuler = v.findViewById(R.id.annuler);
        button_confirmer = v.findViewById(R.id.confirmer);
        button_retour = v.findViewById(R.id.retour);
        edtModel = v.findViewById(R.id.model);
        edtMarque = v.findViewById(R.id.marque);
        edtMatricule = v.findViewById(R.id.matricule);
    }
}