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

    public List<ThanhVien> getThanhViens(String keyword_id, String keyword_hoThanhVien, String keyword_tenThanhVien, String keyword_soDienThoai) throws SQLException {
        List<ThanhVien> thanhViens = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {

            String query = "SELECT * FROM ThanhVien";

            if (keyword_id != null && !keyword_id.isEmpty()) {
                query += " WHERE id_thanh_vien = ?";
            }

            if (keyword_hoThanhVien != null && !keyword_hoThanhVien.isEmpty()) {
                query += " WHERE ho_thanh_vien LIKE concat('%', ?, '%')";
            }

            if (keyword_tenThanhVien != null && !keyword_tenThanhVien.isEmpty()) {
                query += " WHERE ten_thanh_vien LIKE concat('%', ?, '%')";
            }

            if (keyword_soDienThoai != null && !keyword_soDienThoai.isEmpty()) {
                query += " WHERE so_dien_thoai LIKE concat('%', ?, '%')";
            }

            PreparedStatement stm = conn.prepareCall(query);

            if (keyword_id != null && !keyword_id.isEmpty()) {
                stm.setString(1, keyword_id);
            }

            if (keyword_hoThanhVien != null && !keyword_hoThanhVien.isEmpty()) {
                stm.setString(1, keyword_hoThanhVien);
            }

            if (keyword_tenThanhVien != null && !keyword_tenThanhVien.isEmpty()) {
                stm.setString(1, keyword_tenThanhVien);
            }

            if (keyword_soDienThoai != null && !keyword_soDienThoai.isEmpty()) {
                stm.setString(1, keyword_soDienThoai);
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

//    public boolean addThanhVien(ThanhVien thanhVien) {
//        try {
//            try (Connection conn = JdbcUtils.getConn()) {
//                conn.setAutoCommit(false);
//                String query = "INSERT INTO ThanhVien(id_thanh_vien, ho_thanh_vien, ten_thanh_vien, ngay_sinh_thanh_vien, so_dien_thoai)"
//                        + " VALUES(?, ?, ?, ?, ?)";
//
//                PreparedStatement stm = conn.prepareCall(query);
//
//                stm.setInt(1, thanhVien.getIdThanhVien());
//                stm.setString(2, thanhVien.getHoThanhVien());
//                stm.setString(3, thanhVien.getTenThanhVien());
//                stm.setDate(4, thanhVien.getNgaySinhThanhVien());
//                stm.setString(5, thanhVien.getSoDienThoai());
//
//                stm.executeUpdate();
//
//                conn.commit();
//                return true;
//            }
//
//        } catch (SQLException ex) {
//            System.err.println(ex.getMessage());
//            return false;
//        }
//
//    }
    public boolean addThanhVien(ThanhVien thanhVien) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String query = "INSERT INTO ThanhVien(id_thanh_vien, ho_thanh_vien, ten_thanh_vien, ngay_sinh_thanh_vien, so_dien_thoai)"
                    + " VALUES(?, ?, ?, ?, ?)";

            PreparedStatement stm = conn.prepareCall(query);

            stm.setInt(1, thanhVien.getIdThanhVien());
            stm.setString(2, thanhVien.getHoThanhVien());
            stm.setString(3, thanhVien.getTenThanhVien());
            stm.setDate(4, thanhVien.getNgaySinhThanhVien());
            stm.setString(5, thanhVien.getSoDienThoai());

            stm.executeUpdate();

            try {
                conn.commit();
                return true;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                return false;
            }
        }
    }

    public boolean deleteNhanVien(String id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "DELETE FROM ThanhVien WHERE id_thanh_vien = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, id);

            return stm.executeUpdate() > 0;
        }
    }

    public boolean updateNhanVien(String id, String hoThanhVien, String tenThanhVien, Date ngaySinh, String soDienThoai) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "UPDATE ThanhVien SET ho_thanh_vien = ?, "
                    + "ten_thanh_vien = ?, "
                    + "ngay_sinh_thanh_vien = ?, "
                    + "so_dien_thoai = ? "
                    + "WHERE id_thanh_vien = ?";

            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, hoThanhVien);
            stm.setString(2, tenThanhVien);
            stm.setDate(3, ngaySinh);
            stm.setString(4, soDienThoai);
            stm.setString(5, id);

            return stm.executeUpdate() > 0;
        }
    }
}
