package com.zrisan.my_finance.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zrisan.my_finance.models.Account;

import java.util.List;

public class AccountSpinnerAdapter extends ArrayAdapter<Account> {
    public AccountSpinnerAdapter(Context context, List<Account> accounts) {
        super(context, 0, accounts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        TextView nameTextView = convertView.findViewById(android.R.id.text1);
        TextView balanceTextView = convertView.findViewById(android.R.id.text2);

        Account account = getItem(position);
        nameTextView.setText(account.getName());
        balanceTextView.setText(account.getCurrentBalance());

        return convertView;
    }
}
