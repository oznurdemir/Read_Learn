package com.oznurdemir.readlearn;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.oznurdemir.readlearn.model.BooksPage;
import com.oznurdemir.readlearn.saklambac.SaklambacDetail;


public class BooksFragment extends Fragment {
    private FirebaseAuth auth;

    public BooksFragment() {
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
        auth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView saklambacImageView = view.findViewById(R.id.saklambac);
        ImageView bisikletImageView = view.findViewById(R.id.bisiklet);
        ImageView gizemliHaritaImageView = view.findViewById(R.id.gizemliHarita);
        ImageView periBacalarıImageView = view.findViewById(R.id.periBacalari);


        saklambacImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SaklambacDetail.class);
                startActivity(intent);
            }
        });

        bisikletImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        gizemliHaritaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        periBacalarıImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


}