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
public class ThanhVien {
    private int idThanhVien;
    private String hoThanhVien;
    private String tenThanhVien;
    private Date ngaySinhThanhVien;
    private String soDienThoai;

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
     * @return the hoThanhVien
     */
    public String getHoThanhVien() {
        return hoThanhVien;
    }

    /**
     * @param hoThanhVien the hoThanhVien to set
     */
    public void setHoThanhVien(String hoThanhVien) {
        this.hoThanhVien = hoThanhVien;
    }

    /**
     * @return the tenThanhVien
     */
    public String getTenThanhVien() {
        return tenThanhVien;
    }

    /**
     * @param tenThanhVien the tenThanhVien to set
     */
    public void setTenThanhVien(String tenThanhVien) {
        this.tenThanhVien = tenThanhVien;
    }

    /**
     * @return the ngaySinhThanhVien
     */
    public Date getNgaySinhThanhVien() {
        return ngaySinhThanhVien;
    }

    /**
     * @param ngaySinhThanhVien the ngaySinhThanhVien to set
     */
    public void setNgaySinhThanhVien(Date ngaySinhThanhVien) {
        this.ngaySinhThanhVien = ngaySinhThanhVien;
    }

    /**
     * @return the soDienThoai
     */
    public String getSoDienThoai() {
        return soDienThoai;
    }

    /**
     * @param soDienThoai the soDienThoai to set
     */
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    
    
}
