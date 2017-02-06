package com.martin.categoriasyproductos.ui;

import com.martin.categoriasyproductos.R;
import com.martin.categoriasyproductos.model.NumberTextWatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedProductActivity extends AppCompatActivity {

    @BindView(R.id.editTextName) EditText mName;
    @BindView(R.id.editTextPrice) EditText mPrice;
    @BindView(R.id.editTextStock) EditText mStock;
    @BindView(R.id.editTextCreation) EditText mCreation;
    @BindView(R.id.editTextExpiration) EditText mExpiration;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_product);

        ButterKnife.bind(this);

        mPrice.addTextChangedListener(new NumberTextWatcher(mPrice,"#,##"));



    }


}


