package com.example.mycart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycart.Utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnterOTPActivity extends AppCompatActivity {

    @BindView(R.id.otpNumber)
    EditText otpNumber;

    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_o_t_p);
        ButterKnife.bind(this);

         code  = this.getIntent().getExtras().getString("code");
    }

    public void proceedClicked(View view) {
        if (otpNumber.getText().length() == 10){
            Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
            intent.putExtra("number",otpNumber.getText().toString());
            intent.putExtra("code",code);
            startActivity(intent);
        }else {
            CommonUtils.showCustomDialog(this,"Alert!","Enter a valid Number",false);
//            Toast.makeText(this, "Enter a valid Number", Toast.LENGTH_SHORT).show();
        }
    }
}