package com.example.mycart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnterOTPActivity extends AppCompatActivity {

    @BindView(R.id.otpNumber)
    EditText otpNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_o_t_p);
        ButterKnife.bind(this);
    }

    public void proceedClicked(View view) {
        if (otpNumber.getText().length() == 10){
            Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
            intent.putExtra("number",otpNumber.getText().toString());
            startActivity(intent);
        }else {
            Toast.makeText(this, "Enter a valid Number", Toast.LENGTH_SHORT).show();
        }
    }
}