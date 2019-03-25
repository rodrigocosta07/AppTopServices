package com.app.topservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import Config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {


    private Button Sair;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Sair = (Button) findViewById(R.id.BtnSair);

        Sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}
