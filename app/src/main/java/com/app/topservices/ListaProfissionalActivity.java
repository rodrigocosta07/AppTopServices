package com.app.topservices;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Config.ConfiguracaoFirebase;
import Model.Condominio;
import Model.Profissional;

public class ListaProfissionalActivity extends AppCompatActivity {


    ArrayList<Profissional> profissionais;
    ListView listView;
    private DatabaseReference referenciaFirebase;
    private static CustomAdapterProfissional adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_profissional);

        listView = (ListView) findViewById(R.id.list);

        profissionais = new ArrayList<>();

        adapter = new CustomAdapterProfissional(profissionais,getApplicationContext());
        listView.setAdapter(adapter);

        referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("Profissional");
        referenciaFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profissionais.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren() ){
                    Profissional profissional = new Profissional();
                    String nomeProf = (String) dados.child("nome").getValue();
                    String Telefone = (String) dados.child("telefone").getValue();
                    String Email = (String ) dados.child("email").getValue();

                    profissional.setNome(nomeProf);
                    profissional.setTelefone(Telefone);
                    profissional.setEmail(Email);
                    profissionais.add(profissional);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
