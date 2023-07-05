package com.oznurdemir.readlearn.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oznurdemir.readlearn.AnaEkranActivity;
import com.oznurdemir.readlearn.ForgotPasswordActivity;
import com.oznurdemir.readlearn.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.forgotPasswordEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();// initialize

        FirebaseUser user = auth.getCurrentUser(); // Daha önce giriş yapmıs bir kullanıcı varsa bunu verecektir.
        // user.isEmailVerified doğrulama için ekledim.
        if( user != null && user.isEmailVerified()){
            Intent intent = new Intent(MainActivity.this, AnaEkranActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void login(View view){
        String email = binding.emailEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();
        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Lütfen Mailinizi ve Şifrenizi Giriniz.", Toast.LENGTH_LONG).show();
        }else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    //64. satır sonradan eklendi.
                    if(auth.getCurrentUser().isEmailVerified()){
                        Intent intent = new Intent(MainActivity.this, AnaEkranActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, "Lütfen e-postanızı doğrulayınız.", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public void logup(View view){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
        //finish();

    }
}