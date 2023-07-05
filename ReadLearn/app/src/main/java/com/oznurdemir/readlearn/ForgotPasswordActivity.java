package com.oznurdemir.readlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.oznurdemir.readlearn.model.MainActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Kullanıcı arayüzündeki bileşenlerin referanslarını alıyoruz
        emailEditText = (EditText) findViewById(R.id.emailText);
        resetPasswordButton = (Button) findViewById(R.id.buttonPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // FirebaseAuth sınıfından bir nesne oluşturuyoruz
        auth = FirebaseAuth.getInstance();


        // Şifre sıfırlama butonuna tıklama olayını dinliyoruz
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

    }

    // Şifreyi sıfırlama işlemini gerçekleştiren metod
    private void reset() {
        // E-posta adresini alıyoruz
        String email = emailEditText.getText().toString().trim();

        // E-posta alanının boş olup olmadığını kontrol ediyoruz
        if(email.isEmpty()){
            emailEditText.setError("Hata!");
            emailEditText.requestFocus();
            return;
        }

        // Girilen e-posta adresinin geçerli bir e-posta adresi olup olmadığını kontrol ediyoruz
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("lütfen geçerli bir e-posta adresi giriniz!");
            emailEditText.requestFocus();
            return;
        }

        // İlerleme çubuğunu görünür hale getiriyoruz
        progressBar.setVisibility(View.VISIBLE);

        // FirebaseAuth nesnesi üzerinden şifre sıfırlama e-postası gönderiyoruz
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // E-posta başarıyla gönderildiğinde kullanıcıya bir mesaj gösteriyoruz
                    Toast.makeText(ForgotPasswordActivity.this, "Mail gönderildi.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    // E-posta gönderilemediğinde kullanıcıya bir hata mesajı gösteriyoruz
                    Toast.makeText(ForgotPasswordActivity.this, "Mail gönderilmedi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}