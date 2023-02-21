package com.example.rampasimage.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rampasimage.MainActivity;
import com.example.rampasimage.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private TextView loginLink;
    private ImageView imagePhoto;
    private EditText password;
    private TextInputEditText nombre;
    private TextInputEditText email;
    private Button registrar;
    private int request_code = 1;
    private Bitmap bitmap;
    private ProgressDialog progreso;
    RequestQueue requestQueue; //permitara la conexion directamente del web service
    StringRequest stringRequest;
    private TextInputEditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loginLink = (TextView)findViewById(R.id.link_login);
        email = (TextInputEditText)findViewById(R.id.correo_registro);
        password = (EditText)findViewById(R.id.password_registro);
        nombre = (TextInputEditText)findViewById(R.id.nombre_registro);
        registrar = (Button)findViewById(R.id.btn_registro_usuario);
        username = (TextInputEditText)findViewById(R.id.username_registro);

        requestQueue = Volley.newRequestQueue(this);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registrar();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
    }

    private void Registrar() {

        if (!validar()) return;

        progreso = new ProgressDialog(this);
        progreso.setMessage("Iniciando...");
        progreso.show();
        String url = "http://ip/upload_file/RestApi/register_movil.php?";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UserParcelable userParcelable = new UserParcelable();;
                Log.i("RESPUESTA JSON: ",""+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.names().get(0).equals("success")){
                        email.setText("");
                        nombre.setText("");
                        password.setText("");
                        username.setText("");
                        userParcelable.setId(jsonObject.getJSONArray("usuario").getJSONObject(0).getInt("iduser_"));
                        userParcelable.setEmail(jsonObject.getJSONArray("usuario").getJSONObject(0).getString("email"));
                        userParcelable.setNombre(jsonObject.getJSONArray("usuario").getJSONObject(0).getString("nombres"));
                        userParcelable.setUsername(jsonObject.getJSONArray("usuario").getJSONObject(0).getString("username"));

                        Toast.makeText(getApplicationContext(),jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                        progreso.dismiss();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("DATA_USER",userParcelable);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),jsonObject.getString("error"),Toast.LENGTH_SHORT).show();
                        Log.i("RESPUESTA JSON: ",""+jsonObject.getString("error"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progreso.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                Log.i("ERROR: ",""+error.toString());
                progreso.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {//para enviar los datos mediante POST
                String sEmail = email.getText().toString();
                String sPassword =  password.getText().toString();
                String sNombre = nombre.getText().toString();
                String sUsername = username.getText().toString();

                Map<String,String> parametros = new HashMap<>();
                parametros.put("email",sEmail);
                parametros.put("password",sPassword);
                parametros.put("nombres",sNombre);
                parametros.put("username",sUsername);
                //estos parametros son enviados a nuestro web service

                return parametros;
            }
        };

        requestQueue.add(stringRequest);
    }

    private boolean validar() {
        boolean valid = true;

        String sNombre = nombre.getText().toString();
        String sPassword = password.getText().toString();
        String sEmail = email.getText().toString();


        if (sNombre.isEmpty() || sNombre.length() < 3) {
            nombre.setError("Ingrese al menos 3 caracteres");
            valid = false;
        } else {
            nombre.setError(null);
        }

        if (sEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {
            email.setError("Dirección de correo electrónico no válida");
            valid = false;
        } else {
            email.setError(null);
        }

        if (sPassword.isEmpty() || password.length() < 4 || password.length() > 10) {
            password.setError("Ingrese entre 4 a 10 caracteres alfanuméricos");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

}