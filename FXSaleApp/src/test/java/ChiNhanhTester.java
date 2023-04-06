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

//    ChiNhanhService chiNhanhService;
//    Connection conn;
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
    public void testAddSuccessfull() throws SQLException {
        List<ChiNhanh> listChiNhanh = chiNhanhService.getChiNhanhs(null, null);
        int idChiNhanh = listChiNhanh.get(listChiNhanh.size() - 1).getIdChiNhanh() + 1;
        String diaChi = "DƯƠNG HỮU THÀNH";
        ChiNhanh chiNhanh = new ChiNhanh(idChiNhanh, diaChi);

//        boolean check = true;
//        Assertions.assertTrue(check);
        try {
            boolean check = chiNhanhService.addChiNhanh(chiNhanh);
            Assertions.assertTrue(check);

            String query = "SELECT * FROM ChiNhanh WHERE id_chi_nhanh = ?";

            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(chiNhanh.getIdChiNhanh()));

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());

            Assertions.assertEquals(diaChi, rs.getString("dia_chi"));

        } catch (SQLException ex) {
            Logger.getLogger(ChiNhanhTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetChiNhanh() throws SQLException {
        String keyword_id = "5";
//        String keyword_diaChi = "PHÃš NHUáº¬N";
        String keyword_diaChi = "TEST PhÃº Nhuáº­n";
        List<ChiNhanh> chiNhanhs_byId = chiNhanhService.getChiNhanhs(keyword_id, null);
        List<ChiNhanh> chiNhanhs_byDiaChi = chiNhanhService.getChiNhanhs(null, keyword_diaChi);

//        System.err.println(chiNhanhs_byId.get(0).getIdChiNhanh());
//        Assertions.assertTrue(chiNhanhs_byId.get(0).getIdChiNhanh() == Integer.parseInt(keyword_id));
        
        for (ChiNhanh cn : chiNhanhs_byDiaChi) {
            System.err.println(cn.getDiaChi());
            Assertions.assertTrue(cn.getDiaChi().contains(keyword_diaChi));
        }
    }
}
