/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.tuantran.pojo.ThanhVien;
import com.tuantran.services.JdbcUtils;
import com.tuantran.services.ThanhVienService;
import java.sql.Connection;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TuanTran
 */
public class ThanhVienTester {

    private static Connection conn;
    private static ThanhVienService thanhVienService;

    @BeforeAll
    public static void beforeAll() {
        System.err.println("BEFOREALL");
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(ThanhVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        thanhVienService = new ThanhVienService();
    }

    @AfterAll
    public static void afterAll() {
        System.err.println("AFTERALL");
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThanhVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testAddSuccessful() throws SQLException {
        List<ThanhVien> listThanhVien = thanhVienService.getThanhViens(null, null, null, null);
        int idThanhVien = listThanhVien.get(listThanhVien.size() - 1).getIdThanhVien() + 1;
        String hoThanhVien = "Tran Dang " + idThanhVien;
        String tenThanhVien = "Tuan UnitTest " + idThanhVien;
        Date ngaySinh = Date.valueOf(LocalDate.now());
        String soDienThoai = "2051050549" + idThanhVien;

        ThanhVien thanhVien = new ThanhVien(idThanhVien, hoThanhVien, tenThanhVien, ngaySinh, soDienThoai);

        try {
            boolean check = thanhVienService.addThanhVien(thanhVien);
            Assertions.assertTrue(check);

            String query = "SELECT * FROM ThanhVien WHERE id_thanh_vien = ?";

            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(thanhVien.getIdThanhVien()));

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());

            System.err.println("Ma thanh vien moi = " + rs.getString("id_thanh_vien"));
            System.err.println("Ho thanh vien moi = " + rs.getString("ho_thanh_vien"));
            System.err.println("Ten thanh vien moi = " + rs.getString("ten_thanh_vien"));
            System.err.println("Ngay sinh thanh vien moi = " + rs.getDate("ngay_sinh_thanh_vien"));
            System.err.println("So dien thoai moi = " + rs.getString("so_dien_thoai"));

            Assertions.assertEquals(idThanhVien, rs.getInt("id_thanh_vien"));
            Assertions.assertEquals(hoThanhVien, rs.getString("ho_thanh_vien"));
            Assertions.assertEquals(tenThanhVien, rs.getString("ten_thanh_vien"));
            Assertions.assertEquals(ngaySinh, rs.getDate("ngay_sinh_thanh_vien"));
            Assertions.assertEquals(soDienThoai, rs.getString("so_dien_thoai"));

            thanhVienService.deleteThanhVien(Integer.toString(idThanhVien));

        } catch (SQLException ex) {
            Logger.getLogger(ThanhVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testSearchThanhVien_byId() throws SQLException {
        String keyword_id = "5";
        List<ThanhVien> thanhViens = thanhVienService.getThanhViens(keyword_id, null, null, null);

        System.err.println("size cua List = " + thanhViens.size());
        Assertions.assertEquals(1, thanhViens.size());
        System.err.println("id cua Thanh Vien = " + thanhViens.get(0).getIdThanhVien());
        Assertions.assertTrue(thanhViens.get(0).getIdThanhVien() == Integer.parseInt(keyword_id));
    }

    @Test
    public void testSearchThanhVien_byHoThanhVien() throws SQLException {
        String keyword_hoThanhVien = "Raiden";

        List<ThanhVien> thanhViens = thanhVienService.getThanhViens(null, keyword_hoThanhVien, null, null);

        System.err.println("size cua List = " + thanhViens.size());
        Assertions.assertEquals(2, thanhViens.size());

        for (ThanhVien thanhVien : thanhViens) {
            System.err.println(thanhVien.getHoThanhVien());
            Assertions.assertTrue(thanhVien.getHoThanhVien().contains(keyword_hoThanhVien));
        }
    }

    @Test
    public void testSearchThanhVien_byTenThanhVien() throws SQLException {
        String keyword_tenThanhVien = "Go";

        List<ThanhVien> thanhViens = thanhVienService.getThanhViens(null, null, keyword_tenThanhVien, null);

        System.err.println("size cua List = " + thanhViens.size());
        Assertions.assertEquals(3, thanhViens.size());

        for (ThanhVien thanhVien : thanhViens) {
            System.err.println(thanhVien.getTenThanhVien());
            Assertions.assertTrue(thanhVien.getTenThanhVien().contains(keyword_tenThanhVien));
        }
    }

    @Test
    public void testSearchThanhVien_bySoDienThoai() throws SQLException {
        String keyword_soDienThoai = "2051";

        List<ThanhVien> thanhViens = thanhVienService.getThanhViens(null, null, null, keyword_soDienThoai);

        System.err.println("size cua List = " + thanhViens.size());
        Assertions.assertEquals(2, thanhViens.size());

        for (ThanhVien thanhVien : thanhViens) {
            System.err.println(thanhVien.getSoDienThoai());
            Assertions.assertTrue(thanhVien.getSoDienThoai().contains(keyword_soDienThoai));
        }
    }

    @Test
    public void testDelete() throws SQLException {
        List<ThanhVien> listThanhVien = thanhVienService.getThanhViens(null, null, null, null);
        int idThanhVien = listThanhVien.get(listThanhVien.size() - 1).getIdThanhVien() + 1;
        String hoThanhVien = "Tran Dang " + idThanhVien;
        String tenThanhVien = "Tuan UnitTest " + idThanhVien;
        Date ngaySinh = Date.valueOf(LocalDate.now());
        String soDienThoai = "2051050549" + idThanhVien;

        ThanhVien thanhVien = new ThanhVien(idThanhVien, hoThanhVien, tenThanhVien, ngaySinh, soDienThoai);
        thanhVienService.addThanhVien(thanhVien);
        boolean check;
        try {
            check = thanhVienService.deleteThanhVien(Integer.toString(idThanhVien));
            Assertions.assertTrue(check);

            String query = "SELECT * FROM ThanhVien WHERE id_thanh_vien = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(idThanhVien));
            ResultSet rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(ThanhVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void updateSuccessful() throws SQLException {
        List<ThanhVien> listThanhVien = thanhVienService.getThanhViens(null, null, null, null);
        int idThanhVien = listThanhVien.get(listThanhVien.size() - 1).getIdThanhVien() + 1;
        String hoThanhVien = "Tran Dang " + idThanhVien;
        String tenThanhVien = "Tuan UnitTest " + idThanhVien;

        Date ngaySinh = Date.valueOf(LocalDate.now().plusDays(10));
        String soDienThoai = idThanhVien + "2051050549";
        
        thanhVienService.addThanhVien(new ThanhVien(idThanhVien, hoThanhVien, tenThanhVien, ngaySinh, soDienThoai));

        boolean check;
        try {
            check = thanhVienService.updateThanhVien(Integer.toString(idThanhVien), hoThanhVien, tenThanhVien, ngaySinh, soDienThoai);
            Assertions.assertTrue(check);

            String query = "SELECT * FROM ThanhVien WHERE id_thanh_vien = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(idThanhVien));
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());

            System.err.println("Ma thanh vien moi = " + rs.getString("id_thanh_vien"));
            System.err.println("Ho thanh vien moi = " + rs.getString("ho_thanh_vien"));
            System.err.println("Ten thanh vien moi = " + rs.getString("ten_thanh_vien"));
            System.err.println("Ngay sinh thanh vien moi = " + rs.getDate("ngay_sinh_thanh_vien"));
            System.err.println("So dien thoai moi = " + rs.getString("so_dien_thoai"));

            Assertions.assertEquals(idThanhVien, rs.getInt("id_thanh_vien"));
            Assertions.assertEquals(hoThanhVien, rs.getString("ho_thanh_vien"));
            Assertions.assertEquals(tenThanhVien, rs.getString("ten_thanh_vien"));
            Assertions.assertEquals(ngaySinh, rs.getDate("ngay_sinh_thanh_vien"));
            Assertions.assertEquals(soDienThoai, rs.getString("so_dien_thoai"));

        } catch (SQLException ex) {
            Logger.getLogger(ThanhVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
