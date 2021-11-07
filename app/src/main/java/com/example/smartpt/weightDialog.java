package com.example.smartpt;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatDialogFragment;

public class weightDialog extends AppCompatDialogFragment {
    private DialogListener listener;
    private NumberPicker eWeightPicker;



    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.weight_dialog, null);

        eWeightPicker=view.findViewById(R.id.weightPicker);

        eWeightPicker.setMinValue(39);
        eWeightPicker.setMaxValue(150);




        builder.setView(view)
                .setTitle("Weight")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                int pickedValue = eWeightPicker.getValue();
                StringBuilder sb=new StringBuilder();
                sb.append(Integer.toString(pickedValue)).append("Kg");
                String wStr=sb.toString();
                listener.applyWeightText(wStr);


            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener=(DialogListener) context;
        } catch (Exception e){
            throw new ClassCastException(context.toString());
        }
    }

    public interface DialogListener{
        void applyWeightText(String weight);
    }
}

