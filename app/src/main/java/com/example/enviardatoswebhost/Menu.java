package com.example.enviardatoswebhost;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Menu extends AppCompatActivity {

    LinearLayout irPerfil;
    ImageButton  btnAdopcion, btnPerdido, btnAdoptar, btnEncontrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        irPerfil = (LinearLayout) findViewById(R.id.irPerfil);
        btnAdopcion = (ImageButton) findViewById(R.id.btn_reg_adopcion);
        btnPerdido = (ImageButton) findViewById(R.id.btn_reg_perdido);
        btnAdoptar = (ImageButton) findViewById(R.id.paraAdoptar);
        btnEncontrar = (ImageButton) findViewById(R.id.buscarPerdido);



    }

    public void irPerfil(View view){
        Intent intent = new Intent(this, PerfilUsuario.class);
        startActivity(intent);
    }


    public  void registrarmascota(View view){
        Intent intent = new Intent(this, RegistroDeAdopcion.class);
        startActivity(intent);
    }
    public  void RegistroPerdido(View view){
        Intent intent = new Intent(this, RegistroPerdido.class);
        startActivity(intent);
    }
    public  void AdoptarMascota(View view){
        Intent intent = new Intent(this, CatalogoAdopcion.class);
        startActivity(intent);
    }

    public  void BuscarPerdido(View view){
        Intent intent = new Intent(this, CatalogoPerdidos.class);
        startActivity(intent);
    }



}