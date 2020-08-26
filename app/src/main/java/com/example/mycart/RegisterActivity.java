package com.example.mycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycart.Utils.CommonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.otpCardVew)
    CardView otpCard;
    @BindView(R.id.personalDetCardView)
    CardView personalCardView;
//    @BindView(R.id.addressCardView)
//    CardView addressCardView;
    @BindView(R.id.restPassCardView)
    CardView resetPassCardView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    //otpCardView
    @BindView(R.id.circleField)
    EditText otpEditText;
    @BindView(R.id.mobileText)
    TextView mobileText;

    //personal details
    @BindView(R.id.etFname)
    EditText etFname;
    @BindView(R.id.etLname)
    EditText etLname;
    @BindView(R.id.etPass)
    EditText etPass;
    @BindView(R.id.etConPass)
    EditText etConpass;



    //radio groups
    @BindView(R.id.genderRadioGroup)
    RadioGroup genderRadioGroup;


    RadioButton radioButton;
    String phoneNumber,code;
    private String verificationId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        code = this.getIntent().getExtras().getString("code");
        phoneNumber = this.getIntent().getExtras().getString("number");
        mobileText.setText("OTP sent On Mobile Number \n "+phoneNumber);
        sendVerificationCode("+91"+phoneNumber);

//        openPhoneDialog();


    }

    private  void openPhoneDialog(){

            View view = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.phone_screen, null, false);
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setView(view);
            builder.setCancelable(false);

            Button proceedButton = view.findViewById(R.id.proceed);
            EditText editText = view.findViewById(R.id.enter_number);
            TextView textViewError = view.findViewById(R.id.error_tv);



            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            proceedButton.setOnClickListener(view1 -> {

                if (editText.getText().length() != 10){
                    textViewError.setVisibility(View.VISIBLE);
                }else {
                    phoneNumber = editText.getText().toString();
                    alertDialog.cancel();
                }
            });
    }

    public void backpressed(View view) {
        onBackPressed();
    }

    public void nextClickOtp(View view) {

        String code = otpEditText.getText().toString().trim();

        if ((code.isEmpty() || code.length() < 6)){

            otpEditText.setError("Enter code...");
            otpEditText.requestFocus();
            return;
        }
        verifyCode(code);

    }

    //personal details
    public void perDetClick(View view) {

        if (etFname.getText().length() == 0){
            CommonUtils.showCustomDialog(RegisterActivity.this,"Alert","Enter Your First Name",false);

        }else if (etLname.getText().length() == 0){
            CommonUtils.showCustomDialog(RegisterActivity.this,"Alert","Enter Your Last Name",false);

        }else if (etPass.getText().length() == 0){
            CommonUtils.showCustomDialog(RegisterActivity.this,"Alert","Enter Your Password",false);

        }else if (!etConpass.getText().toString().equals(etPass.getText().toString())){
            CommonUtils.showCustomDialog(RegisterActivity.this,"Alert","Password Does not Match",false);

        }else if (genderRadioGroup.getCheckedRadioButtonId() == -1){
            CommonUtils.showCustomDialog(RegisterActivity.this,"Alert","Please Select gender",false);
        }
        else {

            int selectedId = genderRadioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(selectedId);
            Toast.makeText(this,radioButton.getText() , Toast.LENGTH_SHORT).show();

//            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_out_right);
//            personalCardView.setAnimation(animation);
//            personalCardView.setVisibility(View.GONE);
//            Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left);
//            addressCardView.setAnimation(animation1);
//            addressCardView.setVisibility(View.VISIBLE);
        }

    }


    private void verifyCode(String code){

        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential (PhoneAuthCredential credential){

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.slide_out_right);
                            otpCard.setAnimation(animation);
                            otpCard.setVisibility(View.GONE);
                            Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.slide_in_left);
                            if (code.equals("1")){
                                personalCardView.setAnimation(animation1);
                                personalCardView.setVisibility(View.VISIBLE);
                            }else {
                                resetPassCardView.setAnimation(animation1);
                                resetPassCardView.setVisibility(View.VISIBLE);
                            }


                        }else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void sendVerificationCode(String number){

        final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    progressBar.setVisibility(View.GONE);
//                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                Log.e("Error",e.getMessage());

            }
        };
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack);




    }

    public void resetPassword(View view) {

    }
}
