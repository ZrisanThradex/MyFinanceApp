package com.zrisan.my_finance.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zrisan.my_finance.R;
import com.zrisan.my_finance.models.Account;

import java.util.List;

public class AccountAdapter extends ArrayAdapter<Account> {

    public AccountAdapter(Context context, List<Account> accounts) {
        super(context, 0, accounts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Account account = getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView balanceTextView = convertView.findViewById(R.id.balanceTextView);


        nameTextView.setText(account.getName());
        balanceTextView.setText("S/. "+account.getCurrentBalance());

        return convertView;
    }
}
