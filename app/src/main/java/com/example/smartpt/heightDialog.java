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

public class heightDialog extends AppCompatDialogFragment {
    private DialogListener listener;
    private NumberPicker eHeightPicker;



    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.height_dialog, null);

        eHeightPicker=(NumberPicker) view.findViewById(R.id.heightPicker);

        eHeightPicker.setMinValue(140);
        eHeightPicker.setMaxValue(195);


        builder.setView(view)
                .setTitle("Height")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                int pickedValue = eHeightPicker.getValue();
                StringBuilder sb=new StringBuilder();
                sb.append(Integer.toString(pickedValue)).append("cm");
                String hStr=sb.toString();
                listener.applyHeightText(hStr);


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
        void applyHeightText(String height);
    }
}
