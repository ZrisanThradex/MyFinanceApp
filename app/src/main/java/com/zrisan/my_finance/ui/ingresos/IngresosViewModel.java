package com.zrisan.my_finance.ui.ingresos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IngresosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public IngresosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Ingresos fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}