package com.example.davidandres.densidaddesuelo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.view.View.OnClickListener;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


/**
 * Created by DAVIDANDRES on 31-05-2017.
 */

public class CalculoDensidadSuelo extends AppCompatActivity implements OnClickListener {

    EditText espesorCapa11,espesorCapa12;
    EditText espesorMedicion11, espesorMedicion12;
    EditText densidadHumeda11,densidadHumeda12,densidadHumeda13,densidadHumeda14,densidadHumeda15;
    EditText humedad11,humedad12,humedad13,humedad14,humedad15;
    EditText densidadSeca11,densidadSeca12,densidadSeca13,densidadSeca14,densidadSeca15;
    EditText proctor11;
    EditText compactacion11,compactacion12,compactacion13,compactacion14,compactacion15;

    EditText boletaCalculoDS;

    Button siguienteTM;

    private final static String NOMBRE_DIRECTORIO = "Densidad de Suelo";
    private String NOMBRE_DOCUMENTO = getDateTime()+".pdf";
    private final static String ETIQUETA_ERROR = "ERROR";
    private static final int MY_WRITE_EXTERNAL_STORAGE = 0;



    public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();

        String fecha = dateFormat.format(date).toString();
        //String fecha = Bol;
        return fecha; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculo_densidad_suelo);
        findViewById(R.id.btnGenerar).setOnClickListener(this);
        verifyPermission();

        Bundle datos = getIntent().getExtras();
        final String numeroBoletaMostrar = datos.getString("boleta");
        boletaCalculoDS = (EditText) findViewById(R.id.editTextBoletaCalculoDS);
        boletaCalculoDS.setText(numeroBoletaMostrar);

        espesorCapa11 = (EditText) findViewById(R.id.editTextEC11);
        espesorCapa12 = (EditText) findViewById(R.id.editTextEC12);
        espesorMedicion11 = (EditText) findViewById(R.id.editTextEM11);
        espesorMedicion12 = (EditText) findViewById(R.id.editTextEM12);
        densidadHumeda11 = (EditText) findViewById(R.id.editTextDH11);
        densidadHumeda12 = (EditText) findViewById(R.id.editTextDH12);
        densidadHumeda13 = (EditText) findViewById(R.id.editTextDH13);
        densidadHumeda14 = (EditText) findViewById(R.id.editTextDH14);
        densidadHumeda15 = (EditText) findViewById(R.id.editTextDH15);
        humedad11 = (EditText) findViewById(R.id.editTextH11);
        humedad12 = (EditText) findViewById(R.id.editTextH12);
        humedad13 = (EditText) findViewById(R.id.editTextH13);
        humedad14 = (EditText) findViewById(R.id.editTextH14);
        humedad15 = (EditText) findViewById(R.id.editTextH15);
        densidadSeca11 = (EditText) findViewById(R.id.editTextDS11);
        densidadSeca12 = (EditText) findViewById(R.id.editTextDS12);
        densidadSeca13 = (EditText) findViewById(R.id.editTextDS13);
        densidadSeca14 = (EditText) findViewById(R.id.editTextDS14);
        densidadSeca15 = (EditText) findViewById(R.id.editTextDS15);
        proctor11 = (EditText) findViewById(R.id.editTextDMCS11);
        compactacion11 = (EditText) findViewById(R.id.editTextC11);
        compactacion12 = (EditText) findViewById(R.id.editTextC12);
        compactacion13 = (EditText) findViewById(R.id.editTextC13);
        compactacion14 = (EditText) findViewById(R.id.editTextC14);
        compactacion15 = (EditText) findViewById(R.id.editTextC15);

        espesorCapa11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {

                int cantDigito = Integer.parseInt(String.valueOf(s.length()));
                if (cantDigito == 1){
                    alertDialogBasico();
                } else if(cantDigito == 2){
                    Double espesorCapa_11 = (Double.parseDouble(espesorCapa11.getText().toString()))/100;
                    espesorCapa12.setText(Double.toString(espesorCapa_11));
                    espesorMedicion11.requestFocus();
                }else{
                    finish();
                }
            }
        });


        espesorMedicion11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int cantDigitoEM11 = Integer.parseInt(String.valueOf(s.length()));
                if(cantDigitoEM11 < 2){
                    alertDialogBasicoEM11();
                }else if(cantDigitoEM11 == 2){
                    Double espesorMedicion_12 = (Double.parseDouble(espesorMedicion11.getText().toString())/100);
                    Log.d("R", "EM12 " + espesorMedicion_12);
                    espesorMedicion12.setText(Double.toString(espesorMedicion_12));
                    densidadHumeda11.requestFocus();
                }
            }
        });

        densidadHumeda11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String cantDigitoDH11 = String.valueOf(s.length());
                Log.d("R","DH11 "+cantDigitoDH11);
                int cantDigito_DH11 = Integer.parseInt(cantDigitoDH11);
                if(cantDigito_DH11 < 5){
                    densidadHumeda11.requestFocus();
                    if(cantDigito_DH11 == 4){
                        String densidadHumeda_11 = densidadHumeda11.getText().toString();
                        densidadHumeda12.requestFocus();
                        densidadHumeda15.setText(densidadHumeda_11);
                    }
                }
            }
        });

        densidadHumeda12.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String cantDigitoDH12 = String.valueOf(s.length());
                Log.d("R","DH12 "+cantDigitoDH12);
                int cantDigito_DH12 = Integer.parseInt(cantDigitoDH12);
                if(cantDigito_DH12 < 5){
                    densidadHumeda12.requestFocus();
                    if(cantDigito_DH12 == 4){
                        String densidadHumeda_11 = densidadHumeda11.getText().toString();
                        int dH11 = Integer.parseInt(densidadHumeda_11);
                        String densidadHumeda_12 = densidadHumeda12.getText().toString();
                        int dH12 = Integer.parseInt(densidadHumeda_12);
                        int promedioDH12 = (dH11+dH12)/2;
                        densidadHumeda13.requestFocus();
                        densidadHumeda15.setText(Integer.toString(promedioDH12));
                    }
                }
            }
        });

        densidadHumeda13.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String cantDigitoDH13 = String.valueOf(s.length());
                Log.d("R","DH13 "+cantDigitoDH13);
                int cantDigito_DH13 = Integer.parseInt(cantDigitoDH13);
                if(cantDigito_DH13 < 5){
                    densidadHumeda13.requestFocus();
                    if(cantDigito_DH13 == 4){
                        String densidadHumeda_11 = densidadHumeda11.getText().toString();
                        int dH11 = Integer.parseInt(densidadHumeda_11);
                        String densidadHumeda_12 = densidadHumeda12.getText().toString();
                        int dH12 = Integer.parseInt(densidadHumeda_12);
                        String densidadHumeda_13 = densidadHumeda13.getText().toString();
                        int dH13 = Integer.parseInt(densidadHumeda_13);
                        int promedioDH13 = (dH11+dH12+dH13)/3;
                        densidadHumeda14.requestFocus();
                        densidadHumeda15.setText(Integer.toString(promedioDH13));
                    }
                }
            }
        });

        densidadHumeda14.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String cantDigitoDH14 = String.valueOf(s.length());
                Log.d("R","DH14 "+cantDigitoDH14);
                int cantDigito_DH14 = Integer.parseInt(cantDigitoDH14);
                if(cantDigito_DH14 < 5){
                    densidadHumeda14.requestFocus();
                    if(cantDigito_DH14 == 4){
                        Double dH11 = Double.parseDouble(densidadHumeda11.getText().toString());
                        Double dH12 = Double.parseDouble(densidadHumeda12.getText().toString());
                        Double dH13 = Double.parseDouble(densidadHumeda13.getText().toString());
                        Double dH14 = Double.parseDouble(densidadHumeda14.getText().toString());
                        Double sumadH14 = (dH11+dH12+dH13+dH14);
                        Log.d("R","DH1" + sumadH14);
                        Double promedioDH14 = sumadH14/4.0;
                        Log.d("R","DH1" + promedioDH14);
                        humedad11.requestFocus();
                        densidadHumeda15.setText(Double.toString(Math.round(promedioDH14)));
                    }
                }
            }
        });

        humedad11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String cantDigitoH11 = String.valueOf(s.length());
                Log.d("R","H11 "+cantDigitoH11);
                int cantDigito_H11 = Integer.parseInt(cantDigitoH11);
                if(cantDigito_H11 < 4){
                    humedad11.requestFocus();
                    if(cantDigito_H11 == 3){
                        //String densidadHumeda_11 = densidadHumeda11.getText().toString();
                        //String humedad_11= humedad11.getText().toString();
                        Double dH_11 = Double.parseDouble(densidadHumeda11.getText().toString());
                        Double h_11 = Double.parseDouble(humedad11.getText().toString());
                        //Long hh = Math.round(h_11*0.01);
                        //Log.d("R","H1 "+ hh);
                        //Long h = (1 + (Math.round(h_11*0.01)));
                        //Log.d("R","H2 "+ h);
                        //Double densidaSeca_1 = dH_11 / (1 + (Math.round(h_11*0.01)));
                        Double densidaSeca_1 = dH_11 / (1 + (h_11*0.01));
                        Log.d("R","H3 "+ densidaSeca_1);
                        //Double densidaSeca_1 = densidaHumeda_1/(1*1+(humeda_1*(1/100)*1));
                        String densidadSeca_1 = Double.toString(Math.round(densidaSeca_1));
                        Log.d("R","H4 "+ densidadSeca_1);
                        densidadSeca11.setText(densidadSeca_1);
                        humedad15.setText(Double.toString(h_11));
                        humedad12.requestFocus();
                    }
                }
            }
        });

        humedad12.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String cantDigitoH12 = String.valueOf(s.length());
                Log.d("R","H12 "+cantDigitoH12);
                int cantDigito_H12 = Integer.parseInt(cantDigitoH12);
                if(cantDigito_H12 < 4){
                    humedad12.requestFocus();
                    if(cantDigito_H12 == 3){
                        Double h_11 = Double.parseDouble(humedad11.getText().toString());
                        Double dH_12 = Double.parseDouble(densidadHumeda12.getText().toString());
                        Double h_12 = Double.parseDouble(humedad12.getText().toString());
                        Double dS_12 = dH_12 / (1 + (h_12*0.01));
                        Log.d("R","H3 "+ dS_12);
                        //Double densidaSeca_1 = densidaHumeda_1/(1*1+(humeda_1*(1/100)*1));
                        //String densidadSeca_12 = Double.toString(Math.round(dS_12));
                        densidadSeca12.setText(Double.toString(Math.round(dS_12)));
                        humedad15.setText(Double.toString((h_11+h_12)/2.0));
                        humedad13.requestFocus();
                    }
                }
            }
        });

        humedad13.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String cantDigitoH13 = String.valueOf(s.length());
                Log.d("R","H13 "+cantDigitoH13);
                int cantDigito_H13 = Integer.parseInt(cantDigitoH13);
                if(cantDigito_H13 < 4){
                    humedad13.requestFocus();
                    if(cantDigito_H13 == 3){
                        Double h_11 = Double.parseDouble(humedad11.getText().toString());
                        Double h_12 = Double.parseDouble(humedad12.getText().toString());
                        Double dH_13 = Double.parseDouble(densidadHumeda13.getText().toString());
                        Double h_13 = Double.parseDouble(humedad13.getText().toString());
                        Double dS_13 = dH_13 / (1 + (h_13*0.01));
                        densidadSeca13.setText(Double.toString(Math.round(dS_13)));
                        humedad15.setText(Double.toString((h_11+h_12+h_13)/3.0));
                        humedad14.requestFocus();
                    }
                }
            }
        });

        humedad14.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String cantDigitoH14 = String.valueOf(s.length());
                Log.d("R","H13 "+cantDigitoH14);
                int cantDigito_H14 = Integer.parseInt(cantDigitoH14);
                if(cantDigito_H14 < 4){
                    humedad14.requestFocus();
                    if(cantDigito_H14 == 3){
                        Double h_11 = Double.parseDouble(humedad11.getText().toString());
                        Double h_12 = Double.parseDouble(humedad12.getText().toString());
                        Double h_13 = Double.parseDouble(humedad13.getText().toString());
                        Double dH_14 = Double.parseDouble(densidadHumeda14.getText().toString());
                        Double h_14 = Double.parseDouble(humedad14.getText().toString());
                        Double dS_14 = dH_14 / (1 + (h_14*0.01));
                        densidadSeca14.setText(Double.toString(Math.round(dS_14)));
                        humedad15.setText(Double.toString((h_11+h_12+h_13+h_14)/4.0));
                        Double dH_15 = Double.parseDouble(densidadHumeda15.getText().toString());
                        Double h_15 = Double.parseDouble(humedad15.getText().toString());
                        Double dS_15 = dH_15 / (1 + (h_15*0.01));
                        densidadSeca15.setText(Double.toString(Math.round(dS_15)));
                        proctor11.requestFocus();
                    }
                }
            }
        });

        proctor11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {

                String cantDigitoProctor11 = String.valueOf(s.length());
                int cantDigito = Integer.parseInt(cantDigitoProctor11);
                if(cantDigito < 5){
                    proctor11.requestFocus();
                    if(cantDigito == 4){
                        //String densidadSeca_11 = densidadSeca11.getText().toString();
                        Double densidad_Seca_11 = Double.parseDouble(densidadSeca11.getText().toString());
                        Double densidad_Seca_12 = Double.parseDouble(densidadSeca12.getText().toString());
                        Double densidad_Seca_13 = Double.parseDouble(densidadSeca13.getText().toString());
                        Double densidad_Seca_14 = Double.parseDouble(densidadSeca14.getText().toString());
                        Double densidad_Seca_15 = Double.parseDouble(densidadSeca15.getText().toString());
                        //String proctor_11 = proctor11.getText().toString();
                        Double proctor_dmcs_11 = Double.parseDouble(proctor11.getText().toString());
                        Double compactacion_11 = (densidad_Seca_11/proctor_dmcs_11)*100;
                        Double compactacion_12 = (densidad_Seca_12/proctor_dmcs_11)*100;
                        Double compactacion_13 = (densidad_Seca_13/proctor_dmcs_11)*100;
                        Double compactacion_14 = (densidad_Seca_14/proctor_dmcs_11)*100;
                        Double compactacion_15 = (densidad_Seca_15/proctor_dmcs_11)*100;
                        Log.d("R","C1 " + compactacion_11);
                        compactacion11.setText(Double.toString(compactacion_11));
                        compactacion12.setText(Double.toString(compactacion_12));
                        compactacion13.setText(Double.toString(compactacion_13));
                        compactacion14.setText(Double.toString(compactacion_14));
                        compactacion15.setText(Double.toString(compactacion_15));
                    }
                }

            }
        });

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

    private void verifyPermission() {

        //WRITE_EXTERNAL_STORAGE tiene implícito READ_EXTERNAL_STORAGE porque pertenecen al mismo
        //grupo de permisos

        int writePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            saveComments();
        }
    }


    //Paso 2: Solicitar permiso
    private void requestPermission() {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        } else {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_WRITE_EXTERNAL_STORAGE);
        }
    }

    //Paso 3: Procesar respuesta de usuario
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Si el requestCode corresponde al que usamos para solicitar el permiso y
        //la respuesta del usuario fue positiva
        if (requestCode == MY_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveComments();
            } else {

            }
        }
    }




    /**
     * Abre el intento de detalles de configuración de nuestra aplicación
     */
    public void openSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * Guarda el comentario
     */
    private void saveComments() {

        if (isExternalStorageWritable()) {
            try {
                File file = new File(Environment.getExternalStorageDirectory(), "comments.aee");
                boolean created = file.createNewFile();
                if (file.exists()) {
                    FileOutputStream fo = new FileOutputStream(file, true);
                    fo.write(6);
                    fo.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Checks if external storage is available for read and write */

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public void alertDialogBasico(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Observe")
                .setTitle("Ingrese segundo digito");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                espesorCapa11.requestFocus();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Estas saliendo de la aplicacion", Toast.LENGTH_LONG);

                toast1.show();
                finish();
            }
        });
        builder.show();
    }
    public void alertDialogBasicoEM11(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Observe")
                .setTitle("Ingrese segundo digito");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                espesorMedicion11.requestFocus();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Estas saliendo de la aplicacion", Toast.LENGTH_LONG);

                toast1.show();
                finish();
            }
        });
        builder.show();
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

    public void onClick(View v) {

        boletaCalculoDS = (EditText) findViewById(R.id.editTextBoletaCalculoDS);
        boletaCalculoDS.getText().toString();
        // Creamos el documento.
        Document documento = new Document();

        try {

            // Creamos el fichero con el nombre que deseemos.
            File f = crearFichero(boletaCalculoDS.getText().toString()+NOMBRE_DOCUMENTO);

            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream(
                    f.getAbsolutePath());

            // Asociamos el flujo que acabamos de crear al documento.
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

            // Incluimos el píe de página y una cabecera
            HeaderFooter cabecera = new HeaderFooter(new Phrase(
                    "Densidad de Suelo."), false);
            HeaderFooter pie = new HeaderFooter(new Phrase(
                    "DS - Densidad de Suelo."), false);

            documento.setHeader(cabecera);
            documento.setFooter(pie);

            // Abrimos el documento.
            documento.open();


            // Añadimos un título con la fuente por defecto.
            Paragraph titulo1 = new Paragraph("INFORME DE ENSAYO N° "+33+"                        Fecha de Emision: "+getDateTime());
            titulo1.setSpacingAfter(6);
            documento.add(titulo1);

            Paragraph tituloAntecedentes = new Paragraph("ANTECEDENTES GENERALES");
            tituloAntecedentes.setAlignment(Paragraph.ALIGN_CENTER);
            tituloAntecedentes.setSpacingAfter(10);
            documento.add(tituloAntecedentes);

            // Añadimos un título con una fuente personalizada.
            //Font fontTitTabla = FontFactory.getFont(FontFactory.HELVETICA, 28,
            //Font.BOLD, Color.BLACK);

            // Insertamos una tabla.
            PdfPTable tabla1 = new PdfPTable(1);

            tabla1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            String titulosAnt[] = {"NOMBRE DEL ENSAYO: Densidad in situ con densimetro nuclear","CÓDIGO DE OBRA: ","OBRA: ","UBICACIÖN DE LA OBRA: ",
                    "DESTINATARIO: ","CONTRATISTA: ","N° BOLETA: ","SOLICITANTE: ","FECHA INSPEC: ","DIRECCIÓN: ","FACILITY: "};

            for (int i = 0; i < 11; i++) {
                tabla1.addCell(titulosAnt[i]);
            }

            documento.add(tabla1);

            Paragraph tituloDensimetro = new Paragraph("DATOS DEL DENSIMETRO");
            tituloDensimetro.setAlignment(Paragraph.ALIGN_CENTER);
            tituloDensimetro.setSpacingAfter(10);
            documento.add(tituloDensimetro);

            PdfPTable tabla2 = new PdfPTable(1);

            tabla2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            String titulosDensimetro[] = {"MARCA: ","MODELO: ","N° DENSIMETRO: ","OPERADOR: ","CONTEO: ","HUMEDAD: "};

            for (int i = 0; i < 6; i++) {
                tabla2.addCell(titulosDensimetro[i]);
            }

            documento.add(tabla2);

            Paragraph tituloPuntosDeControl = new Paragraph("UBICACIÓN DE PUNTOS DE CONTROL");
            tituloPuntosDeControl.setAlignment(Paragraph.ALIGN_CENTER);
            tituloPuntosDeControl.setSpacingAfter(10);
            documento.add(tituloPuntosDeControl);

            PdfPTable tabla3 = new PdfPTable(1);

            tabla3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            String titulosUbicacion[] = {"D1 ","D2 ","D3 ","D4 ","D5 ","D6 "};

            for (int i = 0; i < 6; i++) {
                tabla3.addCell(titulosUbicacion[i]);
            }

            documento.add(tabla3);

            Paragraph tituloDensidadInSitu = new Paragraph("DENSIDAD IN SITU");
            tituloDensidadInSitu.setAlignment(Paragraph.ALIGN_CENTER);
            tituloDensidadInSitu.setSpacingAfter(10);
            documento.add(tituloDensidadInSitu);

            PdfPTable tabla4 = new PdfPTable(1);

            tabla4.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            String titulosDensidadInSitu[] = {"D1 ","D2 ","D3 ","D4 ","D5 ","D6 "};

            for (int i = 0; i < 6; i++) {
                tabla4.addCell(titulosDensidadInSitu[i]);
            }

            documento.add(tabla4);

            // Insertamos una tabla.
           /* PdfPTable tabla = new PdfPTable(5);

            tabla.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            for (int i = 0; i < 15; i++) {
                tabla.addCell("Celda " + i);

            }
            documento.add(tabla);*/

            // Agregar marca de agua
            /*fontTitTabla = FontFactory.getFont(FontFactory.HELVETICA, 42, Font.BOLD,
                    Color.GRAY);
            ColumnText.showTextAligned(writer.getDirectContentUnder(),
                    Element.ALIGN_CENTER, new Paragraph(
                            "amatellanes.wordpress.com", fontTitTabla), 297.5f, 421,
                    writer.getPageNumber() % 2 == 1 ? 45 : -45);*/

        } catch (DocumentException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } catch (IOException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } finally {

            // Cerramos el documento.
            documento.close();
            prueba1();
        }
    }

    /**
     * Crea un fichero con el nombre que se le pasa a la función y en la ruta
     * especificada.
     *
     * @param nombreFichero
     * @return
     * @throws IOException
     */
    public  File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();

        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);

        return fichero;
    }

    public void prueba1() {
        Toast toast = Toast.makeText(this, "Se ha creado el archivo "+NOMBRE_DOCUMENTO, Toast.LENGTH_LONG);
        toast.show();
    }
    /**
     * Obtenemos la ruta donde vamos a almacenar el fichero.
     *
     * @return
     */

    public static File getRuta() {

        // El fichero será almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }

        return ruta;
    }


}
