package com.zrisan.my_finance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zrisan.my_finance.helpers.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        registerButton = findViewById(R.id.registroButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Mostrar el diálogo de carga
                LoadingDialogFragment loadingDialog = new LoadingDialogFragment();
                loadingDialog.show(getSupportFragmentManager(), "loading_dialog");

                // Guardar el usuario en la base de datos en un hilo de fondo
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Aquí puedes realizar la operación de guardar en la base de datos
                        // Por ejemplo, llamar al método insertUser() de DatabaseHelper
                        if(!databaseHelper.isTableExists()){
                            Log.v("LOGEO","NO SE HA REGISTRADO");
                        }else{
                            databaseHelper.insertUser(username, password);
                            //Guardando preferencias
                            SharedPreferences sharedPreferences = getSharedPreferences("Auth", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            //editor.putString("username", username);
                            //editor.putString("password", password);
                            editor.putBoolean("isLoggedIn", true);
                            editor.apply();

                            // Ocultar el diálogo de carga en el hilo principal
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Ocultar el diálogo de carga
                                    loadingDialog.dismiss();

                                    // Mostrar mensaje de éxito
                                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                                    // Continuar con la lógica de tu aplicación o iniciar una nueva actividad
                                    Intent principal = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(principal);
                                }
                            });
                        }

                    }
                }).start();
            }
        });
    }
}