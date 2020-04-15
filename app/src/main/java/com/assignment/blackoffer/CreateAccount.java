package com.assignment.blackoffer;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button createAccount;
    private FirebaseAuth firebaseAuth;
    private TextView goToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        init();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTxt=email.getText().toString();
                String passwordTxt=password.getText().toString();
                String confirmPasswordTxt=confirmPassword.getText().toString().trim();
                String emailPattern = "[a-z0-9._]+(?:\\.[a-z0-9]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

                if(TextUtils.isEmpty(emailTxt) || TextUtils.isEmpty(passwordTxt) || TextUtils.isEmpty(confirmPasswordTxt) ){
                    Toast.makeText(CreateAccount.this, "All fields required."+passwordTxt+confirmPasswordTxt, Toast.LENGTH_LONG).show();
                    return;
                }
                if(!isPasswordValid(passwordTxt)){
                    Toast.makeText(CreateAccount.this, "Password should contain 1 Capital letter,1 Number and a spacial character @#$%^&+=", Toast.LENGTH_LONG).show();
                    return;
                }
                if(passwordTxt.length()<6){
                    Toast.makeText(CreateAccount.this, "password should contain at least 6 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordTxt.equals(confirmPasswordTxt) && emailTxt.matches(emailPattern)){
                    firebaseAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt)
                            .addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(CreateAccount.this, "Registered successfully.Please check your email for verification.", Toast.LENGTH_SHORT).show();
                                                    firebaseAuth.signOut();
                                                    finish();
                                                }
                                                else{
                                                    Toast.makeText(CreateAccount.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Log.w("CreateAccount", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(CreateAccount.this, "User already exists.",Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
                else{
                    Toast.makeText(CreateAccount.this, "Not a valid email or password.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private Boolean isPasswordValid(String passwordTxt) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(passwordTxt);

        return matcher.matches();
    }

    private void init() {
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        confirmPassword=(EditText)findViewById(R.id.confirmPassword);
        createAccount=(Button)findViewById(R.id.createAccount);
        firebaseAuth= FirebaseAuth.getInstance();
        goToLogin=(TextView)findViewById(R.id.loginText);

    }
}
