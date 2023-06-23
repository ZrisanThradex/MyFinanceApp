package com.zrisan.my_finance.ui.gastos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zrisan.my_finance.R;
import com.zrisan.my_finance.api.APIClient;
import com.zrisan.my_finance.api.APIService;
import com.zrisan.my_finance.models.Account;
import com.zrisan.my_finance.models.Category;
import com.zrisan.my_finance.models.Transaction;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormGastos extends AppCompatActivity {

    private EditText titleEditText;
    private EditText amountEditText;
    private EditText dateEditText;
    private Button categoryButton,saveButton,buttonAccounts;
    private TextView textViewCategory,idCategory,textViewAccount,accountData;

    private APIService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_gastos);
        ActionBar actionBar = getSupportActionBar();

        // Configurar ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Agregar Gasto"); // Cambia esto con el título deseado
            actionBar.setElevation(0); // Opcional: para eliminar la sombra debajo del ActionBar
            actionBar.setDisplayHomeAsUpEnabled(true); // Habilita el botón de navegación en la barra de acción
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.jungle_green))); // Cambia "mi_color" con el nombre de tu color personalizado
        }

        // Inicializa los Elementos
        titleEditText = findViewById(R.id.editTextTitle);
        amountEditText = findViewById(R.id.editTextAmount);
        dateEditText = findViewById(R.id.editTextDate);
        categoryButton = findViewById(R.id.buttonCategory);
        buttonAccounts = findViewById(R.id.buttonAccount);
        textViewAccount=findViewById(R.id.textViewAccount);
        textViewCategory = findViewById(R.id.textViewCategory);
        idCategory = findViewById(R.id.categoryData);
        accountData=findViewById(R.id.accountData);
        saveButton = findViewById(R.id.buttonSave);
        apiService = APIClient.getApiService(this);

        // Configura el EditText para tener una sola línea
        titleEditText.setSingleLine();

        // Para hacer el TextView invisible y que no ocupe espacio
        idCategory.setVisibility(View.GONE);
        accountData.setVisibility(View.GONE);

        // Configura el OnClickListener para mostrar el DatePicker
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerCategoriasGastos();
            }
        });

        buttonAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerCuentas();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = titleEditText.getText().toString();
                Double amount =Double.parseDouble(amountEditText.getText().toString());
                String date = dateEditText.getText().toString();
                String type = "GASTO";
                int idCat =Integer.parseInt(idCategory.getText().toString());
                int idAcc =Integer.parseInt(accountData.getText().toString());

                Call<Transaction> callExpensive = apiService.saveTransaction(description,amount,date,type,idCat,idAcc);

                // Ejecutar la llamada de forma asíncrona
                callExpensive.enqueue(new Callback<Transaction>() {
                    @Override
                    public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                        if (response.isSuccessful()) {
                            // La solicitud fue exitosa
                            Transaction transaction = response.body();
                            Toast.makeText(getApplicationContext(), "Categoría guardada con éxito", Toast.LENGTH_SHORT).show();

                            // Limpiar el contenido del EditText

                            titleEditText.setText("");
                            textViewAccount.setText("Seleccione una cuenta");
                            amountEditText.setText("");
                            dateEditText.setText("");
                            textViewCategory.setText("Selecciona una categoria");
                            idCategory.setText("");
                            accountData.setText("");


                            // Actualiza los datos en el fragmento CategoriasFragment
                            // categoriasFragment.actualizarCategorias();

                            // Redireccionar a la página anterior
                            onBackPressed();
                        } else {
                            // La solicitud no fue exitosa
                            // Manejar el error de acuerdo a tus necesidades
                            Toast.makeText(getApplicationContext(), "Error al guardar la transaccion", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Transaction> call, Throwable t) {
                        // Manejar el error en caso de fallo en la solicitud
                        t.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void mostrarSeleccionEnTextViewCategory(String opcion,int id) {
        TextView textView = findViewById(R.id.textViewCategory);
        TextView textViewId = findViewById(R.id.categoryData);

        textView.setText(opcion);
        textViewId.setText(String.valueOf(id));
    }

    private void obtenerCategoriasGastos() {
        Call<List<Category>> call = apiService.obtenerCategoryExpense();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    List<Category> categorias = response.body();
                    mostrarAlertDialogCategory(categorias);
                } else {
                    // Manejo de error en caso de que la llamada no sea exitosa
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                // Manejo de error en caso de fallo en la llamada
            }
        });
    }

    private void mostrarAlertDialogCategory(List<Category> categorias) {
        final CharSequence[] opciones = new CharSequence[categorias.size()];
        final int[] ids = new int[categorias.size()];

        for (int i = 0; i < categorias.size(); i++) {
            Category categoria = categorias.get(i);
            opciones[i] = categoria.getName();
            ids[i] = categoria.getId();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int seleccion) {
                String opcionSeleccionada = opciones[seleccion].toString();
                int idSeleccionado = ids[seleccion];
                mostrarSeleccionEnTextViewCategory(opcionSeleccionada, idSeleccionado);
            }
        });
        builder.show();
    }

    //Accounts
    private void obtenerCuentas() {
        Call<List<Account>> call = apiService.obtenerCuentas();
        call.enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if (response.isSuccessful()) {
                    List<Account> accounts = response.body();
                    mostrarAlertDialogAccount(accounts);
                } else {
                    // Manejo de error en caso de que la llamada no sea exitosa
                }
            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {
                // Manejo de error en caso de fallo en la llamada
            }
        });
    }
    private void mostrarAlertDialogAccount(List<Account> accounts) {
        final CharSequence[] opciones = new CharSequence[accounts.size()];
        final int[] ids = new int[accounts.size()];

        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            opciones[i] = account.getName();
            ids[i] = account.getId();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una cuenta");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int seleccion) {
                String opcionSeleccionada = opciones[seleccion].toString();
                int idSeleccionado = ids[seleccion];
                mostrarSeleccionEnTextViewAccount(opcionSeleccionada,idSeleccionado);
            }
        });
        builder.show();
    }

    private void mostrarSeleccionEnTextViewAccount(String opcion,int id) {
        TextView textView = findViewById(R.id.textViewAccount);
        TextView textViewId = findViewById(R.id.accountData);

        textView.setText(opcion);
        textViewId.setText(String.valueOf(id));
    }

    // Método para mostrar el DatePicker
    private void showDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Aquí obtienes la fecha seleccionada y puedes actualizar el campo de fecha
                // con el formato deseado
                String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year);
                dateEditText.setText(selectedDate);
            }
        };

        // Obtén la fecha actual para mostrar en el DatePicker
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Crea y muestra el DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Verifica si se hizo clic en el botón de navegación (flecha hacia atrás)
        if (item.getItemId() == android.R.id.home) {
            // Finaliza la actividad actual y regresa al activity anterior
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}