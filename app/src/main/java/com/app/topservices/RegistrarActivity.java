package com.app.topservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class RegistrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

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
    }
}
