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
import com.zrisan.my_finance.models.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private Context context;
    private int specialCategoryId; // Parámetro entero para validar la condición especial

    public CategoryAdapter(Context context, List<Category> categories, int specialCategoryId) {
        super(context, 0, categories);
        this.context = context;
        this.specialCategoryId = specialCategoryId;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflar el diseño del elemento de categoría
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        }

        // Obtener referencias a los elementos de diseño
        ImageView iconImageView = convertView.findViewById(R.id.iconImageView);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);

        // Obtener el elemento de categoría actual
        Category category = getItem(position);

        // Establecer el nombre de la categoría
        nameTextView.setText(category.getName());

        // Establecer el icono de la categoría según el parámetro entero
        if (specialCategoryId == 1) {
            iconImageView.setImageResource(R.drawable.ic_up_24);
        } else {
            iconImageView.setImageResource(R.drawable.ic_down_24);
        }

        return convertView;
    }
}

