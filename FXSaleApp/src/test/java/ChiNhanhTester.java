/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.tuantran.pojo.ChiNhanh;
import com.tuantran.services.ChiNhanhService;
import com.tuantran.services.JdbcUtils;
import java.sql.Connection;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TuanTran
 */
public class ChiNhanhTester {

    private static Connection conn;
    private static ChiNhanhService chiNhanhService;

    @BeforeAll
    public static void beforeAll() {
        System.err.println("BEFOREALL");
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(ChiNhanhTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        chiNhanhService = new ChiNhanhService();
    }

    @AfterAll
    public static void afterAll() {
        System.err.println("AFTERALL");
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChiNhanhTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testAddSuccessful() throws SQLException {
        List<ChiNhanh> listChiNhanh = chiNhanhService.getChiNhanhs(null, null);
        int idChiNhanh = listChiNhanh.get(listChiNhanh.size() - 1).getIdChiNhanh() + 1;
        String diaChi = "Chi Nhánh UnitTest " + idChiNhanh;
        ChiNhanh chiNhanh = new ChiNhanh(idChiNhanh, diaChi);

        try {
            boolean check = chiNhanhService.addChiNhanh(chiNhanh);
            Assertions.assertTrue(check);

            String query = "SELECT * FROM ChiNhanh WHERE id_chi_nhanh = ?";

            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(chiNhanh.getIdChiNhanh()));

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());

            Assertions.assertEquals(diaChi, rs.getString("dia_chi"));

            chiNhanhService.deleteChiNhanh(Integer.toString(idChiNhanh));

        } catch (SQLException ex) {
            Logger.getLogger(ChiNhanhTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testSearchChiNhanh_byDiaChi() throws SQLException {
//        String keyword_diaChi = "PHÃš NHUáº¬N";
        String keyword_diaChi = "USA";

        List<ChiNhanh> chiNhanhs = chiNhanhService.getChiNhanhs(null, keyword_diaChi);

        System.err.println("size cua List = " + chiNhanhs.size());
        Assertions.assertEquals(3, chiNhanhs.size());

        for (ChiNhanh chiNhanh : chiNhanhs) {
            System.err.println(chiNhanh.getDiaChi());
            Assertions.assertTrue(chiNhanh.getDiaChi().contains(keyword_diaChi));
        }
    }

    @Test
    public void testSearchChiNhanh_byId() throws SQLException {
        String keyword_id = "6";
        List<ChiNhanh> chiNhanhs = chiNhanhService.getChiNhanhs(keyword_id, null);

        System.err.println("size cua List = " + chiNhanhs.size());
        Assertions.assertEquals(1, chiNhanhs.size());
        System.err.println("id cua ChiNhanh = " + chiNhanhs.get(0).getIdChiNhanh());
        Assertions.assertTrue(chiNhanhs.get(0).getIdChiNhanh() == Integer.parseInt(keyword_id));
    }

    @Test
    public void testDelete() throws SQLException {
        List<ChiNhanh> listChiNhanh = chiNhanhService.getChiNhanhs(null, null);
        String keyword_id = Integer.toString(listChiNhanh.get(listChiNhanh.size() - 1).getIdChiNhanh() + 1);

        chiNhanhService.addChiNhanh(new ChiNhanh(Integer.parseInt(keyword_id), "Địa Chỉ UnitTest"));

        boolean check;
        try {
            check = chiNhanhService.deleteChiNhanh(keyword_id);
            Assertions.assertTrue(check);

            String query = "SELECT * FROM ChiNhanh WHERE id_chi_nhanh = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, keyword_id);
            ResultSet rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(ChiNhanhTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testUpdateSuccessful() throws SQLException {
        List<ChiNhanh> listChiNhanh = chiNhanhService.getChiNhanhs(null, null);
        String keyword_id = Integer.toString(listChiNhanh.get(listChiNhanh.size() - 1).getIdChiNhanh() + 1);
        chiNhanhService.addChiNhanh(new ChiNhanh(Integer.parseInt(keyword_id), "Địa Chỉ UnitTest"));
        String tenChiNhanhNew = "Địa Chỉ UnitTest By Tuấn Trần";
        boolean check;
        try {
            check = chiNhanhService.updateChiNhanh(keyword_id, tenChiNhanhNew);
            Assertions.assertTrue(check);

            String query = "SELECT * FROM ChiNhanh WHERE id_chi_nhanh = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, keyword_id);
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            System.err.println("Dia chi moi = " + rs.getString("dia_chi"));
            Assertions.assertEquals(tenChiNhanhNew, rs.getString("dia_chi"));

            chiNhanhService.deleteChiNhanh(keyword_id);

        } catch (SQLException ex) {
            Logger.getLogger(ChiNhanhTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
