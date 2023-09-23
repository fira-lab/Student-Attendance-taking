package com.example.firaqr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button scanner;
    Button view;
    private Button mSaveBtn, buttonHistory1, buttonHistory, generateQR;
    private TextView textView;
    private ScanDatabase scanDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateQR = findViewById(R.id.gener);
        scanner = findViewById(R.id.scanner);
        textView = findViewById(R.id.Text);
        mSaveBtn = findViewById(R.id.scanner1);
        buttonHistory = findViewById(R.id.scanner2);
        buttonHistory1 = findViewById(R.id.scanner1);
        scanDatabase = new ScanDatabase(this);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = textView.getText().toString().trim();
                saveToDatabase(names);
                Toast.makeText(MainActivity.this, "QR code data saved successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DisplayAttendance.class);
                startActivity(intent);
            }
        });

        generateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GenerateAttendance.class);
                startActivity(intent);
            }
        });

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setPrompt("Scan a QR code");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            String contents = intentResult.getContents();
            if (contents != null) {
                // Check if the data exists in storage
                if (checkDataExists(contents)) {
                    // Retrieve the picture and display it
                    // Replace the following line with your logic to retrieve the picture
                    // and display it in your app
                    Toast.makeText(this, "Picture retrieved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Data not found in storage!", Toast.LENGTH_SHORT).show();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void saveToDatabase(String names) {
        ScanDatabase dbHelper = new ScanDatabase(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ScanDatabase.COLUMN_NAME, names);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        String dateTimeString = dateFormat.format(calendar.getTime());
        values.put(ScanDatabase.COLUMN_TIME, dateTimeString);
        if (!TextUtils.isEmpty(names)) {
            long newRowId = db.insert(ScanDatabase.TABLE_NAME, null, values);
            if (newRowId == -1) {
                Toast.makeText(this, "Failed to save!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkDataExists(String data) {
        // Replace this method with your logic to check if the data exists in storage
        return false;
    }
}