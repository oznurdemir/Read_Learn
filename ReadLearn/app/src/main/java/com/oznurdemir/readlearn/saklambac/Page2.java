package com.oznurdemir.readlearn.saklambac;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.oznurdemir.readlearn.R;
import com.squareup.picasso.Picasso;


public class Page2 extends Fragment {
    MediaPlayer mediaPlayer;// MediaPlayer sınıfından bir nesne tanımlanıyor.
    int sayac = 0;// sayac değişkeni tanımlanıyor.
    Button buttonSonraki; // Button sınıfından bir nesne tanımlanıyor.


    public Page2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = null;// mediaPlayer değişkeni null olarak atanıyor.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page2, container, false);
        // buttonSonraki nesnesi, fragment_page2 layoutundaki R.id.sonrakiSayfaButton2 öğesiyle ilişkilendiriliyor.
        buttonSonraki = view.findViewById(R.id.sonrakiSayfaButton2);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // imageView nesnesi, fragment_page2 layoutundaki R.id.sayfa1Image öğesiyle ilişkilendiriliyor.
        ImageView imageView = view.findViewById(R.id.sayfa1Image);
        // Picasso kütüphanesi kullanılarak resim yükleniyor.
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F2.png?alt=media&token=5f74cae0-bacc-4cc1-920f-e478d2805f4d").into(imageView);


        buttonSonraki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sonrakiSayfaButton(view);// buttonSonraki nesnesine tıklandığında sonrakiSayfaButton metodunu çağırır.
            }
        });

        Button button1 = view.findViewById(R.id.geriButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                geri(view);

            }
        });

        Button button2 = view.findViewById(R.id.dinle1);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dinleButton(view);
            }
        });
    }

    public void geri(View view){
        // Sayaca göre görüntü ve düğme özellikleri değiştirilir, gerekli resimler yüklenir ve sayacın değeri azaltılır.
        // NavDirections ve Navigation işlemleri vardır.
        // Müzik durdurulur.
       // NavDirections action = Page2Directions.actionPage2ToPage1();
        //Navigation.findNavController(view).navigate(action);
        Button button = view.findViewById(R.id.sonrakiSayfaButton2);
        ImageView imageView = view.findViewById(R.id.sayfa1Image);
        if(sayac == 7) {
            button.setText("İleri");
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F9.png?alt=media&token=469fc332-eaa3-4cd2-b9ca-d41ad65a642e").into(imageView);
            sayac--;
        }else if(sayac == 6) {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F7.png?alt=media&token=2b8bbda9-6ef9-49a8-8ab5-29ffc2de6b5b").into(imageView);
            sayac--;
        }else if(sayac == 5) {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F6.png?alt=media&token=8c463892-61e0-4aaa-9246-1eaf432aeb22").into(imageView);
            sayac--;
        }else if(sayac == 4) {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F5.png?alt=media&token=478f45f6-7c37-4ab2-bf35-6e5a060d6282").into(imageView);
            sayac--;
        }else if(sayac == 3) {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F4.png?alt=media&token=cdeb383f-4b48-400d-9130-9b47c71b9847").into(imageView);
            sayac--;
        }else if(sayac == 2){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F3.png?alt=media&token=e2df77c0-342b-4811-8249-cdc3f250d5a4").into(imageView);
            sayac--;
        }else if(sayac == 1){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F2.png?alt=media&token=5f74cae0-bacc-4cc1-920f-e478d2805f4d").into(imageView);
            sayac--;
        }else if(sayac == 0){
             NavDirections action = Page2Directions.actionPage2ToPage1();
             Navigation.findNavController(view).navigate(action);
        }
        stopMusic();

    }

    public void dinleButton(View view){
        //mediaPlayer=null;
        // mediaPlayer null kontrolü
        if(mediaPlayer == null){
            // mediaPlayer null kontrolü
            if(sayac == 0){
                mediaPlayer = MediaPlayer.create(getContext(),R.raw.saklmbac2);
            }else if(sayac == 3){
                mediaPlayer = MediaPlayer.create(getContext(),R.raw.saklambac5);
            }else if(sayac == 4){
                mediaPlayer = MediaPlayer.create(getContext(),R.raw.saklambac6);
            }else if(sayac == 6){
                mediaPlayer = MediaPlayer.create(getContext(),R.raw.saklambac9);
            }else if(sayac == 7){
                mediaPlayer = MediaPlayer.create(getContext(),R.raw.saklambac10);
            }else{
                // sayac değeri 1, 2 veya 5 ise metin yok hatası
                Toast.makeText(getContext(), "METİN YOK!", Toast.LENGTH_SHORT).show();
            }
        }
        // sayac 1, 2 veya 5 değilse müziği çal
        if(sayac != 1 && sayac != 2 && sayac != 5){
            // müzik tamamlandığında çalışacak dinleyici
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // müzik tamamlandığında çalışacak dinleyici
                    stopMusic();
                }
            });
            // müziği başlat
            mediaPlayer.start();
        }
        //mediaPlayer.start();


    }

    private void stopMusic() {
        // mediaPlayer null değilse
        if(mediaPlayer != null) {
            // kaynakları serbest bırak ve mediaPlayer'ı null yap
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void sonrakiSayfaButton(View view){

        ImageView imageView = view.findViewById(R.id.sayfa1Image);
        if(sayac == 0){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F3.png?alt=media&token=e2df77c0-342b-4811-8249-cdc3f250d5a4").into(imageView);
            sayac++;
        }else if(sayac == 1){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F4.png?alt=media&token=cdeb383f-4b48-400d-9130-9b47c71b9847").into(imageView);
            sayac++;
        }else if(sayac == 2){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F5.png?alt=media&token=478f45f6-7c37-4ab2-bf35-6e5a060d6282").into(imageView);
            sayac++;
        }else if(sayac == 3){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F6.png?alt=media&token=8c463892-61e0-4aaa-9246-1eaf432aeb22").into(imageView);
            sayac++;
        }else if(sayac == 4){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F7.png?alt=media&token=2b8bbda9-6ef9-49a8-8ab5-29ffc2de6b5b").into(imageView);
            sayac++;
        }else if(sayac == 5){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F9.png?alt=media&token=469fc332-eaa3-4cd2-b9ca-d41ad65a642e").into(imageView);
            sayac++;
        }else if(sayac == 6){
            buttonSonraki.setText("Test Çöz");
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/readlearn-4e27e.appspot.com/o/saklambac%2F10.png?alt=media&token=53a9d265-41c6-4d94-914e-c213017ef9b8").into(imageView);
            sayac++;
        }else if(sayac == 7){
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("OkuÖğren");
            alert.setMessage("Ölçme Değerlendirmeye Geçmek İstediğinize Emin Misiniz?");
            // Alert diyalogta seçeneklerden bir tanesini seçmeyi zorunlu hale getir.
            alert.setCancelable(false);
            alert.setIcon(R.drawable.oku_ogren_logo);
            alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                     NavDirections navDirections = Page2Directions.actionPage2ToSaklambacSorular();
                     Navigation.findNavController(view).navigate(navDirections);
                }
            });
            alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();// Alert diyaloğu kapat.
                }
            });

            alert.show();

        }
        stopMusic();
        //mediaPlayer.stop();
        //Log.d("mesaj",sayac+"");
        //mediaPlayer=null;
    }
}