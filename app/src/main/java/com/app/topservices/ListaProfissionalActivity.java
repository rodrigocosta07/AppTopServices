package com.app.topservices;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Config.ConfiguracaoFirebase;
import Model.Condominio;
import Model.Profissional;

public class ListaProfissionalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseAuth autenticacao;
    ArrayList<Profissional> profissionais;
    ListView listView;
    private DatabaseReference referenciaFirebase;
    private static CustomAdapterProfissional adapter;
    TextView nomeUser ;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_profissional);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        nomeUser = headerView.findViewById(R.id.userName);
        email = headerView.findViewById(R.id.textViewEmail);
        ImageView imagemHeader = headerView.findViewById(R.id.imageView);
        Drawable drawable= getResources().getDrawable(R.mipmap.ic_condominio);
        imagemHeader.setImageDrawable(drawable);

        user();

        listView = (ListView) findViewById(R.id.list);

        profissionais = new ArrayList<>();

        adapter = new CustomAdapterProfissional(profissionais,getApplicationContext());
        listView.setAdapter(adapter);

        referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("Profissional");
        referenciaFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profissionais.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren() ){
                    Profissional profissional = new Profissional();
                    String nomeProf = (String) dados.child("nome").getValue();
                    String Telefone = (String) dados.child("telefone").getValue();
                    String Email = (String ) dados.child("email").getValue();
                    String idProf = (String) dados.getKey();
                    profissional.setNome(nomeProf);
                    profissional.setTelefone(Telefone);
                    profissional.setEmail(Email);
                    profissional.setIdProfissional(idProf);
                    profissionais.add(profissional);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Profissional profissional = profissionais.get(i);

            Intent intent = new Intent(ListaProfissionalActivity.this, InfoProfissionalActivity.class);
            intent.putExtra("IdProf" , profissional.getIdProfissional());
            startActivity(intent);
        }
    });
    }

    private void user() {
        final String userEmail = null;
        String userId = null;
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        referenciaFirebase = ConfiguracaoFirebase.getFirebase();

        if (autenticacao.getCurrentUser() != null) {
            userId = autenticacao.getCurrentUser().getUid();
            referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("Condominio").child(userId);
            referenciaFirebase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Condominio condominio = dataSnapshot.getValue(Condominio.class);
                        String nome = (String) dataSnapshot.child("nomeCondominio").getValue();
                        String Email = (String) dataSnapshot.child("email").getValue();
                        condominio.setNomeCondominio(nome);
                        condominio.setEmail(Email);

                        email.setText(condominio.getEmail());
                        nomeUser.setText(condominio.getNomeCondominio());

                    } else {
                        Toast.makeText(ListaProfissionalActivity.this, "Usuario não encontrado", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.TelaListaProfissional);
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


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_sair) {
            Intent intent = new Intent(ListaProfissionalActivity.this, LoginActivity.class);
            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.signOut();

            startActivity(intent);
        }else if(id == R.id.nav_servicos){
            Intent intent = new Intent(ListaProfissionalActivity.this, ServicosCondominioActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.TelaListaProfissional);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
