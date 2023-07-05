package com.oznurdemir.readlearn.saklambac;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oznurdemir.readlearn.AnaEkranActivity;
import com.oznurdemir.readlearn.R;
import com.oznurdemir.readlearn.ResultActivity;
import com.oznurdemir.readlearn.databinding.ActivitySaklambacSorularBinding;
import com.oznurdemir.readlearn.model.Question;

import java.util.ArrayList;

public class SaklambacSorular extends AppCompatActivity {
    ActivitySaklambacSorularBinding binding;
    ArrayList<Question> questions;
    int index = 0, correctAnswers = 0;
    Question question;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySaklambacSorularBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        questions = new ArrayList<>();

        questions.add(new Question("Soner ve Mehmet Bey ....... tanıştılar?","Alışverişte","Tatilde","İşte","Tatilde"));
        questions.add(new Question("....... yanında biraz bekledi.","Hastanenin","Pastanenin","Postanenin","Postanenin"));
        questions.add(new Question("Mustafa Bey' in kızının adı ......... .","Beyza","Melis","Eda","Melis"));
        questions.add(new Question("Beyza .......... yaşında.","20","21","22","21"));
        questions.add(new Question("Soner ve Mehmet buluşacak çünkü onlar .............. .","kardeş","komşu","arkadaş","arkadaş"));
        questions.add(new Question("Eda' nın annesi çok sıkıldı çünkü hava ........... .","sıcaktı","soğuktu","ılıktı","sıcaktı"));
        resetTimer();
        setNextQuestion();
    }


    void setNextQuestion(){
     /*   if(timer != null){
            timer.cancel();
        }*/
        timer.start();// Yeni soruya geçildiğinde zamanlayıcıyı başlatır
        if(index < questions.size()){
            binding.questionCounter.setText(String.format("%d/%d",(index+1),questions.size()));// Soru sayacını günceller
            question = questions.get(index);// İndexe karşılık gelen soruyu alır
            binding.questionText.setText(question.getQuestion());// Soru metnini ekranda gösterir
            binding.option1Text.setText(question.getOption1());// Seçenek 1'i ekranda gösterir
            binding.option2Text.setText(question.getOption2());// Seçenek 2'yi ekranda gösterir
            binding.option3Text.setText(question.getOption3());// Seçenek 3'ü ekranda gösterir

        }
    }

    void checkAnswer(TextView textView){
        String selectedAnswer = textView.getText().toString();
        if(selectedAnswer.equals(question.getAnswer())){
            correctAnswers++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        }else{
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }

    void reset(){
        binding.option1Text.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2Text.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3Text.setBackground(getResources().getDrawable(R.drawable.option_unselected));
    }

    void showAnswer(){
        if(question.getAnswer().equals(binding.option1Text.getText().toString())){
            binding.option1Text.setBackground(getResources().getDrawable(R.drawable.option_right));
        }else if(question.getAnswer().equals(binding.option2Text.getText().toString())){
            binding.option2Text.setBackground(getResources().getDrawable(R.drawable.option_right));
        }else if(question.getAnswer().equals(binding.option3Text.getText().toString())){
            binding.option3Text.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
    }
    void resetTimer(){
        timer =new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Her güncelleme aralığında çağrılır, kalan süreyi ekranda gösterir
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                // Geri sayım tamamlandığında çağrılır
                AlertDialog.Builder alert = new AlertDialog.Builder(SaklambacSorular.this);
                alert.setTitle("SÜRE BİTTİ !");
                alert.setMessage("Maalesef Verilen Süre Bitti.");
                alert.setIcon(R.drawable.oku_ogren_logo);
                alert.setCancelable(false);

                // "Sonucu Gör" butonuna tıklandığında gerçekleştirilecek işlemler
                alert.setNegativeButton("Sonucu Gör", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SaklambacSorular.this,ResultActivity.class);
                        intent.putExtra("correct",correctAnswers);
                        startActivity(intent);
                    }
                });

                // "Ana Sayfaya Git" butonuna tıklandığında gerçekleştirilecek işlemler
                alert.setPositiveButton("Ana Sayfaya Git", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // AnaEkranActivity'yi başlatır ve mevcut aktiviteyi sonlandırır
                        Intent intent = new Intent(SaklambacSorular.this, AnaEkranActivity.class);
                        startActivity(intent);
                    }
                });
                // İletişim kutusunu gösterir
                alert.show();

            }
        };
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.option1Text:
            case R.id.option2Text:
            case R.id.option3Text:
               /* if(timer != null){
                    timer.cancel();
                }*/
                // Kullanıcının bir seçenek üzerine tıkladığında gerçekleştirilecek işlemler
                TextView selected = (TextView) view;
                checkAnswer(selected);
                break;
            case R.id.nextBtn:
                // "Sonraki Soru" veya "Testi Bitir" butonuna tıklandığında gerçekleştirilecek işlemler
                reset();// Seçilen seçeneğin arkaplanını sıfırlar
                index++;// Bir sonraki soruya geçmek için indeksi artırır
                if(index < questions.size()){
                    // Hala soruların üzerinden geçilecekse
                    if(index == 5)
                        binding.nextBtn.setText("Testi Bitir");// 5. soruda ise buton metnini "Testi Bitir" olarak değiştirir
                    else
                        binding.nextBtn.setText("Sonraki Soru");// Diğer durumlarda buton metnini "Sonraki Soru" olarak değiştirir
                    setNextQuestion();// Bir sonraki soruyu ayarlar
                }else{
                    Intent intent = new Intent(SaklambacSorular.this, ResultActivity.class);
                    intent.putExtra("correct",correctAnswers); // Doğru cevap sayısını intent ile ResultActivity'ye aktarır
                    startActivity(intent); // ResultActivity'yi başlatır
                    Toast.makeText(this, "Test Bitti.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.quitBtn:
                // Çık butonuna basıldığında ana sayfaya dön.
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("OkuÖğren");
                alert.setIcon(R.drawable.oku_ogren_logo);
                alert.setMessage("Çıkmak İstediğinize Emin Misiniz?");
                alert.setCancelable(false);// Alertte seçeneklerden birini seçmeyi zorunlu kılar.
                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), AnaEkranActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;



        }
    }
    



}