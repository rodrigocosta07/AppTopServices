package Model;

import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import Config.ConfiguracaoFirebase;

public class Servicos {

    private String titulo;
    private String informacoes;
    private String IdCondo;

    public Servicos(String titulo, String informacoes, String idCondo) {
        this.titulo = titulo;
        this.informacoes = informacoes;
        IdCondo = idCondo;
    }

    public Servicos (){

    }
    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("Servicos").child(getIdCondo()).push().setValue(this, new DatabaseReference.CompletionListener(){
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
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInformacoes() {
        return informacoes;
    }

    public void setInformacoes(String informacoes) {
        this.informacoes = informacoes;
    }

    public String getIdCondo() {
        return IdCondo;
    }

    public void setIdCondo(String idCondo) {
        IdCondo = idCondo;
    }
}
