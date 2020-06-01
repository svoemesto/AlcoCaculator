package com.svoemestodev.alcocaculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    private static final int PERMISSION_ALL = 0;
    private Handler h;
    private Runnable r;
    private static final String TAG = "Splash";

    SharedPreferences mPrefs;
    final String settingScreenShownPref = "settingScreenShown";
    final String versionCheckedPref = "versionChecked";

    TextView tv_sp_title;
    ImageView iv_sp_logo;
    TextView tv_sp_version;
    TextView tv_sp_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv_sp_title = findViewById(R.id.tv_sp_title);
        iv_sp_logo = findViewById(R.id.iv_sp_logo);
        tv_sp_version = findViewById(R.id.tv_sp_version);
        tv_sp_status = findViewById(R.id.tv_sp_status);

        String versionText = getString(R.string.version) + ": " + BuildConfig.VERSION_NAME;
        tv_sp_version.setText(versionText);
        tv_sp_status.setText(R.string.loading);

        h = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(Splash.this, "Runnable started", Toast.LENGTH_SHORT).show();

//            // (OPTIONAL) these lines to check if the `First run` ativity is required
                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;

                mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = mPrefs.edit();

                Boolean settingScreenShown = mPrefs.getBoolean(settingScreenShownPref, false);
                int savedVersionCode = mPrefs.getInt(versionCheckedPref, 1);

                if (!settingScreenShown || savedVersionCode != versionCode) {

                    editor.putBoolean(settingScreenShownPref, true);
                    editor.putInt(versionCheckedPref, versionCode);
                    editor.commit();

                }

                createProgramDir();

                startActivity(new Intent(SplashActivity.this, MainActivity.class));

                finish();
            }
        };

        String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        if(!UtilPermissions.hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        else
            h.postDelayed(r, 1500);
    }

    // Put the below OnRequestPermissionsResult code here

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        int index = 0;
        Map<String, Integer> PermissionsMap = new HashMap<String, Integer>();
        for (String permission : permissions){
            PermissionsMap.put(permission, grantResults[index]);
            index++;
        }

        if((PermissionsMap.get(Manifest.permission.READ_EXTERNAL_STORAGE) != 0)
                || PermissionsMap.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) != 0){
            Toast.makeText(this, "Read and write storage permissions are a must", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            h.postDelayed(r, 1500);
        }
    }

    public void createProgramDir() {

        String logMsgPref = "createProgramDir: ";
        Log.i(TAG, logMsgPref + "start");

        // путь к папке программы в корне файловой системы. Если такой папки нет - создаем её
        String pathToALCOcalcFolder = Environment.getExternalStorageDirectory().getPath() + "/" + getString(R.string.program_folder);
        Log.i(TAG, logMsgPref + "pathToALCOcalcFolder = " + pathToALCOcalcFolder);

        File alcoCalcDir = new File(pathToALCOcalcFolder);
        if (!alcoCalcDir.exists()) {
            Log.i(TAG, logMsgPref + "папки " + pathToALCOcalcFolder + " не существует, создаем папку");
            boolean isDirCreated = alcoCalcDir.mkdirs();
            if (isDirCreated) {
                Log.i(TAG, logMsgPref + "Создана папка " + pathToALCOcalcFolder);
            } else {
                Log.e(TAG, logMsgPref + "Не удалось создать папку " + pathToALCOcalcFolder);
            }

        }

        if (alcoCalcDir.exists()) {
            File alcoCalcReceipts = new File(pathToALCOcalcFolder, getString(R.string.file_list_receipts));
            if (!alcoCalcReceipts.exists()) {

                Receipt.saveList(Receipt.getDefaultList(), pathToALCOcalcFolder + "/" + getString(R.string.file_list_receipts));

            }
        }

    }

}
