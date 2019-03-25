package com.app.topservices;

import android.app.ListActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import Config.ConfiguracaoFirebase;
import Model.Profissional;

public class LoginActivity extends AppCompatActivity {


    private EditText Login;
    private EditText Senha;
    private Button Entrar;
    private String userLogin;
    private String userSenha;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                userLogin = Login.getText().toString();
                userSenha = Senha.getText().toString();
                validarLogin();
            }


        });



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
                        Toast.makeText(LoginActivity.this, "Login efetuado com sucesso", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Erro ao efetuar o login" , Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
}
