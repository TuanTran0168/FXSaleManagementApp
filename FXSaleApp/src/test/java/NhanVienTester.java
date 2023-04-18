
import com.tuantran.pojo.NhanVien;
import com.tuantran.services.JdbcUtils;
import com.tuantran.services.NhanVienService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author TuanTran
 */
public class NhanVienTester {

    private static Connection conn;
    private static NhanVienService nhanVienService;

    @BeforeAll
    public static void beforeAll() {
        System.err.println("BEFOREALL");
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        nhanVienService = new NhanVienService();
    }

    @AfterAll
    public static void afterAll() {
        System.err.println("AFTERALL");
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testAddSuccessful() throws SQLException {
        List<NhanVien> listNhanVien = nhanVienService.getNhanViens(null, null, null, null, null, null);
        int idNhanVien = listNhanVien.get(listNhanVien.size() - 1).getIdNhanVien() + 1;
        String hoNhanVien = "Duong Huu " + idNhanVien;
        String tenNhanVien = "Thanh " + idNhanVien;
        int idChiNhanh = 1;
        String taiKhoan = "thanh" + idNhanVien;
        String matKhau = "1";
        boolean quanLy = false;

        NhanVien nhanVien = new NhanVien(idNhanVien, hoNhanVien, tenNhanVien, idChiNhanh, taiKhoan, matKhau, quanLy);

        try {
            boolean check = nhanVienService.addNhanVien(nhanVien);
            Assertions.assertTrue(check);

            String query = "SELECT * FROM NhanVien WHERE id_nhan_vien = ?";

            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(nhanVien.getIdNhanVien()));

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());

            System.err.println("Ma nhan vien moi = " + rs.getString("id_nhan_vien"));
            System.err.println("Ho nhan vien moi = " + rs.getString("ho_nhan_vien"));
            System.err.println("Ten nhan vien moi = " + rs.getString("ten_nhan_vien"));
            System.err.println("Ma chi nhanh moi = " + rs.getString("id_chi_nhanh"));
            System.err.println("Tai moi = " + rs.getString("tai_khoan"));
            System.err.println("Mat khau moi = " + rs.getString("mat_khau"));
            System.err.println("Quan ly moi = " + rs.getString("quan_ly"));

            Assertions.assertEquals(idNhanVien, rs.getInt("id_nhan_vien"));
            Assertions.assertEquals(hoNhanVien, rs.getString("ho_nhan_vien"));
            Assertions.assertEquals(tenNhanVien, rs.getString("ten_nhan_vien"));
            Assertions.assertEquals(idChiNhanh, rs.getInt("id_chi_nhanh"));
            Assertions.assertEquals(taiKhoan, rs.getString("tai_khoan"));
            Assertions.assertEquals(matKhau, rs.getString("mat_khau"));
            Assertions.assertEquals(quanLy, rs.getBoolean("quan_ly"));

            nhanVienService.deleteNhanVien(Integer.toString(idNhanVien));

        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testSearchNhanVien_byId() throws SQLException {
        String keyword_id = "5";
        List<NhanVien> nhanViens = nhanVienService.getNhanViens(keyword_id, null, null, null, null, null);

        System.err.println("size cua List = " + nhanViens.size());
        Assertions.assertEquals(1, nhanViens.size());
        System.err.println("id cua Nhan Vien = " + nhanViens.get(0).getIdNhanVien());
        Assertions.assertTrue(nhanViens.get(0).getIdNhanVien() == Integer.parseInt(keyword_id));
    }

    @Test
    public void testSearchNhanVien_byHoNhanVien() throws SQLException {
        String keyword_hoNhanVien = "Kamisato";

        List<NhanVien> nhanViens = nhanVienService.getNhanViens(null, keyword_hoNhanVien, null, null, null, null);

        System.err.println("size cua List = " + nhanViens.size());
        Assertions.assertEquals(3, nhanViens.size());

        for (NhanVien nhanVien : nhanViens) {
            System.err.println(nhanVien.getHoNhanVien());
            Assertions.assertTrue(nhanVien.getHoNhanVien().contains(keyword_hoNhanVien));
        }
    }

    @Test
    public void testSearchNhanVien_byTenNhanVien() throws SQLException {
        String keyword_tenNhanVien = "Aya";

        List<NhanVien> nhanViens = nhanVienService.getNhanViens(null, null, keyword_tenNhanVien, null, null, null);

        System.err.println("size cua List = " + nhanViens.size());
        Assertions.assertEquals(2, nhanViens.size());

        for (NhanVien nhanVien : nhanViens) {
            System.err.println(nhanVien.getTenNhanVien());
            System.err.println(nhanVien.getTenNhanVien().contains(keyword_tenNhanVien));
            Assertions.assertTrue(nhanVien.getTenNhanVien().contains(keyword_tenNhanVien));
        }
    }

    @Test
    public void testSearchNhanVien_byTaiKhoan() throws SQLException {
        String keyword_taiKhoan = "a";

        List<NhanVien> nhanViens = nhanVienService.getNhanViens(null, null, null, keyword_taiKhoan, null, null);

        System.err.println("size cua List = " + nhanViens.size());
        Assertions.assertEquals(10, nhanViens.size());

        for (NhanVien nhanVien : nhanViens) {
            System.err.println(nhanVien.getTaiKhoan());
            Assertions.assertTrue(nhanVien.getTaiKhoan().contains(keyword_taiKhoan));
        }
    }

    @Test
    public void testSearchNhanVien_byMatKhau() throws SQLException {
        String keyword_matKhau = "1";

        List<NhanVien> nhanViens = nhanVienService.getNhanViens(null, null, null, null, keyword_matKhau, null);

        System.err.println("size cua List = " + nhanViens.size());
        Assertions.assertEquals(18, nhanViens.size());

        for (NhanVien nhanVien : nhanViens) {
            System.err.println(nhanVien.getMatKhau());
            Assertions.assertTrue(nhanVien.getMatKhau().contains(keyword_matKhau));
        }
    }

    @Test
    public void testSearchNhanVien_byIdChiNhanh() throws SQLException {
        String keyword_idChiNhanh = "2";

        List<NhanVien> nhanViens = nhanVienService.getNhanViens(null, null, null, null, null, keyword_idChiNhanh);

        System.err.println("size cua List = " + nhanViens.size());
        Assertions.assertEquals(3, nhanViens.size());

        for (NhanVien nhanVien : nhanViens) {
            System.err.println(nhanVien.getIdChiNhanh());
            Assertions.assertTrue(Integer.toString(nhanVien.getIdChiNhanh()).contains(keyword_idChiNhanh));
        }
    }

    @Test
    public void testDelete() throws SQLException {
        List<NhanVien> listNhanVien = nhanVienService.getNhanViens(null, null, null, null, null, null);
        int idNhanVien = listNhanVien.get(listNhanVien.size() - 1).getIdNhanVien() + 1;
        String hoNhanVien = "Duong Huu " + idNhanVien;
        String tenNhanVien = "Thanh " + idNhanVien;
        int idChiNhanh = 1;
        String taiKhoan = "thanh" + idNhanVien;
        String matKhau = "1";
        boolean quanLy = false;

        NhanVien nhanVien = new NhanVien(idNhanVien, hoNhanVien, tenNhanVien, idChiNhanh, taiKhoan, matKhau, quanLy);
        nhanVienService.addNhanVien(nhanVien);
        boolean check;
        try {
            check = nhanVienService.deleteNhanVien(Integer.toString(idNhanVien));
            Assertions.assertTrue(check);

            String query = "SELECT * FROM NhanVien WHERE id_nhan_vien = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(idNhanVien));
            ResultSet rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testUpdateSuccessful() throws SQLException {
        List<NhanVien> listNhanVien = nhanVienService.getNhanViens(null, null, null, null, null, null);
        int idNhanVien = listNhanVien.get(listNhanVien.size() - 1).getIdNhanVien() + 1;
        
        
        
        String hoNhanVien = "Duong Huu " + idNhanVien;
        String tenNhanVien = "Thanh" + idNhanVien;
        int idChiNhanh = 1;
        String taiKhoan = "thanh" + idNhanVien;
        String matKhau = "1";
        boolean quanLy = false;
        nhanVienService.addNhanVien(new NhanVien(idNhanVien, "Tran Dang ", "Tuan UnitTest", idChiNhanh, taiKhoan, matKhau, true));

        boolean check;
        try {
            check = nhanVienService.updateNhanVien(Integer.toString(idNhanVien), hoNhanVien, tenNhanVien, idChiNhanh, taiKhoan, matKhau, quanLy);
            Assertions.assertTrue(check);

            String query = "SELECT * FROM NhanVien WHERE id_nhan_vien = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(idNhanVien));
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());

            System.err.println("Ma nhan vien moi = " + rs.getString("id_nhan_vien"));
            System.err.println("Ho nhan vien moi = " + rs.getString("ho_nhan_vien"));
            System.err.println("Ten nhan vien moi = " + rs.getString("ten_nhan_vien"));
            System.err.println("Ma chi nhanh moi = " + rs.getString("id_chi_nhanh"));
            System.err.println("Tai khoan moi = " + rs.getString("tai_khoan"));
            System.err.println("Mat khau moi = " + rs.getString("mat_khau"));
            System.err.println("Quan ly moi = " + rs.getString("quan_ly"));

            Assertions.assertEquals(idNhanVien, rs.getInt("id_nhan_vien"));
            Assertions.assertEquals(hoNhanVien, rs.getString("ho_nhan_vien"));
            Assertions.assertEquals(tenNhanVien, rs.getString("ten_nhan_vien"));
            Assertions.assertEquals(idChiNhanh, rs.getInt("id_chi_nhanh"));
            Assertions.assertEquals(taiKhoan, rs.getString("tai_khoan"));
            Assertions.assertEquals(matKhau, rs.getString("mat_khau"));
            Assertions.assertEquals(quanLy, rs.getBoolean("quan_ly"));

        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
