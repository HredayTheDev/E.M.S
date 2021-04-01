package com.example.emsapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.emsapp.R
import com.example.emsapp.models.Employee
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_user_sign_in.*
import kotlinx.android.synthetic.main.activity_user_sign_in.emailET
import kotlinx.android.synthetic.main.activity_user_sign_in.passwordET


class UserSignInActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var employee: Employee
    private lateinit var firebaseUser: FirebaseUser;
    private lateinit var userToken: String
    private lateinit var pgId: String
    private lateinit var userRole: String
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_sign_in)

        mAuth = FirebaseAuth.getInstance()

        signInBtn.setOnClickListener {

            progressbarId.visibility = View.VISIBLE

            if (!validateEmail() or !validatePassword()) {
                return@setOnClickListener
            }

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (email == "admin@gmail.com") {
                        progressbarId.visibility = View.GONE
                        val intent = Intent(this@UserSignInActivity, AdminControllerActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish()

                    } else {
                        progressbarId.visibility = View.GONE
                        val intent = Intent(this@UserSignInActivity, UserHomePageActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish()

                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    progressbarId.visibility = View.GONE

                }
            }

        }
    }

    /*
    private void clickEvents() {

        signInBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final String currentUserRole = userRole;
                uName = userNameEt.getText().toString().trim();
                uPassword = userPasswordEt.getText().toString().trim();

                    if (uName.equals("Admin") && uPassword.equals("123321")) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(UserSignInActivity.this, AdminControllerActivity.class);
                        startActivity(intent);
                        finish();
                        SharedPreferences preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                        preferences.edit().putString("loginState",uName).apply();
                        Log.d(TAG, "onChildAdded: "+ uName);
                    } else {

                        employeeReference = FirebaseDatabase.getInstance().getReference("Employee");

                        employeeReference.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                    Employee employeeInfo = userSnapshot.getValue(Employee.class);
                                    if (employeeInfo.getUserPgId().equals(uName) &&
                                            employeeInfo.getUserRole().equals(currentUserRole) &&
                                            employeeInfo.getUserPassword().equals(uPassword)) {
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(UserSignInActivity.this, UserHomePageActivity.class);
                                        intent.putExtra("pgId", uName);
                                        startActivity(intent);
                                        finish();
                                        SharedPreferences preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                                        preferences.edit().putString("loginState",uName).apply();
                                        Log.d(TAG, "onChildAdded: "+ uName);
                                    }

                                }

                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
            }
        });

    }///clickEvents


    private void inItView() {
        userNameEt = findViewById(R.id.etUserName);
        userPasswordEt = findViewById(R.id.etUserPassword);
        signInBt = findViewById(R.id.btnSignIn);
        progressBar = findViewById(R.id.progressBar);

        /////////User Role View Adapter work/////////
        userRoleSpinner = findViewById(R.id.userRoleSpinner);
        mUserRoleAdapter = new UserRoleAdapter(UserSignInActivity.this, mUserRoleList);
        userRoleSpinner.setAdapter(mUserRoleAdapter);
        userRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserRole clickedUserRole = (UserRole) parent.getItemAtPosition(position);

                userRole = clickedUserRole.getUserRole();

                Toast.makeText(UserSignInActivity.this, userRole +" is selected !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void goToSignUpActivity(View view) {
        startActivity(new Intent(this,SignUpActivity.class));
    }*/

    companion object {
        const val TAG = "SignIn"
    }



/*
    private fun validateUserRole(): Boolean {
        userRole = userRoleDropdownTV.text.toString().trim()
        return if (userRole.isEmpty()) {
            userRoleSpinnerId.error = "Field can't be empty!"
            false
        } else {
            userRoleSpinnerId.error = null
            true
        }
    }*/


    private fun validateEmail(): Boolean {
        email = emailET.editText?.text.toString().trim()
        return if (email.isEmpty()) {
            emailET.error = "Field can't be empty!"
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.error = "Please enter a valid email address!"
            false
        } else {
            emailET.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        password = passwordET.editText?.text.toString().trim()
        return if (password.isEmpty()) {
            passwordET.error = "Field can't be empty!"
            false
        } else {
            passwordET.error = null
            true
        }
    }

    fun goToSignUpActivity(view: View) {
        val intent = Intent(this@UserSignInActivity, SignUpActivity::class.java)
        startActivity(intent)
    }

}