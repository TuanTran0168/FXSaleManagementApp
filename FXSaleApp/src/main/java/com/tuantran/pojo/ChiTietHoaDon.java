/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.pojo;

/**
 *
 * @author TuanTran
 */
public class ChiTietHoaDon {

    private int idCTHD;
    private int idSanPham;
    private int idHoaDon;
    private int soLuong;
    private double thanhTien;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int idCTHD, int idSanPham, int idHoaDon, int soLuong, double thanhTien) {
        this.idCTHD = idCTHD;
        this.idSanPham = idSanPham;
        this.idHoaDon = idHoaDon;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }
    
    

    /**
     * @return the idCTHD
     */
    public int getIdCTHD() {
        return idCTHD;
    }

    /**
     * @param idCTHD the idCTHD to set
     */
    public void setIdCTHD(int idCTHD) {
        this.idCTHD = idCTHD;
    }

    /**
     * @return the idSanPham
     */
    public int getIdSanPham() {
        return idSanPham;
    }

    /**
     * @param idSanPham the idSanPham to set
     */
    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    /**
     * @return the idHoaDon
     */
    public int getIdHoaDon() {
        return idHoaDon;
    }

    /**
     * @param idHoaDon the idHoaDon to set
     */
    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    /**
     * @return the soLuong
     */
    public int getSoLuong() {
        return soLuong;
    }

    /**
     * @param soLuong the soLuong to set
     */
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    /**
     * @return the thanhTien
     */
    public double getThanhTien() {
        return thanhTien;
    }

    /**
     * @param thanhTien the thanhTien to set
     */
    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

}
