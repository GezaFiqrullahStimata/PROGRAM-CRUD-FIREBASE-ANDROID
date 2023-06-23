package com.example.gezacrudfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //Membuat Kode Permintaan
    private var auth : FirebaseAuth? = null
    lateinit var btnLogut : Button
    lateinit var btnSave : Button
    lateinit var btnShowData : Button

    lateinit var EdtNim : EditText
    lateinit var EdtNama : EditText
    lateinit var EdtJurusan : EditText
    private val RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogut = findViewById(R.id.logout)
        btnSave = findViewById(R.id.save)
        btnShowData = findViewById(R.id.showdata)
//Inisialisasi ID (Button)
        btnLogut.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        btnShowData.setOnClickListener(this)
//Mendapatkan Instance Firebase Autentifikasi
        auth = FirebaseAuth.getInstance()
    }
    // Mengecek apakah ada data kosong, digunakan pada Tutorial Selanjutnya
    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }
    override fun onClick(v: View) {
        EdtNim = findViewById(R.id.nim)
        EdtNama = findViewById(R.id.nama)
        EdtJurusan = findViewById(R.id.jurusan)
        when (v.getId()) {
            R.id.save -> {
// Statement program untuk simpan data
//Mendapatkan UserID dari pengguna yang Terautentikasi
                val getUserID = auth!!.currentUser!!.uid
//Mendapatkan Instance dari Database
                val database = FirebaseDatabase.getInstance()

//Menyimpan Data yang diinputkan User kedalam Variable
                val getNIM: String = EdtNim.text.toString()
                val getNama: String = EdtNama.text.toString()
                val getJurusan: String = EdtJurusan.text.toString()
// Mendapatkan Referensi dari Database
                val getReference: DatabaseReference
                getReference = database.reference
// Mengecek apakah ada data yang kosong
                if (isEmpty(getNIM) || isEmpty(getNama) || isEmpty(getJurusan)) {
//Jika Ada, maka akan menampilkan pesan singkan seperti berikut ini.
                    Toast.makeText(this@MainActivity, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show()
                } else {
/* Jika Tidak, maka data dapat diproses dan meyimpannya pada
Database Menyimpan data referensi pada Database berdasarkan User ID
dari masing-masing Akun
*/
                    getReference.child("Admin").child(getUserID).child("Mahasiswa").push()
                        .setValue(data_mahasiswa(getNIM, getNama, getJurusan))
                        .addOnCompleteListener(this) { //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database
                            EdtNim.setText("")
                            EdtNama.setText("")
                            EdtJurusan.setText("")

                            Toast.makeText(this@MainActivity, "Data Tersimpan",
                                Toast.LENGTH_SHORT).show()
                        }
                }
            }
            R.id.logout ->
// Statement program untuk logout/keluar
                AuthUI.getInstance()
                    .signOut(this)

                    .addOnCompleteListener(object : OnCompleteListener<Void> {

                        override fun onComplete(p0: Task<Void>) {
                            Toast.makeText(this@MainActivity, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                                    intent = Intent(applicationContext,
                                LoginActivity::class.java)
                            startActivity(intent)

                            finish()

                        }
                    })
            R.id.showdata -> {
                startActivity(Intent(this@MainActivity, MyListData::class.java))
            }
        }
    }
}