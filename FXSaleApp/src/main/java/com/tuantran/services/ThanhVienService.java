/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.services;

import com.tuantran.pojo.ThanhVien;
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
public class ThanhVienService {
    public List<ThanhVien> getThanhVien(String keyword) throws SQLException {
        List<ThanhVien> thanhViens = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {

            String query = "SELECT * FROM ThanhVien";

            if (keyword != null && !keyword.isEmpty()) {
                query += " WHERE ten_thanh_vien LIKE concat('%', ?, '%')";
            }

            PreparedStatement stm = conn.prepareCall(query);

            if (keyword != null && !keyword.isEmpty()) {
                stm.setString(1, keyword);
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int idThanhVien = rs.getInt("id_thanh_vien");
                String hoThanhVien = rs.getString("ho_thanh_vien");
                String tenThanhVien = rs.getString("ten_thanh_vien");
                Date ngaySinhThanhVien = rs.getDate("ngay_sinh_thanh_vien");
                String soDienThoai = rs.getString("so_dien_thoai");
                
                ThanhVien tv = new ThanhVien(idThanhVien, hoThanhVien, tenThanhVien, ngaySinhThanhVien, soDienThoai);
                thanhViens.add(tv);
            }
        }
        return thanhViens;
    }
}
