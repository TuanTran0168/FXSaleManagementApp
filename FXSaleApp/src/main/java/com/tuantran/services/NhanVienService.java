/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.services;

import com.tuantran.pojo.NhanVien;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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

            while (rs.next()) {
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

//    public List<NhanVien> getNhanViens(String keyword) throws SQLException {
//        List<NhanVien> nhanViens = new ArrayList<>();
//        try (Connection conn = JdbcUtils.getConn()) {
//
//            String query = "SELECT * FROM NhanVien";
//
//            if (keyword != null && !keyword.isEmpty()) {
//                query += " WHERE ten_nhan_vien LIKE concat('%', ?, '%')";
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
//                int idNhanVien = rs.getInt("id_nhan_vien");
//                String hoNhanVien = rs.getString("ho_nhan_vien");
//                String tenNhanVien = rs.getString("ten_nhan_vien");
//                int idChiNhanh = rs.getInt("id_chi_nhanh");
//                String taiKhoan = rs.getString("tai_khoan");
//                String matKhau = rs.getString("mat_khau");
//                boolean quanLy = rs.getBoolean("quan_ly");
//
//                NhanVien n = new NhanVien(idNhanVien, hoNhanVien, tenNhanVien, idChiNhanh, taiKhoan, matKhau, quanLy);
//                nhanViens.add(n);
//            }
//        }
//        return nhanViens;
//    }

//    public List<NhanVien> getNhanViens(String keyword_hoNhanVien, String keyword_tenNhanVien, String keyword_taiKhoan, String keyword_matKhau) throws SQLException {
//        List<NhanVien> nhanViens = new ArrayList<>();
//        try (Connection conn = JdbcUtils.getConn()) {
//
//            String query = "SELECT * FROM NhanVien";
//
//            if (keyword_hoNhanVien != null && !keyword_hoNhanVien.isEmpty()) {
//                query += " WHERE ho_nhan_vien LIKE concat('%', ?, '%')";
//            }
//
//            if (keyword_tenNhanVien != null && !keyword_tenNhanVien.isEmpty()) {
//                query += " WHERE ten_nhan_vien LIKE concat('%', ?, '%')";
//            }
//
//            if (keyword_taiKhoan != null && !keyword_taiKhoan.isEmpty()) {
//                query += " WHERE tai_khoan LIKE concat('%', ?, '%')";
//            }
//
//            if (keyword_matKhau != null && !keyword_matKhau.isEmpty()) {
//                query += " WHERE mat_khau LIKE concat('%', ?, '%')";
//            }
//
//            PreparedStatement stm = conn.prepareCall(query);
//
//            if (keyword_hoNhanVien != null && !keyword_hoNhanVien.isEmpty()) {
//                stm.setString(1, keyword_hoNhanVien);
//            }
//
//            if (keyword_tenNhanVien != null && !keyword_tenNhanVien.isEmpty()) {
//                stm.setString(1, keyword_tenNhanVien);
//            }
//
//            if (keyword_taiKhoan != null && !keyword_taiKhoan.isEmpty()) {
//                stm.setString(1, keyword_taiKhoan);
//            }
//
//            if (keyword_matKhau != null && !keyword_matKhau.isEmpty()) {
//                stm.setString(1, keyword_matKhau);
//            }
//
//            ResultSet rs = stm.executeQuery();
//
//            while (rs.next()) {
//                int idNhanVien = rs.getInt("id_nhan_vien");
//                String hoNhanVien = rs.getString("ho_nhan_vien");
//                String tenNhanVien = rs.getString("ten_nhan_vien");
//                int idChiNhanh = rs.getInt("id_chi_nhanh");
//                String taiKhoan = rs.getString("tai_khoan");
//                String matKhau = rs.getString("mat_khau");
//                boolean quanLy = rs.getBoolean("quan_ly");
//
//                NhanVien n = new NhanVien(idNhanVien, hoNhanVien, tenNhanVien, idChiNhanh, taiKhoan, matKhau, quanLy);
//                nhanViens.add(n);
//            }
//        }
//        return nhanViens;
//    }
    
    public List<NhanVien> getNhanViens(String keyword_id, String keyword_hoNhanVien, String keyword_tenNhanVien, String keyword_taiKhoan, String keyword_matKhau, String keyword_idChiNhanh) throws SQLException {
        List<NhanVien> nhanViens = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {

            String query = "SELECT * FROM NhanVien";
            
             if (keyword_id != null && !keyword_id.isEmpty()) {
                query += " WHERE id_nhan_vien = ?";
            }

            if (keyword_hoNhanVien != null && !keyword_hoNhanVien.isEmpty()) {
                query += " WHERE ho_nhan_vien LIKE concat('%', ?, '%')";
            }

            if (keyword_tenNhanVien != null && !keyword_tenNhanVien.isEmpty()) {
                query += " WHERE ten_nhan_vien LIKE concat('%', ?, '%')";
            }

            if (keyword_taiKhoan != null && !keyword_taiKhoan.isEmpty()) {
                query += " WHERE tai_khoan LIKE concat('%', ?, '%')";
            }

            if (keyword_matKhau != null && !keyword_matKhau.isEmpty()) {
                query += " WHERE mat_khau LIKE concat('%', ?, '%')";
            }
            
              if (keyword_idChiNhanh != null && !keyword_idChiNhanh.isEmpty()) {
                query += " WHERE id_chi_nhanh = ?";
            }

            PreparedStatement stm = conn.prepareCall(query);
            
             if (keyword_id != null && !keyword_id.isEmpty()) {
                stm.setString(1, keyword_id);
            }

            if (keyword_hoNhanVien != null && !keyword_hoNhanVien.isEmpty()) {
                stm.setString(1, keyword_hoNhanVien);
            }

            if (keyword_tenNhanVien != null && !keyword_tenNhanVien.isEmpty()) {
                stm.setString(1, keyword_tenNhanVien);
            }

            if (keyword_taiKhoan != null && !keyword_taiKhoan.isEmpty()) {
                stm.setString(1, keyword_taiKhoan);
            }

            if (keyword_matKhau != null && !keyword_matKhau.isEmpty()) {
                stm.setString(1, keyword_matKhau);
            }
            
             if (keyword_idChiNhanh != null && !keyword_idChiNhanh.isEmpty()) {
                stm.setString(1, keyword_idChiNhanh);
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
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

            while (rs.next()) {
                boolean quanLy = rs.getBoolean("quan_ly");
                if (quanLy == true) {
                    nhanVienQuanLys.add("Quản lý");
                } else {
                    nhanVienQuanLys.add("Nhân viên");
                }
            }
        }

        return nhanVienQuanLys;
    }

//    public boolean addNhanVien(NhanVien nhanVien) {
//        try {
//            try (Connection conn = JdbcUtils.getConn()) {
//                conn.setAutoCommit(false);
//                String query = "INSERT INTO NhanVien(id_nhan_vien, ho_nhan_vien, ten_nhan_vien, id_chi_nhanh, tai_khoan, mat_khau, quan_ly)"
//                        + " VALUES(?, ?, ?, ?, ?, ?, ?)";
//
//                PreparedStatement stm = conn.prepareCall(query);
//
//                stm.setInt(1, nhanVien.getIdNhanVien());
//                stm.setString(2, nhanVien.getHoNhanVien());
//                stm.setString(3, nhanVien.getTenNhanVien());
//                stm.setInt(4, nhanVien.getIdChiNhanh());
//                stm.setString(5, nhanVien.getTaiKhoan());
//                stm.setString(6, nhanVien.getMatKhau());
//                stm.setBoolean(7, nhanVien.isQuanLy());
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
    public boolean addNhanVien(NhanVien nhanVien) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String query = "INSERT INTO NhanVien(id_nhan_vien, ho_nhan_vien, ten_nhan_vien, id_chi_nhanh, tai_khoan, mat_khau, quan_ly)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stm = conn.prepareCall(query);

            stm.setInt(1, nhanVien.getIdNhanVien());
            stm.setString(2, nhanVien.getHoNhanVien());
            stm.setString(3, nhanVien.getTenNhanVien());
            stm.setInt(4, nhanVien.getIdChiNhanh());
            stm.setString(5, nhanVien.getTaiKhoan());
            stm.setString(6, nhanVien.getMatKhau());
            stm.setBoolean(7, nhanVien.isQuanLy());

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
            String query = "DELETE FROM NhanVien WHERE id_nhan_vien = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, id);

            return stm.executeUpdate() > 0;
        }
    }

    public boolean updateNhanVien(String id, String hoNhanVien, String tenNhanVien, int idChiNhanh, String taiKhoan, String matKhau, boolean quanLy) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "UPDATE NhanVien SET ho_nhan_vien = ?, "
                    + "ten_nhan_vien = ?, "
                    + "id_chi_nhanh = ?, "
                    + "tai_khoan = ?, "
                    + "mat_khau = ?, "
                    + "quan_ly = ? "
                    + "WHERE id_nhan_vien = ?";

            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, hoNhanVien);
            stm.setString(2, tenNhanVien);
            stm.setInt(3, idChiNhanh);
            stm.setString(4, taiKhoan);
            stm.setString(5, matKhau);
            stm.setBoolean(6, quanLy);
            stm.setString(7, id);

            return stm.executeUpdate() > 0;
        }
    }
}
