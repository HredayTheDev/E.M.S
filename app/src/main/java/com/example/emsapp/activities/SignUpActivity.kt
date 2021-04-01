package com.example.emsapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.emsapp.MainActivity
import com.example.emsapp.R
import com.example.emsapp.models.Employee
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var employee: Employee
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var userToken: String
    private lateinit var pgId: String
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private val TAG: String = "SignUpActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()


        val employeeRef = firebaseDatabase.getReference("user")


        employee = Employee()


        signUpBtn.setOnClickListener {

            if (!validatePgId() or !validateName() or !validateEmail() or !validatePassword()) {
                return@setOnClickListener
            }

            progressbar.visibility = View.VISIBLE

            //validatePgId()

            val pgIdRef = firebaseDatabase.getReference("pgNumber")
            val pgIdQuery = pgIdRef.orderByChild("pgId").equalTo(pgId)

            pgIdQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val count = snapshot.childrenCount.toInt()
                        Log.d(TAG, "count: $count")

                        when (count) {
                            1 -> {
                                startSignUp()
                            }
                            else -> {
                                Toast.makeText(this@SignUpActivity, "Please contact to admin", Toast.LENGTH_LONG).show()
                                progressbar.visibility = View.GONE
                            }
                        }
                    } else {
                        Toast.makeText(this@SignUpActivity, "You are not authorised, Please contact to admin", Toast.LENGTH_LONG).show()
                        progressbar.visibility = View.GONE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: ${error.message}")
                    progressbar.visibility = View.GONE
                }
            })


        }


    }

    private fun startSignUp() {

        val notificationRef = firebaseDatabase.getReference("notification")
        val employeeRef = firebaseDatabase.getReference("employees")

        Firebase.messaging.subscribeToTopic(TOPIC).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Topic Created")
            } else {
                Log.d(TAG, "Topic Not Created")
            }
        }

        Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->

            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            userToken = task.result.toString()
            Log.d(TAG, "Token: $userToken")

        })

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "startSignUp")

                val currentUser = mAuth.currentUser
                val employee = Employee()
                employee.userPgId = pgId
                employee.userEmail = email
                employee.userName = name
                employee.userId = currentUser.uid
                employee.notificationToken = userToken

                notificationRef.child(employee.userId).setValue(employee).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "DB: started")
                        employeeRef.child(employee.userId).setValue(employee).addOnCompleteListener{ task ->
                            if (task.isSuccessful){
                                Toast.makeText(this@SignUpActivity, "Successful", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this@SignUpActivity, UserHomePageActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                            }
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@SignUpActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        progressbar.visibility = View.GONE

                    }
                }

            } else {
                Toast.makeText(this@SignUpActivity, task.exception?.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                progressbar.visibility = View.GONE
            }

        }

    }


    private fun validateName(): Boolean {
        name = nameET.editText?.text.toString().trim()
        return if (name.isEmpty()) {
            nameET.error = "Field can't be empty!"
            false
        } else {
            nameET.error = null
            true
        }
    }


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

    private fun validatePgId(): Boolean {
        pgId = pgIdET.editText?.text.toString().trim()
        return if (pgId.isEmpty()) {
            pgIdET.error = "Field can'nt be empty"
            false
        } else {
            pgIdET.error = null
            true
        }
    }
}