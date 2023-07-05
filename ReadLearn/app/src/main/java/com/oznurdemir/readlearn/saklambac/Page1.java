package com.oznurdemir.readlearn.saklambac;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.oznurdemir.readlearn.R;
import com.squareup.picasso.Picasso;


public class Page1 extends Fragment {
    MediaPlayer mediaPlayer;



    public Page1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_page1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Firebase' den kitap sayfasını getirtiyoruz.
        ImageView imageView = view.findViewById(R.id.sayfa1Image);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F1.png?alt=media&token=5e478ad2-00fa-4167-9378-7ce6027bc377").into(imageView);

        // Ana sayfaya gitme
        Button button = view.findViewById(R.id.anaSayfaButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anaSayfaButton(view);
            }
        });

        // Sonraki sayfaya gitme
        Button button1 = view.findViewById(R.id.sonrakiSayfaButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonrakiSayfaButton(view);
            }
        });

        //Dinle butonu
        Button button2 = view.findViewById(R.id.dinle);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dinleButton(view);
            }
        });
    }

    public void anaSayfaButton(View view){
        NavDirections navDirections = Page1Directions.actionPage1ToAnaEkranActivity();
        Navigation.findNavController(view).navigate(navDirections);
    }

    public void dinleButton(View view){
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(getContext(),R.raw.saklambac1);
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopMusic();
            }
        });
        mediaPlayer.start();
    }

    private void stopMusic() {
        mediaPlayer.release();
        mediaPlayer = null;
    }



    public void sonrakiSayfaButton(View view){
        NavDirections action = Page1Directions.actionPage1ToPage2();
        Navigation.findNavController(view).navigate(action);

    }
}