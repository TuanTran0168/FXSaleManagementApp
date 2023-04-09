/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.pojo;

/**
 *
 * @author TuanTran
 */
public class ChiTietHoaDonHienThi {
    private String soThuTu;
    private String idCTHD;
    private String tenSanPham;
    private String soLuong;
    private String giaGoc;
    private String giaDaGiam;
    private String thanhTien;

    public ChiTietHoaDonHienThi() {
    }

    public ChiTietHoaDonHienThi(String soThuTu, String idCTHD, String tenSanPham, String soLuong, String giaGoc, String giaDaGiam, String thanhTien) {
        this.soThuTu = soThuTu;
        this.idCTHD = idCTHD;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.giaGoc = giaGoc;
        this.giaDaGiam = giaDaGiam;
        this.thanhTien = thanhTien;
    }

    

    /**
     * @return the idCTHD
     */
    public String getIdCTHD() {
        return idCTHD;
    }

    /**
     * @param idCTHD the idCTHD to set
     */
    public void setIdCTHD(String idCTHD) {
        this.idCTHD = idCTHD;
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
     * @return the soLuong
     */
    public String getSoLuong() {
        return soLuong;
    }

    /**
     * @param soLuong the soLuong to set
     */
    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    /**
     * @return the giaGoc
     */
    public String getGiaGoc() {
        return giaGoc;
    }

    /**
     * @param giaGoc the giaGoc to set
     */
    public void setGiaGoc(String giaGoc) {
        this.giaGoc = giaGoc;
    }

    /**
     * @return the giaDaGiam
     */
    public String getGiaDaGiam() {
        return giaDaGiam;
    }

    /**
     * @param giaDaGiam the giaDaGiam to set
     */
    public void setGiaDaGiam(String giaDaGiam) {
        this.giaDaGiam = giaDaGiam;
    }

    /**
     * @return the thanhTien
     */
    public String getThanhTien() {
        return thanhTien;
    }

    /**
     * @param thanhTien the thanhTien to set
     */
    public void setThanhTien(String thanhTien) {
        this.thanhTien = thanhTien;
    }

    /**
     * @return the soThuTu
     */
    public String getSoThuTu() {
        return soThuTu;
    }

    /**
     * @param soThuTu the soThuTu to set
     */
    public void setSoThuTu(String soThuTu) {
        this.soThuTu = soThuTu;
    }
}
