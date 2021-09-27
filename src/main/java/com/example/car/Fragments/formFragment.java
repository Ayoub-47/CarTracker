package com.example.car.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.car.MainActivity;
import com.example.car.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;

import static com.example.car.ConstantsUtility.Constants.MARQUE_KEY;
import static com.example.car.ConstantsUtility.Constants.MATRICULE_KEY;
import static com.example.car.ConstantsUtility.Constants.MODEL_KEY;
import static com.example.car.ConstantsUtility.Constants.PHONE_KEY_WITHOUT_PLUS;
import static com.example.car.ConstantsUtility.Constants.PRIVATE_CODE_KEY;

public class formFragment extends BottomSheetDialogFragment implements Parcelable {

    public String MARQUE_KEY_MOD = "DEFAULT";
    public String MATRICULE_KEY_MOD= "DEFAULT";
    public String MODEL_KEY_MOD= "DEFAULT";
    public String PHONE_KEY_MOD= "DEFAULT";
    public String PRIVATE_CODE_KEY_MOD= "DEFAULT";

    private Fragment fragment;
    Intent intent;

    public formFragment() {
        // Required empty public constructor
         MARQUE_KEY_MOD = "DEFAULT";
         MATRICULE_KEY_MOD= "DEFAULT";
         MODEL_KEY_MOD= "DEFAULT";
         PHONE_KEY_MOD= "DEFAULT";
         PRIVATE_CODE_KEY_MOD= "DEFAULT";

    }
    public formFragment(HashMap<String, String> dataMap) {
        // Required empty public constructor
        MARQUE_KEY_MOD = dataMap.get(MARQUE_KEY);
        MODEL_KEY_MOD = dataMap.get(MODEL_KEY);
        MATRICULE_KEY_MOD = dataMap.get(MATRICULE_KEY);
        PHONE_KEY_MOD = dataMap.get(PHONE_KEY_WITHOUT_PLUS);
        PRIVATE_CODE_KEY_MOD = dataMap.get(PRIVATE_CODE_KEY);
    }

    protected formFragment(Parcel in) {
    }

    public static final Creator<formFragment> CREATOR = new Creator<formFragment>() {
        @Override
        public formFragment createFromParcel(Parcel in) {
            return new formFragment(in);
        }

        @Override
        public formFragment[] newArray(int size) {
            return new formFragment[size];
        }
    };
    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public Dialog getDialog() {
        return new BottomSheetDialog(requireContext(),getTheme());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        setFragment1(this);

        return v;
    }



    public void setFragment1 (formFragment thisFragment){
        fragment = new formFragment1(thisFragment);
        getChildFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        ).replace(R.id.formContainer,fragment).commit();

        // Pour envoyer l'objet this au fragment.
        Bundle bundle = new Bundle();
        bundle.putParcelable("this", this) ; // Key, value
        fragment.setArguments(bundle);
    }

    public void setFragment2 (formFragment thisFragment ){
        fragment = new formFragment2(thisFragment);
        getChildFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        )
                .replace(R.id.formContainer,fragment).commit();

        Bundle bundle = new Bundle();
        bundle.putParcelable("this", this) ; // Key, value
        fragment.setArguments(bundle);
    }

    public void setFragment3 (formFragment thisFragment){
        fragment = new formFragment3(thisFragment);
        getChildFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        )
                .replace(R.id.formContainer,fragment).commit();

        Bundle bundle = new Bundle();
        bundle.putParcelable("this", this) ; // Key, value
        fragment.setArguments(bundle);
    }




    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        BottomSheetDialog bottomSheetDialog=(BottomSheetDialog)super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(dialog -> {
            BottomSheetDialog dialogc = (BottomSheetDialog) dialog;
            // When using AndroidX the resource can be found at com.google.android.material.R.id.design_bottom_sheet
            FrameLayout bottomSheet =  dialogc.findViewById(com.google.android.material.R.id.design_bottom_sheet);

            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        return bottomSheetDialog;


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
