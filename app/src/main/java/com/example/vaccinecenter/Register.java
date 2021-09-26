package com.example.vaccinecenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sdsmdg.tastytoast.TastyToast;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class Register extends AppCompatActivity {

    private static final String USER = "user";
    EditText name, userEmail, phoneNo, userPassword, confirmPassword;
    CircularProgressButton register;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}";

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    long maxId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //get data from edit text to string variable
        userEmail = findViewById(R.id.editTextEmail);
        userPassword = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextPassword2);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

    }

    public void onRegisterClick(View view) {

        String emailCheck = userEmail.getText().toString();
        String passwordCheck = userPassword.getText().toString();
        String cPasswordCheck = confirmPassword.getText().toString();

        if (emailCheck.isEmpty() || emailCheck == null) {
            //userEmail.setError("Email must be filled.");
            TastyToast.makeText(getApplicationContext(), "Email can not be empty..", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
        }

        if (passwordCheck.isEmpty() || passwordCheck == null) {
            //userPassword.setError("Password must be filled.");
            TastyToast.makeText(getApplicationContext(), "Password can not be empty..", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
        }

        if (cPasswordCheck.isEmpty() || cPasswordCheck == null) {
            //userPassword.setError("Password must be filled.");
            TastyToast.makeText(getApplicationContext(), "Confirm Password can not be empty..", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

        } else {

            CheckValidationOfInputs();
        }
    }

    private void CheckValidationOfInputs() {
        String emailCheck = userEmail.getText().toString();
        String passwordCheck = userPassword.getText().toString();
        String cPasswordCheck = confirmPassword.getText().toString();

        //System.out.println(userEmail.getText().toString());


        if (!emailCheck.matches(emailPattern)) {
            // userEmail.setError("Email is not a valid email. Please enter valid email.");
            TastyToast.makeText(getApplicationContext(), "Email does not a valid format..", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
        }
        if (!passwordCheck.matches(passwordPattern)) {
            // userPassword.setError("Password must be include a digit, a lower, an upper, a special and no whitespace allowed.");
            TastyToast.makeText(getApplicationContext(), "Password must be include a digit, a lower, an upper, a special and no whitespace allowed...", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
        }
        if (!passwordCheck.equals(cPasswordCheck)) {
            // confirmPassword.setError("Confirm password does not match with the password.");
            TastyToast.makeText(getApplicationContext(), "Confirm password does not match with the password..", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
        } else {
            progressDialog.setMessage("Please Wait While Registration Complete.");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(emailCheck, passwordCheck).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        TastyToast.makeText(getApplicationContext(), "Registration Successfully.", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                    } else {
                        progressDialog.dismiss();
                        TastyToast.makeText(getApplicationContext(), "" + task.getException(), TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(Register.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Login.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }


}