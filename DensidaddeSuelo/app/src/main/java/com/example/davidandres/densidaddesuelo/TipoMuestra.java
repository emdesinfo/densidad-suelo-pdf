package com.example.davidandres.densidaddesuelo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by DAVIDANDRES on 31-05-2017.
 */

public class TipoMuestra extends AppCompatActivity{

    EditText numeroBoletaTipoEnsayo;
    Button siguienteTM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tipo_muestra);

        Bundle datos = getIntent().getExtras();
        final String numeroBoletaMostrar = datos.getString("proctor");
        final String densimetroNuclear = datos.getString("densimetro");
        Log.d("DM1","DM1 " + densimetroNuclear);
        numeroBoletaTipoEnsayo = (EditText) findViewById(R.id.editTextNumeroBoletaTipoEnsayo);
        numeroBoletaTipoEnsayo.setText(numeroBoletaMostrar + " " + densimetroNuclear);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void llevaDatosBoleta(View view){
        String boleta = numeroBoletaTipoEnsayo.getText().toString();
        Log.d("R1","R1" + boleta);
        if(!boleta.isEmpty()){
            Log.d("R1","R2" + boleta);
            Intent r = new Intent(this,LocalizacionDensidad.class);
            r.putExtra("proctor" , boleta);
            startActivity(r);
        }else{
            Intent r = new Intent(this,LocalizacionDensidad.class);
            r.putExtra("vacio","vacio");
            startActivity(r);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.principal && id == R.id.tipoDeMuestra && id == R.id.localizacionDensidad && id == R.id.calculoDensidadSuelo) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
