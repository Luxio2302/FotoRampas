package com.example.rampasimage.Login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.example.rampasimage.databinding.ActivityLoginBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


    EditText etuser, etpass;
    Button buttonLogin;
    ProgressBar progressBar;
    CheckBox CBSesion;
    TextView registrar;

    private ProgressDialog progreso;
    private RequestQueue requestQueue;
    StringRequest stringRequest;

    boolean isActivateRadioButton;

    static final String STRING_PREFERENCES = "com.jarves.navigationdrawer";
    static final String PREFERENCE_ESTADO_BUTTON_SESION = "estado.button.sesion";
    static final String PREFERENCE_USUARIO_LOGIN = "usuario.login";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);

        if (obtenerEstadoButton()){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        etuser = findViewById(R.id.username);
        etpass = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progress);
        CBSesion = findViewById(R.id.RBSecion);

        registrar = (TextView)findViewById(R.id.signUpText);

        isActivateRadioButton = CBSesion.isChecked(); //DESACTIVADO

        CBSesion.setOnClickListener(new View.OnClickListener() {
            //ACTIVADO
            @Override
            public void onClick(View v) {
                if(isActivateRadioButton){
                    CBSesion.setChecked(false);
                }
                isActivateRadioButton = CBSesion.isChecked();

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciar();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
    }

    private void iniciar() {


        progreso = new ProgressDialog(this);
        progreso.setMessage("Iniciando...");
        progreso.show();
        String url = "http://ip/upload_file/RestApi/login_movil.php?";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UserParcelable userParcelable = new UserParcelable();;
                Log.i("RESPUESTA JSON: ",""+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.names().get(0).equals("success")){
                        etuser.setText("");
                        etpass.setText("");
                        guardarEstadoButton();

                        SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username",etuser.getText().toString());
                        editor.commit();

                        userParcelable.setId(jsonObject.getJSONArray("usuario").getJSONObject(0).getInt("iduser_"));
                        userParcelable.setEmail(jsonObject.getJSONArray("usuario").getJSONObject(0).getString("email"));
                        userParcelable.setNombre(jsonObject.getJSONArray("usuario").getJSONObject(0).getString("nombres"));
                        userParcelable.setImage(jsonObject.getJSONArray("usuario").getJSONObject(0).getString("photo"));
                        userParcelable.setUsername(jsonObject.getJSONArray("usuario").getJSONObject(0).getString("username"));

                        Toast.makeText(getApplicationContext(),jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                        progreso.dismiss();

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
                progreso.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {//para enviar los datos mediante POST
                String sUsername = etuser.getText().toString();
                String sPassword =  etpass.getText().toString();

                Map<String,String> parametros = new HashMap<>();
                parametros.put("username",sUsername);
                parametros.put("password",sPassword);
                //estos parametros son enviados a nuestro web service

                return parametros;
            }
        };

        requestQueue.add(stringRequest);
    }


    public static void changeEstadoCambiarButton(Context c, boolean b){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SESION,b).apply();
    }

    public void guardarEstadoButton(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SESION,CBSesion.isChecked()).apply();

    }

    public boolean obtenerEstadoButton(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCE_ESTADO_BUTTON_SESION,false);
    }

}