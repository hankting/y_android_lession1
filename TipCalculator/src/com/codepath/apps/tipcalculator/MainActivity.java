package com.codepath.apps.tipcalculator;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    private TextView _vtTotalAmt;
    private EditText _etInputAmt;
    private Integer _selectedRate;
    private EditText _etCustomRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _vtTotalAmt = (TextView) findViewById(R.id.vtTotalAmt);
        _etInputAmt = (EditText) findViewById(R.id.etInputAmt);
        _etCustomRate = (EditText) findViewById(R.id.etCustomRate);
        _setupInputAmtChangedListener();
        _setupCustomRateChangedListener();
    }
    
    private void _setupInputAmtChangedListener() {
        _etInputAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                autoReCalcTip();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // do nothing
            }
         });
    }
    
    private void _setupCustomRateChangedListener() {
        _etCustomRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                autoReCalcTipWithCustomRateChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // do nothing
            }
         });
    }
    
    private static Integer _getRateFromButtonText(View v) {
        Button rateBtn = (Button) v;
        String rateBtnText = rateBtn.getText().toString();
        return Integer.valueOf(rateBtnText.replace("%", ""));        
    }
    
    private void _render(Double tip) {
        DecimalFormat formatter = new DecimalFormat("Tip is: $0.00");
        String tipString = formatter.format(tip);
        _vtTotalAmt.setText(tipString);
    }
    
    private void _calcTipWithAmountAndRate() {
        String inputAmtStr = _etInputAmt.getText().toString();
        Double inputAmt = 0.00; 
        if (!inputAmtStr.isEmpty()) {
            inputAmt = Double.valueOf(inputAmtStr);
        }
        Double tip = inputAmt * _selectedRate * 0.01;
        _render(tip);
    }
    
    public void calcTip(View v) {
        Integer rate = _getRateFromButtonText(v);
        _selectedRate = rate;
        _calcTipWithAmountAndRate();
    }
    
    public void autoReCalcTip() {
        if (_selectedRate == null) {
            return;
        }
        _calcTipWithAmountAndRate();
    }
    
    public void autoReCalcTipWithCustomRateChanged() {
        String customRateStr = _etCustomRate.getText().toString();
        Integer customRate = 0;
        if (!customRateStr.isEmpty()) {
            customRate = Integer.valueOf(customRateStr);
        }
        _selectedRate = customRate;
        autoReCalcTip();
    }
}
