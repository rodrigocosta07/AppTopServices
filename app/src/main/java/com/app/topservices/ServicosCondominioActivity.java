package com.app.topservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import Config.ConfiguracaoFirebase;
import Model.Servicos;

public class ServicosCondominioActivity extends AppCompatActivity {


    EditText titulo;
    EditText info;
    Button publicar;
    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos_condominio);

        titulo = findViewById(R.id.EdtTituloServico);
        info = findViewById(R.id.EdtInfoServico);
        publicar = findViewById(R.id.BtnPublicarServico);

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                String userId = null;
                if( autenticacao.getCurrentUser() != null){
                    userId = autenticacao.getCurrentUser().getUid();
                }
                Servicos servico = new Servicos();

                servico.setTitulo(titulo.getText().toString());
                servico.setInformacoes(info.getText().toString());
                servico.setIdCondo(userId);
                servico.salvar();
            }
        });

    }
}
