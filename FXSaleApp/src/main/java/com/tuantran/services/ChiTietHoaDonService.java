/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.services;

import com.tuantran.pojo.ChiTietHoaDon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TuanTran
 */
public class ChiTietHoaDonService {
    public List<ChiTietHoaDon> getSanPham(String keyword) throws SQLException {
        List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            
            String query = "SELECT * FROM ChiTietHoaDon";
            
            if (keyword != null && !keyword.isEmpty()) {
                query += " WHERE id_CTHD LIKE concat('%', ?, '%')";
            }
            
            PreparedStatement stm = conn.prepareCall(query);
            
            if (keyword != null && !keyword.isEmpty()) {
                stm.setString(1, keyword);
            }
            
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                
                int idCTHD = rs.getInt("id_CTHD");
                int idSanPham = rs.getInt("id_san_pham");
                int idHoaDon = rs.getInt("id_hoa_don");
                int soLuong = rs.getInt("so_luong");
                double thanhTien = rs.getDouble("thanh_tien");
                
                ChiTietHoaDon cthd = new ChiTietHoaDon(idCTHD, idSanPham, idHoaDon, soLuong, thanhTien);
                chiTietHoaDons.add(cthd);
            }
        }

        return chiTietHoaDons;
    }
}
