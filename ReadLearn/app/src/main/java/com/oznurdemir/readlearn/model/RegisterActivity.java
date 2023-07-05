package com.oznurdemir.readlearn.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.oznurdemir.readlearn.AnaEkranActivity;
import com.oznurdemir.readlearn.User;
import com.oznurdemir.readlearn.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();// initialize
        database = FirebaseFirestore.getInstance();
    }

    public void kaydet(View view){
        String email = binding.registeremailEditText.getText().toString();
        String password = binding.registerPasswordEditText.getText().toString();
        //User user = new User(email,password);
        User myUser = new User(email);

        if( email.equals("") || password.equals("")){
            Toast.makeText(this, "Lütfen Mailinizi ve Şifrenizi Giriniz.", Toast.LENGTH_LONG).show();
        }else if(password.length() !=6){
            Toast.makeText(this, "Lütfen Şifre sadece 6 karakterden oluşsun!", Toast.LENGTH_SHORT).show();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "E-posta geçerli değil.", Toast.LENGTH_SHORT).show();
        }else{
            // İnternet sunucuları ile işlem yaptığımızdan asenkron işlem yapacağız. böylelikle kullanıcının arayüzünü kitlemiyoruz.
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    // Bir server' dan mesaj aldığımızda bu metod çalışıyor.
                    String uid = authResult.getUser().getUid();// Oluşturulan kullanıcının UID'sini alıyoruz
                    // myUser yerine user değişkenini koyarsak firestore'da gözüküyor şifre.
//60-64 arası sonradan eklendi.
                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Doğrulama adresi gönderildi.", Toast.LENGTH_SHORT).show();
                                database.collection("users").document(uid).set(myUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        // Firestore'a kullanıcı verilerini kaydediyoruz
                                        // Kaydedilen kullanıcının verilerini içeren bir User nesnesini Firestore'a ekler.


                                           Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                           startActivity(intent);
                                           finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Firestore'a veri kaydedilirken hata oluşursa bu metot çalışır
                                        Toast.makeText(RegisterActivity.this,e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else {
                                Toast.makeText(RegisterActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                   /* database.collection("users").document(uid).set(myUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            // Firestore'a kullanıcı verilerini kaydediyoruz
                            // Kaydedilen kullanıcının verilerini içeren bir User nesnesini Firestore'a ekler.


                         //   Intent intent = new Intent(RegisterActivity.this, AnaEkranActivity.class);
                           // startActivity(intent);
                            //finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Firestore'a veri kaydedilirken hata oluşursa bu metot çalışır
                            Toast.makeText(RegisterActivity.this,e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }); */


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Kayıt işlemi başarısız olursa bu metot çalışır
                    Toast.makeText(RegisterActivity.this,e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}