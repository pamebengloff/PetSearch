package com.example.enviardatoswebhost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegistroUsuario extends AppCompatActivity {

    Button btnRegistro;
    EditText editName, editPhone, editEmail, editUser, editPassword, editPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        btnRegistro = (Button) findViewById(R.id.btnregistro);
        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editUser = (EditText) findViewById(R.id.editUser);
        editPassword = (EditText) findViewById(R.id.editPas1);
        editPassword2 = (EditText) findViewById(R.id.editPas2);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editName.getText().toString().isEmpty()
                        && !editEmail.getText().toString().isEmpty()
                        && !editPhone.getText().toString().isEmpty()
                        && !editUser.getText().toString().isEmpty()
                        && !editPassword.getText().toString().isEmpty()
                        && !editPassword2.getText().toString().isEmpty() ){
                    if (editPassword.getText().toString().equals(editPassword2.getText().toString())){
                        EnviarUsuario("https://pulsatile-cells.000webhostapp.com/Login_Insert.php");
                    }else{
                        Toast.makeText(getApplicationContext(), "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Tienes campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void EnviarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams () throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("editName",editName.getText().toString());
                parametros.put("editPhone",editPhone.getText().toString());
                parametros.put("editEmail",editEmail.getText().toString());
                parametros.put("editUser",editUser.getText().toString());
                parametros.put("editPassword",editPassword2.getText().toString());
                return parametros;
    }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
