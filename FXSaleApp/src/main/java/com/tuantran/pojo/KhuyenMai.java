/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.pojo;

import java.sql.Date;

/**
 *
 * @author TuanTran
 */
public class KhuyenMai {
    private int idKhuyenMai;
    private double giaTri;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    /**
     * @return the idKhuyenMai
     */
    public int getIdKhuyenMai() {
        return idKhuyenMai;
    }

    /**
     * @param idKhuyenMai the idKhuyenMai to set
     */
    public void setIdKhuyenMai(int idKhuyenMai) {
        this.idKhuyenMai = idKhuyenMai;
    }

    /**
     * @return the giaTri
     */
    public double getGiaTri() {
        return giaTri;
    }

    /**
     * @param giaTri the giaTri to set
     */
    public void setGiaTri(double giaTri) {
        this.giaTri = giaTri;
    }

    /**
     * @return the ngayBatDau
     */
    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    /**
     * @param ngayBatDau the ngayBatDau to set
     */
    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    /**
     * @return the ngayKetThuc
     */
    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    /**
     * @param ngayKetThuc the ngayKetThuc to set
     */
    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    
}
