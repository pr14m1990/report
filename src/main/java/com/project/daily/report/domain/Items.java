package com.project.daily.report.domain;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String namabarang;
    public String jumlahbarang;
    public String hargabarang;
    public String storeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public String getJumlahbarang() {
        return jumlahbarang;
    }

    public void setJumlahbarang(String jumlahbarang) {
        this.jumlahbarang = jumlahbarang;
    }

    public String getHargabarang() {
        return hargabarang;
    }

    public void setHargabarang(String hargabarang) {
        this.hargabarang = hargabarang;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", namabarang='" + namabarang + '\'' +
                ", jumlahbarang='" + jumlahbarang + '\'' +
                ", hargabarang='" + hargabarang + '\'' +
                ", storeid='" + storeid + '\'' +
                '}';
    }
}
