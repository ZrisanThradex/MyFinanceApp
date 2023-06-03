package com.zrisan.my_finance.ui.home;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zrisan.my_finance.R;
import com.zrisan.my_finance.databinding.FragmentHomeBinding;
import com.zrisan.my_finance.utils.CustomValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private CardView cardView;
    private CardView cardViewPie;
    private TextView balanceTextView;
    private TextView amountTextView;

    private PieChart donutChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cardView = binding.cardView;
        balanceTextView = binding.textBalance;
        amountTextView = binding.textAmount;

        balanceTextView.setText("Balance");
        amountTextView.setText("S/. 9000");

        donutChart = root.findViewById(R.id.pie_chart);
        cardViewPie = root.findViewById(R.id.card_view_pie);
        setupDonutChart();

        return root;
    }

    private void setupDonutChart() {

        List<PieEntry> entries = createPieEntries();
        PieDataSet dataSet = createPieDataSet(entries);
        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new CustomValueFormatter());// Aplica el formateador personalizado al texto del valor en porcentaje
        pieData.setValueTextSize(14f); // Aumenta el tamaño del texto del valor en porcentaje
        pieData.setValueTypeface(Typeface.DEFAULT_BOLD); // Aplica formato en negrita (bold) al texto del valor en porcentaje

        donutChart.setData(pieData);
        donutChart.setHoleColor(Color.TRANSPARENT); // Color del agujero en el centro
        donutChart.setTransparentCircleColor(Color.TRANSPARENT); // Color del círculo transparente alrededor del agujero
        donutChart.setTransparentCircleAlpha(110); // Transparencia del círculo transparente alrededor del agujero
        donutChart.setHoleRadius(60f); // Tamaño del agujero en el centro
        donutChart.setTransparentCircleRadius(65f); // Tamaño del círculo transparente alrededor del agujero
        donutChart.setDrawCenterText(true); // Dibujar texto en el centro
        donutChart.setCenterText("Gastos"); // Texto en el centro
        donutChart.setCenterTextSize(20f); // Tamaño del texto en el centro
        donutChart.setCenterTextColor(Color.WHITE); // Color del texto en el centro
        donutChart.setDrawEntryLabels(false); // Oculta los textos debajo de cada porcentaje

        Legend legend = donutChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT); // Ubica la leyenda a la izquierda
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); // Ubica la leyenda en la parte superior
        legend.setOrientation(Legend.LegendOrientation.VERTICAL); // Muestra la leyenda en una columna
        legend.setTextColor(Color.WHITE); // Cambia el color del texto de la leyenda a blanco
        // Configura más opciones según tus necesidades
    }

    private List<PieEntry> createPieEntries() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(30f,"Alimentacion"));
        entries.add(new PieEntry(20f,"Diversioni"));
        entries.add(new PieEntry(50f,"Transporte"));
        return entries;
    }

    private PieDataSet createPieDataSet(List<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries,"Categorias");
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