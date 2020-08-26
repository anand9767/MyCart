package com.example.mycart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mycart.Utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAddress extends AppCompatActivity {

    //address
    @BindView(R.id.enter_address_et)
    EditText etAddress;
    @BindView(R.id.town_et)
    EditText etTown;
    @BindView(R.id.landmark_et)
    EditText etLandmark;
    @BindView(R.id.district_et)
    EditText etDistrict;

    RadioButton radioButton;

    @BindView(R.id.addressRadioGroup)
    RadioGroup addressRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);


    }

    public void backpressed(View view) {
        onBackPressed();
    }

    public void addressNextClick(View view) {

        if (etAddress.getText().length() == 0){
            CommonUtils.showCustomDialog(AddAddress.this,"Alert","Enter Address",false);

        }else if (etTown.getText().length() == 0){
            CommonUtils.showCustomDialog(AddAddress.this,"Alert","Enter Town",false);

        }else if (etLandmark.getText().length() == 0){
            CommonUtils.showCustomDialog(AddAddress.this,"Alert","Enter Landmark",false);

        }else if (etDistrict.getText().length() == 0){
            CommonUtils.showCustomDialog(AddAddress.this,"Alert","Enter District",false);

        }else if (addressRadioGroup.getCheckedRadioButtonId() == -1){
            CommonUtils.showCustomDialog(AddAddress.this,"Alert","Please Select Address Type",false);
        }
        else {
            int selectedId = addressRadioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(selectedId);
            Toast.makeText(this,radioButton.getText() , Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
        }

    }
}