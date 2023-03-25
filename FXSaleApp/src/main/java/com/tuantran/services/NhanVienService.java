/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.services;

import com.tuantran.pojo.NhanVien;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TuanTran
 */
public class NhanVienService {
    public List<NhanVien> getNhanViens() throws SQLException {
        List<NhanVien> nhanViens = new ArrayList<>();
        
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            
            String query = "SELECT * FROM NhanVien";
            ResultSet rs = stm.executeQuery(query);
            
            while(rs.next()) {
                int idNhanVien = rs.getInt("id_nhan_vien");
                String hoNhanVien = rs.getString("ho_nhan_vien");
                String tenNhanVien = rs.getString("ten_nhan_vien");
                int idChiNhanh = rs.getInt("id_chi_nhanh");
                String taiKhoan = rs.getString("tai_khoan");
                String matKhau = rs.getString("mat_khau");
                boolean quanLy = rs.getBoolean("quan_ly");
                
                NhanVien n = new NhanVien(idNhanVien, hoNhanVien, tenNhanVien, idChiNhanh, taiKhoan, matKhau, quanLy);
                nhanViens.add(n);
            }
        }
        return nhanViens;
    }
    
    public List<String> getQuanLy() throws SQLException {
        List<String> nhanVienQuanLys = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            
            String query = "SELECT quan_ly FROM NhanVien";
            ResultSet rs = stm.executeQuery(query);
            
            while(rs.next()) {
                boolean quanLy = rs.getBoolean("quan_ly");
                if (quanLy == true)
                    nhanVienQuanLys.add("Quản lý");
                else
                    nhanVienQuanLys.add("Nhân viên");
            }
        }
        
        return nhanVienQuanLys;
    }
    
    
}
