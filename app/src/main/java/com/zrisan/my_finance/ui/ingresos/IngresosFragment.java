package com.zrisan.my_finance.ui.ingresos;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.zrisan.my_finance.databinding.FragmentIngresosBinding;
import com.zrisan.my_finance.helpers.CategoryTableHelper;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class IngresosFragment extends Fragment {
    private CategoryTableHelper categoryTableHelper;
    private EditText titleEditText;
    private EditText amountEditText;
    private EditText dateEditText;
    private Button categoryButton;
    private Button saveButton;
    private TextView textViewCategory;
    private FragmentIngresosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IngresosViewModel ingresosViewModel =
                new ViewModelProvider(this).get(IngresosViewModel.class);

        binding = FragmentIngresosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        titleEditText = binding.editTextTitle;
        amountEditText = binding.editTextAmount;
        dateEditText = binding.editTextDate;
        textViewCategory = binding.textViewCategory;
        categoryButton = binding.buttonCategory;
        saveButton = binding.buttonSave;



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
                showCategoryListDialog();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCategoria();
            }
        });

        return root;
    }

    private void guardarCategoria() {
        String nombre = titleEditText.getText().toString().trim();
        String categoria = textViewCategory.getText().toString().trim();

        // Aquí puedes implementar la lógica para guardar la categoría en tu base de datos o en cualquier otro lugar
        // Puedes utilizar las variables 'nombre' y 'categoria' como desees

        Toast.makeText(getActivity(), "Categoría guardada: " + nombre + ", Categoría: " + categoria, Toast.LENGTH_SHORT).show();
        // Puedes realizar otras acciones después de guardar la categoría, como volver a la lista de categorías, etc.
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    private void showCategoryListDialog() {
        // Obtén la lista de categorías desde tu base de datos o estructura de datos
        categoryTableHelper = new CategoryTableHelper(requireContext());
        List<String> categoryList = categoryTableHelper.getAllCategories();

        if (categoryList.isEmpty()) {
            // No hay datos en la tabla "categories"
            Log.d("CategoryTableHelper", "No hay datos en la tabla");
        } else {
            // Hay datos en la tabla "categories"
            Log.d("CategoryTableHelper", "Hay " + categoryList.size() + " registros en la tabla");

            // Puedes recorrer la lista e imprimir los nombres de las categorías
            for (String category : categoryList) {
                Log.d("CategoryTableHelper", "Categoría: " + category);
            }
        }

        // Convierte la lista de categorías en un arreglo de cadenas
        final CharSequence[] categories = categoryList.toArray(new CharSequence[categoryList.size()]);

        // Crea un AlertDialog con la lista de categorías
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Seleccionar Categoría")
                .setItems(categories, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Aquí obtienes la categoría seleccionada y puedes mostrarla en el TextView
                        String selectedCategory = categoryList.get(which);
                        textViewCategory.setText(selectedCategory);
                    }
                })
                .show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}