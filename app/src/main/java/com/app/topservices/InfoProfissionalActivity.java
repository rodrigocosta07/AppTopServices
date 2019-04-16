package com.app.topservices;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import Config.ConfiguracaoFirebase;
import Model.Condominio;
import Model.Profissional;

public class InfoProfissionalActivity extends AppCompatActivity {
    private DatabaseReference referenciaFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_profissional);


        final TextView nomeProf = findViewById(R.id.TxtProfissional);
        final TextView telefoneProf = findViewById(R.id.TxtTelProf);

        final TextView emailProf = findViewById(R.id.TxtEmailProf);
        final TextView  cidadeProf = findViewById(R.id.TxtCidadeProf);

        Bundle extra = getIntent().getExtras();

        String idProf  = extra.getString("IdProf");


        referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("Profissional").child(idProf);
        referenciaFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Profissional profissional = dataSnapshot.getValue(Profissional.class);
                    String nome = (String) dataSnapshot.child("nome").getValue();
                    String email = (String) dataSnapshot.child("email").getValue();
                    String telefone = (String) dataSnapshot.child("telefone").getValue();
                    String cidade = (String) dataSnapshot.child("cidade").getValue();
                    profissional.setNome(nome);
                    profissional.setEmail(email);
                    profissional.setTelefone(telefone);
                    profissional.setCidade(cidade);

                    nomeProf.setText(profissional.getNome());
                    telefoneProf.setText(profissional.getTelefone());
                    emailProf.setText(profissional.getEmail());
                    cidadeProf.setText(profissional.getCidade());

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
