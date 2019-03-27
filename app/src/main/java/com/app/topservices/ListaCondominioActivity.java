package com.app.topservices;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Config.ConfiguracaoFirebase;
import Model.Condominio;

public class ListaCondominioActivity extends AppCompatActivity {


    ArrayList<Condominio> condominios;
    ListView listView;
    private DatabaseReference referenciaFirebase;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_condominio);

        listView = (ListView) findViewById(R.id.list);

        condominios = new ArrayList<>();

        adapter = new CustomAdapter(condominios,getApplicationContext());

        listView.setAdapter(adapter);
        referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("Condominio");
        referenciaFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                condominios.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren() ){
                    Condominio condominio = new Condominio();
                    String nomeCondo = (String) dados.child("nomeCondominio").getValue();
                    condominio.setNomeCondominio(nomeCondo);
                    condominios.add(condominio);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
