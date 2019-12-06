package com.example.enviardatoswebhost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RegistroPerdido extends AppCompatActivity {
    ToggleButton tgEdad, tgVacunas, PerroGato;
    Button btn_reg_per;
    TextView txtEdadPer, txtUbicacion;
    EditText raza,edtDesc, nombre, valor;
    SeekBar seekbarPer;
    String MascotaPer, EdadMascPer, Edad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_perdido);


        //TogleButton
        PerroGato = (ToggleButton) findViewById(R.id.PerroGato);
        tgEdad = (ToggleButton) findViewById(R.id.tgbtnEdad);

        //TextView
        //txtEdadPer = (TextView) findViewById(R.id.txtEdadPer);
        txtUbicacion = (TextView) findViewById(R.id.txtUbicacion);
        //EditText
        raza = (EditText) findViewById(R.id.raza);
        edtDesc = (EditText) findViewById(R.id.editDesc);
        nombre = (EditText) findViewById(R.id.name_perdido);
        valor = (EditText) findViewById(R.id.editValor);
        //SeekBar
        seekbarPer = (SeekBar) findViewById(R.id.seekBarEdadPer);
        //Metodo Seek Bar

        seekbarPer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valor.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Edad = txtEdadPer.getText().toString() + " " + EdadMascPer;
        //Metodo ToggleButton
        PerroGato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PerroGato.isChecked()) {
                    //txtMacPer.setText("Perro");
                    MascotaPer ="Perro";

                }else{
                    MascotaPer ="Gato";
                    //txtMacPer.setText("Gato");
                }
            }
        });


    }
    public void reg_per(View view){
        if (!raza.getText().toString().isEmpty() && !edtDesc.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty() ){
            Toast.makeText(getApplicationContext(), Edad, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Campo de Raza/Descripcion/Nombre Vacio", Toast.LENGTH_SHORT).show();
        }
    }
    //Localizacion Automatica
    public void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        //Local.setMainActivity(this);
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc){
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    txtUbicacion.setText(DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        RegistroPerdido UbiPerdido;
        public RegistroPerdido getMainActivity() {
            return UbiPerdido;
        }
        public void setMainActivity(RegistroPerdido UbiPerdido) {
            this.UbiPerdido = UbiPerdido;
        }
        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            this.UbiPerdido.setLocation(loc);
            //this.mainActivity.setL
        }
        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            //mensaje1.setText("GPS Desactivado");
            Toast.makeText(getApplicationContext(), "GPS DESACTIVADO", Toast.LENGTH_SHORT).show();

        }
        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            //mensaje1.setText("GPS Activado");
            Toast.makeText(getApplicationContext(), "GPS ACTIVADO", Toast.LENGTH_SHORT).show();

        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }

}
