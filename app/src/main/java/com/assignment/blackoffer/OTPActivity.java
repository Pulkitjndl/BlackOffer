package com.assignment.blackoffer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.victor.loading.rotate.RotateLoading;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    private String mVerificationId;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    Pinview editTextCode;
    TextView ph_num;
    TextView timer;
    TextView resendCode;
    private RotateLoading rotateLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth = FirebaseAuth.getInstance();
        editTextCode = findViewById(R.id.editTextCode);
        ph_num=(TextView)findViewById(R.id.ph_num);
        resendCode=(TextView)findViewById(R.id.resendText);
        timer=(TextView)findViewById(R.id.timer);
        rotateLoading=(RotateLoading)findViewById(R.id.progressIndicator);

        resendCode.setPaintFlags(resendCode.getPaintFlags() |
                Paint.UNDERLINE_TEXT_FLAG);

        Intent intent = getIntent();
        final String mobile = intent.getStringExtra("number");
        sendVerificationCode(mobile);
        ph_num.setText(mobile);

        findViewById(R.id.btnVerify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextCode.getValue();
                if (code.isEmpty() || code.length() < 6) {
                    Toast.makeText(OTPActivity.this, "Enter valid Code.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //verifying the code entered manually
                verifyVerificationCode(code);
            }
        });

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode(mobile);
                timer();
                resendCode.setEnabled(false);
                resendCode.setTextColor(Color.RED);
            }
        });

    }

    private void timer() {
        new CountDownTimer(60000,1000){

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {

                if(millisUntilFinished/1000 <10)
                    timer.setText("00:0"+millisUntilFinished/1000);
                else
                    timer.setText("00:"+millisUntilFinished / 1000);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                timer.setText("00:00");
                resendCode.setEnabled(true);
                resendCode.setPaintFlags(resendCode.getPaintFlags() |
                        Paint.UNDERLINE_TEXT_FLAG);
                resendCode.setTextColor(Color.BLACK);

            }
        }.start();
    }

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        rotateLoading.setVisibility(View.VISIBLE);
        rotateLoading.start();

        //signing the user
        signInWithPhoneAuthCredential(credential);

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTPActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            Intent intent = new Intent(OTPActivity.this, MainActivity.class);
                            intent.putExtra("phone",ph_num.getText().toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            rotateLoading.stop();
                            rotateLoading.setVisibility(View.GONE);
                            finish();

                        } else {

                            //verification unsuccessful.. display an error message
                            String message = "Somthing is wrong, we will fix it soon...";
                            rotateLoading.setVisibility(View.GONE);
                            rotateLoading.stop();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(OTPActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
        timer();
        resendCode.setEnabled(false);
        resendCode.setTextColor(Color.RED);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();
            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                rotateLoading.start();
                editTextCode.setValue(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            mResendToken = forceResendingToken;
        }
    };

}