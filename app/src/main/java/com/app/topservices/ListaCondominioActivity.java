package com.app.topservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;

import Config.ConfiguracaoFirebase;

public class ListaCondominioActivity extends AppCompatActivity {

    private DatabaseReference referenciaFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_condominio);

        referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("Condominio");

    }
}
