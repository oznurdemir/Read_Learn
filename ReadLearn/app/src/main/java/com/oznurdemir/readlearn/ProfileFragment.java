package com.oznurdemir.readlearn;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {
    Button updateMailBtn, updatePsswordBtn;
    EditText mailText, passwordText,oldpasswordText;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    public ProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        // Firebase Firestore ve FirebaseAuth örneklerini alın
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mailText = view.findViewById(R.id.emailText);
        passwordText = view.findViewById(R.id.sifreText);
        oldpasswordText = view.findViewById(R.id.eskiSifreText);

        updateMailBtn = view.findViewById(R.id.mailGuncelleBtn);
        updateMailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMail();
            }
        });

        updatePsswordBtn = view.findViewById(R.id.sifreGuncelleBtn);
        updatePsswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });


        return view;
    }

    private void updatePassword() {
        String newPassword = passwordText.getText().toString().trim();
        String oldPassword = oldpasswordText.getText().toString().trim();

        FirebaseUser user = mAuth.getCurrentUser();
        //DocumentReference userRef = db.collection("users").document(user.getUid());

        // Kullanıcı oturumu alınmışsa şifreyi güncelle
        if(TextUtils.isEmpty(oldPassword)){
            Toast.makeText(getContext(), "Lütfen mevcut şifrenizi giriniz.", Toast.LENGTH_SHORT).show();
            return;
        }else if(newPassword.length()!=6){
            Toast.makeText(getContext(), "Lütfen şifre 6 haneden oluşsun.", Toast.LENGTH_SHORT).show();
            return;
        }else if(newPassword.equals(oldPassword)){
            Toast.makeText(getContext(), "Yeni şifre ile mevcut şifre aynı olamaz.", Toast.LENGTH_SHORT).show();
            return;
        }else if (user != null && !TextUtils.isEmpty(newPassword)) {
            user.updatePassword(newPassword)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Şifre güncellendi.", Toast.LENGTH_SHORT).show();

                            /*userRef.update("password",newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getContext(), "Şifre Güncellendi.", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }*/;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void updateMail(){
        String newMail = mailText.getText().toString();


        FirebaseUser user = mAuth.getCurrentUser();
        DocumentReference userRef = db.collection("users").document(user.getUid());

        if(user != null && !TextUtils.isEmpty(newMail)){
            user.updateEmail(newMail)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            userRef.update("mail",newMail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getContext(), "E-Mail Güncellendi.", Toast.LENGTH_SHORT).show();
                                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getContext(), "Doğrulama adresi gönderildi.", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    Toast.makeText(getContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }else{
                                        Toast.makeText(getContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }




}