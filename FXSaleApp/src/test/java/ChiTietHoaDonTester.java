
import com.tuantran.pojo.ChiTietHoaDon;
import com.tuantran.services.ChiTietHoaDonService;
import com.tuantran.services.JdbcUtils;
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
public class ChiTietHoaDonTester {

    private static Connection conn;
    private static ChiTietHoaDonService chiTietHoaDonService;

    @BeforeAll
    public static void beforeAll() {
        System.err.println("BEFOREALL");
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(ThanhVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        chiTietHoaDonService = new ChiTietHoaDonService();
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
        List<ChiTietHoaDon> listChiTietHoaDon = chiTietHoaDonService.getChiTietHoaDon(null);
        int idChiTietHoaDon = listChiTietHoaDon.get(listChiTietHoaDon.size() - 1).getIdCTHD() + 1;
        int idSanPham = 1;
        int idHoaDon = 1;
        int soLuong = 2;
        double thanhTien = 999999;

//        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(idChiTietHoaDon, idSanPham, idHoaDon, soLuong, thanhTien);
        try {
            boolean check;
            String query = "INSERT INTO ChiTietHoaDon(id_CTHD, id_san_pham, id_hoa_don, so_luong, thanh_tien)"
                    + " VALUES (?, ?, ?, ?, ?)";

            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareCall(query);

            stm.setInt(1, idChiTietHoaDon);
            stm.setInt(2, idSanPham);
            stm.setInt(3, idHoaDon);
            stm.setInt(4, soLuong);
            stm.setDouble(5, thanhTien);
            stm.executeUpdate();

            try {
                conn.commit();
                check = true;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                check = false;
            }

            Assertions.assertTrue(check);

            String query1 = "SELECT * FROM ChiTietHoaDon WHERE id_CTHD = ?";

            PreparedStatement stm1 = conn.prepareCall(query1);
            stm1.setString(1, Integer.toString(idChiTietHoaDon));

            ResultSet rs = stm1.executeQuery();
            Assertions.assertNotNull(rs.next());

            System.err.println("Ma CTHD moi = " + rs.getString("id_CTHD"));
            System.err.println("Ma san pham moi = " + rs.getString("id_san_pham"));
            System.err.println("Ma hoa don moi = " + rs.getString("id_hoa_don"));
            System.err.println("So luong moi = " + rs.getInt("so_luong"));
            System.err.println("Thanh tien moi = " + rs.getString("thanh_tien"));

            Assertions.assertEquals(idChiTietHoaDon, rs.getInt("id_CTHD"));
            Assertions.assertEquals(idSanPham, rs.getInt("id_san_pham"));
            Assertions.assertEquals(idHoaDon, rs.getInt("id_hoa_don"));
            Assertions.assertEquals(soLuong, rs.getInt("so_luong"));
            Assertions.assertEquals(thanhTien, rs.getDouble("thanh_tien"));

        } catch (SQLException ex) {
            Logger.getLogger(ThanhVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
