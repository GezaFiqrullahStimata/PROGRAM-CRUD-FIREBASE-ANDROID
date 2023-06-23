package com.example.gezacrudfirebase

class data_mahasiswa {
    var nim: String? = null
    var nama: String? = null
    var jurusan: String? = null
    var key: String? = null
    constructor() {}
    constructor(nim: String?, nama: String?, jurusan: String?) {
        this.nim = nim
        this.nama = nama
        this.jurusan = jurusan
    }
}