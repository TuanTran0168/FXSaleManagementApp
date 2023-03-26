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
public class HoaDon {
    private int idHoaDon;
    private int idNhanVien;
    private int idChiNhanh;
    private int idThanhVien;
    private double tongTien;
    private double soTienNhan;
    private Date ngayCT;

    public HoaDon() {
    }

    public HoaDon(int idHoaDon, int idNhanVien, int idChiNhanh, int idThanhVien, double tongTien, double soTienNhan, Date ngayCT) {
        this.idHoaDon = idHoaDon;
        this.idNhanVien = idNhanVien;
        this.idChiNhanh = idChiNhanh;
        this.idThanhVien = idThanhVien;
        this.tongTien = tongTien;
        this.soTienNhan = soTienNhan;
        this.ngayCT = ngayCT;
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
     * @return the idThanhVien
     */
    public int getIdThanhVien() {
        return idThanhVien;
    }

    /**
     * @param idThanhVien the idThanhVien to set
     */
    public void setIdThanhVien(int idThanhVien) {
        this.idThanhVien = idThanhVien;
    }

    /**
     * @return the tongTien
     */
    public double getTongTien() {
        return tongTien;
    }

    /**
     * @param tongTien the tongTien to set
     */
    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    /**
     * @return the soTienNhan
     */
    public double getSoTienNhan() {
        return soTienNhan;
    }

    /**
     * @param soTienNhan the soTienNhan to set
     */
    public void setSoTienNhan(double soTienNhan) {
        this.soTienNhan = soTienNhan;
    }

    /**
     * @return the ngayCT
     */
    public Date getNgayCT() {
        return ngayCT;
    }

    /**
     * @param ngayCT the ngayCT to set
     */
    public void setNgayCT(Date ngayCT) {
        this.ngayCT = ngayCT;
    }
}
