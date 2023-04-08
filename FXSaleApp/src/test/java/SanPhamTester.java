
import com.tuantran.pojo.SanPham;
import com.tuantran.services.JdbcUtils;
import com.tuantran.services.SanPhamService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class SanPhamTester {
     private static Connection conn;
    private static SanPhamService sanPhamService;
    
    @BeforeAll
    public static void beforeAll() {
        System.err.println("BEFOREALL");
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sanPhamService = new SanPhamService();
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
        List<SanPham> listSanPham = sanPhamService.getSanPham(null, null, null, null, null, null);
        int idSanPham = listSanPham.get(listSanPham.size() - 1).getIdSanPham()+ 1;
        String tenSanPham = "Duong Huu Thanh";
        double gia = 999999;
        String donVi = "Human";
        int idKhuyenMai = 1;
        int idChiNhanh = 1;
        
        SanPham sanPham = new SanPham(idSanPham, tenSanPham, gia, donVi, idKhuyenMai, idChiNhanh);
        
        try {
            boolean check = sanPhamService.addSanPham(sanPham);
            Assertions.assertTrue(check);
            
            String query = "SELECT * FROM SanPham WHERE id_san_pham = ?";
            
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(sanPham.getIdSanPham()));
            
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            
            System.err.println("Ma san pham moi = " + rs.getString("id_san_pham"));
            System.err.println("Ten san pham moi = " + rs.getString("ten_san_pham"));
            System.err.println("Gia moi = " + rs.getString("gia"));
            System.err.println("Don vi moi = " + rs.getString("don_vi"));
            System.err.println("Ma chi nhanh moi = " + rs.getString("id_chi_nhanh"));
            System.err.println("Ma khuyen mai moi = " + rs.getString("id_khuyen_mai"));
            
            Assertions.assertEquals(idSanPham, rs.getInt("id_san_pham"));
            Assertions.assertEquals(tenSanPham, rs.getString("ten_san_pham"));
            Assertions.assertEquals(gia, rs.getDouble("gia"));
            Assertions.assertEquals(donVi, rs.getString("don_vi"));
            Assertions.assertEquals(idChiNhanh, rs.getInt("id_chi_nhanh"));
            Assertions.assertEquals(idKhuyenMai, rs.getInt("id_khuyen_mai"));
            
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testSearchSanPham_byId() throws SQLException {
        String keyword_id = "5";
        List<SanPham> sanPhams = sanPhamService.getSanPham(keyword_id, null, null, null, null, null);
        
        System.err.println("size cua List = " + sanPhams.size());
        Assertions.assertEquals(1, sanPhams.size());
        System.err.println("id cua San Pham = " + sanPhams.get(0).getIdSanPham());
        Assertions.assertTrue(sanPhams.get(0).getIdSanPham()== Integer.parseInt(keyword_id));
    }
    
    @Test
    public void testSearchSanPham_byTenSanPham() throws SQLException {
        String keyword_tenSanPham = "Duong";
        
        List<SanPham> sanPhams = sanPhamService.getSanPham(null, keyword_tenSanPham, null, null, null, null);
        
        System.err.println("size cua List = " + sanPhams.size());
        Assertions.assertEquals(2, sanPhams.size());
        
        for (SanPham sanPham : sanPhams) {
            System.err.println(sanPham.getTenSanPham());
            Assertions.assertTrue(sanPham.getTenSanPham().contains(keyword_tenSanPham));
        }
    }
    
    @Test
    public void testSearchSanPham_byGia() throws SQLException {
        String keyword_gia= "12";
        
        List<SanPham> sanPhams = sanPhamService.getSanPham(null, null, keyword_gia, null, null, null);
        
        System.err.println("size cua List = " + sanPhams.size());
        Assertions.assertEquals(4, sanPhams.size());
        
        for (SanPham sanPham : sanPhams) {
            System.err.println(sanPham.getGia());
            System.err.println(Double.toString(sanPham.getGia()).contains(keyword_gia));
            Assertions.assertTrue(Double.toString(sanPham.getGia()).contains(keyword_gia));
        }
    }
    
    @Test
    public void testSearchSanPham_byDonVi() throws SQLException {
        String keyword_donVi = "kg";
        
        List<SanPham> sanPhams = sanPhamService.getSanPham(null, null, null, keyword_donVi, null, null);
        
        System.err.println("size cua List = " + sanPhams.size());
        Assertions.assertEquals(2, sanPhams.size());
        
        for (SanPham sanPham : sanPhams) {
            System.err.println(sanPham.getDonVi());
            Assertions.assertTrue(sanPham.getDonVi().contains(keyword_donVi));
        }
    }
    
    @Test
    public void testSearchSanPham_byIdKhuyenMai() throws SQLException {
        String keyword_idKhuyenMai = "1";
        
       List<SanPham> sanPhams = sanPhamService.getSanPham(null, null, null, null, null, keyword_idKhuyenMai);
        
        System.err.println("size cua List = " + sanPhams.size());
        Assertions.assertEquals(4, sanPhams.size());
        
        for (SanPham sanPham : sanPhams) {
            System.err.println(sanPham.getIdKhuyenMai());
            Assertions.assertTrue(Integer.toString(sanPham.getIdKhuyenMai()).contains(keyword_idKhuyenMai));
        }
    }
    
    @Test
    public void testSearchSanPham_byIdChiNhanh() throws SQLException {
        String keyword_idChiNhanh = "1";
        
        List<SanPham> sanPhams = sanPhamService.getSanPham(null, null, null, null, keyword_idChiNhanh, null);
        
        System.err.println("size cua List = " + sanPhams.size());
        Assertions.assertEquals(5, sanPhams.size());
        
        for (SanPham sanPham : sanPhams) {
            System.err.println(sanPham.getIdChiNhanh());
            Assertions.assertTrue(Integer.toString(sanPham.getIdChiNhanh()).contains(keyword_idChiNhanh));
        }
    }
    
    @Test
    public void testDelete() {
        String keyword_id = "17";
        boolean check;
        try {
            check = sanPhamService.deleteSanPham(keyword_id);
            Assertions.assertTrue(check);
            
            String query = "SELECT * FROM SanPham WHERE id_san_pham = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, keyword_id);
            ResultSet rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testUpdateSuccessful() {
        int idSanPham = 19;
        String tenSanPham = "Tran Dang Tuan";
        double gia = 8888;
        String donVi = "human";
        int idChiNhanh = 1;
        int idKhuyenMai = 0;
        
        boolean check;
        try {
            check = sanPhamService.updateSanPham(Integer.toString(idSanPham), tenSanPham, gia, donVi, idKhuyenMai, idChiNhanh);
            Assertions.assertTrue(check);
            
            String query = "SELECT * FROM SanPham WHERE id_san_pham = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, Integer.toString(idSanPham));
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            
            System.err.println("Ma san pham moi = " + rs.getString("id_san_pham"));
            System.err.println("Ten san pham moi = " + rs.getString("ten_san_pham"));
            System.err.println("Gia moi = " + rs.getString("gia"));
            System.err.println("Don vi moi = " + rs.getString("don_vi"));
            System.err.println("Ma chi nhanh moi = " + rs.getString("id_chi_nhanh"));
            System.err.println("Ma khuyen mai moi = " + rs.getString("id_khuyen_mai"));
            
            Assertions.assertEquals(idSanPham, rs.getInt("id_san_pham"));
            Assertions.assertEquals(tenSanPham, rs.getString("ten_san_pham"));
            Assertions.assertEquals(gia, rs.getDouble("gia"));
            Assertions.assertEquals(donVi, rs.getString("don_vi"));
            Assertions.assertEquals(idChiNhanh, rs.getInt("id_chi_nhanh"));
            Assertions.assertEquals(idKhuyenMai, rs.getInt("id_khuyen_mai"));
            
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
