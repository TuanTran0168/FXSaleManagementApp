/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.services;

import com.tuantran.pojo.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TuanTran
 */
public class SanPhamService {

    public List<SanPham> getSanPham() throws SQLException {
        List<SanPham> sanPhams = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();

            String query = "SELECT * FROM SanPham";
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                int idSanPham = rs.getInt("id_san_pham");
                String tenSanPham = rs.getString("ten_san_pham");
                double gia = rs.getDouble("gia");
                String donVi = rs.getString("don_vi");
                int idKhuyenMai = rs.getInt("id_khuyen_mai");

                SanPham sp = new SanPham(idSanPham, tenSanPham, gia, donVi, idKhuyenMai);
                sanPhams.add(sp);
            }
        }

        return sanPhams;
    }
    
    public List<SanPham> getSanPham(String keyword) throws SQLException {
        List<SanPham> sanPhams = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            
            String query = "SELECT * FROM SanPham";
            
            if (keyword != null && !keyword.isEmpty()) {
                query += " WHERE ten_san_pham LIKE concat('%', ?, '%')";
            }
            
            PreparedStatement stm = conn.prepareCall(query);
            
            if (keyword != null && !keyword.isEmpty()) {
                stm.setString(1, keyword);
            }
            
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                int idSanPham = rs.getInt("id_san_pham");
                String tenSanPham = rs.getString("ten_san_pham");
                double gia = rs.getDouble("gia");
                String donVi = rs.getString("don_vi");
                int idKhuyenMai = rs.getInt("id_khuyen_mai");

                SanPham sp = new SanPham(idSanPham, tenSanPham, gia, donVi, idKhuyenMai);
                sanPhams.add(sp);
            }
        }

        return sanPhams;
    }
}