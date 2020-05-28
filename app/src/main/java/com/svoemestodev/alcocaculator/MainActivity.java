package com.svoemestodev.alcocaculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tv_ma_ingredients;
    TextView tv_ma_name;
    TextView tv_ma_conc;
    TextView tv_ma_vol;

    CheckBox ch_ma_sol1;
    EditText et_ma_sol1_name;
    Switch sw_ma_sol1_conc;
    EditText et_ma_sol1_conc;
    Switch sw_ma_sol1_vol;
    EditText et_ma_sol1_vol;

    CheckBox ch_ma_sol2;
    EditText et_ma_sol2_name;
    Switch sw_ma_sol2_conc;
    EditText et_ma_sol2_conc;
    Switch sw_ma_sol2_vol;
    EditText et_ma_sol2_vol;

    CheckBox ch_ma_sol3;
    EditText et_ma_sol3_name;
    Switch sw_ma_sol3_conc;
    EditText et_ma_sol3_conc;
    Switch sw_ma_sol3_vol;
    EditText et_ma_sol3_vol;

    CheckBox ch_ma_sol4;
    EditText et_ma_sol4_name;
    Switch sw_ma_sol4_conc;
    EditText et_ma_sol4_conc;
    Switch sw_ma_sol4_vol;
    EditText et_ma_sol4_vol;

    CheckBox ch_ma_sol5;
    EditText et_ma_sol5_name;
    Switch sw_ma_sol5_conc;
    EditText et_ma_sol5_conc;
    Switch sw_ma_sol5_vol;
    EditText et_ma_sol5_vol;

    CheckBox ch_ma_result;
    EditText et_ma_result_name;
    Switch sw_ma_result_conc;
    EditText et_ma_result_conc;
    Switch sw_ma_result_vol;
    EditText et_ma_result_vol;

    TextView tv_ma_result;
    FloatingActionButton fb_ma_calculate;

    AdView ad_ma_banner;

    public SolutionUI solutionSol1;
    public SolutionUI solutionSol2;
    public SolutionUI solutionSol3;
    public SolutionUI solutionSol4;
    public SolutionUI solutionSol5;
    public SolutionUI solutionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeSolutions();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        ad_ma_banner.loadAd(adRequest);

    }

    private void initializeSolutions() {
        solutionSol1 = new SolutionUI(1, "C₂H₅OH", true, 96.4, true, 2.0);
        solutionSol2 = new SolutionUI(2, "H₂O", true, 0.0, true, 3.0);
        solutionSol3 = new SolutionUI(3, null, false, null, false, null);
        solutionSol4 = new SolutionUI(4, null, false, null, false, null);
        solutionSol5 = new SolutionUI(5, null, false, null, false, null);
        solutionResult = new SolutionUI(0, "Vodka", false, null, false, null);
    }

    public void calculate(View view) {

        if (!solutionSol1.sw_ma_sol_conc.isChecked()) solutionSol1.setConc(null);
        if (!solutionSol1.sw_ma_sol_vol.isChecked()) solutionSol1.setVol(null);

        if (!solutionSol2.sw_ma_sol_conc.isChecked()) solutionSol2.setConc(null);
        if (!solutionSol2.sw_ma_sol_vol.isChecked()) solutionSol2.setVol(null);

        if (!solutionSol3.sw_ma_sol_conc.isChecked()) solutionSol3.setConc(null);
        if (!solutionSol3.sw_ma_sol_vol.isChecked()) solutionSol3.setVol(null);

        if (!solutionSol4.sw_ma_sol_conc.isChecked()) solutionSol4.setConc(null);
        if (!solutionSol4.sw_ma_sol_vol.isChecked()) solutionSol4.setVol(null);

        if (!solutionSol5.sw_ma_sol_conc.isChecked()) solutionSol5.setConc(null);
        if (!solutionSol5.sw_ma_sol_vol.isChecked()) solutionSol5.setVol(null);

        if (!solutionResult.sw_ma_sol_conc.isChecked()) solutionResult.setConc(null);
        if (!solutionResult.sw_ma_sol_vol.isChecked()) solutionResult.setVol(null);

        List<Solution> listSolutions = new ArrayList<>();
        if (solutionSol1.ch_ma_sol.isChecked()) listSolutions.add(solutionSol1);
        if (solutionSol2.ch_ma_sol.isChecked()) listSolutions.add(solutionSol2);
        if (solutionSol3.ch_ma_sol.isChecked()) listSolutions.add(solutionSol3);
        if (solutionSol4.ch_ma_sol.isChecked()) listSolutions.add(solutionSol4);
        if (solutionSol5.ch_ma_sol.isChecked()) listSolutions.add(solutionSol5);

        int mixerResult = Solution.Mixer(solutionResult, listSolutions);

        switch (mixerResult) {
            case -2:
                Toast.makeText(this, R.string.done, Toast.LENGTH_LONG).show();
                break;
            case -1:
                Toast.makeText(this, R.string.It_is_impossible_to_calculate_two_concentrations, Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, getString(R.string.unknown_variables_for_mixing_should_be_2) + " " + mixerResult + ". " + getString(R.string.mixer_cannot_calculate) + ".", Toast.LENGTH_LONG).show();
        }

        loadDataToViews();

    }

    private void loadDataToViews() {
        solutionSol1.loadDataToViews();
        solutionSol2.loadDataToViews();
        solutionSol3.loadDataToViews();
        solutionSol4.loadDataToViews();
        solutionSol5.loadDataToViews();
        solutionResult.loadDataToViews();
    }

    private void initializeViews() {

        tv_ma_ingredients = findViewById(R.id.tv_ma_ingredients);
        tv_ma_name = findViewById(R.id.tv_ma_name);
        tv_ma_conc = findViewById(R.id.tv_ma_conc);
        tv_ma_vol = findViewById(R.id.tv_ma_vol);

        ch_ma_sol1 = findViewById(R.id.ch_ma_sol1);
        et_ma_sol1_name = findViewById(R.id.et_ma_sol1_name);
        sw_ma_sol1_conc = findViewById(R.id.sw_ma_sol1_conc);
        et_ma_sol1_conc = findViewById(R.id.et_ma_sol1_conc);
        sw_ma_sol1_vol = findViewById(R.id.sw_ma_sol1_vol);
        et_ma_sol1_vol = findViewById(R.id.et_ma_sol1_vol);

        ch_ma_sol2 = findViewById(R.id.ch_ma_sol2);
        et_ma_sol2_name = findViewById(R.id.et_ma_sol2_name);
        sw_ma_sol2_conc = findViewById(R.id.sw_ma_sol2_conc);
        et_ma_sol2_conc = findViewById(R.id.et_ma_sol2_conc);
        sw_ma_sol2_vol = findViewById(R.id.sw_ma_sol2_vol);
        et_ma_sol2_vol = findViewById(R.id.et_ma_sol2_vol);

        ch_ma_sol3 = findViewById(R.id.ch_ma_sol3);
        et_ma_sol3_name = findViewById(R.id.et_ma_sol3_name);
        sw_ma_sol3_conc = findViewById(R.id.sw_ma_sol3_conc);
        et_ma_sol3_conc = findViewById(R.id.et_ma_sol3_conc);
        sw_ma_sol3_vol = findViewById(R.id.sw_ma_sol3_vol);
        et_ma_sol3_vol = findViewById(R.id.et_ma_sol3_vol);

        ch_ma_sol4 = findViewById(R.id.ch_ma_sol4);
        et_ma_sol4_name = findViewById(R.id.et_ma_sol4_name);
        sw_ma_sol4_conc = findViewById(R.id.sw_ma_sol4_conc);
        et_ma_sol4_conc = findViewById(R.id.et_ma_sol4_conc);
        sw_ma_sol4_vol = findViewById(R.id.sw_ma_sol4_vol);
        et_ma_sol4_vol = findViewById(R.id.et_ma_sol4_vol);

        ch_ma_sol5 = findViewById(R.id.ch_ma_sol5);
        et_ma_sol5_name = findViewById(R.id.et_ma_sol5_name);
        sw_ma_sol5_conc = findViewById(R.id.sw_ma_sol5_conc);
        et_ma_sol5_conc = findViewById(R.id.et_ma_sol5_conc);
        sw_ma_sol5_vol = findViewById(R.id.sw_ma_sol5_vol);
        et_ma_sol5_vol = findViewById(R.id.et_ma_sol5_vol);

        ch_ma_result = findViewById(R.id.ch_ma_result);
        et_ma_result_name = findViewById(R.id.et_ma_result_name);
        sw_ma_result_conc = findViewById(R.id.sw_ma_result_conc);
        et_ma_result_conc = findViewById(R.id.et_ma_result_conc);
        sw_ma_result_vol = findViewById(R.id.sw_ma_result_vol);
        et_ma_result_vol = findViewById(R.id.et_ma_result_vol);

        tv_ma_result = findViewById(R.id.tv_ma_result);
        fb_ma_calculate = findViewById(R.id.fb_ma_calculate);

        ad_ma_banner = findViewById(R.id.ad_ma_banner);

        
    }

    private class SolutionUI extends Solution {
        private boolean isCalculatedVol;
        private boolean isCalculatedConc;
        CheckBox ch_ma_sol;
        EditText et_ma_sol_name;
        Switch sw_ma_sol_conc;
        EditText et_ma_sol_conc;
        Switch sw_ma_sol_vol;
        EditText et_ma_sol_vol;

        SolutionUI(int solution, String solName, boolean isCalcConc, Double conc, boolean isCalcVol, Double vol) {
            this.setName(solName);
            this.setVol(vol);
            this.setConc(conc);
            this.isCalculatedVol = isCalcVol;
            this. isCalculatedConc = isCalcConc;
            switch (solution) {
                case 1:
                    ch_ma_sol = findViewById(R.id.ch_ma_sol1);
                    et_ma_sol_name = findViewById(R.id.et_ma_sol1_name);
                    sw_ma_sol_conc = findViewById(R.id.sw_ma_sol1_conc);
                    et_ma_sol_conc = findViewById(R.id.et_ma_sol1_conc);
                    sw_ma_sol_vol = findViewById(R.id.sw_ma_sol1_vol);
                    et_ma_sol_vol = findViewById(R.id.et_ma_sol1_vol);
                    break;
                case 2:
                    ch_ma_sol = findViewById(R.id.ch_ma_sol2);
                    et_ma_sol_name = findViewById(R.id.et_ma_sol2_name);
                    sw_ma_sol_conc = findViewById(R.id.sw_ma_sol2_conc);
                    et_ma_sol_conc = findViewById(R.id.et_ma_sol2_conc);
                    sw_ma_sol_vol = findViewById(R.id.sw_ma_sol2_vol);
                    et_ma_sol_vol = findViewById(R.id.et_ma_sol2_vol);
                    break;
                case 3:
                    ch_ma_sol = findViewById(R.id.ch_ma_sol3);
                    et_ma_sol_name = findViewById(R.id.et_ma_sol3_name);
                    sw_ma_sol_conc = findViewById(R.id.sw_ma_sol3_conc);
                    et_ma_sol_conc = findViewById(R.id.et_ma_sol3_conc);
                    sw_ma_sol_vol = findViewById(R.id.sw_ma_sol3_vol);
                    et_ma_sol_vol = findViewById(R.id.et_ma_sol3_vol);
                    break;
                case 4:
                    ch_ma_sol = findViewById(R.id.ch_ma_sol4);
                    et_ma_sol_name = findViewById(R.id.et_ma_sol4_name);
                    sw_ma_sol_conc = findViewById(R.id.sw_ma_sol4_conc);
                    et_ma_sol_conc = findViewById(R.id.et_ma_sol4_conc);
                    sw_ma_sol_vol = findViewById(R.id.sw_ma_sol4_vol);
                    et_ma_sol_vol = findViewById(R.id.et_ma_sol4_vol);
                    break;
                case 5:
                    ch_ma_sol = findViewById(R.id.ch_ma_sol5);
                    et_ma_sol_name = findViewById(R.id.et_ma_sol5_name);
                    sw_ma_sol_conc = findViewById(R.id.sw_ma_sol5_conc);
                    et_ma_sol_conc = findViewById(R.id.et_ma_sol5_conc);
                    sw_ma_sol_vol = findViewById(R.id.sw_ma_sol5_vol);
                    et_ma_sol_vol = findViewById(R.id.et_ma_sol5_vol);
                    break;
                default:
                    ch_ma_sol = findViewById(R.id.ch_ma_result);
                    et_ma_sol_name = findViewById(R.id.et_ma_result_name);
                    sw_ma_sol_conc = findViewById(R.id.sw_ma_result_conc);
                    et_ma_sol_conc = findViewById(R.id.et_ma_result_conc);
                    sw_ma_sol_vol = findViewById(R.id.sw_ma_result_vol);
                    et_ma_sol_vol = findViewById(R.id.et_ma_result_vol);
            }

//            et_ma_sol_name.setText(getName() == null ? "" : getName());
//            et_ma_sol_conc.setText(getConc() == null ? "" : (String.format(Locale.getDefault(), "%01.02f", getConc()).replace(',','.')));
//            et_ma_sol_vol.setText(getVol() == null ? "" : (String.format(Locale.getDefault(), "%01.02f", getVol()).replace(',','.')));

            loadDataToViews();

            ch_ma_sol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    loadDataToViews();
                }
            });

            sw_ma_sol_conc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    isCalculatedConc = isChecked;
                    et_ma_sol_conc.setEnabled(isChecked);
                    setConc(isChecked ? 0.0 : null);
                    et_ma_sol_conc.setText(isChecked ? getConc() == null ? "" : (String.format(Locale.getDefault(), "%01.02f", getConc()).replace(',','.')) : "");
                    et_ma_sol_conc.setTextColor(isCalculatedConc ? Color.BLACK : Color.RED);
                }
            });

            sw_ma_sol_vol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    isCalculatedVol = isChecked;
                    et_ma_sol_vol.setEnabled(isChecked);
                    setVol(isChecked ? 0.0 : null);
                    et_ma_sol_vol.setText(isChecked ? getVol() == null ? "" : (String.format(Locale.getDefault(), "%01.02f", getVol()).replace(',','.')) : "");
                    et_ma_sol_vol.setTextColor(isCalculatedVol ? Color.BLACK : Color.RED);
                }
            });

            et_ma_sol_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    setName(s.toString());
                }

            });

            et_ma_sol_vol.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (sw_ma_sol_vol.isChecked()) {
                        try {
                            setVol(Double.parseDouble(s.toString()));
                        } catch (NumberFormatException e) {
                            setVol(null);
                        }
                    }

                }
            });

            et_ma_sol_conc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (sw_ma_sol_conc.isChecked()) {
                        try {
                            setConc(Double.parseDouble(s.toString()));
                        } catch (NumberFormatException e) {
                            setConc(null);
                        }
                    }

                }
            });

        }

        private void loadDataToViews() {
            if (ch_ma_sol.isChecked()) {
                et_ma_sol_name.setText(this.getName());

                et_ma_sol_conc.setText(getConc() == null ? "" : (String.format(Locale.getDefault(), "%01.02f", getConc()).replace(',','.')));
                et_ma_sol_conc.setEnabled(true);

                et_ma_sol_vol.setText(getVol() == null ? "" : (String.format(Locale.getDefault(), "%01.02f", getVol()).replace(',','.')));
                et_ma_sol_vol.setEnabled(true);

                sw_ma_sol_conc.setChecked(isCalculatedConc);
                sw_ma_sol_conc.setEnabled(true);

                sw_ma_sol_vol.setChecked(isCalculatedVol);
                sw_ma_sol_vol.setEnabled(true);

                et_ma_sol_conc.setTextColor(isCalculatedConc ? Color.BLACK : Color.RED);
                et_ma_sol_vol.setTextColor(isCalculatedVol ? Color.BLACK : Color.RED);

            } else {
                et_ma_sol_name.setText("");
                et_ma_sol_name.setEnabled(false);

                sw_ma_sol_conc.setChecked(false);
                sw_ma_sol_conc.setEnabled(false);

                et_ma_sol_conc.setText("");
                et_ma_sol_conc.setEnabled(false);

                sw_ma_sol_vol.setChecked(false);
                sw_ma_sol_vol.setEnabled(false);

                et_ma_sol_vol.setText("");
                et_ma_sol_vol.setEnabled(false);

                setVol(null);
                setConc(null);

            }
        }

    }
    

}
