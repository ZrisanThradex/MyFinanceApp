package com.zrisan.my_finance.ui.account;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zrisan.my_finance.R;
import com.zrisan.my_finance.api.APIClient;
import com.zrisan.my_finance.api.APIService;
import com.zrisan.my_finance.models.Account;
import com.zrisan.my_finance.models.Category;
import com.zrisan.my_finance.models.NewAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormAccount extends AppCompatActivity {
    private EditText txtNombre,txtBalance;
    private Button buttonGuardar;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_account);

        ActionBar actionBar = getSupportActionBar();

        // Configurar ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Agregar Gasto"); // Cambia esto con el título deseado
            actionBar.setElevation(0); // Opcional: para eliminar la sombra debajo del ActionBar
            actionBar.setDisplayHomeAsUpEnabled(true); // Habilita el botón de navegación en la barra de acción
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.jungle_green)));
        }

        // Inicializa el EditText
        txtNombre = findViewById(R.id.edit_text_nombre);
        txtBalance = findViewById(R.id.edit_text_balance);
        buttonGuardar = findViewById(R.id.button_guardar);

        apiService = APIClient.getApiService(getApplicationContext());

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Realizar la solicitud POST a la API de ingresos
                    Call<NewAccount> saveAccount = apiService.saveAccount(txtNombre.getText().toString(),Double.parseDouble(txtBalance.getText().toString()));

                    // Ejecutar la llamada de forma asíncrona
                saveAccount.enqueue(new Callback<NewAccount>() {
                        @Override
                        public void onResponse(Call<NewAccount> call, Response<NewAccount> response) {
                            if (response.isSuccessful()) {
                                // La solicitud fue exitosa
                                NewAccount savedAccount = response.body();
                                if (savedAccount != null) {
                                    // Acceder a los atributos del objeto NewAccount
                                    String name = savedAccount.getName();
                                    double currentBalance = savedAccount.getCurrentBalance();
                                    int userId = savedAccount.getUserId();
                                    int id = savedAccount.getId();

                                    // Hacer lo que necesites con los atributos
                                    Log.d("TAG", "Nombre: " + name);
                                    Log.d("TAG", "Saldo actual: " + currentBalance);
                                    Log.d("TAG", "ID de usuario: " + userId);
                                    Log.d("TAG", "ID de cuenta: " + id);

                                    Toast.makeText(getApplicationContext(), "Cuenta guardada con éxito", Toast.LENGTH_SHORT).show();
                                    txtNombre.setText("");
                                    txtBalance.setText("");
                                    onBackPressed();
                                } else {
                                    Toast.makeText(getApplicationContext(), "La respuesta no contiene datos válidos", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // La solicitud no fue exitosa
                                // Manejar el error de acuerdo a tus necesidades
                                Toast.makeText(getApplicationContext(), "Error al guardar la cuenta", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<NewAccount> call, Throwable t) {
                            // Manejar el error en caso de fallo en la solicitud

                            t.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();

                        }
                    });
            }
        });

    }
}