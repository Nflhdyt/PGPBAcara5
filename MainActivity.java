package com.example.presensi;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private Spinner spinnerKeterangan;
    private EditText keteranganEditText;
    private Button btnSubmit;

    private int selectedYear, selectedMonth, selectedDay;
    private int selectedHour, selectedMinute;
    private String selectedKeterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        spinnerKeterangan = findViewById(R.id.spinnerKeterangan);
        keteranganEditText = findViewById(R.id.keteranganEditText);
        btnSubmit = findViewById(R.id.btn_submit);

        // Configure TimePicker
        timePicker.setIs24HourView(true);

        // Set up Spinner with values from strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.keterangan_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKeterangan.setAdapter(adapter);

        spinnerKeterangan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedKeterangan = parentView.getItemAtPosition(position).toString();

                // Show or hide the EditText based on the selected item
                if (selectedKeterangan.equals("Sakit") || selectedKeterangan.equals("Terlambat") || selectedKeterangan.equals("Izin")) {
                    keteranganEditText.setVisibility(View.VISIBLE);  // Show EditText
                } else {
                    keteranganEditText.setVisibility(View.GONE);     // Hide EditText
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedKeterangan = "Hadir tepat waktu"; // Default value if none selected
            }
        });

        // Set up Submit Button logic
        btnSubmit.setOnClickListener(view -> {
            // Retrieve the selected date from DatePicker
            selectedYear = datePicker.getYear();
            selectedMonth = datePicker.getMonth() + 1; // Month is 0-based in Android DatePicker
            selectedDay = datePicker.getDayOfMonth();

            // Retrieve the selected time from TimePicker
            selectedHour = timePicker.getCurrentHour();
            selectedMinute = timePicker.getCurrentMinute();

            // Check if additional keterangan is needed
            String additionalKeterangan = "";
            if (keteranganEditText.getVisibility() == View.VISIBLE) {
                additionalKeterangan = keteranganEditText.getText().toString();
                if (additionalKeterangan.isEmpty()) {
                    additionalKeterangan = "Tidak ada keterangan tambahan.";
                }
            }

            // Create a toast message combining Date, Time, Spinner Selection, and additional keterangan
            String toastMessage = "Keterangan: " + selectedKeterangan +
                    "\nTanggal: " + selectedDay + "/" + selectedMonth + "/" + selectedYear +
                    "\nJam: " + String.format("%02d:%02d", selectedHour, selectedMinute) +
                    "\nKeterangan Tambahan: " + additionalKeterangan;

            // Display the Toast
            Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_LONG).show();
        });
    }
}

