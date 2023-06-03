package com.zrisan.my_finance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zrisan.my_finance.helpers.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private TextView registroTextView;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registroTextView = findViewById(R.id.registrarse);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Mostrar el diálogo de carga
                LoadingDialogFragment loadingDialog = new LoadingDialogFragment();
                loadingDialog.show(getSupportFragmentManager(), "loading_dialog");

                SharedPreferences sharedPreferences = getSharedPreferences("Auth", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Simular una operación de inicio de sesión
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Ocultar el diálogo de carga
                        loadingDialog.dismiss();

                        // Aquí puedes realizar la validación de las credenciales
                        if (databaseHelper.checkUser(username, password)) {
                            // Guardando preferencias
                            //editor.putString("username", username);
                            //editor.putString("password", password);
                            editor.putBoolean("isLoggedIn", true);
                            editor.apply();

                            // Mostrar Toast en el hilo principal (UI thread)
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                }
                            });

                            // Credenciales válidas, realizar acciones adicionales
                            // Continuar con la lógica de tu aplicación o iniciar una nueva actividad
                            Intent principal = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(principal);
                        } else {
                            // Mostrar Toast en el hilo principal (UI thread)
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();

            }
        });

        registroTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registro = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registro);
            }
        });
    }

}
