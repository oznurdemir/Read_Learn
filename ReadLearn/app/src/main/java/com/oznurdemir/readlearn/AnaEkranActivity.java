package com.oznurdemir.readlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.oznurdemir.readlearn.databinding.ActivityAnaEkranBinding;
import com.oznurdemir.readlearn.model.MainActivity;

public class AnaEkranActivity extends AppCompatActivity {
    ActivityAnaEkranBinding binding;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Görünüm bağlama (view binding) kullanarak layout'u ayarlıyoruz.
        binding = ActivityAnaEkranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Araç çubuğunu (toolbar) eylem çubuğu olarak ayarlıyoruz
        setSupportActionBar(binding.toolbar);

        // FirebaseAuth örneğini başlatıyoruz
        auth = FirebaseAuth.getInstance();

        // İlk fragmenti BooksFragment ile değiştiriyoruz
        replaceFragment(new BooksFragment());

        // Alt gezinme görünümündeki öğe seçimini işliyoruz
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.book:
                    replaceFragment(new BooksFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new FeedbackFragment());
                    break;

            }
            return true;


        });
    }

    // Fragment'ı değiştirmek için kullanılan yardımcı metod
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        // FragmentManager üzerinden bir FragmentTransaction oluşturulur.
        // Bu transaction, fragment işlemlerini gerçekleştirmek için kullanılır.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Option menüyü şişiriyoruz (inflate ediyoruz)
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){

            // Çıkış yapmak isteyip istemediğimizi kullanıcıya soruyoruz
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Çıkış Yapmak İstediğinize Emin Misiniz?");
            alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Firebase'den çıkış yapıyoruz
                    auth.signOut(); // Firebase' e çıkış yaptığımızı haber veriyoruz.

                    // Giriş sayfasına yönlendirme yapılıyor
                    Intent intent = new Intent(AnaEkranActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();

        }
        return super.onOptionsItemSelected(item);
    }
}