/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.pojo;

/**
 *
 * @author TuanTran
 */
public class SanPham {
    private int idSanPham;
    private String tenSanPham;
    private double gia;
    private String donVi;
    private int idKhuyenMai;
    
    private double soLuongTemp = 1;

    @Override
    public String toString() {
        return this.getTenSanPham();
    }

    public SanPham() {
    }

    public SanPham(int idSanPham, String tenSanPham, double gia, String donVi, int idKhuyenMai) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.gia = gia;
        this.donVi = donVi;
        this.idKhuyenMai = idKhuyenMai;
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
     * @return the tenSanPham
     */
    public String getTenSanPham() {
        return tenSanPham;
    }

    /**
     * @param tenSanPham the tenSanPham to set
     */
    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    /**
     * @return the gia
     */
    public double getGia() {
        return gia;
    }

    /**
     * @param gia the gia to set
     */
    public void setGia(double gia) {
        this.gia = gia;
    }

    /**
     * @return the donVi
     */
    public String getDonVi() {
        return donVi;
    }

    /**
     * @param donVi the donVi to set
     */
    public void setDonVi(String donVi) {
        this.donVi = donVi;
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
     * @return the soLuongTemp
     */
    public double getSoLuongTemp() {
        return soLuongTemp;
    }

    /**
     * @param soLuongTemp the soLuongTemp to set
     */
    public void setSoLuongTemp(double soLuongTemp) {
        this.soLuongTemp = soLuongTemp;
    }
}
