package com.zrisan.my_finance.ui.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zrisan.my_finance.adapters.CategoryAdapter;
import com.zrisan.my_finance.api.APIClient;
import com.zrisan.my_finance.api.APIService;
import com.zrisan.my_finance.databinding.FragmentAccountBinding;
import com.zrisan.my_finance.databinding.FragmentGastosBinding;
import com.zrisan.my_finance.models.Account;
import com.zrisan.my_finance.models.Category;
import com.zrisan.my_finance.ui.categorias.FormCategory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAccount extends Fragment {
    private FloatingActionButton addAccountButton;
    private FragmentAccountBinding binding;
    private APIService apiService;
    ListView accountListView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FragmentAccount() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        apiService = APIClient.getApiService(requireContext());
        swipeRefreshLayout = binding.swipeRefreshLayoutAccount;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Aquí realizas la acción de actualización de datos
                 mostrarCuentas();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Configura el clic del FloatingActionButton
        addAccountButton = binding.addAccountButton;
        accountListView= binding.accountListView;

        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FormAccount.class);
                startActivity(intent);
            }
        });

        // Llama al método para mostrar los datos de las categorias
        mostrarCuentas();
    }

    private void mostrarCuentas() {
        // Realiza la llamada a obtenerTransaccionesCuenta() pasando el ID de la cuenta
        Call<List<Account>> callEx = apiService.obtenerCuentas();
        callEx.enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if (response.isSuccessful()) {
                    // Obtén la lista de transacciones de la respuesta
                    List<Account> accounts = response.body();

                        // Crea un adaptador para el Spinner utilizando la lista de nombres de cuentas
                        ArrayAdapter<Account> accountAdapter = new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_spinner_item, accounts);
                        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // Establece el adaptador en el Spinner
                        binding.accountListView.setAdapter(accountAdapter);
                } else {
                    // Maneja el error de la respuesta no exitosa
                    Log.d("RESPONSE_BODY", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {
                // Maneja el error de la llamada fallida
                Log.d("RESPONSE_BODY", "",t);
            }
        });
    }
}