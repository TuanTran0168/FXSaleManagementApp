/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.pojo;

/**
 *
 * @author TuanTran
 */
public class ChiNhanh {
    private int idChiNhanh;
    private String diaChi;

    public ChiNhanh() {
    }

    public ChiNhanh(int idChiNhanh, String diaChi) {
        this.idChiNhanh = idChiNhanh;
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return this.diaChi;
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
     * @return the diaChi
     */
    public String getDiaChi() {
        return diaChi;
    }

    /**
     * @param diaChi the diaChi to set
     */
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
}
