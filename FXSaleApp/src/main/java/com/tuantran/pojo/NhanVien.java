/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.pojo;

/**
 *
 * @author TuanTran
 */
public class NhanVien {
    private int idNhanVien;
    private String hoNhanVien;
    private String tenNhanVien;
    private int idChiNhanh;
    private String taiKhoan;
    private String matKhau;
    private boolean quanLy;

    public NhanVien() {
    }

    public NhanVien(int idNhanVien, String hoNhanVien, String tenNhanVien, int idChiNhanh, String taiKhoan, String matKhau, boolean quanLy) {
        this.idNhanVien = idNhanVien;
        this.hoNhanVien = hoNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.idChiNhanh = idChiNhanh;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.quanLy = quanLy;
    }

    @Override
    public String toString() {
        if (this.quanLy == true) {
            return "Quản lý";
        }
        else {
            return "Nhân viên";
        }
    }

    /**
     * @return the idNhanVien
     */
    public int getIdNhanVien() {
        return idNhanVien;
    }

    /**
     * @param idNhanVien the idNhanVien to set
     */
    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    /**
     * @return the hoNhanVien
     */
    public String getHoNhanVien() {
        return hoNhanVien;
    }

    /**
     * @param hoNhanVien the hoNhanVien to set
     */
    public void setHoNhanVien(String hoNhanVien) {
        this.hoNhanVien = hoNhanVien;
    }

    /**
     * @return the tenNhanVien
     */
    public String getTenNhanVien() {
        return tenNhanVien;
    }

    /**
     * @param tenNhanVien the tenNhanVien to set
     */
    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    /**
     * @return the idChiNhanh
     */
    public int getIdChiNhanh() {
        return idChiNhanh;
    }

    /**
     * @param idChiNhanh the idChiNhanh to set
     */
    public void setIdChiNhanh(int idChiNhanh) {
        this.idChiNhanh = idChiNhanh;
    }

    /**
     * @return the taiKhoan
     */
    public String getTaiKhoan() {
        return taiKhoan;
    }

    /**
     * @param taiKhoan the taiKhoan to set
     */
    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    /**
     * @return the matKhau
     */
    public String getMatKhau() {
        return matKhau;
    }

    /**
     * @param matKhau the matKhau to set
     */
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    /**
     * @return the quanLy
     */
    public boolean isQuanLy() {
        return quanLy;
    }

    /**
     * @param quanLy the quanLy to set
     */
    public void setQuanLy(boolean quanLy) {
        this.quanLy = quanLy;
    }

    
}
