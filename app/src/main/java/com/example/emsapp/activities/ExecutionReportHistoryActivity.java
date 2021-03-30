package com.example.emsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emsapp.adapters.ExecutionAdapter;
import com.example.emsapp.adapters.MonthAdapter;
import com.example.emsapp.models.Employee;
import com.example.emsapp.models.Execution;
import com.example.emsapp.models.Month;
import com.example.emsapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ExecutionReportHistoryActivity extends AppCompatActivity {
    private RecyclerView executionReportHistoryList;
    private Spinner monthSpinner;
    private ArrayList<Month> monthArrayList;
    private TextView nullMessage;
    private MonthAdapter monthAdapter;
    private String monthName, currentMonthName;
    private Employee employeeInfo;
    private DatabaseReference executionReportReference;
    private ArrayList<Execution> executionReportArrayList = new ArrayList<>();;
    private ExecutionAdapter executionAdapter;
    public static final String TAG = "history";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution_report_history);
        inItList();
        inItView();
        Intent intent1 = getIntent();
        employeeInfo = (Employee) intent1.getSerializableExtra("employeeInfo");
        executionReportHistoryList.setLayoutManager(new LinearLayoutManager(ExecutionReportHistoryActivity.this));
        executionAdapter = new ExecutionAdapter(ExecutionReportHistoryActivity.this, executionReportArrayList, employeeInfo);
        executionReportHistoryList.setAdapter(executionAdapter);

        Calendar calendar = Calendar.getInstance();
        currentMonthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        getCurrentMonthReport(currentMonthName);
    }

    private void getCurrentMonthReport(String currentMonthName) {

        executionReportReference = FirebaseDatabase.getInstance().getReference("Executive")
                .child(currentMonthName);

        executionReportReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                executionReportArrayList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Execution executionInfo = userSnapshot.getValue(Execution.class);
                        executionReportArrayList.add(executionInfo);

                    executionReportHistoryList.setLayoutManager(new LinearLayoutManager(ExecutionReportHistoryActivity.this));
                    executionAdapter = new ExecutionAdapter(ExecutionReportHistoryActivity.this, executionReportArrayList, employeeInfo);
                    executionReportHistoryList.setAdapter(executionAdapter);
                }
                Log.d(TAG, "onChildAdded: " + executionReportArrayList.size());
                executionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inItList() {
        monthArrayList = new ArrayList<>();
        monthArrayList.add(new Month("Current Month"));
        monthArrayList.add(new Month("January"));
        monthArrayList.add(new Month("February"));
        monthArrayList.add(new Month("March"));
        monthArrayList.add(new Month("April"));
        monthArrayList.add(new Month("May"));
        monthArrayList.add(new Month("June"));
        monthArrayList.add(new Month("July"));
        monthArrayList.add(new Month("August"));
        monthArrayList.add(new Month("September"));
        monthArrayList.add(new Month("October"));
        monthArrayList.add(new Month("November"));
        monthArrayList.add(new Month("December"));
    }

    private void inItView() {
        executionReportHistoryList = findViewById(R.id.recyclerViewForExecutionReportList);
        monthSpinner = findViewById(R.id.monthSpinner);

        monthAdapter = new MonthAdapter(ExecutionReportHistoryActivity.this, monthArrayList);
        monthSpinner.setAdapter(monthAdapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Month clickedMonth = (Month) parent.getItemAtPosition(position);

                monthName = clickedMonth.getMonthName();
                if(monthName.equals("Current Month")){
                    Toast.makeText(ExecutionReportHistoryActivity.this, "Please Select Your desire month name", Toast.LENGTH_SHORT).show();
                }else{

                    getDesireReportList(monthName);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getDesireReportList(String monthName) {
        executionReportReference = FirebaseDatabase.getInstance().getReference("Executive")
                .child(monthName);

        executionReportReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                executionReportArrayList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Execution executionInfo = userSnapshot.getValue(Execution.class);
                    executionReportArrayList.add(executionInfo);

                }
                Log.d(TAG, "onChildAdded: " + executionReportArrayList.size());
                executionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}