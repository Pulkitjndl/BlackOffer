package com.assignment.blackoffer;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class SelectLoginType extends AppCompatActivity {

    private static final int RC_SIGN_IN = 234;
    private ImageView otpLogin;
    private Dialog myDialog;
    private Button createAcc;
    private ImageView facebookLogin;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private Button emailLogin;
    private TextView forgotText;
    private ImageView googleLogin;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        init();


        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectLoginType.this, CreateAccount.class));
            }
        });

        otpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });

        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogin.setEnabled(false);
                LoginManager.getInstance().logInWithReadPermissions(SelectLoginType.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("FacebookLog", "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }
                    @Override
                    public void onCancel() {
                        Log.d("FacebookLog", "facebook:onCancel");
                        Toast.makeText(SelectLoginType.this, "Cancelled clicked", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("FacebookLog", "facebook:onError", error);
                    }
                });
            }
        });

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        forgotText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectLoginType.this,PWResetActivity.class));
            }
        });


        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTxt=email.getText().toString();
                String passwordTxt=password.getText().toString();
                if (TextUtils.isEmpty(emailTxt) || TextUtils.isEmpty(passwordTxt )){
                    Toast.makeText(SelectLoginType.this, "A valid email and password required.", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(emailTxt, passwordTxt)
                        .addOnCompleteListener(SelectLoginType.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    if(mAuth.getCurrentUser().isEmailVerified()){
                                        Log.d("Login", "signInWithEmail:success");
                                        startActivity(new Intent(SelectLoginType.this,MainActivity.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(SelectLoginType.this, "Please verify your email.", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Login", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SelectLoginType.this, "Incorrect login or password.",Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

            }
        });

    }

    public void init() {
        createAcc = (Button) findViewById(R.id.createAccPage);
        otpLogin = (ImageView)findViewById(R.id.otpLogin);
        myDialog = new Dialog(this);
        facebookLogin = (ImageView)findViewById(R.id.facebookLogin);
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        emailLogin=(Button)findViewById(R.id.emailLogin);
        forgotText=(TextView)findViewById(R.id.forgotText);
        googleLogin=(ImageView)findViewById(R.id.googleLogin);

    }

    public void showPopup() {
        final EditText phnNumber;
        Button sendOtp;
        myDialog.setContentView(R.layout.otp_login_popup);
        phnNumber = (EditText) myDialog.findViewById(R.id.phoneText);
        sendOtp = (Button) myDialog.findViewById(R.id.sendOTP);

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+91 " + phnNumber.getText().toString();

                if (phnNumber == null || phnNumber.length() < 10) {
                    Toast.makeText(SelectLoginType.this, "Enter valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(SelectLoginType.this, OTPActivity.class);
                    i.putExtra("number", number);
                    startActivity(i);
                    myDialog.dismiss();
                }
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
            updateUI(currentUser);
        }

    }

    private void updateUI(FirebaseUser user) {

        if(user!=null){
            String userId=user.getUid();
            Intent homeIntent = new Intent(SelectLoginType.this, MainActivity.class);
            homeIntent.putExtra("UserID",userId);
            startActivity(homeIntent);
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FacebookLog", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            facebookLogin.setEnabled(true);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FacebookLog", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FacebookLog", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SelectLoginType.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            facebookLogin.setEnabled(true);
                            updateUI(null);
                        }
                        // ...
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(SelectLoginType.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        init();
        facebookLogin.setEnabled(true);
    }
}
