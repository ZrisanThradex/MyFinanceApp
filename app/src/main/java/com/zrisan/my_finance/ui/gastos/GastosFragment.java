package com.zrisan.my_finance.ui.gastos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.zrisan.my_finance.adapters.TransactionAdapter;
import com.zrisan.my_finance.api.APIClient;
import com.zrisan.my_finance.api.APIService;
import com.zrisan.my_finance.databinding.FragmentGastosBinding;
import com.zrisan.my_finance.models.Account;
import com.zrisan.my_finance.models.Transaction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GastosFragment extends Fragment {
    private FloatingActionButton addExpenseButton;
    private FragmentGastosBinding binding;
    private APIService apiService;
    ListView transactionListView;
    private List<Account> accounts; // Variable de clase para almacenar la lista de cuentas
    private SwipeRefreshLayout swipeRefreshLayout;

    public GastosFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GastosViewModel gastosViewModel =
                new ViewModelProvider(this).get(GastosViewModel.class);

        binding = FragmentGastosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        apiService = APIClient.getApiService(requireContext());
        swipeRefreshLayout = binding.swipeRefreshLayoutGasto;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Aquí realizas la acción de actualización de datos
                mostrarDatosCuenta();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configura el clic del FloatingActionButton
        addExpenseButton = binding.addExpenseButton;
        transactionListView= binding.transactionListView;

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FormGastos.class);
                startActivity(intent);
            }
        });

        binding.accountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Acción al seleccionar un elemento del Spinner
                String selectedAccountName = (String) parent.getItemAtPosition(position);

                // Busca el objeto Account correspondiente al nombre de la cuenta seleccionada
                Account selectedAccount = null;
                for (Account account : accounts) {
                    if (account.getName().equals(selectedAccountName)) {
                        selectedAccount = account;
                        break;
                    }
                }

                // Verifica si se encontró el objeto Account correspondiente
                if (selectedAccount != null) {
                    // Aquí puedes obtener el ID del objeto Account seleccionado utilizando selectedAccount.getId()
                    int accountId = selectedAccount.getId();

                    // Realiza la llamada para obtener las transacciones de la cuenta
                    obtenerTransaccionesCuenta(accountId);
                }

                // Realiza las operaciones necesarias con la cuenta seleccionada
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acción cuando no se selecciona ningún elemento
            }
        });

        // Llama al método para mostrar los datos de la cuenta
        mostrarDatosCuenta();
    }

    private void mostrarDatosCuenta() {
        // Realiza la llamada a obtenerCuentas()
        Call<List<Account>> call = apiService.obtenerCuentas();
        call.enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if (response.isSuccessful()) {
                    accounts = response.body();

                    // Crea una lista de nombres de cuentas
                    List<String> accountNames = new ArrayList<>();
                    for (Account account : accounts) {
                        accountNames.add(account.getName());
                    }

                    // Crea un adaptador para el Spinner utilizando la lista de nombres de cuentas
                    ArrayAdapter<String> accountAdapter = new ArrayAdapter<>(requireContext(),
                            android.R.layout.simple_spinner_item, accountNames);
                    accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Establece el adaptador en el Spinner
                    binding.accountSpinner.setAdapter(accountAdapter);
                } else {
                    // Maneja el error de la respuesta no exitosa

                }
            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {
                // Maneja el error de la llamada fallida

            }
        });
    }

    private void obtenerTransaccionesCuenta(int accountId) {
        // Realiza la llamada a obtenerTransaccionesCuenta() pasando el ID de la cuenta
        Call<List<Transaction>> call = apiService.obtenerTransaccionesCuenta(accountId,"GASTO");
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful()) {
                    // Obtén la lista de transacciones de la respuesta
                    List<Transaction> transactions = response.body();
                    if (!transactions.isEmpty()) {
                        // Crea una instancia del adaptador personalizado
                        TransactionAdapter adapter = new TransactionAdapter(transactionListView.getContext(), transactions,2);
                        binding.transactionListView.setAdapter(adapter);
                    } else {
                        TransactionAdapter adapter = new TransactionAdapter(transactionListView.getContext(), transactions,2);
                        binding.transactionListView.setAdapter(adapter);
                        // No hay transacciones disponibles
                        Log.d("DATA", "No hay transacciones disponibles");
                    }
                } else {
                    // Maneja el error de la respuesta no exitosa
                    Log.d("RESPONSE_BODY", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
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