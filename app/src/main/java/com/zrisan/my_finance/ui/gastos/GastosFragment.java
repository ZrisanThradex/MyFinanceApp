package com.zrisan.my_finance.ui.gastos;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.zrisan.my_finance.databinding.FragmentGastosBinding;

import java.util.Calendar;
import java.util.Locale;

public class GastosFragment extends Fragment {
    private EditText titleEditText;
    private EditText amountEditText;
    private EditText dateEditText;
    private Button categoryButton;
    private Button saveButton;
    private TextView textViewCategory;
    private FragmentGastosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GastosViewModel gastosViewModel =
                new ViewModelProvider(this).get(GastosViewModel.class);

        binding = FragmentGastosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

         titleEditText = binding.editTextTitle;
         amountEditText = binding.editTextAmount;
         dateEditText = binding.editTextDate;
        textViewCategory = binding.textViewCategory;
         categoryButton = binding.buttonCategory;
         saveButton = binding.buttonSave;

        titleEditText.setSingleLine();

        // Configura el OnClickListener para mostrar el DatePicker
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        return root;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}