package com.example.enviardatoswebhost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RegistroDeAdopcion extends AppCompatActivity {
    TextView ubicacion, txtedad;
    SeekBar edad;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_de_adopcion);

        ubicacion = (TextView) findViewById(R.id.txtUbicacion);
        edad = (SeekBar) findViewById(R.id.seekBarEdad);
        txtedad = (TextView)findViewById(R.id.txtEdad);
        imageView = (ImageView) findViewById(R.id.imageIcon);
        //seekbar que marca la edad de la mascota
        edad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtedad.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //Permisos de GPS
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();

        }
        //Permisos para acceder a la camara
        if (ContextCompat.checkSelfPermission(RegistroDeAdopcion.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RegistroDeAdopcion.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RegistroDeAdopcion.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }
    }
    //Metodo para acceder a la camara del dispositivo
    public void takeImage(View view){
        Intent camaraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camaraintent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(camaraintent, 1);
        }
    }
    //aqui inicia

    public void pickImage(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,321);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bm);
        } else {
            if (requestCode == 321) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
    }

    public void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
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
                    ubicacion.setText(DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        RegistroDeAdopcion mainActivity;
        public RegistroDeAdopcion getMainActivity() {
            return mainActivity;
        }
        public void setMainActivity(RegistroDeAdopcion mainActivity) {
            this.mainActivity = mainActivity;
        }
        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            this.mainActivity.setLocation(loc);
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


