/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.services;

import com.tuantran.pojo.ChiNhanh;
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
public class ChiNhanhService {
    public List<ChiNhanh> getChiNhanhs(String keyword) throws SQLException {
        List<ChiNhanh> chiNhanhs = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {

            String query = "SELECT * FROM ChiNhanh";

            if (keyword != null && !keyword.isEmpty()) {
                query += " WHERE dia_chi LIKE concat('%', ?, '%')";
            }

            PreparedStatement stm = conn.prepareCall(query);

            if (keyword != null && !keyword.isEmpty()) {
                stm.setString(1, keyword);
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int idChiNhanh = rs.getInt("id_chi_nhanh");
                String diaChi = rs.getString("dia_chi");

                ChiNhanh cn = new ChiNhanh(idChiNhanh, diaChi);
                chiNhanhs.add(cn);
            }
        }

        return chiNhanhs;
    }
    
    public List<ChiNhanh> getChiNhanhs(String keyword_id, String keyword_diaChi) throws SQLException {
        List<ChiNhanh> chiNhanhs = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {

            String query = "SELECT * FROM ChiNhanh";

            if (keyword_id != null && !keyword_id.isEmpty()) {
                query += " WHERE id_chi_nhanh LIKE concat('%', ?, '%')";
            }
            
            if (keyword_diaChi != null && !keyword_diaChi.isEmpty()) {
                query += " WHERE dia_chi LIKE concat('%', ?, '%')";
            }

            PreparedStatement stm = conn.prepareCall(query);

            if (keyword_id != null && !keyword_id.isEmpty()) {
                stm.setString(1, keyword_id);
            }
            
            if (keyword_diaChi != null && !keyword_diaChi.isEmpty()) {
                stm.setString(1, keyword_diaChi);
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int idChiNhanh = rs.getInt("id_chi_nhanh");
                String diaChi = rs.getString("dia_chi");

                ChiNhanh cn = new ChiNhanh(idChiNhanh, diaChi);
                chiNhanhs.add(cn);
            }
        }

        return chiNhanhs;
    }
}
