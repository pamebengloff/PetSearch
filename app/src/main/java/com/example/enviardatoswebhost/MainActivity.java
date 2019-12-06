package com.example.enviardatoswebhost;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Entity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    Button btnIniciarSesion;
    TextView textRegistro;
    EditText user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnIniciarSesion = (Button) findViewById(R.id.btnLogin);
        textRegistro = (TextView) findViewById(R.id.textRegistro);
        user = (EditText) findViewById(R.id.editUserLog);
        password = (EditText) findViewById(R.id.editPasswordLog);

    }

    public void RegistroUsuario(View view) {
        Intent registrousuario = new Intent(this, RegistroUsuario.class);
        startActivity(registrousuario);

    }

    public void Login(View view) {
        if (!user.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
            Intent registrousuario = new Intent(this, Menu.class);
            startActivity(registrousuario);
        } else {
            Toast.makeText(getApplicationContext(), "Usuario/Contraseña Vacios", Toast.LENGTH_SHORT).show();
        }
    }
}






