package com.example.mycart.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mycart.R;

public class CommonUtils {

    public static void showAlertDialog(Context context){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alertbox_layout);
        dialog.setCancelable(true);
        dialog.show();
    }

    public static void showCustomDialog(Context context,String text1,String text2) {


        View view = LayoutInflater.from(context).inflate(R.layout.alertbox_layout, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setCancelable(false);

        TextView textViewAlert = view.findViewById(R.id.alertTitle);
        TextView textViewMessage = view.findViewById(R.id.alertText);
        Button button = view.findViewById(R.id.alertButton);

        textViewAlert.setText(text1);
        textViewMessage.setText(text2);


        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
