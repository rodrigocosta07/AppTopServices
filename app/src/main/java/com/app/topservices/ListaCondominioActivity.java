package com.app.topservices;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Config.ConfiguracaoFirebase;
import Model.Condominio;

public class ListaCondominioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList<Condominio> condominios;
    ListView listView;
    private DatabaseReference referenciaFirebase;
    private static CustomAdapter adapter;
    private FirebaseAuth autenticacao;
    TextView nome ;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_condominio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nome = findViewById(R.id.userName);
        email = findViewById(R.id.textViewEmail);
        user();
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView = (ListView) findViewById(R.id.list);
        condominios = new ArrayList<>();
        adapter = new CustomAdapter(condominios,getApplicationContext());

        listView.setAdapter(adapter);
        referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("Condominio");
        referenciaFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                condominios.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren() ){
                    Condominio condominio = new Condominio();
                    String nomeCondo = (String) dados.child("nomeCondominio").getValue();
                    String nomeResponsavel = (String) dados.child("nomeResponsavel").getValue();
                    String Email = (String ) dados.child("email").getValue();

                    condominio.setNomeCondominio(nomeCondo);
                    condominio.setNomeResponsavel(nomeResponsavel);
                    condominio.setEmail(Email);
                    condominios.add(condominio);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void user(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        String userEmail = null;
        String userId = null;
        if( autenticacao.getCurrentUser() != null){
            userId = autenticacao.getCurrentUser().getUid();
            userEmail = autenticacao.getCurrentUser().getEmail();
           //email.setText(userEmail.toString());
        }

        


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.TelaListaCondominio);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teste, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_sair) {
            Intent intent = new Intent(ListaCondominioActivity.this, LoginActivity.class);
            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.signOut();

           startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.TelaListaCondominio);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
