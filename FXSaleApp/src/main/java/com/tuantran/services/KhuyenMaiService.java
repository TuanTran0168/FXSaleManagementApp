/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.services;

import com.tuantran.pojo.KhuyenMai;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TuanTran
 */
public class KhuyenMaiService {
    public List<KhuyenMai> getKhuyenMai(String keyword) throws SQLException {
        List<KhuyenMai> khuyenMais = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {

            String query = "SELECT * FROM KhuyenMai";

            if (keyword != null && !keyword.isEmpty()) {
                query += " WHERE ten_khuyen_mai LIKE concat('%', ?, '%')";
            }

            PreparedStatement stm = conn.prepareCall(query);

            if (keyword != null && !keyword.isEmpty()) {
                stm.setString(1, keyword);
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
               int idKhuyenMai = rs.getInt("id_khuyen_mai");
               String tenKhuyenMai = rs.getString("ten_khuyen_mai");
               double giaTri = rs.getDouble("gia_tri");
               Date ngayBatDau = rs.getDate("ngay_bat_dau");
               Date ngayKetThuc = rs.getDate("ngay_ket_thuc");
               
               KhuyenMai km = new KhuyenMai(idKhuyenMai, tenKhuyenMai, giaTri, ngayBatDau, ngayKetThuc);
               khuyenMais.add(km);
            }
        }
        return khuyenMais;
    }
}
