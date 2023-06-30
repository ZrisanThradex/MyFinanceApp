package com.zrisan.my_finance.ui.categorias;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zrisan.my_finance.R;
import com.zrisan.my_finance.api.APIClient;
import com.zrisan.my_finance.api.APIService;
import com.zrisan.my_finance.models.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormCategory extends AppCompatActivity  {
    private EditText txtNombre;
    private Spinner spinnerTipo;
    private Button buttonGuardar;
    private APIService apiService;

    private CategoriasFragment categoriasFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_category);

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
        spinnerTipo = findViewById(R.id.spinner_tipo);
        buttonGuardar = findViewById(R.id.button_guardar);
        categoriasFragment = new CategoriasFragment();
        apiService = APIClient.getApiService(FormCategory.this);

        List<String> items = new ArrayList<>();
        items.add("- Seleccione un tipo -");
        items.add("INGRESO");
        items.add("GASTO");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTipo.setAdapter(adapter);

        // Agregar transición de entrada personalizada
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el ítem seleccionado del Spinner
                String selectedItem = spinnerTipo.getSelectedItem().toString();

                // Verificar el ítem seleccionado y realizar la solicitud correspondiente
                if (selectedItem.equals("INGRESO")) {
                    // Realizar la solicitud POST a la API de ingresos
                    Call<Category> callIngresos = apiService.saveCategoryIncome(txtNombre.getText().toString());

                    // Ejecutar la llamada de forma asíncrona
                    callIngresos.enqueue(new Callback<Category>() {
                        @Override
                        public void onResponse(Call<Category> call, Response<Category> response) {
                            if (response.isSuccessful()) {
                                // La solicitud fue exitosa
                                Category savedCategory = response.body();
                                Toast.makeText(getApplicationContext(), "Categoría guardada con éxito", Toast.LENGTH_SHORT).show();

                                // Limpiar el contenido del EditText
                                txtNombre.setText("");

                                // Actualiza los datos en el fragmento CategoriasFragment
                                // categoriasFragment.actualizarCategorias();

                                // Redireccionar a la página anterior
                                onBackPressed();
                            } else {
                                // La solicitud no fue exitosa
                                // Manejar el error de acuerdo a tus necesidades
                                Toast.makeText(getApplicationContext(), "Error al guardar la categoría", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Category> call, Throwable t) {
                            // Manejar el error en caso de fallo en la solicitud

                            t.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();

                        }
                    });
                } else if (selectedItem.equals("GASTO")) {
                    // Crear una instancia de Category para gastos

                    // Realizar la solicitud POST a la API de gastos
                    Call<Category> callGastos = apiService.saveCategoryExpense(txtNombre.getText().toString());

                    // Ejecutar la llamada de forma asíncrona
                    callGastos.enqueue(new Callback<Category>() {
                        @Override
                        public void onResponse(Call<Category> call, Response<Category> response) {
                            if (response.isSuccessful()) {
                                // La solicitud fue exitosa
                                Category savedCategory = response.body();
                                Toast.makeText(getApplicationContext(), "Categoría guardada con éxito", Toast.LENGTH_SHORT).show();

                                // Limpiar el contenido del EditText
                                txtNombre.setText("");

                                // Actualiza los datos en el fragmento CategoriasFragment
                                //categoriasFragment.actualizarCategorias();

                                // Redireccionar a la página anterior
                                onBackPressed();
                            } else {
                                // La solicitud no fue exitosa
                                // Manejar el error de acuerdo a tus necesidades
                                Toast.makeText(getApplicationContext(), "Error al guardar la categoría", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Category> call, Throwable t) {
                            // Manejar el error en caso de fallo en la solicitud
                            t.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

}