package com.example.mycart.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mycart.R;
import com.example.mycart.SqlDB.QueryClass;
import com.example.mycart.SqlDB.SqlDataStore;

public class CommonUtils {



    public static void showAlertDialog(Context context){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.phone_screen);
        dialog.setCancelable(true);
        dialog.show();
    }



    public static void deleteCartTable(Context context,String tableName){

        SqlDataStore sqlDataStore = new SqlDataStore(context);
        sqlDataStore.open();
        sqlDataStore.delete(tableName,null);
        sqlDataStore.close();

    }

    public static void showCustomDialog(Context context,String title,String message,boolean check) {


        View view = LayoutInflater.from(context).inflate(R.layout.alertbox_layout, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setCancelable(false);

        TextView textViewAlert = view.findViewById(R.id.alertTitle);
        TextView textViewMessage = view.findViewById(R.id.alertText);
        Button button = view.findViewById(R.id.alertButton);
        Button buttonCancel = view.findViewById(R.id.alertButtonCancel);

        if (check){
            button.setVisibility(View.VISIBLE);
        }

        textViewAlert.setText(title);
        textViewMessage.setText(message);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

}
