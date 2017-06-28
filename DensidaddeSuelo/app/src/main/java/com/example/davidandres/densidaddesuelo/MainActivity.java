package com.example.davidandres.densidaddesuelo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    EditText numeroBoleta;
    RadioButton densimetroNuclear, conoDeArena;
    Button salirPantalla1;

    //GPS
    private Switch encendidoApagado;
    private EditText longitud;
    private EditText latitud;
    private EditText direccion;
    private EditText gps;
    private String proveedor;          //provaider
    public LocationManager encargado;  //handle


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GPS
        encendidoApagado = (Switch) findViewById(R.id.switchEncenderApagar);
        longitud = (EditText) findViewById(R.id.editTextLongitud);
        latitud = (EditText) findViewById(R.id.editTextLatitud);
        direccion = (EditText) findViewById(R.id.editTextDireccion);
        gps = (EditText) findViewById(R.id.editTextGps);

        numeroBoleta = (EditText) findViewById(R.id.editTextNumeroBoleta);
        densimetroNuclear = (RadioButton) findViewById(R.id.radioButtonDN);
        conoDeArena = (RadioButton) findViewById(R.id.radioButtonCA);
        salirPantalla1 = (Button) findViewById(R.id.buttonSalirPantalla1);

        //IniciarServicio();
        encendidoApagado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEstadoEncendidoApagado(isChecked);
            }
        });

        salirPantalla1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoDeEquipo();
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

    public void setEstadoEncendidoApagado(boolean x){
        if(x){
            Log.d("P","P13" + x);
            IniciarServicio();
            muestraPosicionActual();
        }else{
            pararServicio();
        }
    }

    public void IniciarServicio() {
        Toast.makeText(this,"activado",Toast.LENGTH_LONG).show();
        encargado = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.d("P","P12 " + encargado);
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        Log.d("P","P11 " + c);
        proveedor = encargado.getBestProvider(c, true);
        Log.d("P","P10 " + proveedor);
        gps.setText(proveedor);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;

        }
        encargado.requestLocationUpdates(proveedor, 10000, 1, this);
    }

    public void muestraPosicionActual() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        Location location = encargado.getLastKnownLocation(proveedor);
        Log.d("P","P14" + location);
        if(location==null){
            longitud.setText("Desconocido");
            latitud.setText("Desconocido");
        }else{
            longitud.setText(String.valueOf(location.getLongitude()));
            latitud.setText(String.valueOf(location.getLatitude()));
        }
        setDireccion(location);
    }

    public void setDireccion(Location loc){
        if(loc != null){
            if(loc.getLongitude() != 0.0 && loc.getLatitude() != 0.0){
                try{
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> list = geocoder.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
                    if(!list.isEmpty()){
                        Address calle = list.get(0);
                        direccion.setText(calle.getAddressLine(0));
                    }
                }catch (IOException e){
                    direccion.setText("" + e);
                }
            }
        }
    }

    public void pararServicio(){
        encargado.removeUpdates(this);
        latitud.setText(null);
        longitud.setText(null);
        direccion.setText(null);
        Toast.makeText(this,"desactivado",Toast.LENGTH_LONG).show();
    }

    public String tipoDeEquipo(){
        String dN="";
        if(densimetroNuclear.isChecked()){
            dN = "Densimetro Nuclear";
            //Toast.makeText(this, text, Toast.LENGTH_LONG).show();

        }
        return dN;
    }



    public void llevaDatosProctor(View view){
        String boleta = numeroBoleta.getText().toString();
        Log.d("R1","R1" + boleta);
        String densimetroNuclear_1 = tipoDeEquipo();
        Log.d("DM","DM " + densimetroNuclear_1);

        if(!boleta.isEmpty()){
            Log.d("R1","R2" + boleta);
            Intent r = new Intent(this,TipoMuestra.class);
            r.putExtra("proctor" , boleta);
            r.putExtra("densimetro", densimetroNuclear_1);
            startActivity(r);
        }else{
            Intent r = new Intent(this,TipoMuestra.class);
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

    @Override
    public void onLocationChanged(Location location) {
        if(location==null){
            longitud.setText("Desconocido");
            latitud.setText("Desconocido");
        }else{
            longitud.setText(String.valueOf(location.getLongitude()));
            latitud.setText(String.valueOf(location.getLatitude()));
        }
        setDireccion(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
