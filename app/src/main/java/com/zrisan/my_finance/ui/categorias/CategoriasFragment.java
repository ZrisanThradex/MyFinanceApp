package com.zrisan.my_finance.ui.categorias;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zrisan.my_finance.databinding.FragmentCategoriasBinding;
import com.zrisan.my_finance.helpers.CategoryTableHelper;

public class CategoriasFragment extends Fragment {
    private CategoryTableHelper categoryTableHelper;
    private EditText editTextNombre;
    private Button buttonGuardar;
    private FragmentCategoriasBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CategoriasViewModel categoriasViewModel =
                new ViewModelProvider(this).get(CategoriasViewModel.class);

        binding = FragmentCategoriasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Context context = requireContext();
        categoryTableHelper= new CategoryTableHelper(context);

        editTextNombre = binding.editTextNombre;
        buttonGuardar = binding.buttonGuardar;

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCategoria();
            }
        });
        return root;
    }

    private void guardarCategoria() {
        String nombre = editTextNombre.getText().toString().trim();

        if (!categoryTableHelper.isTableExists()) {
            // La tabla no existe, muestra un mensaje de error o realiza acciones necesarias
            Toast.makeText(getActivity(),"Aun NO",Toast.LENGTH_SHORT);
        } else {
            // La tabla existe, puedes ejecutar la inserción
            categoryTableHelper.insertCategory(nombre);
            Toast.makeText(getActivity(), "Categoría guardada: " + nombre, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}