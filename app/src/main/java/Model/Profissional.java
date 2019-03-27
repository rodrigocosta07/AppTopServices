package Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import Config.ConfiguracaoFirebase;

public class Profissional {

    private String idProfissional;
    private String nome;
    private String email;
    private String cidade;
    private String telefone;
    private String senha;


    public Profissional(String idProfissional, String nome, String email, String cidade, String telefone, String senha) {
        this.idProfissional = idProfissional;
        this.nome = nome;
        this.email = email;
        this.cidade = cidade;
        this.telefone = telefone;
        this.senha = senha;
    }

    public Profissional(){

    }



    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("Profissional").child(getIdProfissional()).setValue(this);
    }

    @Exclude
    public String getIdProfissional() {
        return idProfissional;
    }


    public void setIdProfissional(String idProfissional) {
        this.idProfissional = idProfissional;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
