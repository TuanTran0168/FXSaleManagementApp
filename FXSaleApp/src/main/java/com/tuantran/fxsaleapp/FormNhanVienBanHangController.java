package com.tuantran.fxsaleapp;

import com.tuantran.pojo.*;
import com.tuantran.services.ChiNhanhService;
import com.tuantran.services.ChiTietHoaDonService;
import com.tuantran.services.HoaDonService;
import com.tuantran.services.KhuyenMaiService;
import com.tuantran.services.SanPhamService;
import com.tuantran.services.ThanhVienService;
import com.tuantran.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.DoubleStringConverter;

public class FormNhanVienBanHangController implements Initializable {

    private static final SanPhamService sanPhamService = new SanPhamService();
    private static final HoaDonService hoaDonService = new HoaDonService();
    private static final ChiNhanhService chiNhanhService = new ChiNhanhService();
    private static final ChiTietHoaDonService chiTietHoaDonService = new ChiTietHoaDonService();
    private static final ThanhVienService thanhVienService = new ThanhVienService();
    private static final KhuyenMaiService khuyenMaiService = new KhuyenMaiService();
    private final FormUtils FORM_UTILS = new FormUtils();
//    private final double SO_TIEN_GIAM_GIA_THEO_QUY_DINH = 1000000;
//    private final double PHAN_TRAM_DISCOUNT = 10 / (100 * 1.0);

    private List<SanPham> sanPhamDuocChon;
    private List<String> listSoLuongSanPhamDuocChon;

    private int count;
    private NhanVien nhanVienDiemDanh;
    private ThanhVien thanhVienDuocKhuyenMai;

    @FXML
    private TableView<SanPham> tbSanPhams;
    @FXML
    private TableView<SanPham> tbSanPhamDuocChon;

    @FXML
    private TextField txtSearchSanPham;
    @FXML
    private TextField txtSearchThanhVien;
    @FXML
    private Label txtTestTen;
    @FXML
    private Label txtTestHo;
    @FXML
    private Label txtDiaChi;
    @FXML
    private TableView<ThanhVien> tbThanhVien;
    @FXML
    private TextField txtThanhVienApDung;
    @FXML
    private TextField txtThanhTien;
    @FXML
    private TextField txtTienNhan;
    @FXML
    private Button btnThanhToan;
    @FXML
    private TextField txtTienThoi;
    @FXML
    private Button btnDiemDanh;
    @FXML
    private Button btnDangXuat;
    @FXML
    private Button btnDangKyThanhVien;
    @FXML
    private Button btnHuyDangKyThanhVien;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txtTienThoi.setDisable(true);
        this.btnHuyDangKyThanhVien.setDisable(true);
        if (this.nhanVienDiemDanh == null) {
            this.btnThanhToan.setDisable(true);
            this.btnDangKyThanhVien.setDisable(true);
        }

        if (this.txtThanhTien.getText().isEmpty() || this.txtThanhTien.getText().compareTo("0") == 1) {
            this.txtTienNhan.setDisable(true);
        }
    }

    public void loadALL() {
        try {
            this.txtTienNhan.setText("");
            this.txtTienNhan.setDisable(true);
            this.txtTienThoi.setText("");
            this.txtThanhTien.setText("");
            this.txtThanhVienApDung.setText("");

            this.tbSanPhamDuocChon.setEditable(true);
            this.sanPhamDuocChon = new ArrayList<>();

            this.loadTableDataSanPham(Integer.toString(this.nhanVienDiemDanh.getIdChiNhanh()), null, this.tbSanPhams);
            this.loadTableColumnsSanPham(this.tbSanPhams);

            this.addTextChangeSanPham(this.txtSearchSanPham, this.tbSanPhams);
//            ==========================================================================
            this.loadTableColumnsSanPhamDuocChon(this.tbSanPhamDuocChon);
            this.tbSanPhamDuocChon.setItems(FXCollections.observableList(this.sanPhamDuocChon));
//            ==========================================================================
            this.loadTableColumnsThanhVien(tbThanhVien);
            this.loadTableDataThanhVien(null, tbThanhVien);
            this.addTextChangeThanhVien(txtSearchThanhVien, tbThanhVien);

            FORM_UTILS.onlyDoubleNumbers(this.txtTienNhan);

            this.addTextChangeTienThoi(this.txtTienNhan, this.txtTienThoi);

        } catch (SQLException ex) {
            Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        Scene currentScene = ((Node) event.getSource()).getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        NhanVien nv = (NhanVien) currentStage.getUserData();
        Alert a = MessageBox.getBox("CC", nv.getTenNhanVien(), Alert.AlertType.CONFIRMATION);
        a.show();
    }

    @FXML
    public void OpenQuanLyThanhVien(ActionEvent evt) throws IOException {
        String formName = "FormQuanLyThanhVien";
        String titleForm = "Quản Lý Thành Viên";
        FORM_UTILS.newForm(formName, titleForm);
    }

    @FXML
    public void xuLyDiemDanh(ActionEvent event) throws SQLException {
        Scene currentScene = ((Node) event.getSource()).getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        nhanVienDiemDanh = (NhanVien) currentStage.getUserData();

        if (nhanVienDiemDanh != null) {
            this.btnDiemDanh.setDisable(true);

            this.txtTestHo.setText(nhanVienDiemDanh.getHoNhanVien());
            this.txtTestTen.setText(nhanVienDiemDanh.getTenNhanVien());

            List<ChiNhanh> cn = chiNhanhService.getChiNhanhs(Integer.toString(nhanVienDiemDanh.getIdChiNhanh()), null);
            this.txtDiaChi.setText(cn.get(0).getDiaChi());
            this.btnThanhToan.setDisable(false);
            this.btnDangKyThanhVien.setDisable(false);
            Alert a = MessageBox.getBox("Thông báo", "Điểm danh thành công! chào " + nhanVienDiemDanh.getTenNhanVien(), Alert.AlertType.CONFIRMATION);
            a.show();

            this.loadALL();
        }
    }

    @FXML
    public void dangXuat() throws IOException {
        String formName = "Primary";
        String titleForm = "Đăng nhập";
        FORM_UTILS.newForm(formName, titleForm);
        Stage oldStage = (Stage) btnDangXuat.getScene().getWindow();

        oldStage.close();
    }

    private void loadTableColumnsSanPham(TableView tableView) {
        TableColumn colIdSanPham = new TableColumn("Mã sản phẩm");
        TableColumn colTenSanPham = new TableColumn("Tên sản phẩm");
        TableColumn colGia = new TableColumn("Giá sản phẩm");
        TableColumn colDonVi = new TableColumn("Đơn vị");
        TableColumn colIdKhuyenMai = new TableColumn("Mã khuyến mãi");
        TableColumn colIdChiNhanh = new TableColumn("Mã chi nhánh");

//        pojo
        colIdSanPham.setCellValueFactory(new PropertyValueFactory("idSanPham"));
        colTenSanPham.setCellValueFactory(new PropertyValueFactory("tenSanPham"));
        colGia.setCellValueFactory(new PropertyValueFactory("gia"));
        colDonVi.setCellValueFactory(new PropertyValueFactory("donVi"));
        colIdKhuyenMai.setCellValueFactory(new PropertyValueFactory("idKhuyenMai"));
        colIdChiNhanh.setCellValueFactory(new PropertyValueFactory("idChiNhanh"));

        TableColumn colLuaChon = new TableColumn("Lựa chọn");

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colIdSanPham, colTenSanPham, colDonVi, colGia, colIdKhuyenMai, colIdChiNhanh);

        this.themButtonVaoTableColumnSanPham(tableView, colLuaChon, "Chọn nha");
    }

    private void loadTableColumnsSanPhamDuocChon(TableView tableView) {
        TableColumn colIdSanPham = new TableColumn("Mã sản phẩm");
        TableColumn colTenSanPham = new TableColumn("Tên sản phẩm");
        TableColumn colGia = new TableColumn("Giá sản phẩm");
        TableColumn colDonVi = new TableColumn("Đơn vị");
        TableColumn colIdKhuyenMai = new TableColumn("Mã khuyến mãi");
        TableColumn<SanPham, Double> colSoLuong = new TableColumn<>("Số lượng");
        TableColumn colIdChiNhanh = new TableColumn("Mã chi nhánh");

//        pojo
        colIdSanPham.setCellValueFactory(new PropertyValueFactory("idSanPham"));
        colTenSanPham.setCellValueFactory(new PropertyValueFactory("tenSanPham"));
        colGia.setCellValueFactory(new PropertyValueFactory("gia"));
        colDonVi.setCellValueFactory(new PropertyValueFactory("donVi"));
        colIdKhuyenMai.setCellValueFactory(new PropertyValueFactory("idKhuyenMai"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soLuongTemp"));
        colSoLuong.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colIdChiNhanh.setCellValueFactory(new PropertyValueFactory("idChiNhanh"));

        colSoLuong.setOnEditCommit((TableColumn.CellEditEvent<SanPham, Double> event) -> {
            SanPham sp = event.getTableView().getItems().get(event.getTablePosition().getRow());

            if (event.getNewValue() > 0) {
                sp.setSoLuongTemp(event.getNewValue());
                this.txtThanhTien.setText(" " + this.tinhTongTien());
            } else {
                MessageBox.getBox("Question", "Số lượng phải lớn hơn 0", Alert.AlertType.INFORMATION).show();
                sp.setSoLuongTemp(1);
            }
        });

        colSoLuong.setEditable(true);

        TableColumn colLuaChon = new TableColumn("Xóa");

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colIdSanPham, colTenSanPham, colDonVi, colGia, colIdKhuyenMai, colSoLuong, colIdChiNhanh);

        this.themButtonVaoTableColumnSanPhamDuocChon(tableView, colLuaChon, "Xóa");

    }

    private void loadTableColumnsThanhVien(TableView tableView) {
        TableColumn colIdThanhVien = new TableColumn("Mã thành viên");
        TableColumn colHoThanhVien = new TableColumn("Họ thành viên");
        TableColumn colTenThanhVien = new TableColumn("Tên thành viên");
        TableColumn colNgaySinhThanhVien = new TableColumn("Ngày sinh");
        TableColumn colSoDienThoai = new TableColumn("Số điện thoại");
        TableColumn colLayId = new TableColumn("Lựa chọn");
//        pojo
        colIdThanhVien.setCellValueFactory(new PropertyValueFactory("idThanhVien"));
        colHoThanhVien.setCellValueFactory(new PropertyValueFactory("hoThanhVien"));
        colTenThanhVien.setCellValueFactory(new PropertyValueFactory("tenThanhVien"));
        colNgaySinhThanhVien.setCellValueFactory(new PropertyValueFactory("ngaySinhThanhVien"));
        colSoDienThoai.setCellValueFactory(new PropertyValueFactory("soDienThoai"));

        tableView.getColumns().addAll(colIdThanhVien, colHoThanhVien, colTenThanhVien, colNgaySinhThanhVien, colSoDienThoai);
        this.themButtonVaoTableColumnThanhVien(tableView, colLayId, "Áp dụng");
    }

    @FXML
    public void luuSoLuongSanPham(ActionEvent evt) throws SQLException {
        this.listSoLuongSanPhamDuocChon = new ArrayList<>();
        for (SanPham s : this.tbSanPhamDuocChon.getItems()) {
            this.listSoLuongSanPhamDuocChon.add(Double.toString(s.getSoLuongTemp()));
        }
    }

    private void loadTableDataSanPham(String keyword_idChiNhanh, String keyword_tenSanPham, TableView tableView) throws SQLException {
        List<SanPham> sanPhams = sanPhamService.getSanPhamTrongChiNhanh(keyword_tenSanPham, keyword_idChiNhanh);
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(sanPhams));
    }

    private void loadTableDataThanhVien(String keyword, TableView tableView) throws SQLException {
        List<ThanhVien> thanhViens = thanhVienService.getThanhViens(keyword, keyword, keyword, keyword);
        if (!thanhViens.isEmpty()) {
            if (thanhViens.get(0).getIdThanhVien() == 0) {
                thanhViens.remove(0);
            }
        }
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(thanhViens));
    }

    private void themButtonVaoTableColumnSanPham(TableView tableView, TableColumn tableColumn, String nameButton) {
        tableColumn.setCellFactory(r -> {
            Button btn = new Button(nameButton);

            this.xuLyButtonThemSanPham(btn);

            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;

        });

        tableView.getColumns().add(tableColumn);
    }

    private void themButtonVaoTableColumnSanPhamDuocChon(TableView tableView, TableColumn tableColumn, String nameButton) {
        tableColumn.setCellFactory(r -> {
            Button btn = new Button(nameButton);

            this.xuLyButtonXoaSanPham(btn);

            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;

        });

        tableView.getColumns().add(tableColumn);
    }

    private void themButtonVaoTableColumnThanhVien(TableView tableView, TableColumn tableColumn, String nameButton) {
        tableColumn.setCellFactory(r -> {
            Button btn = new Button(nameButton);

            this.xuLyButtonThemIdThanhVien(btn);

            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;

        });

        tableView.getColumns().add(tableColumn);
    }

//    private void xuLyButtonThemSanPham(Button button) {
//        button.setOnAction(evt -> {
//            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn chọn?", Alert.AlertType.CONFIRMATION);
//            a.showAndWait().ifPresent(res -> {
//                if (res == ButtonType.OK) {
//                    Button btnXuLy = (Button) evt.getSource();
//                    TableCell cell = (TableCell) btnXuLy.getParent();
//                    SanPham s = (SanPham) cell.getTableRow().getItem();
//                    s.setSoLuongTemp(1);
//
////                    THUẬT TOÁN THÊM ĐẲNG CẤP :) RESET BIẾN ĐẾM ĐÚNG CHỖ :) TỐN 4 TIẾNG RƯỠI CỦA BỐ :)
//                    if (sanPhamDuocChon.isEmpty()) {
//                        sanPhamDuocChon.add(s);
//                        count = 0;
//                    } else {
//                        for (SanPham sp : sanPhamDuocChon) {
//                            if (s.getIdSanPham() == sp.getIdSanPham()) {
//                                Alert c = MessageBox.getBox("Question", "Đã tồn tại sản phẩm", Alert.AlertType.CONFIRMATION);
//                                c.show();
//                                count = 0;
//                                break;
//                            } else {
//                                count++;
//                            }
//                        }
//                        if (count == sanPhamDuocChon.size()) {
//                            sanPhamDuocChon.add(s);
//                            count = 0;
//                        }
//                    }
//                }
//            });
//
//            for (SanPham sanPham : sanPhamDuocChon) {
//                try {
//                    int idKhuyenMai = sanPham.getIdKhuyenMai();
//                    List<KhuyenMai> khuyenMais = khuyenMaiService.getKhuyenMai(Integer.toString(idKhuyenMai), null, null);
//                    KhuyenMai khuyenMaiCuaSanPham = khuyenMais.get(0);
//                    double giaDaGiam = sanPham.getGia() - khuyenMaiCuaSanPham.getGiaTri();
//                    if (giaDaGiam < 0) {
//                        sanPham.setGia(0);
//                    } else {
//                        sanPham.setGia(giaDaGiam);
//                    }
//
//                } catch (SQLException ex) {
//                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//            this.tbSanPhamDuocChon.setItems(FXCollections.observableList(sanPhamDuocChon));
//
//            this.txtThanhTien.setText(" " + this.tinhTongTien());
//            if (!this.txtThanhTien.getText().isEmpty() || !this.txtThanhTien.getText().equals("0")) {
//                this.txtTienNhan.setDisable(false);
//            }
//        });
//    }
    private void xuLyButtonThemSanPham(Button button) {
        button.setOnAction(evt -> {

            Button btnXuLy = (Button) evt.getSource();
            TableCell cell = (TableCell) btnXuLy.getParent();
            SanPham s = (SanPham) cell.getTableRow().getItem();
            s.setSoLuongTemp(1);

//                    THUẬT TOÁN THÊM ĐẲNG CẤP :) RESET BIẾN ĐẾM ĐÚNG CHỖ :) TỐN 4 TIẾNG RƯỠI CỦA BỐ :)
            if (sanPhamDuocChon.isEmpty()) {
                sanPhamDuocChon.add(s);
                // Áp khuyến mãi
                this.applyKhuyenMai(s);
                count = 0;
            } else {
                for (SanPham sp : sanPhamDuocChon) {
                    if (s.getIdSanPham() == sp.getIdSanPham()) {
                        Alert c = MessageBox.getBox("Question", "Đã tồn tại sản phẩm", Alert.AlertType.CONFIRMATION);
                        c.show();
                        count = 0;
                        break;
                    } else {
                        count++;
                    }
                }
                if (count == sanPhamDuocChon.size()) {
                    sanPhamDuocChon.add(s);
                    // Áp khuyến mãi
                    this.applyKhuyenMai(s);
                    count = 0;
                }
            }

            try {
                this.loadTableDataSanPham(Integer.toString(nhanVienDiemDanh.getIdNhanVien()), null, this.tbSanPhams); //Load lại cho nó đỡ bị ngáo
            } catch (SQLException ex) {
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.tbSanPhamDuocChon.setItems(FXCollections.observableList(sanPhamDuocChon));

            this.txtThanhTien.setText(" " + this.tinhTongTien());
            if (!this.txtThanhTien.getText().isEmpty() || !this.txtThanhTien.getText().equals("0")) {
                this.txtTienNhan.setDisable(false);
            }
        });
    }

//    private void applyKhuyenMai() {
//        for (SanPham sanPham : sanPhamDuocChon) {
//            try {
//                int idKhuyenMai = sanPham.getIdKhuyenMai();
//                List<KhuyenMai> khuyenMais = khuyenMaiService.getKhuyenMai(Integer.toString(idKhuyenMai), null, null);
//                KhuyenMai khuyenMaiCuaSanPham = khuyenMais.get(0);
//
//                LocalDate ngayBatDau = khuyenMaiCuaSanPham.getNgayBatDau().toLocalDate();
//                LocalDate ngayKetThuc = khuyenMaiCuaSanPham.getNgayKetThuc().toLocalDate();
//                LocalDate ngayHienTai = LocalDate.now();
//
//                if (ngayHienTai.compareTo(ngayBatDau) > 0 && ngayHienTai.compareTo(ngayKetThuc) < 0) {
//                    double giaDaGiam = sanPham.getGia() - khuyenMaiCuaSanPham.getGiaTri();
//                    if (giaDaGiam < 0) {
//                        sanPham.setGia(0);
//                    } else {
//                        sanPham.setGia(giaDaGiam);
//                    }
//                    MessageBox.getBox("Thông báo", "Sản phẩm đang trong thời gian giảm giá!", Alert.AlertType.CONFIRMATION).show();
//                }
//
//            } catch (SQLException ex) {
//                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    private void applyKhuyenMai(SanPham sanPham) {
        try {
            int idKhuyenMai = sanPham.getIdKhuyenMai();
            List<KhuyenMai> khuyenMais = khuyenMaiService.getKhuyenMai(Integer.toString(idKhuyenMai), null, null);
            KhuyenMai khuyenMaiCuaSanPham = khuyenMais.get(0);

            LocalDate ngayBatDau = khuyenMaiCuaSanPham.getNgayBatDau().toLocalDate();
            LocalDate ngayKetThuc = khuyenMaiCuaSanPham.getNgayKetThuc().toLocalDate();
            LocalDate ngayHienTai = LocalDate.now();

            if (ngayHienTai.compareTo(ngayBatDau) >= 0 && ngayHienTai.compareTo(ngayKetThuc) <= 0) {
                double giaDaGiam = sanPham.getGia() - khuyenMaiCuaSanPham.getGiaTri();
                if (giaDaGiam < 0) {
                    sanPham.setGia(0);
                } else {
                    sanPham.setGia(giaDaGiam);
                }
                MessageBox.getBox("Thông báo", "Sản phẩm đang trong thời gian giảm giá!", Alert.AlertType.CONFIRMATION).show();
            }

        } catch (SQLException ex) {
            Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private double tinhTongTien() {
        double tong = 0;
        LocalDate today = LocalDate.now();
        ObservableList<SanPham> listSP = FXCollections.observableArrayList();
        listSP.addAll(tbSanPhamDuocChon.getItems());
        for (SanPham sp : listSP) {
            tong += sp.getGia() * sp.getSoLuongTemp();
        }

        if (thanhVienDuocKhuyenMai != null) {
            LocalDate ngaySinhThanhVien = thanhVienDuocKhuyenMai.getNgaySinhThanhVien().toLocalDate();
            if ((ngaySinhThanhVien.getMonthValue() == today.getMonthValue()
                    && ngaySinhThanhVien.getDayOfMonth() == today.getDayOfMonth())
                    && tong > FORM_UTILS.SO_TIEN_GIAM_GIA_THEO_QUY_DINH) {
                tong = tong - tong * FORM_UTILS.PHAN_TRAM_DISCOUNT;
                MessageBox.getBox("Thông báo", "BẠN ĐÃ ĐƯỢC GIẢM " + FORM_UTILS.PHAN_TRAM_DISCOUNT_TEXT + ", SỐ TIỀN CỦA BẠN LÀ = " + tong, Alert.AlertType.CONFIRMATION).show();
            }
        }

//        if (this.txtThanhTien.getText().isEmpty() || this.txtThanhTien.getText().compareTo("0") == 1) {
//            this.txtTienNhan.setDisable(true);
//        } else {
//            this.txtTienNhan.setDisable(false);
//        }
        return tong;
    }

    @FXML
    public void xuLyThemHoaDon(ActionEvent evt) throws SQLException {
        this.luuSoLuongSanPham(evt);

        LocalDate ngayCT_LocalDate = LocalDate.now();
        Date ngayCT_Date = Date.valueOf(ngayCT_LocalDate);

        int idNhanVien = this.nhanVienDiemDanh.getIdNhanVien();
        int idChiNhanh = this.nhanVienDiemDanh.getIdChiNhanh();

        List<HoaDon> listHoaDon = hoaDonService.getHoaDon(null);
        int idHoaDon = listHoaDon.get(listHoaDon.size() - 1).getIdHoaDon() + 1;

        List<ChiTietHoaDon> listChiTietHoaDon = chiTietHoaDonService.getChiTietHoaDon(null);
        int idChiTietHoaDon = listChiTietHoaDon.get(listChiTietHoaDon.size() - 1).getIdCTHD();

//        Lấy id thành viên được chọn trong hệ thống để tiến hành giảm giá
        int idThanhVien = 0;

        if (this.thanhVienDuocKhuyenMai != null) {
            idThanhVien = this.thanhVienDuocKhuyenMai.getIdThanhVien();
        }

//        Tạo hóa đơn
        HoaDon hoaDon = new HoaDon(idHoaDon, idNhanVien, idChiNhanh, idThanhVien, 0, 0, ngayCT_Date);

//        MessageBox.getBox("Question", "SIze = " + listHoaDon.size() + "id = " + idHoaDon, Alert.AlertType.INFORMATION).show();
//        Danh sách chứa sản phẩm được chọn không có
        if (!this.txtTienNhan.getText().isEmpty()) {
            if (!sanPhamDuocChon.isEmpty()) {
                List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();
                double tongTien = 0;
                int countSL = 0;
                double soTienNhan = Double.parseDouble(this.txtTienNhan.getText());

                for (SanPham sp : sanPhamDuocChon) {
                    double soLuong = Double.parseDouble(listSoLuongSanPhamDuocChon.get(countSL));
                    countSL++;
                    idChiTietHoaDon++;
                    double thanhTien = sp.getGia() * soLuong;
                    ChiTietHoaDon cthd = new ChiTietHoaDon(idChiTietHoaDon, sp.getIdSanPham(), hoaDon.getIdHoaDon(), soLuong, thanhTien);
                    chiTietHoaDons.add(cthd);
                    tongTien += thanhTien;
                }

//                if (idThanhVien != 0) {
//                    LocalDate ngaySinhThanhVien = thanhVienDuocKhuyenMai.getNgaySinhThanhVien().toLocalDate();
//                    if ((ngaySinhThanhVien.getMonthValue() == ngayCT_LocalDate.getMonthValue()
//                            && ngaySinhThanhVien.getDayOfMonth() == ngayCT_LocalDate.getDayOfMonth())
//                            && tongTien > this.SO_TIEN_GIAM_GIA_THEO_QUY_DINH) {
//                        tongTien = tongTien - tongTien * this.PHAN_TRAM_DISCOUNT;
//                        MessageBox.getBox("Thông báo", "BẠN ĐÃ ĐƯỢC GIẢM 10% = " + this.PHAN_TRAM_DISCOUNT, Alert.AlertType.CONFIRMATION).show();
//                    }
//                }
                if (soTienNhan >= tongTien) {
                    hoaDon.setTongTien(tongTien);
                    hoaDon.setSoTienNhan(soTienNhan);

//                    hoaDonService.addHoaDon(hoaDon, chiTietHoaDons);
//                        MessageBox.getBox("Question", "Thêm hóa đơn thành công", Alert.AlertType.INFORMATION).show();
//                    this.loadALL();
                    try {
                        hoaDonService.addHoaDon(hoaDon, chiTietHoaDons);
                        MessageBox.getBox("Question", "Thêm hóa đơn thành công", Alert.AlertType.INFORMATION).show();
                        this.loadALL();
                    } catch (SQLException ex) {
                        MessageBox.getBox("Question", "Thêm hóa đơn thất bại", Alert.AlertType.INFORMATION).show();
                        Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    MessageBox.getBox("Question", "Số tiền nhận từ khách phải lớn hơn hoặc bằng tổng tiền!", Alert.AlertType.INFORMATION).show();
                }

            } else {
                MessageBox.getBox("Question", "Hãy chọn sản phẩm", Alert.AlertType.INFORMATION).show();
            }
        } else {
            MessageBox.getBox("Question", "Hãy nhập số tiền nhận", Alert.AlertType.INFORMATION).show();
        }
    }

    private void xuLyButtonXoaSanPham(Button button) {
        button.setOnAction(evt -> {
            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn xóa?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    Button btnXuLy = (Button) evt.getSource();
                    TableCell cell = (TableCell) btnXuLy.getParent();
                    SanPham s = (SanPham) cell.getTableRow().getItem();
                    sanPhamDuocChon.remove(s);
                    this.txtThanhTien.setText(" " + this.tinhTongTien());

                    if (this.tinhTongTien() == 0) {
                        this.txtTienNhan.setDisable(true);
                        this.txtTienNhan.setText("0");
                    }
                }
            });
            this.tbSanPhamDuocChon.setItems(FXCollections.observableList(sanPhamDuocChon));

        });

    }

    private void xuLyButtonThemIdThanhVien(Button button) {
        button.setOnAction(evt -> {
            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn chọn?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    Button btnXuLy = (Button) evt.getSource();
                    TableCell cell = (TableCell) btnXuLy.getParent();
                    thanhVienDuocKhuyenMai = (ThanhVien) cell.getTableRow().getItem();

                    this.txtThanhVienApDung.setText(thanhVienDuocKhuyenMai.getHoThanhVien() + " " + thanhVienDuocKhuyenMai.getTenThanhVien());
                    this.txtThanhTien.setText(this.tinhTongTien() + "");
                    MessageBox.getBox("Thông báo", "Đã chọn thành viên: " + thanhVienDuocKhuyenMai.getHoThanhVien() + " " + thanhVienDuocKhuyenMai.getTenThanhVien(), Alert.AlertType.CONFIRMATION).show();
                    this.btnHuyDangKyThanhVien.setDisable(false);
                }
            });

            this.tbSanPhamDuocChon.setItems(FXCollections.observableList(sanPhamDuocChon));
        });
    }

    private void addTextChangeSanPham(TextField textField, TableView tableView) {
        textField.textProperty().addListener(e -> {
            try {
                this.loadTableDataSanPham(Integer.toString(this.nhanVienDiemDanh.getIdChiNhanh()), textField.getText(), tableView);
            } catch (SQLException ex) {
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void addTextChangeThanhVien(TextField textField, TableView tableView) {
        textField.textProperty().addListener(e -> {
            try {
                this.loadTableDataThanhVien(textField.getText(), tableView);
            } catch (SQLException ex) {
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void addTextChangeTienThoi(TextField textField_1, TextField textField_2) {
        textField_1.textProperty().addListener(e -> {
            if (!textField_1.getText().isEmpty()) {
                double thanhTien = Double.parseDouble(this.txtThanhTien.getText());
                double soTienNhan = Double.parseDouble(textField_1.getText());
                textField_2.setText(" " + (soTienNhan - thanhTien));
            }
        });
    }

    @FXML
    public void huyApDungThanhVien() {
        Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn hủy?", Alert.AlertType.CONFIRMATION);
        a.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                if (this.thanhVienDuocKhuyenMai != null) {
                    this.thanhVienDuocKhuyenMai = null;
                    this.txtThanhVienApDung.setText("");
                    this.btnHuyDangKyThanhVien.setDisable(true);
                }
            }
        });
    }
}
