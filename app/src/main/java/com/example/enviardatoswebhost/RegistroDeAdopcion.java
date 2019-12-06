package com.example.enviardatoswebhost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class RegistroDeAdopcion extends AppCompatActivity {
    TextView  textEdad, textAM, textVacuna, textDueño, textMascota;
    ToggleButton tgbtnMascota, tgbtnEdad, tgbtVacuna, tgbtnDueños;
    ImageView imageIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_de_adopcion);

        imageIcon = (ImageView) findViewById(R.id.imageIcon);

        textMascota = (TextView) findViewById(R.id.textMascota);
        textAM = (TextView) findViewById(R.id.textAM);
        textEdad= (TextView) findViewById(R.id.textEdad);
        textVacuna = (TextView) findViewById(R.id.textvacuna);
        textDueño = (TextView) findViewById(R.id.textDueño);
        //Toggle Buttons
        tgbtnMascota = (ToggleButton) findViewById(R.id.tgbtnMascota);
        tgbtnEdad = (ToggleButton) findViewById(R.id.tgbtnEdad);
        tgbtVacuna = (ToggleButton) findViewById(R.id.tgbtnVacunas);
        tgbtnDueños = (ToggleButton) findViewById(R.id.tgbtnDueños);

        //SeekBar

        //Orden de los ToggleButton Segun su Accion
        tgbtnMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tgbtnMascota.isChecked()){
                    textMascota.setText("Perro");
                }else{
                    textMascota.setText("Gato");
                }
            }
        });
        tgbtnEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tgbtnEdad.isChecked()){
                    textAM.setText("Meses");
                }else{textAM.setText("Año");}
            }
        });
        tgbtVacuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tgbtVacuna.isChecked()){
                    textVacuna.setText("Si");
                }else{textVacuna.setText("No");}
            }
        });
        tgbtnDueños.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tgbtnDueños.isChecked()){
                    textDueño.setText("Unico");
                }else{textDueño.setText("Desconocido");}
            }
        });


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
            imageIcon.setImageBitmap(bm);
        } else {
            if (requestCode == 321) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                imageIcon.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
    }
}


