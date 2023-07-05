package com.oznurdemir.readlearn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class FeedbackFragment extends Fragment {

    private RatingBar ratingBar;// RatingBar bileşeni için referans
    private EditText feedbackEditText;// EditText bileşeni için referans
    private Button submitButton;// Button bileşeni için referans

    private FirebaseAuth mAuth;// Firebase Authentication referansı
    private FirebaseFirestore firebase;// Firestore referansı
    private FirebaseUser currentUser; // Geçerli kullanıcı referansı

    public FeedbackFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        ratingBar = view.findViewById(R.id.ratingBar); // RatingBar bileşenini layouttan alıyoruz
        feedbackEditText = view.findViewById(R.id.feedbackEditText); // EditText bileşenini layouttan alıyoruz
        submitButton = view.findViewById(R.id.submitButton);// Button bileşenini layouttan alıyoruz


        mAuth = FirebaseAuth.getInstance();// FirebaseAuth referansını alıyoruz
        firebase = FirebaseFirestore.getInstance();// Firestore referansını alıyoruz
        currentUser = mAuth.getCurrentUser(); // Geçerli kullanıcıyı alıyoruz

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
        // submitButton'a tıklandığında sendFeedback() yöntemini çağırıyoruz
        return view;
    }

    private void sendFeedback() {
        float rating = ratingBar.getRating();// RatingBar'dan seçilen puanı alıyoruz
        String feedback = feedbackEditText.getText().toString();// EditText'ten geri bildirim metnini alıyoruz

        if(!feedback.isEmpty()){// Geri bildirim metni boş değilse devam ediyoruz
            String userEmail = currentUser.getEmail();// Geçerli kullanıcının emailini alıyoruz

            Map<String,Object> feedbackData = new HashMap<>();// Geri bildirim verilerini içeren bir Map oluşturuyoruz
            feedbackData.put("email",userEmail);
            feedbackData.put("rating",rating);
            feedbackData.put("message",feedback);

            firebase.collection("Feedback")// "Feedback" koleksiyonuna erişiyoruz
                    .add(feedbackData)// Geri bildirim verilerini koleksiyona ekliyoruz
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getContext(), "Geri Bildiriminiz Başarıyla İletildi.", Toast.LENGTH_SHORT).show();
                    feedbackEditText.setText("");
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