package com.example.smartpt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class UpdateDialog extends AppCompatDialogFragment {

    private EditText sets;
    private EditText reps;
    private EditText weight;
    private updateDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.update_dialog,null);
        sets=view.findViewById(R.id.edit_sets);
        reps=view.findViewById(R.id.edit_reps);
        weight=view.findViewById(R.id.edit_weight);

        builder.setView(view).setTitle("Log your Exercise details")
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {




            }
        }).setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String setsText= sets.getText().toString();
                String repsText= reps.getText().toString();
                String weightText= weight.getText().toString();


                listener.applyText(setsText,repsText,weightText);

            }
        });


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener=(updateDialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException( context.toString()+" must implemet dialog listener");
        }
    }

    public interface updateDialogListener{

        void applyText(String sets,String reps,String weight);
    }
}
