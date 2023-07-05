package com.oznurdemir.readlearn;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.oznurdemir.readlearn.AnaEkranActivity;
import com.oznurdemir.readlearn.R;
import com.oznurdemir.readlearn.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {
    ActivityResultBinding binding;
    MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        int correctAnswer= intent.getExtras().getInt("correct");

        if(correctAnswer >= 3) {
            binding.commitTextView.setText("TEBRİKLER");
            if(mediaPlayer == null)
                mediaPlayer = MediaPlayer.create(this,R.raw.win);



        }else{
                binding.commitTextView.setText("ÜZGÜNÜM");
                binding.lottieAnimationWin.setAnimation(R.raw.you_lose);
            if(mediaPlayer == null)
                mediaPlayer = MediaPlayer.create(this,R.raw.lose);
            }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopMusic();
            }
        });
        mediaPlayer.start();

        binding.skorTextView.setText("Skor: " + correctAnswer + "/6");

    }

    private void stopMusic() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void goHomePage(View view){
        Intent intent = new Intent(this, AnaEkranActivity.class);
        startActivity(intent);
        finish();
    }
}