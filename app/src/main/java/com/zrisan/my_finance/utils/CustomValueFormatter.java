package com.zrisan.my_finance.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class CustomValueFormatter extends ValueFormatter {
    private NumberFormat format;

    public CustomValueFormatter() {
        format = NumberFormat.getCurrencyInstance(new Locale("es", "PE")); // Establece el locale a Perú (español, Perú)
        format.setCurrency(Currency.getInstance("PEN")); // Establece la moneda a PEN (Soles) en este caso
        format.setMaximumFractionDigits(2); // Establece el número máximo de dígitos decimales a mostrar
        format.setMinimumFractionDigits(2); // Establece el número mínimo de dígitos decimales a mostrar
    }

    @Override
    public String getFormattedValue(float value) {
        return format.format(value); // Retorna el valor formateado como texto personalizado
    }
}
