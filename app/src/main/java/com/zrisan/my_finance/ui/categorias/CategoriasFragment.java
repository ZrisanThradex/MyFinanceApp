package com.zrisan.my_finance.ui.categorias;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zrisan.my_finance.adapters.CategoryAdapter;
import com.zrisan.my_finance.adapters.TransactionAdapter;
import com.zrisan.my_finance.api.APIClient;
import com.zrisan.my_finance.api.APIService;
import com.zrisan.my_finance.databinding.FragmentCategoriasBinding;
import com.zrisan.my_finance.models.Account;
import com.zrisan.my_finance.models.Category;
import com.zrisan.my_finance.models.Transaction;
import com.zrisan.my_finance.ui.gastos.FormGastos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriasFragment extends Fragment {
    private FloatingActionButton addCategoryButton;
    private APIService apiService;
    ListView categoryIncomeListView;
    ListView categoryExpenseListView;
    private FragmentCategoriasBinding binding;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CategoriasViewModel categoriasViewModel =
                new ViewModelProvider(this).get(CategoriasViewModel.class);

        binding = FragmentCategoriasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        apiService = APIClient.getApiService(requireContext());

        swipeRefreshLayout = binding.swipeRefreshLayout;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Aquí realizas la acción de actualización de datos
                obtenerCategorias();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configura el clic del FloatingActionButton
        addCategoryButton = binding.addCategoryButton;

        categoryExpenseListView = binding.categoryExpenseListView;
        categoryIncomeListView = binding.categoryIncomeListView;

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FormCategory.class);
                startActivity(intent);
            }
        });

        // Llama al método para mostrar los datos de las categorias
        obtenerCategorias();

    }

    private void obtenerCategorias() {
        // Realiza la llamada a obtenerTransaccionesCuenta() pasando el ID de la cuenta
        Call<List<Category>> callEx = apiService.obtenerCategoryExpense();
        callEx.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    // Obtén la lista de transacciones de la respuesta
                    List<Category> category = response.body();
                    if (!category.isEmpty()) {
                        // Crea una instancia del adaptador personalizado
                        CategoryAdapter adapter = new CategoryAdapter(categoryExpenseListView.getContext(), category,2);
                        binding.categoryExpenseListView.setAdapter(adapter);

                        // Llama a la interfaz para indicar que se han actualizado las categorías

                    } else {
                        CategoryAdapter adapter = new CategoryAdapter(categoryExpenseListView.getContext(), category,2);
                        binding.categoryExpenseListView.setAdapter(adapter);
                        // No hay transacciones disponibles
                        Log.d("DATA", "No hay categorias disponibles");
                    }
                } else {
                    // Maneja el error de la respuesta no exitosa
                    Log.d("RESPONSE_BODY", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                // Maneja el error de la llamada fallida
                Log.d("RESPONSE_BODY", "",t);
            }
        });

        // Realiza la llamada a obtenerTransaccionesCuenta() pasando el ID de la cuenta
        Call<List<Category>> callIn = apiService.obtenerCategoryIncome();
        callIn.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    // Obtén la lista de transacciones de la respuesta
                    List<Category> category = response.body();
                    if (!category.isEmpty()) {
                        // Crea una instancia del adaptador personalizado
                        CategoryAdapter adapter = new CategoryAdapter(categoryIncomeListView.getContext(), category,1);
                        binding.categoryIncomeListView.setAdapter(adapter);

                    } else {
                        CategoryAdapter adapter = new CategoryAdapter(categoryIncomeListView.getContext(), category,1);
                        binding.categoryIncomeListView.setAdapter(adapter);
                        // No hay transacciones disponibles
                        Log.d("DATA", "No hay categorias disponibles");
                    }
                } else {
                    // Maneja el error de la respuesta no exitosa
                    Log.d("RESPONSE_BODY", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                // Maneja el error de la llamada fallida
                Log.d("RESPONSE_BODY", "",t);
            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}