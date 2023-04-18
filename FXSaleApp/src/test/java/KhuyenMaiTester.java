/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.tuantran.pojo.KhuyenMai;
import com.tuantran.services.JdbcUtils;
import com.tuantran.services.KhuyenMaiService;
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
public class KhuyenMaiTester {

    private static Connection conn;
    private static KhuyenMaiService khuyenMaiService;

    @BeforeAll
    public static void beforeAll() {
        System.err.println("BEFOREALL");
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        khuyenMaiService = new KhuyenMaiService();
    }

    @AfterAll
    public static void afterAll() {
        System.err.println("AFTERALL");
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testAddSuccessful() throws SQLException {
        List<KhuyenMai> listKhuyenMai = khuyenMaiService.getKhuyenMai(null, null, null);
        int idKhuyenMai = listKhuyenMai.get(listKhuyenMai.size() - 1).getIdKhuyenMai() + 1;
        String tenKhuyenMai = "Khuyến Mãi UnitTest " + idKhuyenMai;
        double giaTri = 999999;
        Date ngayBatDau = Date.valueOf(LocalDate.now());
        Date ngayKetThuc = Date.valueOf(LocalDate.now().plusDays(1));

        KhuyenMai khuyenMai = new KhuyenMai(idKhuyenMai, tenKhuyenMai, giaTri, ngayBatDau, ngayKetThuc);

        try {
            boolean check = khuyenMaiService.addKhuyenMai(khuyenMai);
            Assertions.assertTrue(check);

            String query = "SELECT * FROM KhuyenMai WHERE id_khuyen_mai = ?";

            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(khuyenMai.getIdKhuyenMai()));

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());

            System.err.println("Ma khuyen mai moi = " + rs.getString("id_khuyen_mai"));
            System.err.println("Ten khuyen mai moi = " + rs.getString("ten_khuyen_mai"));
            System.err.println("Gia tri moi = " + rs.getDouble("gia_tri"));
            System.err.println("Ngay bat dau moi = " + rs.getDate("ngay_bat_dau"));
            System.err.println("Ngay ket thuc moi = " + rs.getDate("ngay_ket_thuc"));

            Assertions.assertEquals(idKhuyenMai, rs.getInt("id_khuyen_mai"));
            Assertions.assertEquals(tenKhuyenMai, rs.getString("ten_khuyen_mai"));
            Assertions.assertEquals(giaTri, rs.getDouble("gia_tri"));
            Assertions.assertEquals(ngayBatDau, rs.getDate("ngay_bat_dau"));
            Assertions.assertEquals(ngayKetThuc, rs.getDate("ngay_ket_thuc"));

            khuyenMaiService.deleteKhuyenMai(Integer.toString(idKhuyenMai));

        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testSearchKhuyenMai_byId() throws SQLException {
        String keyword_id = "5";
        List<KhuyenMai> khuyenMais = khuyenMaiService.getKhuyenMai(keyword_id, null, null);

        System.err.println("size cua List = " + khuyenMais.size());
        Assertions.assertEquals(1, khuyenMais.size());
        System.err.println("id cua Khuyen Mai = " + khuyenMais.get(0).getIdKhuyenMai());
        Assertions.assertTrue(khuyenMais.get(0).getIdKhuyenMai() == Integer.parseInt(keyword_id));
    }

    @Test
    public void testSearchKhuyenMai_byTenKhuyenMai() throws SQLException {
        String keyword_tenKhuyenMai = "View";

        List<KhuyenMai> khuyenMais = khuyenMaiService.getKhuyenMai(null, keyword_tenKhuyenMai, null);

        System.err.println("size cua List = " + khuyenMais.size());
        Assertions.assertEquals(3, khuyenMais.size());

        for (KhuyenMai khuyenMai : khuyenMais) {
            System.err.println(khuyenMai.getTenKhuyenMai());
            Assertions.assertTrue(khuyenMai.getTenKhuyenMai().contains(keyword_tenKhuyenMai));
        }
    }

    @Test
    public void testSearchKhuyenMai_byGiaTri() throws SQLException {
        String keyword_giaTri = "9999";

        List<KhuyenMai> khuyenMais = khuyenMaiService.getKhuyenMai(null, null, keyword_giaTri);

        System.err.println("size cua List = " + khuyenMais.size());
        Assertions.assertEquals(1, khuyenMais.size());

        for (KhuyenMai khuyenMai : khuyenMais) {
            System.err.println(khuyenMai.getGiaTri());
            Assertions.assertTrue(Double.toString(khuyenMai.getGiaTri()).contains(keyword_giaTri));
        }
    }

    @Test
    public void testDelete() throws SQLException {
        List<KhuyenMai> listKhuyenMai = khuyenMaiService.getKhuyenMai(null, null, null);
        int idKhuyenMai = listKhuyenMai.get(listKhuyenMai.size() - 1).getIdKhuyenMai() + 1;
        String tenKhuyenMai = "Khuyến Mãi UnitTest " + idKhuyenMai;
        double giaTri = 999999;
        Date ngayBatDau = Date.valueOf(LocalDate.now());
        Date ngayKetThuc = Date.valueOf(LocalDate.now().plusDays(1));

        KhuyenMai khuyenMai = new KhuyenMai(idKhuyenMai, tenKhuyenMai, giaTri, ngayBatDau, ngayKetThuc);
        khuyenMaiService.addKhuyenMai(khuyenMai);
        boolean check;
        try {
            check = khuyenMaiService.deleteKhuyenMai(Integer.toString(idKhuyenMai));
            Assertions.assertTrue(check);

            String query = "SELECT * FROM KhuyenMai WHERE id_khuyen_mai = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(idKhuyenMai));
            ResultSet rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void updateSuccessful() throws SQLException {
        List<KhuyenMai> listKhuyenMai = khuyenMaiService.getKhuyenMai(null, null, null);
        int idKhuyenMai = listKhuyenMai.get(listKhuyenMai.size() - 1).getIdKhuyenMai() + 1;
        String tenKhuyenMaiNew = "Khuyen Mai UnitTest By Tuan Tran " + idKhuyenMai;
        String giaTriNew = "8888888";
        Date ngayBatDau = Date.valueOf(LocalDate.now().plusDays(10));
        Date ngayKetThuc = Date.valueOf(LocalDate.now().plusDays(29));

        khuyenMaiService.addKhuyenMai(new KhuyenMai(idKhuyenMai, "FREE", idKhuyenMai, ngayBatDau, ngayKetThuc));

        boolean check;
        try {
            check = khuyenMaiService.updateKhuyenMai(Integer.toString(idKhuyenMai), tenKhuyenMaiNew, Double.parseDouble(giaTriNew), ngayBatDau, ngayKetThuc);
            Assertions.assertTrue(check);

            String query = "SELECT * FROM KhuyenMai WHERE id_khuyen_mai = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(idKhuyenMai));
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            System.err.println("Ten khuyen mai moi = " + rs.getString("ten_khuyen_mai"));
            System.err.println("Gia tri moi = " + rs.getDouble("gia_tri"));
            System.err.println("Ngay bat dau moi = " + rs.getDate("ngay_bat_dau"));
            System.err.println("Ngay ket thuc moi = " + rs.getDate("ngay_ket_thuc"));

            Assertions.assertEquals(tenKhuyenMaiNew, rs.getString("ten_khuyen_mai"));
            Assertions.assertEquals(Double.parseDouble(giaTriNew), rs.getDouble("gia_tri"));
            Assertions.assertEquals(ngayBatDau, rs.getDate("ngay_bat_dau"));
            Assertions.assertEquals(ngayKetThuc, rs.getDate("ngay_ket_thuc"));

            khuyenMaiService.deleteKhuyenMai(Integer.toString(idKhuyenMai));
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
