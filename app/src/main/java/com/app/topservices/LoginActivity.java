package com.app.topservices;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import Config.ConfiguracaoFirebase;

import static android.view.View.GONE;

public class LoginActivity extends AppCompatActivity {


    private EditText Login;
    private EditText Senha;
    private Button Entrar;
    private String userLogin;
    private String userSenha;
    private DatabaseReference referenciaFirebase;
    private FirebaseAuth autenticacao;
    ProgressBar mProgressBar;
    Integer count =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verificarNet();
        verificarUsuarioLogado();

        Login = (EditText) findViewById(R.id.EdtLogin);
        Senha = (EditText) findViewById(R.id.EdtSenha);
        Entrar = (Button) findViewById(R.id.BtnLogin);


        final Button Registrar = (Button) this.findViewById(R.id.BtnCadastrar);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);


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
                    Login.requestFocus();
                }else if (Login.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Digite o seu email" , Toast.LENGTH_LONG).show();
                    Login.requestFocus();
                }else if(Senha.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Digite sua senha" , Toast.LENGTH_LONG).show();
                    Senha.requestFocus();
                }else{
                    userLogin = Login.getText().toString();
                    userSenha = Senha.getText().toString();
                    //validarLogin();
                    count = 1;
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(0);
                    new MinhaTask(view.getContext()).execute(10);
                }


            }


        });
    }

    private void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if( autenticacao.getCurrentUser() != null){
            String userId = autenticacao.getCurrentUser().getUid();
            consultaUser(userId);
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
                        Toast.makeText(LoginActivity.this, "Login efetuado com sucesso", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Erro ao efetuar o login" , Toast.LENGTH_LONG).show();
                    }
                }
            });

    }

    private void consultaUser(String id){

        referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("Condominio").child(id);
        referenciaFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                   abrirTelaPrincipalProfissional();
               }else{
                   abrirTelaPrincipalCondominio();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void abrirTelaPrincipalCondominio(){
        Intent intent = new Intent(LoginActivity.this, ListaCondominioActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirTelaPrincipalProfissional(){
        Intent intent = new Intent(LoginActivity.this, ListaProfissionalActivity.class);
        startActivity(intent);
        finish();
    }

    private void verificarNet(){
        ConnectivityManager conexao = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = conexao.getActiveNetworkInfo();

        if(netinfo != null && netinfo.isConnectedOrConnecting()){
            Toast.makeText(LoginActivity.this, "Você está conectado a internet", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(LoginActivity.this, "Você não está conectado a internet", Toast.LENGTH_LONG).show();
        }
    }

    private void exibirProgress(boolean exibir) {
        mProgressBar.setVisibility(exibir ? View.VISIBLE : GONE);
    }

    private class MinhaTask extends AsyncTask<Integer, Integer, String> {
        private Context context;
        public MinhaTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Integer... params) {
            validarLogin();
            for(; count <= params[0]; count++ ){
                try{
                    Thread.sleep(800);
                    publishProgress(count);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            Entrar.setEnabled(false);
        }

        @Override
        protected void onPostExecute(String retorno) {
            mProgressBar.setVisibility(View.GONE);
            Entrar.setEnabled(true);
        }
        @Override
        protected  void onProgressUpdate(Integer... values){
            mProgressBar.setProgress(values[0]);
        }
    }
}
