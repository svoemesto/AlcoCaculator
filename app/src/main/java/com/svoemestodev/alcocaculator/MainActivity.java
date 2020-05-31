package com.svoemestodev.alcocaculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tv_ma_receipt_name;
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

    public String receiptName;

    public List<Receipt> listReceipts;

    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        loadListReceipts();
        initializeSolutions("");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        ad_ma_banner.loadAd(adRequest);

    }

    private void loadListReceipts() {
        String pathToReceipts = Environment.getExternalStorageDirectory().getPath() + "/ALCOcalc/AlcoCalcReceipts";
        File alcoCalcReceipts = new File(pathToReceipts);
        try {
            FileInputStream fileInputStream = new FileInputStream(alcoCalcReceipts);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            listReceipts = (List<Receipt>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    }

    private void initializeSolutions(String receiptName) {

        if (listReceipts != null) {
            if (listReceipts.size() > 0) {
                for (int i = 0; i < listReceipts.size(); i++) {
                    if (receiptName.equals("") || receiptName.equals(listReceipts.get(i).getName())) {
                        this.receiptName = listReceipts.get(i).getName();

                        tv_ma_receipt_name.setText(this.receiptName);

                        if (listReceipts.get(i).getIngredients().size() > 0) {
                            solutionSol1 = new SolutionUI(1,
                                    listReceipts.get(i).getIngredients().get(0).getName(),
                                    listReceipts.get(i).getIngredients().get(0).isCalculatedConc(),
                                    listReceipts.get(i).getIngredients().get(0).getConc(),
                                    listReceipts.get(i).getIngredients().get(0).isCalculatedVol(),
                                    listReceipts.get(i).getIngredients().get(0).getVol());
                        } else {
                            solutionSol1 = new SolutionUI(1, null, false, null, false, null);
                        }

                        if (listReceipts.get(i).getIngredients().size() > 1) {
                            solutionSol2 = new SolutionUI(2,
                                    listReceipts.get(i).getIngredients().get(1).getName(),
                                    listReceipts.get(i).getIngredients().get(1).isCalculatedConc(),
                                    listReceipts.get(i).getIngredients().get(1).getConc(),
                                    listReceipts.get(i).getIngredients().get(1).isCalculatedVol(),
                                    listReceipts.get(i).getIngredients().get(1).getVol());
                        } else {
                            solutionSol2 = new SolutionUI(2, null, false, null, false, null);
                        }

                        if (listReceipts.get(i).getIngredients().size() > 2) {
                            solutionSol3 = new SolutionUI(3,
                                    listReceipts.get(i).getIngredients().get(2).getName(),
                                    listReceipts.get(i).getIngredients().get(2).isCalculatedConc(),
                                    listReceipts.get(i).getIngredients().get(2).getConc(),
                                    listReceipts.get(i).getIngredients().get(2).isCalculatedVol(),
                                    listReceipts.get(i).getIngredients().get(2).getVol());
                        } else {
                            solutionSol3 = new SolutionUI(3, null, false, null, false, null);
                        }

                        if (listReceipts.get(i).getIngredients().size() > 3) {
                            solutionSol4 = new SolutionUI(4,
                                    listReceipts.get(i).getIngredients().get(3).getName(),
                                    listReceipts.get(i).getIngredients().get(3).isCalculatedConc(),
                                    listReceipts.get(i).getIngredients().get(3).getConc(),
                                    listReceipts.get(i).getIngredients().get(3).isCalculatedVol(),
                                    listReceipts.get(i).getIngredients().get(3).getVol());
                        } else {
                            solutionSol4 = new SolutionUI(4, null, false, null, false, null);
                        }

                        if (listReceipts.get(i).getIngredients().size() > 4) {
                            solutionSol5 = new SolutionUI(5,
                                    listReceipts.get(i).getIngredients().get(4).getName(),
                                    listReceipts.get(i).getIngredients().get(4).isCalculatedConc(),
                                    listReceipts.get(i).getIngredients().get(4).getConc(),
                                    listReceipts.get(i).getIngredients().get(4).isCalculatedVol(),
                                    listReceipts.get(i).getIngredients().get(4).getVol());
                        } else {
                            solutionSol5 = new SolutionUI(5, null, false, null, false, null);
                        }


                        solutionResult = new SolutionUI(0,
                                listReceipts.get(i).getResult().getName(),
                                listReceipts.get(i).getResult().isCalculatedConc(),
                                listReceipts.get(i).getResult().getConc(),
                                listReceipts.get(i).getResult().isCalculatedVol(),
                                listReceipts.get(i).getResult().getVol());

                        break;
                    }
                }
            }
        }

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
            case -3:
                Toast.makeText(this, R.string.done, Toast.LENGTH_LONG).show();
                loadDataToViews();
                break;
            case -2:
                Toast.makeText(this, R.string.impossible_result, Toast.LENGTH_LONG).show();
                break;
            case -1:
                Toast.makeText(this, R.string.It_is_impossible_to_calculate_two_concentrations, Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, getString(R.string.unknown_variables_for_mixing_should_be_2) + " " + mixerResult + ". " + getString(R.string.mixer_cannot_calculate) + ".", Toast.LENGTH_LONG).show();
        }



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

        tv_ma_receipt_name = findViewById(R.id.tv_ma_receipt_name);
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

    public void receiptDelete(View view) {
        final String logMsgPref = "receiptDelete: ";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.delete_receipt);
        builder.setMessage(getString(R.string.confirm_del_receipt) + receiptName + "\"?");
        builder.setPositiveButton(R.string.yes_want_del,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listReceipts.size() == 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle(R.string.error);
                            builder.setMessage(R.string.unable_del_receipt);
                            builder.setPositiveButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                            AlertDialog dialogErrorDelete = builder.create();
                            dialogErrorDelete.show();
                        } else {

                            List<Receipt> newReceiptList = new ArrayList<>();
                            for (Receipt receipt: listReceipts) {
                                if (!receipt.getName().equals(receiptName)) {
                                    newReceiptList.add(receipt);
                                }
                            }

                            String pathToReceipts = Environment.getExternalStorageDirectory().getPath() + "/ALCOcalc/AlcoCalcReceipts";
                            File alcoCalcReceipts = new File(pathToReceipts);

                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(alcoCalcReceipts);
                                ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
                                oos.writeObject(newReceiptList);
                                oos.close();
                                listReceipts = newReceiptList;
                                initializeSolutions("");
                            } catch (IOException e) {
                                Log.e(TAG, logMsgPref + "Ошибка сериализации в файл " + alcoCalcReceipts.getAbsolutePath());
                            }


                        }
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void receiptSave(View view) {
        final String logMsgPref = "receiptSave: ";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.save_receipt);
        builder.setMessage(getString(R.string.confirm_save_receipt) + receiptName + "\"?");
        builder.setPositiveButton(R.string.yes_want_save,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        List<Receipt> newListReceipts = new ArrayList<>();

                        for (Receipt receipt : listReceipts) {
                            if (receipt.getName().equals(receiptName)) {

                                List<Solution> ingredients = new ArrayList<>();
                                ingredients.add(new Solution(solutionSol1.isCalculatedVol(), solutionSol1.isCalculatedConc(), solutionSol1.getName(),solutionSol1.getVol(),solutionSol1.getConc()));
                                ingredients.add(new Solution(solutionSol2.isCalculatedVol(), solutionSol2.isCalculatedConc(), solutionSol2.getName(),solutionSol2.getVol(),solutionSol2.getConc()));
                                if (solutionSol3.ch_ma_sol.isChecked()) ingredients.add(new Solution(solutionSol3.isCalculatedVol(), solutionSol3.isCalculatedConc(), solutionSol3.getName(),solutionSol3.getVol(),solutionSol3.getConc()));
                                if (solutionSol4.ch_ma_sol.isChecked()) ingredients.add(new Solution(solutionSol4.isCalculatedVol(), solutionSol4.isCalculatedConc(), solutionSol4.getName(),solutionSol4.getVol(),solutionSol4.getConc()));
                                if (solutionSol5.ch_ma_sol.isChecked()) ingredients.add(new Solution(solutionSol5.isCalculatedVol(), solutionSol5.isCalculatedConc(), solutionSol5.getName(),solutionSol5.getVol(),solutionSol5.getConc()));

                                Receipt newReceipt = new Receipt(receiptName,new Solution(solutionResult.isCalculatedVol(), solutionResult.isCalculatedConc(), solutionResult.getName(),solutionResult.getVol(),solutionResult.getConc()), ingredients);
                                newListReceipts.add(newReceipt);

                            } else {
                                newListReceipts.add(receipt);
                            }

                            String pathToReceipts = Environment.getExternalStorageDirectory().getPath() + "/ALCOcalc/AlcoCalcReceipts";
                            File alcoCalcReceipts = new File(pathToReceipts);

                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(alcoCalcReceipts);
                                ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
                                oos.writeObject(newListReceipts);
                                oos.close();

                            } catch (IOException e) {
                                Log.e(TAG, logMsgPref + "Ошибка сериализации в файл " + alcoCalcReceipts.getAbsolutePath());
                            }

                        }

                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void receiptLoad(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.select_receipt_load);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        for (Receipt receipt: listReceipts) {
            arrayAdapter.add(receipt.getName());
        }

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                initializeSolutions(strName);
            }
        });
        builder.show();

    }

    public void receiptNew(View view) {
        final String logMsgPref = "receiptNew: ";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.creating_new_receipt);
        builder.setMessage(R.string.want_create_new_receipt);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(R.string.new_receipt);
                        builder.setMessage(R.string.enter_receipt_name);

                        String newReceiptName = getString(R.string.receipt_num) + (listReceipts.size()+1);

                        final EditText input = new EditText(MainActivity.this);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        input.setText(newReceiptName);
                        builder.setView(input);

                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newReceiptName = input.getText().toString();
                                if (newReceiptName.equals("")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setTitle(R.string.error);
                                    builder.setMessage(R.string.enpty_name);
                                    builder.setPositiveButton(android.R.string.ok,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            });
                                    AlertDialog dialogErrorEmptyName = builder.create();
                                    dialogErrorEmptyName.show();
                                } else {
                                    boolean isFind = false;
                                    for (Receipt receipt: listReceipts) {
                                        if (newReceiptName.equals(receipt.getName())) {
                                            isFind = true;
                                            break;
                                        }
                                    }
                                    if (isFind) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setTitle(R.string.error);
                                        builder.setMessage(R.string.receipt_present);
                                        builder.setPositiveButton(android.R.string.ok,
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                });
                                        AlertDialog dialogErrorPresentName = builder.create();
                                        dialogErrorPresentName.show();

                                    } else {

                                        List<Solution> ingredients = new ArrayList<>();
                                        ingredients.add(new Solution(solutionSol1.isCalculatedVol(), solutionSol1.isCalculatedConc(), solutionSol1.getName(),solutionSol1.getVol(),solutionSol1.getConc()));
                                        ingredients.add(new Solution(solutionSol2.isCalculatedVol(), solutionSol2.isCalculatedConc(), solutionSol2.getName(),solutionSol2.getVol(),solutionSol2.getConc()));
                                        if (solutionSol3.ch_ma_sol.isChecked()) ingredients.add(new Solution(solutionSol3.isCalculatedVol(), solutionSol3.isCalculatedConc(), solutionSol3.getName(),solutionSol3.getVol(),solutionSol3.getConc()));
                                        if (solutionSol4.ch_ma_sol.isChecked()) ingredients.add(new Solution(solutionSol4.isCalculatedVol(), solutionSol4.isCalculatedConc(), solutionSol4.getName(),solutionSol4.getVol(),solutionSol4.getConc()));
                                        if (solutionSol5.ch_ma_sol.isChecked()) ingredients.add(new Solution(solutionSol5.isCalculatedVol(), solutionSol5.isCalculatedConc(), solutionSol5.getName(),solutionSol5.getVol(),solutionSol5.getConc()));

                                        Receipt newReceipt = new Receipt(newReceiptName,new Solution(solutionResult.isCalculatedVol(), solutionResult.isCalculatedConc(), solutionResult.getName(),solutionResult.getVol(),solutionResult.getConc()), ingredients);
                                        listReceipts.add(newReceipt);

                                        String pathToReceipts = Environment.getExternalStorageDirectory().getPath() + "/ALCOcalc/AlcoCalcReceipts";
                                        File alcoCalcReceipts = new File(pathToReceipts);

                                        try {
                                            FileOutputStream fileOutputStream = new FileOutputStream(alcoCalcReceipts);
                                            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
                                            oos.writeObject(listReceipts);
                                            oos.close();
                                            initializeSolutions(newReceiptName);

                                        } catch (IOException e) {
                                            Log.e(TAG, logMsgPref + "Ошибка сериализации в файл " + alcoCalcReceipts.getAbsolutePath());
                                        }

                                    }


                                }
                            }
                        });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.show();

                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class SolutionUI extends Solution {

        transient CheckBox ch_ma_sol;
        transient EditText et_ma_sol_name;
        transient Switch sw_ma_sol_conc;
        transient EditText et_ma_sol_conc;
        transient Switch sw_ma_sol_vol;
        transient EditText et_ma_sol_vol;

        SolutionUI(int solution, String solName, boolean isCalcConc, Double conc, boolean isCalcVol, Double vol) {
            this.setName(solName);
            this.setVol(vol);
            this.setConc(conc);
            this.setCalculatedVol(isCalcVol);
            this.setCalculatedConc(isCalcConc);
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

            ch_ma_sol.setChecked(solName != null);

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
                    setCalculatedConc(isChecked);
                    et_ma_sol_conc.setEnabled(isChecked);
                    setConc(isChecked ? 0.0 : null);
                    et_ma_sol_conc.setEnabled(isChecked);
                    et_ma_sol_conc.setText(isChecked ? getConc() == null ? "" : (String.format(Locale.getDefault(), "%01.02f", getConc()).replace(',','.')) : "");
                    et_ma_sol_conc.setTextColor(isChecked ? Color.BLACK : Color.RED);
                }
            });

            sw_ma_sol_vol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setCalculatedVol(isChecked);
                    et_ma_sol_vol.setEnabled(isChecked);
                    setVol(isChecked ? 0.0 : null);
                    et_ma_sol_vol.setEnabled(isChecked);
                    et_ma_sol_vol.setText(isChecked ? getVol() == null ? "" : (String.format(Locale.getDefault(), "%01.02f", getVol()).replace(',','.')) : "");
                    et_ma_sol_vol.setTextColor(isChecked ? Color.BLACK : Color.RED);
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
                et_ma_sol_name.setEnabled(true);

                sw_ma_sol_conc.setChecked(isCalculatedConc());
                sw_ma_sol_conc.setEnabled(true);

                sw_ma_sol_vol.setChecked(isCalculatedVol());
                sw_ma_sol_vol.setEnabled(true);

                et_ma_sol_conc.setText(getConc() == null ? "" : (String.format(Locale.getDefault(), "%01.02f", getConc()).replace(',','.')));
                et_ma_sol_conc.setEnabled(isCalculatedConc());

                et_ma_sol_vol.setText(getVol() == null ? "" : (String.format(Locale.getDefault(), "%01.02f", getVol()).replace(',','.')));
                et_ma_sol_vol.setEnabled(isCalculatedVol());

                et_ma_sol_conc.setTextColor(isCalculatedConc() ? Color.BLACK : Color.RED);
                et_ma_sol_vol.setTextColor(isCalculatedVol() ? Color.BLACK : Color.RED);

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
