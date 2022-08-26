package com.example.renmobil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {
    private lateinit var etEmpName: EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpSalary: EditText
    private lateinit var etEmpMobil: EditText
    private lateinit var etEmpJenis: EditText
    private lateinit var etEmpTglP: EditText
    private lateinit var etEmpTglK: EditText
    private lateinit var btnSaveData: Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpAge = findViewById(R.id.etEmpAge)
        etEmpSalary = findViewById(R.id.etEmpSalary)
        etEmpMobil = findViewById(R.id.etEmpMobil)
        etEmpJenis = findViewById(R.id.etEmpJenis)
        etEmpTglP = findViewById(R.id.etEmpTglP)
        etEmpTglK = findViewById(R.id.etEmpTglK)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {
        val empName = etEmpName.text.toString()
        val empAge = etEmpAge.text.toString()
        val empSalary = etEmpSalary.text.toString()
        val empMobil = etEmpMobil.text.toString()
        val empJenis = etEmpJenis.text.toString()
        val empTglP = etEmpTglP.text.toString()
        val empTglK = etEmpTglK.text.toString()

        if (empName.isEmpty()) {
            etEmpName.error = "Please enter name"
        }
        if (empAge.isEmpty()) {
            etEmpAge.error = "Please enter age"
        }
        if (empSalary.isEmpty()) {
            etEmpSalary.error = "Please enter salary"
        }
        if (empMobil.isEmpty()) {
            etEmpMobil.error = "Please enter Mobil"
        }
        if (empJenis.isEmpty()) {
            etEmpJenis.error = "Please enter Jenis"
        }
        if (empTglP.isEmpty()) {
            etEmpTglP.error = "Please enter Tanggal Pinjam"
        }
        if (empTglK.isEmpty()) {
            etEmpTglK.error = "Please enter Tanggal Kembali"
        }

        val empId = dbRef.push().key!!

        val employee = EmployeeModel(empId, empName, empAge, empSalary, empMobil, empJenis, empTglP, empTglK)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etEmpName.text.clear()
                etEmpAge.text.clear()
                etEmpSalary.text.clear()
                etEmpMobil.text.clear()
                etEmpJenis.text.clear()
                etEmpTglP.text.clear()
                etEmpTglK.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}