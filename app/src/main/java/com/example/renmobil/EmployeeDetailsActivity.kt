package com.example.renmobil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpName: TextView
    private lateinit var tvEmpAge: TextView
    private lateinit var tvEmpSalary: TextView
    private lateinit var tvEmpMobil: TextView
    private lateinit var tvEmpJenis: TextView
    private lateinit var tvEmpTglP: TextView
    private lateinit var tvEmpTglK: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }

    }

    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpName = findViewById(R.id.tvEmpName)
        tvEmpAge = findViewById(R.id.tvEmpAge)
        tvEmpSalary = findViewById(R.id.tvEmpSalary)
        tvEmpMobil = findViewById(R.id.tvEmpMobil)
        tvEmpJenis = findViewById(R.id.tvEmpJenis)
        tvEmpTglP = findViewById(R.id.tvEmpTglP)
        tvEmpTglK = findViewById(R.id.tvEmpTglK)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpName.text = intent.getStringExtra("empName")
        tvEmpAge.text = intent.getStringExtra("empAge")
        tvEmpSalary.text = intent.getStringExtra("empSalary")
        tvEmpMobil.text = intent.getStringExtra("empMobil")
        tvEmpJenis.text = intent.getStringExtra("empJenis")
        tvEmpTglP.text = intent.getStringExtra("empTglP")
        tvEmpTglK.text = intent.getStringExtra("empTglK")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Employee data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        empId: String,
        empName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etEmpName = mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etEmpAge = mDialogView.findViewById<EditText>(R.id.etEmpAge)
        val etEmpSalary = mDialogView.findViewById<EditText>(R.id.etEmpSalary)
        val etEmpMobil = mDialogView.findViewById<EditText>(R.id.etEmpMobil)
        val etEmpType = mDialogView.findViewById<EditText>(R.id.etEmpType)
        val etEmpTglP = mDialogView.findViewById<EditText>(R.id.etEmpTglP)
        val etEmpTglK = mDialogView.findViewById<EditText>(R.id.etEmpTglK)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etEmpName.setText(intent.getStringExtra("empName").toString())
        etEmpAge.setText(intent.getStringExtra("empAge").toString())
        etEmpSalary.setText(intent.getStringExtra("empSalary").toString())
        etEmpMobil.setText(intent.getStringExtra("empMobil")).toString()
        etEmpType.setText(intent.getStringExtra("empJenis")).toString()
        etEmpTglP.setText(intent.getStringExtra("empTglP")).toString()
        etEmpTglK.setText(intent.getStringExtra("empTglK")).toString()

        mDialog.setTitle("Updating $empName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etEmpName.text.toString(),
                etEmpAge.text.toString(),
                etEmpSalary.text.toString(),
                etEmpMobil.text.toString(),
                etEmpType.text.toString(),
                etEmpTglP.text.toString(),
                etEmpTglK.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvEmpName.text = etEmpName.text.toString()
            tvEmpAge.text = etEmpAge.text.toString()
            tvEmpSalary.text = etEmpSalary.text.toString()
            tvEmpMobil.text = etEmpMobil.text.toString()
            tvEmpJenis.text = etEmpType.text.toString()
            tvEmpTglP.text = etEmpTglP.text.toString()
            tvEmpTglK.text = etEmpTglK.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        name: String,
        age: String,
        salary: String,
        mobil: String,
        jenis: String,
        tglP: String,
        tglK: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empInfo = EmployeeModel(id, name, age, salary, mobil, jenis, tglP, tglK)
        dbRef.setValue(empInfo)
    }

}