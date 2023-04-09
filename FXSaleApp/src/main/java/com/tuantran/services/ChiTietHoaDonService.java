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

    public List<ChiTietHoaDon> getChiTietHoaDon(String keyword_id, String keyword_idHoaDon) throws SQLException {
        List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {

            String query = "SELECT * FROM ChiTietHoaDon";

            if (keyword_id != null && !keyword_id.isEmpty()) {
                query += " WHERE id_CTHD = ?";
            }

            if (keyword_idHoaDon != null && !keyword_idHoaDon.isEmpty()) {
                query += " WHERE id_hoa_don = ?";
            }

            PreparedStatement stm = conn.prepareCall(query);

            if (keyword_id != null && !keyword_id.isEmpty()) {
                stm.setString(1, keyword_id);
            }

            if (keyword_idHoaDon != null && !keyword_idHoaDon.isEmpty()) {
                stm.setString(1, keyword_idHoaDon);
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
