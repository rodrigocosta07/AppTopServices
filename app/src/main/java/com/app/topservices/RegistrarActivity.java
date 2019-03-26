package com.app.topservices;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Config.ConfiguracaoFirebase;
import Model.Condominio;
import Model.Profissional;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class RegistrarActivity extends AppCompatActivity {

    private DatabaseReference referenciaFirebase;
    private FirebaseAuth autenticacao;

    private EditText NomeCondo;
    private EditText NomeResp;
    private EditText EnderecoCondo;
    private EditText telefoneCondo;
    private EditText EmailCondo;
    private EditText SenhaCondo;
    private Button CadastrarCondo;


    private EditText NomeProf;
    private EditText Cidade;
    private EditText telefoneProf;
    private EditText EmailProf;
    private EditText SenhaProf;
    private Button CadastrarProf;

    private Profissional profissional;
    private Condominio condominio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);




        NomeCondo = (EditText) findViewById(R.id.TxtNomeCondo);
        NomeResp = (EditText) findViewById(R.id.TxtNomeResp);
        EnderecoCondo = (EditText) findViewById(R.id.TxtEnderecoCondo);
        telefoneCondo = (EditText) findViewById(R.id.TxtTelCondo);
        EmailCondo = (EditText) findViewById(R.id.TxtEmailCondo);
        SenhaCondo = (EditText) findViewById(R.id.TxtSenhaCondo);
        CadastrarCondo = (Button) findViewById(R.id.BtnCadastrarCondo);

        NomeProf = (EditText) findViewById(R.id.TxtNome);
        Cidade = (EditText) findViewById(R.id.TxtCidadeProfissional);
        telefoneProf = (EditText) findViewById(R.id.TxtTelProfissional);
        EmailProf = (EditText) findViewById(R.id.TxtEmailProfissional);
        SenhaProf = (EditText) findViewById(R.id.TxtSenhaProfissional);
        CadastrarProf = (Button) findViewById(R.id.BtnCadastrarProf);


       final RadioGroup group = (RadioGroup) findViewById(R.id.RadioGroup);
       final LinearLayout Condo = (LinearLayout) findViewById(R.id.FormCondominio);

       final LinearLayout Prof = (LinearLayout) findViewById(R.id.FormProfissional);

       Condo.setVisibility(View.GONE);

       group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int i) {
              if(i == R.id.RadioCondominio){
                  Prof.setVisibility(View.INVISIBLE);
                  Condo.setVisibility(View.VISIBLE);
              }else if(i == R.id.RadioProfissional){
                  Condo.setVisibility(View.GONE);
                  Prof.setVisibility(View.VISIBLE);
              }

           }
       });

       CadastrarCondo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                condominio = new Condominio();
                condominio.setNomeCondominio(NomeCondo.getText().toString());
               condominio.setNomeResponsavel(NomeResp.getText().toString());
               condominio.setEndereco(EnderecoCondo.getText().toString());
               condominio.setTelefone(telefoneCondo.getText().toString());
               condominio.setEmail(EmailCondo.getText().toString());
               condominio.setSenha(SenhaCondo.getText().toString());
               CadastrarCondominio();
           }
       });

       CadastrarProf.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                profissional = new Profissional();
               profissional.setNome(NomeProf.getText().toString());
               profissional.setCidade(Cidade.getText().toString());
               profissional.setTelefone(telefoneProf.getText().toString());
               profissional.setEmail(EmailProf.getText().toString());
               profissional.setSenha(SenhaProf.getText().toString());
               CadastrarProfissional();
           }
       });
    }

    private void CadastrarProfissional(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                profissional.getEmail(),
                profissional.getSenha()
        ).addOnCompleteListener(RegistrarActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegistrarActivity.this, "Cadastro realizado com sucesso" , Toast.LENGTH_LONG).show();
                    FirebaseUser profissionalFirebase = task.getResult().getUser();
                    profissional.setIdProfissional(profissionalFirebase.getUid());
                    profissional.salvar();
                    autenticacao.signOut();
                    finish();
                }else{

                    String erroExecucao = "";

                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExecucao = "Digite uma senha mais forte, com letras e numeros";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExecucao = "Email invalido, digite um novo email";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExecucao = "Email já está em uso, digite um novo email";
                    }catch (Exception e){
                        erroExecucao = "Erro ao fazer o cadastro";
                        e.printStackTrace();
                    }

                    Toast.makeText(RegistrarActivity.this, "Erro: " + erroExecucao , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void CadastrarCondominio(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                condominio.getEmail(),
                condominio.getSenha()
        ).addOnCompleteListener(RegistrarActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegistrarActivity.this, "Cadastro realizado com sucesso" , Toast.LENGTH_LONG).show();
                    FirebaseUser condominioFirebase = task.getResult().getUser();
                    condominio.setIdCondo(condominioFirebase.getUid());
                    condominio.salvar();
                    autenticacao.signOut();
                    finish();
                }else{
                    Toast.makeText(RegistrarActivity.this, "Erro ao realizar o cadastro" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
