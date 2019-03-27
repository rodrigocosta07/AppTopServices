package com.app.topservices;

import android.app.ListActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Config.ConfiguracaoFirebase;
import Model.Profissional;

public class LoginActivity extends AppCompatActivity {


    private EditText Login;
    private EditText Senha;
    private Button Entrar;
    private String userLogin;
    private String userSenha;
    private DatabaseReference referenciaFirebase;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        Login = (EditText) findViewById(R.id.EdtLogin);
        Senha = (EditText) findViewById(R.id.EdtSenha);
        Entrar = (Button) findViewById(R.id.BtnLogin);


        final Button Registrar = (Button) this.findViewById(R.id.BtnCadastrar);


        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrarActivity.class);
                startActivity(intent);

            }
        });

        Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Login.getText().toString().isEmpty() && Senha.getText().toString().isEmpty() ){
                    Toast.makeText(LoginActivity.this, "Digite o seu email e senha" , Toast.LENGTH_LONG).show();
                }else if (Login.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Digite o seu email" , Toast.LENGTH_LONG).show();
                }else if(Senha.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Digite sua senha" , Toast.LENGTH_LONG).show();
                }else{
                    userLogin = Login.getText().toString();
                    userSenha = Senha.getText().toString();
                    validarLogin();
                }


            }


        });



    }
    private void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if( autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    private void validarLogin() {
            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.signInWithEmailAndPassword(
                    userLogin,
                    userSenha
            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if( task.isSuccessful()){
                        String userId = task.getResult().getUser().getUid();
                        consultaUser(userId);
                        abrirTelaPrincipal();
                        Toast.makeText(LoginActivity.this, "Login efetuado com sucesso", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Erro ao efetuar o login" , Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    private void consultaUser(String id){
        referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("Condominio").child(id);
        if(referenciaFirebase != null){
            Toast.makeText(LoginActivity.this, "profissional" , Toast.LENGTH_LONG).show();
        }
        referenciaFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("FIRE", dataSnapshot.getChildren().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("FIRE" , "Erro");

            }
        });
    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, ListaCondominioActivity.class);
        startActivity(intent);
        finish();
    }
}
