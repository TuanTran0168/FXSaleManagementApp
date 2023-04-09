
import com.tuantran.pojo.ChiTietHoaDon;
import com.tuantran.pojo.HoaDon;
import com.tuantran.pojo.SanPham;
import com.tuantran.services.ChiTietHoaDonService;
import com.tuantran.services.HoaDonService;
import com.tuantran.services.JdbcUtils;
import com.tuantran.services.SanPhamService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class HoaDonTester {

    private static Connection conn;
    private static HoaDonService hoaDonService;
    private static SanPhamService sanPhamService;
    private static ChiTietHoaDonService chiTietHoaDonService;

    @BeforeAll
    public static void beforeAll() {
        System.err.println("BEFOREALL");
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(ThanhVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        hoaDonService = new HoaDonService();
        sanPhamService = new SanPhamService();
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
        List<HoaDon> listHoaDon = hoaDonService.getHoaDon(null);
        int idHoaDon = listHoaDon.get(listHoaDon.size() - 1).getIdHoaDon() + 1;
        int idNhanVien = 1;
        int idChiNhanh = 1;
        int idThanhVien = 1;
        double tongTien;
        int soTienNhan = 999999;
        Date ngayCT = Date.valueOf(LocalDate.now());

        List<SanPham> listSanPham = sanPhamService.getSanPham(null, null, null, null, null, null);

        int soLuong = 2;

        List<ChiTietHoaDon> l = chiTietHoaDonService.getChiTietHoaDon(null, null);
        int idChiTietHoaDon = l.get(l.size() - 1).getIdCTHD();
        List<ChiTietHoaDon> listChiTietHoaDon = new ArrayList<>();

        double tongTien1 = listSanPham.get(0).getGia() * soLuong;
        double tongTien2 = listSanPham.get(1).getGia() * soLuong;
        double tongTien3 = listSanPham.get(2).getGia() * soLuong;
        ChiTietHoaDon cthd1 = new ChiTietHoaDon(idChiTietHoaDon + 1, listSanPham.get(0).getIdSanPham(), idHoaDon, soLuong, tongTien1);
        ChiTietHoaDon cthd2 = new ChiTietHoaDon(idChiTietHoaDon + 2, listSanPham.get(1).getIdSanPham(), idHoaDon, soLuong, tongTien2);
        ChiTietHoaDon cthd3 = new ChiTietHoaDon(idChiTietHoaDon + 3, listSanPham.get(2).getIdSanPham(), idHoaDon, soLuong, tongTien3);

        listChiTietHoaDon.add(cthd1);
        listChiTietHoaDon.add(cthd2);
        listChiTietHoaDon.add(cthd3);

        tongTien = tongTien1 + tongTien2 + tongTien3;

        HoaDon hoaDon = new HoaDon(idHoaDon, idNhanVien, idChiNhanh, idThanhVien, tongTien, soTienNhan, ngayCT);

        try {
            boolean check = hoaDonService.addHoaDon(hoaDon, listChiTietHoaDon);
            Assertions.assertTrue(check);

            String query = "SELECT * FROM HoaDon WHERE id_hoa_don = ?";

            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(hoaDon.getIdHoaDon()));

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());

//            String queryCTHD = "SELECT * FROM ChiTietHoaDon WHERE id_CTHD = ? OR id_CTHD = ? OR id_CTHD = ?";
//            PreparedStatement stmCTHD = conn.prepareCall(queryCTHD);
//            stmCTHD.setString(1, Integer.toString(cthd1.getIdCTHD()));
//            stmCTHD.setString(2, Integer.toString(cthd2.getIdCTHD()));
//            stmCTHD.setString(3, Integer.toString(cthd3.getIdCTHD()));
            
//            ResultSet rsCTHD = stmCTHD.executeQuery();
//            Assertions.assertNotNull(rsCTHD.next());

            System.err.println("Ma hoa don moi = " + rs.getString("id_hoa_don"));
            System.err.println("Ma nhan vien moi = " + rs.getString("id_nhan_vien"));
            System.err.println("Ma chi nhanh moi = " + rs.getString("id_chi_nhanh"));
            System.err.println("Ma thanh vien moi = " + rs.getString("id_thanh_vien"));
            System.err.println("Tong tien moi = " + rs.getString("tong_tien"));
            System.err.println("So tien nhan moi = " + rs.getString("so_tien_nhan"));
            System.err.println("Ngay CT moi = " + rs.getDate("ngay_CT"));

            Assertions.assertEquals(idHoaDon, rs.getInt("id_hoa_don"));
            Assertions.assertEquals(idNhanVien, rs.getInt("id_nhan_vien"));
            Assertions.assertEquals(idChiNhanh, rs.getInt("id_chi_nhanh"));
            Assertions.assertEquals(idThanhVien, rs.getInt("id_thanh_vien"));
            Assertions.assertEquals(tongTien, rs.getDouble("tong_tien"));
            Assertions.assertEquals(soTienNhan, rs.getDouble("so_tien_nhan"));
            Assertions.assertEquals(ngayCT, rs.getDate("ngay_CT"));
            

        } catch (SQLException ex) {
            Logger.getLogger(ThanhVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testSearchHoaDon_byTongTien() throws SQLException {
        String keyword_tongTien = "20000";
        List<HoaDon> hoaDons = hoaDonService.getHoaDon(keyword_tongTien);
        hoaDons.sort((hd1, hd2) -> (int)(hd1.getTongTien() - hd2.getTongTien()) );

        System.err.println("size cua List = " + hoaDons.size());
        Assertions.assertEquals(21, hoaDons.size());
        
       for (HoaDon hoaDon : hoaDons) {
           System.err.println("Tong tien = " + hoaDon.getTongTien());
           Assertions.assertTrue(hoaDon.getTongTien() - Double.parseDouble(keyword_tongTien) >= 0);
       }
    }
}
