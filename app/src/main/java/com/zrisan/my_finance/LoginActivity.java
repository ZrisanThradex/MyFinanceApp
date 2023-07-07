package com.zrisan.my_finance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zrisan.my_finance.api.APIClient;
import com.zrisan.my_finance.api.APIService;
import com.zrisan.my_finance.models.AuthToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView registroTextView;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private APIService apiService;

    private Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registroTextView = findViewById(R.id.registrarse);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        context = getBaseContext();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Mostrar el diálogo de carga
                LoadingDialogFragment loadingDialog = new LoadingDialogFragment();
                loadingDialog.show(getSupportFragmentManager(), "loading_dialog");

                apiService = APIClient.getApiService(getApplicationContext());

                // Realizar la llamada a la API en un hilo de trabajo separado
                Call<AuthToken> call = apiService.login(username, password);
                call.enqueue(new Callback<AuthToken>() {
                    @Override
                    public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                        // Ocultar el diálogo de carga
                        loadingDialog.dismiss();

                        if (response.isSuccessful()) {
                            AuthToken authToken = response.body();
                            String token = authToken.getToken().getToken();
                            // Procesar la respuesta del usuario
                            SharedPreferences preferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", token);
                            editor.putString("user", username);
                            editor.apply();
                            // Credenciales válidas, realizar acciones adicionales
                            // Continuar con la lógica de tu aplicación o iniciar una nueva actividad
                            Intent principal = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(principal);
                            Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        } else {
                            // Manejar error de respuesta
                            Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<AuthToken> call, Throwable t) {
                        // Ocultar el diálogo de carga
                        loadingDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
                        Log.v("err","meesage",t);
                    }
                });
            }
        });

        registroTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registro);
            }
        });
    }


}
