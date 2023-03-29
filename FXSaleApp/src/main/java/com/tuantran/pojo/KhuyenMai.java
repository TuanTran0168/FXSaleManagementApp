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
    private String tenKhuyenMai;
    private double giaTri;
    private Date ngayBatDau;
    private Date ngayKetThuc;

    public KhuyenMai() {
    }

    public KhuyenMai(int idKhuyenMai, double giaTri, Date ngayBatDau, Date ngayKetThuc) {
        this.idKhuyenMai = idKhuyenMai;
        this.giaTri = giaTri;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public KhuyenMai(int idKhuyenMai, String tenKhuyenMai, double giaTri, Date ngayBatDau, Date ngayKetThuc) {
        this.idKhuyenMai = idKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.giaTri = giaTri;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }
    
    
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

    /**
     * @return the tenKhuyenMai
     */
    public String getTenKhuyenMai() {
        return tenKhuyenMai;
    }

    /**
     * @param tenKhuyenMai the tenKhuyenMai to set
     */
    public void setTenKhuyenMai(String tenKhuyenMai) {
        this.tenKhuyenMai = tenKhuyenMai;
    } 
    
}
