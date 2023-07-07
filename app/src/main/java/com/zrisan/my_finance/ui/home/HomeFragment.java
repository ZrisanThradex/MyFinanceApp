package com.zrisan.my_finance.ui.home;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zrisan.my_finance.MainActivity;
import com.zrisan.my_finance.R;
import com.zrisan.my_finance.adapters.AccountAdapter;
import com.zrisan.my_finance.api.APIClient;
import com.zrisan.my_finance.api.APIService;
import com.zrisan.my_finance.databinding.FragmentHomeBinding;
import com.zrisan.my_finance.models.Account;
import com.zrisan.my_finance.models.CategoryTotal;
import com.zrisan.my_finance.models.TotalBalance;
import com.zrisan.my_finance.utils.CustomValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TextView balanceTextView;
    private TextView amountTextView;
    private APIService apiService;
    private List<CategoryTotal> listCategoryTotal,listCategoryTotal2;

    private PieChart donutChart,donutChart2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        balanceTextView = binding.textBalance;
        amountTextView = binding.textAmount;
        apiService = APIClient.getApiService(requireContext());
        balanceTextView.setText("Balance Total");

        donutChart = root.findViewById(R.id.pie_chart);
        donutChart2 = root.findViewById(R.id.pie_chart_two);

        amountTotal();
        setupDonutChart();
        setupDonutChartTwo();
        totalByExpense();
        totalByIncome();

        return root;
    }



    private void amountTotal() {
        Call<TotalBalance> callTotal = apiService.obtenerTotal();
        callTotal.enqueue(new Callback<TotalBalance>() {
            @Override
            public void onResponse(Call<TotalBalance> call, Response<TotalBalance> response) {
                if (response.isSuccessful()) {
                    TotalBalance totalBalance = response.body();
                    double saldoTotal = totalBalance.getTotalBalance();
                    amountTextView.setText("S/." + String.valueOf(saldoTotal));
                } else {
                    Log.d("RESPONSE_BODY", response.toString());
                }
            }

            @Override
            public void onFailure(Call<TotalBalance> call, Throwable t) {
                Log.d("RESPONSE_BODY", "", t);
            }
        });
    }

    private void totalByExpense() {
        Call<List<CategoryTotal>> callExpense = apiService.obtenerTotalExpenses();

        callExpense.enqueue(new Callback<List<CategoryTotal>>() {
            @Override
            public void onResponse(Call<List<CategoryTotal>> call, Response<List<CategoryTotal>> response) {
                if (response.isSuccessful()) {
                    listCategoryTotal = response.body();
                    if (listCategoryTotal != null) {
                        setupDonutChart();
                    } else {
                        // Tratar el caso en que no haya datos disponibles
                    }
                } else {
                    Toast.makeText(getContext(), "Error en la respuesta", Toast.LENGTH_SHORT).show();
                    Log.d("RESPONSE_BODY", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<CategoryTotal>> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
                Log.d("RESPONSE_BODY", "", t);
            }
        });
    }
    private void totalByIncome() {
        Call<List<CategoryTotal>> callExpense = apiService.obtenerTotalIncomes();

        callExpense.enqueue(new Callback<List<CategoryTotal>>() {
            @Override
            public void onResponse(Call<List<CategoryTotal>> call, Response<List<CategoryTotal>> response) {
                if (response.isSuccessful()) {
                    listCategoryTotal2 = response.body();
                    if (listCategoryTotal2 != null) {
                        setupDonutChartTwo();
                    } else {
                        // Tratar el caso en que no haya datos disponibles
                    }
                } else {
                    Toast.makeText(getContext(), "Error en la respuesta", Toast.LENGTH_SHORT).show();
                    Log.d("RESPONSE_BODY", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<CategoryTotal>> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
                Log.d("RESPONSE_BODY", "", t);
            }
        });
    }

    private List<Integer> generateColors(int count) {
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int color = getRandomColor();
            colors.add(color);
        }
        return colors;
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private void setupDonutChart() {
        List<PieEntry> entries = createPieEntries(listCategoryTotal);
        PieDataSet dataSet = createPieDataSet(entries);

        // Generar colores aleatorios para las entradas del gráfico
        List<Integer> colors = generateColors(entries.size());
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new CustomValueFormatter());
        pieData.setValueTextSize(14f);
        pieData.setValueTypeface(Typeface.DEFAULT_BOLD);

        donutChart.setData(pieData);
        donutChart.setHoleColor(Color.TRANSPARENT);
        donutChart.setTransparentCircleColor(Color.TRANSPARENT);
        donutChart.setTransparentCircleAlpha(110);
        donutChart.setHoleRadius(60f);
        donutChart.setTransparentCircleRadius(65f);
        donutChart.setDrawCenterText(true);
        donutChart.setCenterText("Gastos");
        donutChart.setCenterTextSize(20f);
        donutChart.setCenterTextColor(Color.WHITE);
        donutChart.setDrawEntryLabels(false);
        donutChart.getDescription().setEnabled(false);

        Legend legend = donutChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextColor(Color.WHITE);
    }


    private void setupDonutChartTwo() {
        List<PieEntry> entries = createPieEntries(listCategoryTotal2);
        PieDataSet dataSet = createPieDataSet(entries);

        // Generar colores aleatorios para las entradas del gráfico
        List<Integer> colors = generateColors(entries.size());
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new CustomValueFormatter());
        pieData.setValueTextSize(14f);
        pieData.setValueTypeface(Typeface.DEFAULT_BOLD);

        donutChart2.setData(pieData);
        donutChart2.setHoleColor(Color.TRANSPARENT);
        donutChart2.setTransparentCircleColor(Color.TRANSPARENT);
        donutChart2.setTransparentCircleAlpha(110);
        donutChart2.setHoleRadius(60f);
        donutChart2.setTransparentCircleRadius(65f);
        donutChart2.setDrawCenterText(true);
        donutChart2.setCenterText("Ingresos");
        donutChart2.setCenterTextSize(20f);
        donutChart2.setCenterTextColor(Color.WHITE);
        donutChart2.setDrawEntryLabels(false);
        donutChart2.getDescription().setEnabled(false);

        Legend legend = donutChart2.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextColor(Color.WHITE);
    }

    private List<PieEntry> createPieEntries(List<CategoryTotal> category) {
        List<PieEntry> entries = new ArrayList<>();
        if (category != null) {
            for (CategoryTotal categoryTotal : category) {
                entries.add(new PieEntry((float) categoryTotal.getBalance(), categoryTotal.getName()));
            }
        }
        return entries;
    }

    private PieDataSet createPieDataSet(List<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "Categorias");
        dataSet.setColors(Color.BLUE, Color.GREEN, Color.RED);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);
        return dataSet;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


