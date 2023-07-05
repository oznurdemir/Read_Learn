package com.oznurdemir.readlearn.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.oznurdemir.readlearn.R;
import com.oznurdemir.readlearn.saklambac.SaklambacDetail;

public class BooksPage extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_page);

        auth = FirebaseAuth.getInstance();
    }

    public void saklambac(View view){
        Intent intent = new Intent(BooksPage.this, SaklambacDetail.class);
        startActivity(intent);
        finish();

    }

    public void bisiklet(View view){

    }

    public void gizemliHarita(View view){

    }

    public void periBacalari(View view){

    }

    // Menüye bağlama işlemi yapılır.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater(); // xml' e bağlamamıza yarayan nesne oluşturdum.
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // seçenekleri seçince ne olacağı yazılır.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
            auth.signOut(); // Firebase' e çıkış yaptığımızı haber veriyoruz.

            Intent intent = new Intent(BooksPage.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}