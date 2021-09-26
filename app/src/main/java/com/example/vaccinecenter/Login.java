package com.example.vaccinecenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sdsmdg.tastytoast.TastyToast;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class Login extends AppCompatActivity {

    CircularProgressButton login;
    TextView register_tex;


    EditText userEmail, userPassword;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}";

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.cirLoginButton);
        register_tex = findViewById(R.id.register_text);


        userEmail = findViewById(R.id.editTextEmail);
        userPassword = findViewById(R.id.editTextPassword);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Login.this, Admin_NavBar.class);
//                startActivity(intent);

                String emailCheck = userEmail.getText().toString();
                String passwordCheck = userPassword.getText().toString();

                if (emailCheck.isEmpty() || emailCheck == null) {
                    //userEmail.setError("Email must be filled.");
                    TastyToast.makeText(getApplicationContext(), "Email can not be empty..", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                }

                if (passwordCheck.isEmpty() || passwordCheck == null) {
//                    userPassword.setError("Password must be filled.");
                    TastyToast.makeText(getApplicationContext(), "Password can not be empty..", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                } else {

                    CheckValidationOfInputs();
                }
            }
        });

    }

    private void CheckValidationOfInputs() {
        String emailCheck = userEmail.getText().toString();
        String passwordCheck = userPassword.getText().toString();

        //System.out.println(userEmail.getText().toString());


        if (!emailCheck.matches(emailPattern)) {
            userEmail.setError("Email is not a valid email. Please enter valid email.");
        }
        if (!passwordCheck.matches(passwordPattern)) {
            userPassword.setError("Password must be include a digit, a lower, an upper, a special and no whitespace allowed.");
        } else {
            progressDialog.setMessage("Please Wait While Login Complete.");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            if (emailCheck.matches("admin@gmail.com") && passwordCheck.equals("Admin@1234")) {
                progressDialog.dismiss();
                Toast.makeText(Login.this, "Admin Login Successfully.", Toast.LENGTH_SHORT).show();
                sendAdminToNextActivity();
            } else {
                firebaseAuth.signInWithEmailAndPassword(emailCheck, passwordCheck).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();

                            //Toast.makeText(Login.this, "Login Successfully.", Toast.LENGTH_SHORT).show();
                            TastyToast.makeText(getApplicationContext(), "Login Successfully.", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                            sendUserToNextActivity(firebaseUser);

                        } else {
                            progressDialog.dismiss();
                            //Toast.makeText(Login.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            TastyToast.makeText(getApplicationContext(), "" + task.getException(), TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                        }
                    }
                });

            }

        }
    }


    private void sendUserToNextActivity( FirebaseUser currentUser) {
        Intent intent = new Intent(Login.this, Customer_Navbar.class);
        intent.putExtra("email", currentUser.getEmail());
        //intent.putExtra("uid", currentUser.getUid());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void sendAdminToNextActivity() {
        Intent intent = new Intent(Login.this, Admin_NavBar.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Register.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
}