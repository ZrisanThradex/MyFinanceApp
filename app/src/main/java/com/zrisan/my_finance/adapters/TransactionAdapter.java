package com.zrisan.my_finance.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zrisan.my_finance.R;
import com.zrisan.my_finance.models.Transaction;

import java.util.List;

public class TransactionAdapter extends ArrayAdapter<Transaction> {
    private Context context;
    private int specialIcon; // Parámetro entero para validar la condición especial

    public TransactionAdapter(Context context, List<Transaction> transactions,int specialIcon) {
        super(context, 0, transactions);
        this.context = context;
        this.specialIcon = specialIcon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_transaction, parent, false);
        }

        // Obtén el objeto Transaction correspondiente a la posición actual
        Transaction transaction = getItem(position);

        // Obtén las referencias a los elementos de la interfaz de diseño
        ImageView iconImageView = convertView.findViewById(R.id.iconImageView);
        TextView amountTextView = convertView.findViewById(R.id.amountTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);

        // Asigna los valores de la transacción a los elementos de la interfaz de diseño
        amountTextView.setText(String.valueOf(transaction.getAmount()));
        descriptionTextView.setText(transaction.getDescription());

        // Establecer el icono de la categoría según el parámetro entero
        if (specialIcon == 1) {
            iconImageView.setImageResource(R.drawable.ic_up_24);
        } else {
            iconImageView.setImageResource(R.drawable.ic_down_24);
        }

        return convertView;
    }
}

