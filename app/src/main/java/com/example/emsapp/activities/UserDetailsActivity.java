package com.example.emsapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emsapp.R;
import com.example.emsapp.adapters.EmployeeAdapter;
import com.example.emsapp.models.Employee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserDetailsActivity extends AppCompatActivity {
    private TextView eName, eEmail, ePhone, eNidNo, eCurrentCity,
            eCurrentLocation, eVillage, eUpazilla, eZilla,
            eDivision, ePgId, eDesignation, eJoiningDate, eDepartment, eConcern;
    private FirebaseUser user;
    private ArrayList<Employee> employeeInfoList = new ArrayList<>();
    private EmployeeAdapter mEmployeeAdapter;
    private DatabaseReference employeeReference;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private Employee employee;
    private static final String TAG = "UserDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        inItView();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String userId = currentUser.getUid();
       employeeReference = FirebaseDatabase.getInstance().getReference("employees").child(userId);

       employeeReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               Employee employee = snapshot.getValue(Employee.class);
               Log.d(TAG, "Employee: "+employee.toString());
               eName.setText("Name: " + employee.getUserName());
               eEmail.setText("Email: " + employee.getUserEmail());
               ePhone.setText("Contact: " + employee.getUserPhone());
               eNidNo.setText("Nid: " + employee.getUserNid());
               eCurrentCity.setText("City: " + employee.getUserCurrentCity());
               eCurrentLocation.setText("Location: " + employee.getUserCurrentLocation());
               eVillage.setText("Village: " + employee.getUserVillage());
               eUpazilla.setText("Upazilla: " + employee.getUserUpazilla());
               eZilla.setText("Zilla: " + employee.getUserZilla());
               eDivision.setText("Division: " + employee.getUserDivision());
               ePgId.setText("PG ID: " + employee.getUserPgId());
               eDesignation.setText("Designation: " + employee.getUserDesignation());
               eJoiningDate.setText("Joining Date: " + employee.getUserJoiningDate());
               eDepartment.setText("Department: " + employee.getUserDepartment());
               eConcern.setText("Concern: " + employee.getUserConcern());
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });


       /* eName.setText("Name: " + employee.getUserName());
        eEmail.setText("Email: " + employee.getUserEmail());
        ePhone.setText("Contact: " + employee.getUserPhone());
        eNidNo.setText("Nid: " + employee.getUserNid());
        eCurrentCity.setText("City: " + employee.getUserCurrentCity());
        eCurrentLocation.setText("Location: " + employee.getUserCurrentLocation());
        eVillage.setText("Village: " + employee.getUserVillage());
        eUpazilla.setText("Upazilla: " + employee.getUserUpazilla());
        eZilla.setText("Zilla: " + employee.getUserZilla());
        eDivision.setText("Division: " + employee.getUserDivision());
        ePgId.setText("PG ID: " + employee.getUserPgId());
        eDesignation.setText("Designation: " + employee.getUserDesignation());
        eJoiningDate.setText("Joining Date: " + employee.getUserJoiningDate());
        eDepartment.setText("Department: " + employee.getUserDepartment());
        eConcern.setText("Concern: " + employee.getUserConcern());
*/
    }

    private void inItView() {
        firebaseAuth = FirebaseAuth.getInstance();
        eName = findViewById(R.id.employeeNameEt);
        eEmail = findViewById(R.id.employeeEmailEt);
        ePhone = findViewById(R.id.employeePhoneEt);
        eNidNo = findViewById(R.id.employeeNidNoEt);
        eCurrentCity = findViewById(R.id.employeeCurrentCityEt);
        eCurrentLocation = findViewById(R.id.employeeLocationEt);
        eVillage = findViewById(R.id.employeeVillageEt);
        eUpazilla = findViewById(R.id.employeeUpazillaEt);
        eZilla = findViewById(R.id.employeeZillaEt);
        eDivision = findViewById(R.id.employeeDivisionEt);
        ePgId = findViewById(R.id.employeePgIdEt);
        eDesignation = findViewById(R.id.employeeDesignationEt);
        eConcern = findViewById(R.id.employeeConcernEt);
        eJoiningDate = findViewById(R.id.startDateET);
        //progressBar = findViewById(R.id.progressBar);
        eDepartment = findViewById(R.id.department);

    }
}