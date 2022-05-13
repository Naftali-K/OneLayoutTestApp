package com.nk.onelayouttestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Naftali
 * @Date: 27.10.2021 16:44
 */
public class AccountSettingsDialog extends DialogFragment {

    public interface ExampleDialogListener {
        void applyText(String userName);
    }


    ExampleDialogListener exampleDialogListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            exampleDialogListener = (ExampleDialogListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implementation beChannelEventListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_account_settings, null);

        Spinner countrySpinner = view.findViewById(R.id.country_spinner);

        List<String> countryList = new ArrayList<>();
        countryList.add("Israel");
        countryList.add("Japan");
        countryList.add("Russia");

        ArrayAdapter<String> arraySpinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, countryList);
        countrySpinner.setAdapter(arraySpinnerAdapter);


        //------------------------------------------------------------------------------------------
        //Dialog Frame
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

//        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_fragment_style);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        builder.setTitle("User Settings");
        builder.setView(view);

        EditText user_name = view.findViewById(R.id.user_name_edittext);

        //working when pushing to made btn
        Button saveBtn = view.findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Btn Save", Toast.LENGTH_SHORT).show();

                exampleDialogListener.applyText(user_name.getText().toString());

                if(builder != null){
                    dismiss();
                }
            }
        });

        //working when pushing "Cancel"
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //working when pushing "Save"
//        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getActivity(), "Save changes", Toast.LENGTH_SHORT).show();
//            }
//        });

        return builder.create();
    }

    // Working when pushing back or out dialog
    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);

        Toast.makeText(getActivity(), "onCancel", Toast.LENGTH_SHORT).show();
    }
}
