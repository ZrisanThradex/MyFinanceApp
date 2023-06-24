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

import com.zrisan.my_finance.api.APIClient;
import com.zrisan.my_finance.api.APIService;
import com.zrisan.my_finance.models.AuthToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private APIService apiService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Obtener referencias a las vistas
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

                SharedPreferences sharedPreferences = getSharedPreferences("Auth", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                apiService = APIClient.getApiService(null);

                // Realizar la llamada a la API en un hilo de trabajo separado
                Call<AuthToken> call = apiService.register(username, password);
                call.enqueue(new Callback<AuthToken>() {
                    @Override
                    public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                        // Ocultar el diálogo de carga
                        loadingDialog.dismiss();

                        if (response.isSuccessful()) {
                            // Registro exitoso, realizar acciones adicionales si es necesario
                            AuthToken authToken = response.body();

                            // Continuar con la lógica de tu aplicación o iniciar una nueva actividad
                            Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(login);
                            Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        } else {
                            // Manejar error de respuesta
                            Toast.makeText(RegisterActivity.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthToken> call, Throwable t) {
                        // Ocultar el diálogo de carga
                        loadingDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}