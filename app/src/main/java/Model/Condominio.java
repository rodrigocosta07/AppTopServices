package Model;

import android.widget.Toast;

import com.app.topservices.RegistrarActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import Config.ConfiguracaoFirebase;

public class Condominio {

    private String idCondo;
    private String nomeCondominio;
    private String nomeResponsavel;
    private String telefone;
    private String endereco;
    private String email;
    private String senha;


    public Condominio(String idCondo, String nomeCondominio, String nomeResponsavel, String telefone, String endereco, String email, String senha) {
        this.idCondo = idCondo;
        this.nomeCondominio = nomeCondominio;
        this.nomeResponsavel = nomeResponsavel;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
    }

    public Condominio() {

    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("Condominio").child(getIdCondo()).setValue(this , new DatabaseReference.CompletionListener(){
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });
    }

    @Exclude
    public String getIdCondo() {
        return idCondo;
    }

    public void setIdCondo(String idCondo) {
        this.idCondo = idCondo;
    }

    public String getNomeCondominio() {
        return nomeCondominio;
    }

    public void setNomeCondominio(String nomeCondominio) {
        this.nomeCondominio = nomeCondominio;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
