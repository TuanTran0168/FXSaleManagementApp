/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.pojo;

import com.tuantran.services.ChiTietHoaDonService;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TuanTran
 */
public class ChiTietHoaDon {

    static ChiTietHoaDonService chiTietHoaDonService = new ChiTietHoaDonService();
//    static int count = 0;
    private int idCTHD;
    private int idSanPham;
    private int idHoaDon;
    private double soLuong;
    private double thanhTien;

//    static {
//        try {
//            List<ChiTietHoaDon> chiTietHoaDons = chiTietHoaDonService.getChiTietHoaDon(null);
//            
//            count = chiTietHoaDons.size();
//        } catch (SQLException ex) {
//            Logger.getLogger(ChiTietHoaDon.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public ChiTietHoaDon() {

    }

    public ChiTietHoaDon(int idCTHD, int idSanPham, int idHoaDon, double soLuong, double thanhTien) {
        this.idCTHD = idCTHD;
        this.idSanPham = idSanPham;
        this.idHoaDon = idHoaDon;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public ChiTietHoaDon(int idSanPham, int idHoaDon, double soLuong, double thanhTien) {
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
    public double getSoLuong() {
        return soLuong;
    }

    /**
     * @param soLuong the soLuong to set
     */
    public void setSoLuong(double soLuong) {
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
