package com.example.gezacrudfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateData : AppCompatActivity() {
    //Deklarasi Variable
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNIM: String? = null
    private var cekNama: String? = null
    private var cekJurusan: String? = null

    lateinit var btnUpdate: Button
    lateinit var EdtNewNim: EditText
    lateinit var EdtNewNama: EditText
    lateinit var EdtNewJurusan: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)


        btnUpdate = findViewById(R.id.update)
        EdtNewNim = findViewById(R.id.new_nim)
        EdtNewNama = findViewById(R.id.new_nama)
        EdtNewJurusan= findViewById(R.id.new_jurusan)

        supportActionBar!!.title = "Update Data"
//Mendapatkan Instance autentikasi dan Referensi dari Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data //memanggil method "data"
        btnUpdate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
//Mendapatkan Data Mahasiswa yang akan dicek
                cekNIM = EdtNewNim.text.toString()
                cekNama = EdtNewNama.text.toString()
                cekJurusan = EdtNewJurusan.text.toString()

//Mengecek agar tidak ada data yang kosong, saat proses update
                if (isEmpty(cekNIM!!) || isEmpty(cekNama!!) ||
                    isEmpty(cekJurusan!!)) {
                    Toast.makeText(
                        this@UpdateData,

                        "Data tidak boleh ada yang kosong",
                        Toast.LENGTH_SHORT

                    ).show()
                } else {
/*Menjalankan proses update data.
Method Setter digunakan untuk mendapakan data baru yang diinputkan User.*/
                    EdtNewNim = findViewById(R.id.new_nim)
                    EdtNewNama = findViewById(R.id.new_nama)
                    EdtNewJurusan= findViewById(R.id.new_jurusan)

                    val setMahasiswa = data_mahasiswa()
                    setMahasiswa.nim = EdtNewNim.text.toString()
                    setMahasiswa.nama = EdtNewNama.text.toString()
                    setMahasiswa.jurusan = EdtNewJurusan.text.toString()
                    updateMahasiswa(setMahasiswa)
                }
            }
        })
    }
    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }
    //Menampilkan data yang akan di update
    private val data: Unit
        private get() {
//Menampilkan data dari item yang dipilih sebelumnya
            val getNIM = intent.extras!!.getString("dataNIM")
            val getNama = intent.extras!!.getString("dataNama")
            val getJurusan = intent.extras!!.getString("dataJurusan")

            EdtNewNim = findViewById(R.id.new_nim)
            EdtNewNama = findViewById(R.id.new_nama)
            EdtNewJurusan= findViewById(R.id.new_jurusan)

            EdtNewNim!!.setText(getNIM)
            EdtNewNama!!.setText(getNama)
            EdtNewJurusan!!.setText(getJurusan)
        }
    //Proses Update data yang sudah ditentukan
    private fun updateMahasiswa(mahasiswa: data_mahasiswa) {
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")

        EdtNewNim = findViewById(R.id.new_nim)
        EdtNewNama = findViewById(R.id.new_nama)
        EdtNewJurusan= findViewById(R.id.new_jurusan)

        database!!.child("Admin")
            .child(userID!!)
            .child("Mahasiswa")
            .child(getKey!!)
            .setValue(mahasiswa)
            .addOnSuccessListener {
                EdtNewNim!!.setText("")
                EdtNewNama!!.setText("")
                EdtNewJurusan!!.setText("")
                Toast.makeText(this@UpdateData, "Data Berhasil diubah", Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}