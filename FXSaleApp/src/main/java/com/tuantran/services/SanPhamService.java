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

//    public List<SanPham> getSanPham() throws SQLException {
//        List<SanPham> sanPhams = new ArrayList<>();
//
//        try (Connection conn = JdbcUtils.getConn()) {
//            Statement stm = conn.createStatement();
//
//            String query = "SELECT * FROM SanPham";
//            ResultSet rs = stm.executeQuery(query);
//
//            while (rs.next()) {
//                int idSanPham = rs.getInt("id_san_pham");
//                String tenSanPham = rs.getString("ten_san_pham");
//                double gia = rs.getDouble("gia");
//                String donVi = rs.getString("don_vi");
//                int idKhuyenMai = rs.getInt("id_khuyen_mai");
//                int idChiNhanh = rs.getInt("id_chi_nhanh");
//
//                SanPham sp = new SanPham(idSanPham, tenSanPham, gia, donVi, idKhuyenMai, idChiNhanh);
//                sanPhams.add(sp);
//            }
//        }
//
//        return sanPhams;
//    }
//
//    public List<SanPham> getSanPham(String keyword) throws SQLException {
//        List<SanPham> sanPhams = new ArrayList<>();
//
//        try (Connection conn = JdbcUtils.getConn()) {
//
//            String query = "SELECT * FROM SanPham";
//
//            if (keyword != null && !keyword.isEmpty()) {
//                query += " WHERE ten_san_pham LIKE concat('%', ?, '%')";
//            }
//
//            PreparedStatement stm = conn.prepareCall(query);
//
//            if (keyword != null && !keyword.isEmpty()) {
//                stm.setString(1, keyword);
//            }
//
//            ResultSet rs = stm.executeQuery();
//
//            while (rs.next()) {
//                int idSanPham = rs.getInt("id_san_pham");
//                String tenSanPham = rs.getString("ten_san_pham");
//                double gia = rs.getDouble("gia");
//                String donVi = rs.getString("don_vi");
//                int idKhuyenMai = rs.getInt("id_khuyen_mai");
//                int idChiNhanh = rs.getInt("id_chi_nhanh");
//
//                SanPham sp = new SanPham(idSanPham, tenSanPham, gia, donVi, idKhuyenMai, idChiNhanh);
//                sanPhams.add(sp);
//            }
//        }
//
//        return sanPhams;
//    }

    public List<SanPham> getSanPham(String keyword_id, String keyword_tenSanPham, String keyword_gia, String keyword_donVi, String keyword_idChiNhanh) throws SQLException {
        List<SanPham> sanPhams = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {

            String query = "SELECT * FROM SanPham";

            if (keyword_id != null && !keyword_id.isEmpty()) {
                query += " WHERE id_san_pham = ?";
            }

            if (keyword_tenSanPham != null && !keyword_tenSanPham.isEmpty()) {
                query += " WHERE ten_san_pham LIKE concat('%', ?, '%')";
            }
            if (keyword_gia != null && !keyword_gia.isEmpty()) {
                query += " WHERE gia LIKE concat('%', ?, '%')";
            }
            if (keyword_donVi != null && !keyword_donVi.isEmpty()) {
                query += " WHERE don_vi LIKE concat('%', ?, '%')";
            }
            if (keyword_idChiNhanh != null && !keyword_idChiNhanh.isEmpty()) {
                query += " WHERE id_chi_nhanh = ?";
            }

            PreparedStatement stm = conn.prepareCall(query);

            if (keyword_id != null && !keyword_id.isEmpty()) {
                stm.setString(1, keyword_id);
            }

            if (keyword_tenSanPham != null && !keyword_tenSanPham.isEmpty()) {
                stm.setString(1, keyword_tenSanPham);
            }

            if (keyword_gia != null && !keyword_gia.isEmpty()) {
                stm.setString(1, keyword_gia);
            }

            if (keyword_donVi != null && !keyword_donVi.isEmpty()) {
                stm.setString(1, keyword_donVi);
            }

            if (keyword_idChiNhanh != null && !keyword_idChiNhanh.isEmpty()) {
                stm.setString(1, keyword_idChiNhanh);
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int idSanPham = rs.getInt("id_san_pham");
                String tenSanPham = rs.getString("ten_san_pham");
                double gia = rs.getDouble("gia");
                String donVi = rs.getString("don_vi");
                int idKhuyenMai = rs.getInt("id_khuyen_mai");
                int idChiNhanh = rs.getInt("id_chi_nhanh");

                SanPham sp = new SanPham(idSanPham, tenSanPham, gia, donVi, idKhuyenMai, idChiNhanh);
                sanPhams.add(sp);
            }
        }

        return sanPhams;
    }

    public List<SanPham> getSanPhamTrongChiNhanh(String keyword_tenSanPham, String keyword_idChiNhanh) throws SQLException {
        List<SanPham> sanPhams = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {

            String query = "SELECT * FROM SanPham";

//            if (keyword_tenSanPham != null && !keyword_tenSanPham.isEmpty() && keyword_idChiNhanh != null && !keyword_idChiNhanh.isEmpty()) {
//                query += " WHERE ten_san_pham LIKE concat('%', ?, '%') "
//                        + "AND id_chi_nhanh = ?";
//            }
            if (keyword_tenSanPham != null && !keyword_tenSanPham.isEmpty()) {
                query += " WHERE ten_san_pham LIKE concat('%', ?, '%')";
//                        + "AND id_chi_nhanh = ?";
                if (keyword_idChiNhanh != null && !keyword_idChiNhanh.isEmpty()) {
                    query += " AND id_chi_nhanh = ?";
                }
            }
            else {
                if (keyword_idChiNhanh != null && !keyword_idChiNhanh.isEmpty()) {
                    query += " WHERE id_chi_nhanh = ?";
                }
            }

            PreparedStatement stm = conn.prepareCall(query);
            
            if (keyword_tenSanPham != null && !keyword_tenSanPham.isEmpty()) {
              
                if (keyword_idChiNhanh != null && !keyword_idChiNhanh.isEmpty()) {
                    stm.setString(1, keyword_tenSanPham);
                     stm.setString(2, keyword_idChiNhanh);
                }
            }
            else {
                if (keyword_idChiNhanh != null && !keyword_idChiNhanh.isEmpty()) {
                     stm.setString(1, keyword_idChiNhanh);
                }
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int idSanPham = rs.getInt("id_san_pham");
                String tenSanPham = rs.getString("ten_san_pham");
                double gia = rs.getDouble("gia");
                String donVi = rs.getString("don_vi");
                int idKhuyenMai = rs.getInt("id_khuyen_mai");
                int idChiNhanh = rs.getInt("id_chi_nhanh");

                SanPham sp = new SanPham(idSanPham, tenSanPham, gia, donVi, idKhuyenMai, idChiNhanh);
                sanPhams.add(sp);
            }
        }

        return sanPhams;
    }

    public boolean addSanPham(SanPham sanPham) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String query = "INSERT INTO SanPham(id_san_pham, ten_san_pham, gia, don_vi, id_khuyen_mai, id_chi_nhanh)"
                    + " VALUES(?, ?, ?, ?, ?, ?)";

            PreparedStatement stm = conn.prepareCall(query);

            stm.setInt(1, sanPham.getIdSanPham());
            stm.setString(2, sanPham.getTenSanPham());
            stm.setDouble(3, sanPham.getGia());
            stm.setString(4, sanPham.getDonVi());
            stm.setInt(5, sanPham.getIdKhuyenMai());
            stm.setInt(6, sanPham.getIdChiNhanh());

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

    public boolean deleteSanPham(String id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "DELETE FROM SanPham WHERE id_san_pham = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, id);

            return stm.executeUpdate() > 0;
        }
    }

    public boolean updateSanPham(String id, String tenSanPham, double gia, String donVi, int idKhuyenMai, int idChiNhanh) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "UPDATE SanPham SET ten_san_pham = ?, "
                    + "gia = ?, "
                    + "don_vi = ?, "
                    + "id_khuyen_mai = ?, "
                    + "id_chi_nhanh = ? "
                    + "WHERE id_san_pham = ?";

            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, tenSanPham);
            stm.setDouble(2, gia);
            stm.setString(3, donVi);
            stm.setInt(4, idKhuyenMai);
            stm.setInt(5, idChiNhanh);
            stm.setString(6, id);

            return stm.executeUpdate() > 0;
        }
    }
}
