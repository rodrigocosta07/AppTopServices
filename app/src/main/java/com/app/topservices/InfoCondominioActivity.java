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

public class InfoCondominioActivity extends AppCompatActivity {
    private DatabaseReference referenciaFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_condominio);

        final TextView nomeCondo = findViewById(R.id.TxtCondominio);
        final TextView telefoneCondo = findViewById(R.id.TxtTelCondominio);
        final TextView responsavelCondo = findViewById(R.id.TxtResponsavel);
        final TextView emailCondo = findViewById(R.id.TxtEmailCondominio);
        final TextView enderecoCondo = findViewById(R.id.TxtEnderecoCondominio);

        Bundle extra = getIntent().getExtras();

        String idCondo  = extra.getString("IdCondo");


        referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("Condominio").child(idCondo);
        referenciaFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Condominio condominio = dataSnapshot.getValue(Condominio.class);
                    String nome = (String) dataSnapshot.child("nomeCondominio").getValue();
                    String email = (String) dataSnapshot.child("email").getValue();
                    String telefone = (String) dataSnapshot.child("telefone").getValue();
                    String nomeResponsavel = (String) dataSnapshot.child("nomeResponsavel").getValue();
                    String endereco = (String) dataSnapshot.child("endereco").getValue();
                    condominio.setNomeCondominio(nome);
                    condominio.setEmail(email);
                    condominio.setNomeResponsavel(nomeResponsavel);
                    condominio.setTelefone(telefone);
                    condominio.setEndereco(endereco);

                    nomeCondo.setText(condominio.getNomeCondominio());
                    responsavelCondo.setText(condominio.getNomeResponsavel());
                    telefoneCondo.setText(condominio.getTelefone());
                    emailCondo.setText(condominio.getEmail());
                    enderecoCondo.setText(condominio.getEndereco());

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
