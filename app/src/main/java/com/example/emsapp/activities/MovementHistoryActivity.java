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

import com.example.emsapp.adapters.MonthAdapter;
import com.example.emsapp.adapters.MovableAdapter;
import com.example.emsapp.models.Attendance;
import com.example.emsapp.models.Employee;
import com.example.emsapp.models.Month;
import com.example.emsapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MovementHistoryActivity extends AppCompatActivity {

    private RecyclerView movementHistoryList;
    private TextView nullMessage;
    private Spinner monthSpinner;
    private ArrayList<Month> monthArrayList;
    private MonthAdapter monthAdapter;
    private String monthName;
    private Employee employeeInfo;
    private ArrayList<Attendance> mMovementArrayList = new ArrayList<>();
    private ArrayList<Attendance> mSelfMovementList = new ArrayList<>();
    private MovableAdapter movementAdapter;
    private DatabaseReference movementReference;
    public static final String TAG = "history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_history);
        inItList();
        inItView();
        Intent intent = getIntent();
        employeeInfo = (Employee) intent.getSerializableExtra("userInfo");

        movementHistoryList.setLayoutManager(new LinearLayoutManager(MovementHistoryActivity.this));
        movementAdapter = new MovableAdapter(MovementHistoryActivity.this, mMovementArrayList);
        movementHistoryList.setAdapter(movementAdapter);
    }

    private void inItList() {
        monthArrayList = new ArrayList<>();
        monthArrayList.add(new Month("Select Month.."));
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
        movementHistoryList = findViewById(R.id.recyclerViewForMovementHistoryList);
        nullMessage = findViewById(R.id.nullDataTv);
        monthSpinner = findViewById(R.id.monthSpinner);
        monthAdapter = new MonthAdapter(MovementHistoryActivity.this, monthArrayList);
        monthSpinner.setAdapter(monthAdapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Month clickedMonth = (Month) parent.getItemAtPosition(position);

                monthName = clickedMonth.getMonthName();
                if(monthName.equals("Select Month..")){
                    Toast.makeText(MovementHistoryActivity.this, "Please Select Your desire month first", Toast.LENGTH_SHORT).show();
                }else{

                    getDesireAttendanceList(monthName);
                }


                //Toast.makeText(AttendanceHistoryActivity.this, monthName +" is selected !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDesireAttendanceList(String monthName) {
        movementReference = FirebaseDatabase.
                getInstance().
                getReference("MovableReport")
                .child(monthName);
        movementReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMovementArrayList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Attendance attendance = userSnapshot.getValue(Attendance.class);
                    if (employeeInfo.getUserPgId().equals(attendance.getPgId())) {
                        mMovementArrayList.add(attendance);
                    }
                }
                Log.d(TAG, "onChildAdded: " + mMovementArrayList.size());
                if(mMovementArrayList.size() == 0){
                    nullMessage.setVisibility(View.VISIBLE);
                }else{
                    nullMessage.setVisibility(View.GONE);
                    movementHistoryList.setVisibility(View.VISIBLE);
                }
                movementAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}