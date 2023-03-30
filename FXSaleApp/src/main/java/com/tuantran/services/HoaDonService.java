/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.services;

import com.tuantran.pojo.ChiTietHoaDon;
import com.tuantran.pojo.HoaDon;
import com.tuantran.utils.MessageBox;
import java.sql.Connection;
import java.sql.Date;
//import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author TuanTran
 */
public class HoaDonService {

    public List<HoaDon> getHoaDon(String keyword) throws SQLException {
        List<HoaDon> hoaDons = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {

            String query = "SELECT * FROM HoaDon";

            if (keyword != null && !keyword.isEmpty()) {
                query += " WHERE id_hoa_don LIKE concat('%', ?, '%')";
            }

            PreparedStatement stm = conn.prepareCall(query);

            if (keyword != null && !keyword.isEmpty()) {
                stm.setString(1, keyword);
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int idHoaDon = rs.getInt("id_hoa_don");
                int idNhanVien = rs.getInt("id_nhan_vien");
                int idChiNhanh = rs.getInt("id_chi_nhanh");
                int idThanhVien = rs.getInt("id_thanh_vien");
                double tongTien = rs.getDouble("tong_tien");
                double soTienNhan = rs.getDouble("so_tien_nhan");
                Date ngayCT = rs.getDate("ngay_CT");

                HoaDon hd = new HoaDon(idHoaDon, idNhanVien, idChiNhanh, idThanhVien, tongTien, soTienNhan, ngayCT);
                hoaDons.add(hd);
            }
        }

        return hoaDons;
    }

    public boolean addHoaDon(HoaDon hoaDon, List<ChiTietHoaDon> chiTietHoaDons) throws SQLException {

        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String query = "INSERT INTO HoaDon(id_hoa_don, id_nhan_vien, id_chi_nhanh, id_thanh_vien, tong_tien, so_tien_nhan, ngay_CT)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?)";


            PreparedStatement stm = conn.prepareCall(query);

            stm.setInt(1, hoaDon.getIdHoaDon());
            stm.setInt(2, hoaDon.getIdNhanVien());
            stm.setInt(3, hoaDon.getIdChiNhanh());
            stm.setInt(4, hoaDon.getIdThanhVien());
            stm.setDouble(5, hoaDon.getTongTien());
            stm.setDouble(6, hoaDon.getSoTienNhan());
            stm.setDate(7, hoaDon.getNgayCT());

            int row = stm.executeUpdate();
            if (row > 0) {
                query = "INSERT INTO ChiTietHoaDon(id_CTHD, id_san_pham, id_hoa_don, so_luong, thanh_tien)"
                        + " VALUES (?, ?, ?, ?, ?)";


                PreparedStatement stm1 = conn.prepareCall(query);

                Alert d = MessageBox.getBox("Question", "BUG" + query, Alert.AlertType.CONFIRMATION);
                d.show();

                for (ChiTietHoaDon cthd : chiTietHoaDons) {
                    stm1.setInt(1, cthd.getIdCTHD());
                    stm1.setInt(2, cthd.getIdSanPham());
                    stm1.setInt(3, cthd.getIdHoaDon());
                    stm1.setDouble(4, cthd.getSoLuong());
                    stm1.setDouble(5, cthd.getThanhTien());
                    stm1.executeUpdate();
                }
            }

            try {
                conn.commit();
                return true;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                return false;
            }

        }
    }
}
