package com.tuantran.fxsaleapp;

import com.tuantran.pojo.*;
import com.tuantran.pojo.SanPham;
import com.tuantran.services.ChiNhanhService;
import com.tuantran.services.ChiTietHoaDonService;
import com.tuantran.services.HoaDonService;
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

    private List<SanPham> sanPhamDuocChon;
    private List<String> listSoLuongSanPhamDuocChon;

    private int count;
    private NhanVien nhanVienDiemDanh;

    private ThanhVien thanhVienTrongHeThongDuocChon;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            if (this.nhanVienDiemDanh == null) {
                this.btnThanhToan.setDisable(true);
            }

            this.tbSanPhamDuocChon.setEditable(true);
            this.sanPhamDuocChon = new ArrayList<>();

            this.loadTableDataSanPham(null, this.tbSanPhams);
            this.loadTableColumnsSanPham(this.tbSanPhams);

            this.addTextChangeSanPham(this.txtSearchSanPham, this.tbSanPhams);
//            ==========================================================================
            this.loadTableColumnsSanPhamDuocChon(this.tbSanPhamDuocChon);
//            ==========================================================================
            this.loadTableColumnsThanhVien(tbThanhVien);
            this.loadTableDataThanhVien(null, tbThanhVien);
            this.addTextChangeThanhVien(txtSearchThanhVien, tbThanhVien);

            UnaryOperator<Change> filter = change -> {
                String text = change.getText();
                if (text.matches("[0-9]*")) { // Chỉ cho phép nhập số
                    return change;
                } else {
                    Alert a = MessageBox.getBox("Cảnh báo", "Vui lòng nhập số!!", Alert.AlertType.WARNING);
                    a.show();
                }
                return null;
            };
            TextFormatter<String> formatter = new TextFormatter<>(filter);
            this.txtTienNhan.setTextFormatter(formatter);

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
    public void xuLyDiemDanh(ActionEvent event) throws SQLException {
        Scene currentScene = ((Node) event.getSource()).getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        nhanVienDiemDanh = (NhanVien) currentStage.getUserData();
        this.txtTestHo.setText(nhanVienDiemDanh.getHoNhanVien());
        this.txtTestTen.setText(nhanVienDiemDanh.getTenNhanVien());

        List<ChiNhanh> cn = chiNhanhService.getChiNhanhs(String.format("", nhanVienDiemDanh.getIdChiNhanh()));
        this.txtDiaChi.setText(cn.get(0).getDiaChi());
        btnThanhToan.setDisable(false);
        Alert a = MessageBox.getBox("Question", nhanVienDiemDanh.getTenNhanVien(), Alert.AlertType.CONFIRMATION);
        a.show();
    }

    private void loadTableColumnsSanPham(TableView tableView) {
        TableColumn colIdSanPham = new TableColumn("Mã sản phẩm");
        TableColumn colTenSanPham = new TableColumn("Tên sản phẩm");
        TableColumn colGia = new TableColumn("Giá sản phẩm");
        TableColumn colDonVi = new TableColumn("Đơn vị");
        TableColumn colIdKhuyenMai = new TableColumn("Khuyến Mãi");

//        pojo
        colIdSanPham.setCellValueFactory(new PropertyValueFactory("idSanPham"));
        colTenSanPham.setCellValueFactory(new PropertyValueFactory("tenSanPham"));
        colGia.setCellValueFactory(new PropertyValueFactory("gia"));
        colDonVi.setCellValueFactory(new PropertyValueFactory("donVi"));
        colIdKhuyenMai.setCellValueFactory(new PropertyValueFactory("idKhuyenMai"));

        TableColumn colLuaChon = new TableColumn("Lựa chọn");

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colIdSanPham, colTenSanPham, colDonVi, colGia, colIdKhuyenMai);

        this.themButtonVaoTableColumnSanPham(tableView, colLuaChon, "Chọn nha nha");
    }

    private void loadTableColumnsSanPhamDuocChon(TableView tableView) {
        TableColumn colIdSanPham = new TableColumn("Mã sản phẩm");
        TableColumn colTenSanPham = new TableColumn("Tên sản phẩm");
        TableColumn colGia = new TableColumn("Giá sản phẩm");
        TableColumn colDonVi = new TableColumn("Đơn vị");
        TableColumn colIdKhuyenMai = new TableColumn("Khuyến Mãi");
        TableColumn<SanPham, Double> colSoLuong = new TableColumn<>("Số lượng");

//        pojo
        colIdSanPham.setCellValueFactory(new PropertyValueFactory("idSanPham"));
        colTenSanPham.setCellValueFactory(new PropertyValueFactory("tenSanPham"));
        colGia.setCellValueFactory(new PropertyValueFactory("gia"));
        colDonVi.setCellValueFactory(new PropertyValueFactory("donVi"));
        colIdKhuyenMai.setCellValueFactory(new PropertyValueFactory("idKhuyenMai"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soLuongTemp"));
        colSoLuong.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

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
        tableView.getColumns().addAll(colIdSanPham, colTenSanPham, colDonVi, colGia, colIdKhuyenMai, colSoLuong);

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

    private void loadTableDataSanPham(String keyword, TableView tableView) throws SQLException {
        List<SanPham> sanPhams = sanPhamService.getSanPham(keyword);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(sanPhams));
    }

    private void loadTableDataThanhVien(String keyword, TableView tableView) throws SQLException {
        List<ThanhVien> thanhViens = thanhVienService.getThanhVien(keyword);

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

    private void xuLyButtonThemSanPham(Button button) {
        button.setOnAction(evt -> {
            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn chọn?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    Button btnXuLy = (Button) evt.getSource();
                    TableCell cell = (TableCell) btnXuLy.getParent();
                    SanPham s = (SanPham) cell.getTableRow().getItem();
                    s.setSoLuongTemp(1);

//                    THUẬT TOÁN THÊM ĐẲNG CẤP :) RESET BIẾN ĐẾM ĐÚNG CHỖ :) TỐN 4 TIẾNG RƯỠI CỦA BỐ :)
                    if (sanPhamDuocChon.isEmpty()) {
                        sanPhamDuocChon.add(s);
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
                            count = 0;
                        }
                    }
                }
            });

            this.tbSanPhamDuocChon.setItems(FXCollections.observableList(sanPhamDuocChon));
            this.txtThanhTien.setText(" " + this.tinhTongTien());
        });
    }

    private long tinhTongTien() {
        long tong = 0;
        ObservableList<SanPham> listSP = FXCollections.observableArrayList();
        listSP.addAll(tbSanPhamDuocChon.getItems());
        for (SanPham sp : listSP) {
            tong += sp.getGia() * sp.getSoLuongTemp();
        }

        return tong;
    }

    @FXML
    public void xuLyThemHoaDon(ActionEvent evt) throws SQLException {
        this.luuSoLuongSanPham(evt);

        LocalDate ngayCTTemp = LocalDate.now();
        Date ngayCTReal = Date.valueOf(ngayCTTemp);
//        Date ngayCT = new Date(40, 10, 10);

        int idNhanVien = this.nhanVienDiemDanh.getIdNhanVien();
        int idChiNhanh = this.nhanVienDiemDanh.getIdChiNhanh();

        
        List<HoaDon> listHoaDon = hoaDonService.getHoaDon(null);
        int idHoaDon = listHoaDon.get(listHoaDon.size() - 1).getIdHoaDon() + 1;


        List<ChiTietHoaDon> listChiTietHoaDon = chiTietHoaDonService.getChiTietHoaDon(null);
        int idChiTietHoaDon = listChiTietHoaDon.get(listChiTietHoaDon.size() - 1).getIdCTHD();

//        Lấy id thành viên được chọn trong hệ thống
        int idThanhVien = 0;

        if (this.thanhVienTrongHeThongDuocChon != null) {
            idThanhVien = this.thanhVienTrongHeThongDuocChon.getIdThanhVien();
        }

//        Tạo hóa đơn
        HoaDon hoaDon = new HoaDon(idHoaDon, idNhanVien, idChiNhanh, idThanhVien, 0, 0, ngayCTReal);
        MessageBox.getBox("Question", "SIze = " + listHoaDon.size() + "id = " + idHoaDon, Alert.AlertType.INFORMATION).show();

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

                hoaDon.setTongTien(tongTien);
                hoaDon.setSoTienNhan(soTienNhan);

                try {
                    hoaDonService.addHoaDon(hoaDon, chiTietHoaDons);
                    MessageBox.getBox("Question", "Thêm hóa đơn thành công", Alert.AlertType.INFORMATION).show();
                } catch (SQLException ex) {
                    MessageBox.getBox("Question", "Thêm hóa đơn thất bại", Alert.AlertType.INFORMATION).show();
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
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
                    thanhVienTrongHeThongDuocChon = (ThanhVien) cell.getTableRow().getItem();

                    Alert b = MessageBox.getBox("CC", thanhVienTrongHeThongDuocChon.getHoThanhVien() + " " + thanhVienTrongHeThongDuocChon.getTenThanhVien(), Alert.AlertType.CONFIRMATION);
                    b.show();
                    this.txtThanhVienApDung.setText(thanhVienTrongHeThongDuocChon.getHoThanhVien() + " " + thanhVienTrongHeThongDuocChon.getTenThanhVien());
                }
            });

            this.tbSanPhamDuocChon.setItems(FXCollections.observableList(sanPhamDuocChon));
        });
    }

    private void addTextChangeSanPham(TextField textField, TableView tableView) {
        textField.textProperty().addListener(e -> {
            try {
                this.loadTableDataSanPham(textField.getText(), tableView);
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
            double thanhTien = Double.parseDouble(this.txtThanhTien.getText());
            double soTienNhan = Double.parseDouble(textField_1.getText());
            textField_2.setText(" " + (soTienNhan - thanhTien));
        });
    }
}
