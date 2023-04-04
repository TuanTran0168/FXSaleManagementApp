/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.fxsaleapp;

import com.tuantran.pojo.ChiNhanh;
import com.tuantran.pojo.KhuyenMai;
import com.tuantran.pojo.NhanVien;
import com.tuantran.pojo.SanPham;
import com.tuantran.services.ChiNhanhService;
import com.tuantran.services.KhuyenMaiService;
import com.tuantran.services.NhanVienService;
import com.tuantran.services.SanPhamService;
import com.tuantran.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author TuanTran
 */
public class FormQuanLyBanHangController implements Initializable {

    private static final String MY_DATE_FORMAT = "dd/MM/yyyy";
    static final ChiNhanhService chiNhanhService = new ChiNhanhService();
    static final NhanVienService nhanVienService = new NhanVienService();
    static final KhuyenMaiService khuyenMaiService = new KhuyenMaiService();
    static final SanPhamService sanPhamService = new SanPhamService();
    private final FormUtils FORM_UTILS = new FormUtils();

    @FXML
    private TableView<ChiNhanh> tbChiNhanh;
    @FXML
    private TableView<NhanVien> tbNhanVien;
    @FXML
    private TableView<KhuyenMai> tbKhuyenMai;
    @FXML
    private TableView<SanPham> tbSanPham;

    @FXML
    private TextField txtChiNhanh_id;
    @FXML
    private TextField txtChiNhanh_diaChi;

    @FXML
    private TextField txtNhanVien_id;
    @FXML
    private TextField txtNhanVien_hoNhanVien;
    @FXML
    private TextField txtNhanVien_tenNhanVien;
    @FXML
    private TextField txtNhanVien_taiKhoan;
    @FXML
    private TextField txtNhanVien_matKhau;

    @FXML
    private TextField txtKhuyenMai_id;
    @FXML
    private TextField txtKhuyenMai_tenKhuyenMai;
    @FXML
    private TextField txtKhuyenMai_giaTri;

    @FXML
    private TextField txtSanPham_id;
    @FXML
    private TextField txtSanPham_tenSanPham;
    @FXML
    private TextField txtSanPham_donVi;
    @FXML
    private TextField txtSanPham_gia;

    @FXML
    ComboBox<ChiNhanh> cbChiNhanh;
    @FXML
    ComboBox<String> cbNhanVien;
    @FXML
    ComboBox<KhuyenMai> cbKhuyenMai;

    @FXML
    DatePicker dpKhuyenMai_ngayKetThuc;
    @FXML
    DatePicker dpKhuyenMai_ngayBatDau;

//    @FXML
//    ComboBox<String> cbChiNhanh;
//    @FXML
//    ComboBox<String> cbNhanVien;
//    @FXML
//    ComboBox<String> cbKhuyenMai;
    @FXML
    private Button btnDangXuat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.formatDate(MY_DATE_FORMAT, dpKhuyenMai_ngayBatDau);
        this.formatDate(MY_DATE_FORMAT, dpKhuyenMai_ngayKetThuc);

//        LocalDate localDate = LocalDate.of(2023, 4, 3);
//        this.dpKhuyenMai_ngayBatDau.setValue(localDate);
        this.loadALL();
    }

    private void loadALL() {
        try {
            this.txtChiNhanh_diaChi.setText("");
            this.txtChiNhanh_id.setText("");

            this.txtNhanVien_hoNhanVien.setText("");
            this.txtNhanVien_tenNhanVien.setText("");
            this.txtNhanVien_taiKhoan.setText("");
            this.txtNhanVien_matKhau.setText("");

            this.txtKhuyenMai_id.setText("");
            this.txtKhuyenMai_tenKhuyenMai.setText("");
            this.txtKhuyenMai_giaTri.setText("");

            this.txtSanPham_id.setText("");
            this.txtSanPham_tenSanPham.setText("");
            this.txtSanPham_gia.setText("");
            this.txtSanPham_donVi.setText("");

            this.loadTableColumnChiNhanh(tbChiNhanh);
            this.loadTableColumnNhanVien(tbNhanVien);
            this.loadTableColumnKhuyenMai(tbKhuyenMai);
            this.loadTableColumnSanPham(tbSanPham);

            this.loadTableDataChiNhanh(tbChiNhanh, null);
            this.loadTableDataNhanVien(tbNhanVien, null);
            this.loadTableDataKhuyenMai(tbKhuyenMai, null);
            this.loadTableDataSanPham(tbSanPham, null);

            this.addTextChangeChiNhanh(this.txtChiNhanh_diaChi, tbChiNhanh);
            this.addTextChangeNhanVien(this.txtNhanVien_hoNhanVien, this.txtNhanVien_tenNhanVien, this.txtNhanVien_taiKhoan, this.txtNhanVien_matKhau, tbNhanVien);
            this.addTextChangeKhuyenMai(this.txtKhuyenMai_tenKhuyenMai, this.txtKhuyenMai_giaTri, tbKhuyenMai);
            this.addTextChangeSanPham(null, this.txtSanPham_tenSanPham, this.txtSanPham_gia, this.txtSanPham_donVi, this.tbSanPham);

            List<String> listLoaiNhanVien = new ArrayList<>();
            listLoaiNhanVien.add("Nhân viên");
            listLoaiNhanVien.add("Quản lý");

            List<ChiNhanh> listChiNhanh = chiNhanhService.getChiNhanhs(null);
            List<KhuyenMai> listKhuyenMai = khuyenMaiService.getKhuyenMai(null);

//            List<String> listChiNhanhString = listChiNhanh.stream().flatMap(cn -> cn.getDiaChi().lines()).collect(Collectors.toList());
            this.cbChiNhanh.setItems(FXCollections.observableList(listChiNhanh));
            this.cbNhanVien.setItems(FXCollections.observableList(listLoaiNhanVien));
            this.cbKhuyenMai.setItems(FXCollections.observableList(listKhuyenMai));

//            this.cbChiNhanh.setValue(listChiNhanh.get(0));
            this.cbNhanVien.setValue(listLoaiNhanVien.get(0));

            dpKhuyenMai_ngayBatDau.setValue(null);
            dpKhuyenMai_ngayKetThuc.setValue(null);
            this.addTextChangeDatePickerKhuyenMai(dpKhuyenMai_ngayBatDau, dpKhuyenMai_ngayKetThuc);

        } catch (SQLException ex) {
            Logger.getLogger(FormQuanLyBanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void formatDate(String yourDateFormat, DatePicker datePicker) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(yourDateFormat);

        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        datePicker.setConverter(converter);
    }

    @FXML
    private void dangXuat() throws IOException {
        String formName = "Primary";
        String titleForm = "Đăng nhập";
        FORM_UTILS.newForm(formName, titleForm);
        Stage oldStage = (Stage) btnDangXuat.getScene().getWindow();

        oldStage.close();
    }

    private void loadTableColumnChiNhanh(TableView tableView) {
        TableColumn colIdChiNhanh = new TableColumn("Mã chi nhánh");
        TableColumn colDiaChi = new TableColumn("Địa chỉ");
//        pojo
        colIdChiNhanh.setCellValueFactory(new PropertyValueFactory("idChiNhanh"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory("diaChi"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colIdChiNhanh, colDiaChi);
    }

    private void loadTableColumnNhanVien(TableView tableView) {
        TableColumn colIdThanhVien = new TableColumn("Mã nhân viên");
        TableColumn colHoThanhVien = new TableColumn("Họ nhân viên");
        TableColumn colTenThanhVien = new TableColumn("Tên nhân viên");
        TableColumn colIdChiNhanh = new TableColumn("Mã chi nhánh");
        TableColumn colTaiKhoan = new TableColumn("Tài khoản");
        TableColumn colMatKhau = new TableColumn("Mật khẩu");
        TableColumn colQuanLy = new TableColumn("Loại tài khoản");

//        pojo
        colIdThanhVien.setCellValueFactory(new PropertyValueFactory("idNhanVien"));
        colHoThanhVien.setCellValueFactory(new PropertyValueFactory("hoNhanVien"));
        colTenThanhVien.setCellValueFactory(new PropertyValueFactory("tenNhanVien"));
        colIdChiNhanh.setCellValueFactory(new PropertyValueFactory("idChiNhanh"));
        colTaiKhoan.setCellValueFactory(new PropertyValueFactory("taiKhoan"));
        colMatKhau.setCellValueFactory(new PropertyValueFactory("matKhau"));
        colQuanLy.setCellValueFactory(new PropertyValueFactory("quanLy"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colIdThanhVien, colHoThanhVien, colTenThanhVien, colIdChiNhanh, colTaiKhoan, colMatKhau, colQuanLy);
    }

    private void loadTableColumnKhuyenMai(TableView tableView) {
        TableColumn colIdKhuyenMai = new TableColumn("Mã khuyến mãi");
        TableColumn colTenKhuyenMai = new TableColumn("Tên khuyến mãi");
        TableColumn colGiaTri = new TableColumn("Giá trị");
        TableColumn colNgayBatDau = new TableColumn("Ngày bắt đầu");
        TableColumn colNgayKetThuc = new TableColumn("Ngày kết thúc");

//        pojo
        colIdKhuyenMai.setCellValueFactory(new PropertyValueFactory("idKhuyenMai"));
        colTenKhuyenMai.setCellValueFactory(new PropertyValueFactory("tenKhuyenMai"));
        colGiaTri.setCellValueFactory(new PropertyValueFactory("giaTri"));
        colNgayBatDau.setCellValueFactory(new PropertyValueFactory("ngayBatDau"));
        colNgayKetThuc.setCellValueFactory(new PropertyValueFactory("ngayKetThuc"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colIdKhuyenMai, colTenKhuyenMai, colGiaTri, colNgayBatDau, colNgayKetThuc);
    }

    private void loadTableColumnSanPham(TableView tableView) {
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

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colIdSanPham, colTenSanPham, colDonVi, colGia, colIdKhuyenMai);
    }

    private void loadTableDataChiNhanh(TableView tableView, String keyword) throws SQLException {
        List<ChiNhanh> chiNhanhs = chiNhanhService.getChiNhanhs(keyword);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(chiNhanhs));
    }

    private void loadTableDataNhanVien(TableView tableView, String keyword) throws SQLException {
        List<NhanVien> nhanViens = nhanVienService.getNhanViens(keyword);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(nhanViens));
    }

    private void loadTableDataNhanVien(TableView tableView, String keyword_hoNhanVien, String keyword_tenNhanVien, String keyword_taiKhoan, String keyword_matKhau) throws SQLException {
        List<NhanVien> nhanViens = nhanVienService.getNhanViens(keyword_hoNhanVien, keyword_tenNhanVien, keyword_taiKhoan, keyword_matKhau);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(nhanViens));
    }

    private void loadTableDataKhuyenMai(TableView tableView, String keyword) throws SQLException {
        List<KhuyenMai> khuyenMais = khuyenMaiService.getKhuyenMai(keyword);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(khuyenMais));
    }

    private void loadTableDataKhuyenMai(TableView tableView, String keyword_tenKhuyenMai, String keyword_giaTri) throws SQLException {
        List<KhuyenMai> khuyenMais = khuyenMaiService.getKhuyenMai(null, keyword_tenKhuyenMai, keyword_giaTri);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(khuyenMais));
    }

    private void loadTableDataSanPham(TableView tableView, String keyword) throws SQLException {
        List<SanPham> sanPhams = sanPhamService.getSanPham(keyword);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(sanPhams));
    }

    private void loadTableDataSanPham(TableView tableView, String keyword_id, String keyword_tenSanPham, String keyword_gia, String keyword_donVi) throws SQLException {
        List<SanPham> sanPhams = sanPhamService.getSanPham(keyword_id, keyword_tenSanPham, keyword_gia, keyword_donVi);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(sanPhams));
    }

    private void addTextChangeChiNhanh(TextField textField, TableView tableView) {
        textField.textProperty().addListener(e -> {
            try {
                this.loadTableDataChiNhanh(tableView, textField.getText());
            } catch (SQLException ex) {
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void addTextChangeNhanVien(TextField textField, TableView tableView) {
        textField.textProperty().addListener(e -> {
            try {
                this.loadTableDataNhanVien(tableView, textField.getText());
            } catch (SQLException ex) {
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void addTextChangeNhanVien(TextField keyword_hoNhanVien, TextField keyword_tenNhanVien, TextField keyword_taiKhoan, TextField keyword_matKhau, TableView tableView) {
        if (keyword_hoNhanVien != null) {
            keyword_hoNhanVien.textProperty().addListener(e -> {
                try {
                    this.loadTableDataNhanVien(tableView, keyword_hoNhanVien.getText(), null, null, null);
                } catch (SQLException ex) {
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        if (keyword_tenNhanVien != null) {
            keyword_tenNhanVien.textProperty().addListener(e -> {
                try {
                    this.loadTableDataNhanVien(tableView, null, keyword_tenNhanVien.getText(), null, null);
                } catch (SQLException ex) {
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        if (keyword_taiKhoan != null) {
            keyword_taiKhoan.textProperty().addListener(e -> {
                try {
                    this.loadTableDataNhanVien(tableView, null, null, keyword_taiKhoan.getText(), null);
                } catch (SQLException ex) {
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        if (keyword_matKhau != null) {
            keyword_matKhau.textProperty().addListener(e -> {
                try {
                    this.loadTableDataNhanVien(tableView, null, null, null, keyword_matKhau.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    private void addTextChangeKhuyenMai(TextField textField, TableView tableView) {
        textField.textProperty().addListener(e -> {
            try {
                this.loadTableDataKhuyenMai(tableView, textField.getText());
            } catch (SQLException ex) {
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void addTextChangeKhuyenMai(TextField keyword_tenKhuyenMai, TextField keyword_giaTri, TableView tableView) {
        if (keyword_tenKhuyenMai != null) {
            keyword_tenKhuyenMai.textProperty().addListener(e -> {
                try {
                    this.loadTableDataKhuyenMai(tableView, keyword_tenKhuyenMai.getText(), null);
                } catch (SQLException ex) {
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        if (keyword_giaTri != null) {
            keyword_giaTri.textProperty().addListener(e -> {
                try {
                    this.loadTableDataKhuyenMai(tableView, null, keyword_giaTri.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    private void addTextChangeSanPham(TextField textField, TableView tableView) {
        textField.textProperty().addListener(e -> {
            try {
                this.loadTableDataSanPham(tableView, textField.getText(), textField.getText(), textField.getText(), textField.getText());
            } catch (SQLException ex) {
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void addTextChangeSanPham(TextField keyword_id, TextField keyword_tenSanPham, TextField keyword_gia, TextField keyword_donVi, TableView tableView) {
        if (keyword_id != null) {
            keyword_id.textProperty().addListener(e -> {
                try {
//                    this.loadTableDataSanPham(tableView, keyword_id.getText());
                    this.loadTableDataSanPham(tableView, keyword_id.getText(), null, null, null);

                } catch (SQLException ex) {
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        if (keyword_tenSanPham != null) {
            keyword_tenSanPham.textProperty().addListener(e -> {
                try {
                    this.loadTableDataSanPham(tableView, null, keyword_tenSanPham.getText(), null, null);

                } catch (SQLException ex) {
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        if (keyword_gia != null) {
            keyword_gia.textProperty().addListener(e -> {
                try {
                    this.loadTableDataSanPham(tableView, null, null, keyword_gia.getText(), null);

                } catch (SQLException ex) {
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        if (keyword_donVi != null) {
            keyword_donVi.textProperty().addListener(e -> {
                try {
                    this.loadTableDataSanPham(tableView, null, null, null, keyword_donVi.getText());

                } catch (SQLException ex) {
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    @FXML
    public void addChiNhanh(ActionEvent evt) throws SQLException {
        if (!this.txtChiNhanh_diaChi.getText().isEmpty()) {
            List<ChiNhanh> chiNhanhs = chiNhanhService.getChiNhanhs(null);
            int idChiNhanh = chiNhanhs.get(chiNhanhs.size() - 1).getIdChiNhanh() + 1;
            String diaChi = this.txtChiNhanh_diaChi.getText();
            ChiNhanh chiNhanh = new ChiNhanh(idChiNhanh, diaChi);

            try {
                chiNhanhService.addChiNhanh(chiNhanh);
                this.loadTableDataChiNhanh(this.tbChiNhanh, null);
                this.txtChiNhanh_diaChi.setText("");
                MessageBox.getBox("Question", "Thêm chi nhánh thành công", Alert.AlertType.INFORMATION).show();
                this.loadALL();
            } catch (SQLException ex) {
                MessageBox.getBox("Question", "Thêm chi nhánh thất bại", Alert.AlertType.INFORMATION).show();
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            MessageBox.getBox("Question", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void deleteChiNhanh(ActionEvent evt) {
        Object selectedObject = this.tbChiNhanh.getSelectionModel().getSelectedItem();

        if (selectedObject != null) {

            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn xóa không?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    ChiNhanh chiNhanh = (ChiNhanh) selectedObject;
                    int idChiNhanh = chiNhanh.getIdChiNhanh();

                    try {
                        chiNhanhService.deleteChiNhanh(Integer.toString(idChiNhanh));
                        this.loadTableDataChiNhanh(this.tbChiNhanh, null);
                        MessageBox.getBox("Question", "Xóa chi nhánh thành công", Alert.AlertType.INFORMATION).show();
                        this.loadALL();
                    } catch (SQLException ex) {
                        MessageBox.getBox("Question", "Xóa chi nhánh thất bại", Alert.AlertType.INFORMATION).show();
                        Logger.getLogger(FormQuanLyBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } else {
            MessageBox.getBox("Question", "Hãy chọn chi nhánh cần xóa!", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void editChiNhanh(ActionEvent evt) {
        Object selectedObject = this.tbChiNhanh.getSelectionModel().getSelectedItem();
        if (selectedObject != null) {
            ChiNhanh chiNhanh = (ChiNhanh) selectedObject;
            this.txtChiNhanh_diaChi.setText(chiNhanh.getDiaChi());
            this.txtChiNhanh_id.setText(Integer.toString(chiNhanh.getIdChiNhanh()));
        } else {
            MessageBox.getBox("Question", "Hãy chọn chi nhánh cần sửa!", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void updateChiNhanh(ActionEvent evt) throws SQLException {
        if (!this.txtChiNhanh_id.getText().isEmpty()) {
            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn sửa không?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {

                    if (!this.txtChiNhanh_diaChi.getText().isEmpty()) {
                        String idChiNhanh = this.txtChiNhanh_id.getText();
                        String tenChiNhanhNew = this.txtChiNhanh_diaChi.getText();

                        try {
                            chiNhanhService.updateChiNhanh(idChiNhanh, tenChiNhanhNew);
                            this.loadTableDataChiNhanh(this.tbChiNhanh, null);
                            MessageBox.getBox("Question", "Cập nhật chi nhánh thành công", Alert.AlertType.INFORMATION).show();
                            this.loadTableDataChiNhanh(this.tbChiNhanh, null);

                            this.loadALL();
                        } catch (SQLException ex) {
                            MessageBox.getBox("Question", "Cập nhật chi nhánh thất bại", Alert.AlertType.INFORMATION).show();
                            Logger.getLogger(FormQuanLyBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        MessageBox.getBox("Question", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.INFORMATION).show();
                    }
                }
            });

        } else {
            MessageBox.getBox("Question", "Hãy chọn chi nhánh cần cập nhật", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void addNhanVien(ActionEvent evt) throws SQLException {

        if (!this.txtNhanVien_hoNhanVien.getText().isEmpty() && !this.txtNhanVien_tenNhanVien.getText().isEmpty()
                && !this.txtNhanVien_taiKhoan.getText().isEmpty() && !this.txtNhanVien_matKhau.getText().isEmpty()
                && this.cbChiNhanh.getValue() != null) {

            List<NhanVien> nhanViens = nhanVienService.getNhanViens(null);
            int idNhanVien = nhanViens.get(nhanViens.size() - 1).getIdNhanVien() + 1;

            String hoNhanVien = this.txtNhanVien_hoNhanVien.getText();
            String tenNhanVien = this.txtNhanVien_tenNhanVien.getText();
            int idChiNhanh = this.cbChiNhanh.getSelectionModel().getSelectedItem().getIdChiNhanh();
            String taiKhoan = this.txtNhanVien_taiKhoan.getText();
            String matKhau = this.txtNhanVien_matKhau.getText();
            boolean quanLy = this.cbNhanVien.getSelectionModel().getSelectedIndex() == 1;

            NhanVien nhanVien = new NhanVien(idNhanVien, hoNhanVien, tenNhanVien, idChiNhanh, taiKhoan, matKhau, quanLy);

            try {
                nhanVienService.addNhanVien(nhanVien);
                this.loadTableDataNhanVien(this.tbNhanVien, null);
                MessageBox.getBox("Question", "Thêm nhân viên thành công", Alert.AlertType.INFORMATION).show();
                this.loadALL();
            } catch (SQLException ex) {
                MessageBox.getBox("Question", "Thêm nhân viên thất bại", Alert.AlertType.INFORMATION).show();
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            MessageBox.getBox("Question", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void deleteNhanVien(ActionEvent evt) {
        Object selectedObject = this.tbNhanVien.getSelectionModel().getSelectedItem();

        if (selectedObject != null) {

            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn xóa không?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    NhanVien nhanVien = (NhanVien) selectedObject;
                    int idNhanVien = nhanVien.getIdNhanVien();

                    try {
                        nhanVienService.deleteNhanVien(Integer.toString(idNhanVien));
                        this.loadTableDataNhanVien(this.tbNhanVien, null);
                        MessageBox.getBox("Question", "Xóa nhân viên thành công", Alert.AlertType.INFORMATION).show();
                    } catch (SQLException ex) {
                        MessageBox.getBox("Question", "Xóa nhân viên thất bại", Alert.AlertType.INFORMATION).show();
                        Logger.getLogger(FormQuanLyBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } else {
            MessageBox.getBox("Question", "Hãy chọn nhân viên cần xóa!", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void editNhanVien(ActionEvent evt) throws SQLException {
        Object selectedObject = this.tbNhanVien.getSelectionModel().getSelectedItem();
        if (selectedObject != null) {
            NhanVien nhanVien = (NhanVien) selectedObject;
            this.txtNhanVien_id.setText(Integer.toString(nhanVien.getIdNhanVien()));
            this.txtNhanVien_hoNhanVien.setText(nhanVien.getHoNhanVien());
            this.txtNhanVien_tenNhanVien.setText(nhanVien.getTenNhanVien());
            this.txtNhanVien_taiKhoan.setText(nhanVien.getTaiKhoan());
            this.txtNhanVien_matKhau.setText(nhanVien.getMatKhau());

            List<ChiNhanh> chiNhanh = chiNhanhService.getChiNhanhs(Integer.toString(nhanVien.getIdChiNhanh()), null);
            String loaiNhanVien = Boolean.toString(nhanVien.isQuanLy()).equals("true") ? "Quản lý" : "Nhân viên";

            this.cbChiNhanh.setValue(chiNhanh.get(0)); // Chỗ này ảo ma???? Khi vừa loaddAll xong thì lần đầu nó k hiển thị đc value
            this.cbNhanVien.setValue(loaiNhanVien);
            MessageBox.getBox(loaiNhanVien, chiNhanh.get(0).getIdChiNhanh() + chiNhanh.get(0).getDiaChi(), Alert.AlertType.CONFIRMATION).show();
        } else {
            MessageBox.getBox("Question", "Hãy chọn nhân viên cần sửa!", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void updateNhanVien(ActionEvent evt) {
        if (!this.txtNhanVien_id.getText().isEmpty()) {
            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn sửa không?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {

                    if (!this.txtNhanVien_hoNhanVien.getText().isEmpty() && !this.txtNhanVien_tenNhanVien.getText().isEmpty()
                            && !this.txtNhanVien_taiKhoan.getText().isEmpty() && !this.txtNhanVien_matKhau.getText().isEmpty()
                            && this.cbChiNhanh.getValue() != null) {

                        String idNhanVien = this.txtNhanVien_id.getText();
                        String hoNhanVienNew = this.txtNhanVien_hoNhanVien.getText();
                        String tenNhanVienNew = this.txtNhanVien_tenNhanVien.getText();
                        String taiKhoan = this.txtNhanVien_taiKhoan.getText();
                        String matKhau = this.txtNhanVien_matKhau.getText();
                        int idChiNhanh = this.cbChiNhanh.getSelectionModel().getSelectedItem().getIdChiNhanh();
                        boolean quanLy = this.cbNhanVien.getSelectionModel().getSelectedIndex() == 1;

                        try {
                            nhanVienService.updateNhanVien(idNhanVien, hoNhanVienNew, tenNhanVienNew, idChiNhanh, taiKhoan, matKhau, quanLy);
                            this.loadTableDataNhanVien(this.tbNhanVien, null);
                            MessageBox.getBox("Question", "Cập nhật nhân viên thành công", Alert.AlertType.INFORMATION).show();
                            this.loadALL();
                        } catch (SQLException ex) {
                            MessageBox.getBox("Question", "Cập nhật nhân viên thất bại", Alert.AlertType.INFORMATION).show();
                            Logger.getLogger(FormQuanLyBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        MessageBox.getBox("Question", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.INFORMATION).show();
                    }
                }
            });

        } else {
            MessageBox.getBox("Question", "Hãy chọn nhân viên cần cập nhật", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void addKhuyenMai(ActionEvent evt) throws SQLException {
        if (!this.txtKhuyenMai_tenKhuyenMai.getText().isEmpty() && !this.txtKhuyenMai_giaTri.getText().isEmpty()
                && this.dpKhuyenMai_ngayBatDau.getValue() != null && this.dpKhuyenMai_ngayKetThuc.getValue() != null) {

            List<KhuyenMai> khuyenMais = khuyenMaiService.getKhuyenMai(null);
            int idKhuyenMai = khuyenMais.get(khuyenMais.size() - 1).getIdKhuyenMai() + 1;

            String tenKhuyenMai = this.txtKhuyenMai_tenKhuyenMai.getText();
            double giaTri = Double.parseDouble(this.txtKhuyenMai_giaTri.getText());

            // Exception ngay chỗ valueOf() vì nó không thể lấy được tham số truyền vào (null)
            // Mà null thì sao qua được cái if ở trên được :) ?
            // Nhưng nó vẫn chạy được và chuyển được từ LocalDate sang Date bình thường :)))
            // Ảo ma :))) ???
            LocalDate ngayBatDau_LocalDate = this.dpKhuyenMai_ngayBatDau.getValue();
            Date ngayBatDau = Date.valueOf(ngayBatDau_LocalDate);
            LocalDate ngayKetThuc_LocalDate = this.dpKhuyenMai_ngayKetThuc.getValue();
            Date ngayKetThuc = Date.valueOf(ngayKetThuc_LocalDate);

            KhuyenMai khuyenMai = new KhuyenMai(idKhuyenMai, tenKhuyenMai, giaTri, ngayBatDau, ngayKetThuc);

            try {
                khuyenMaiService.addKhuyenMai(khuyenMai);
                this.loadTableDataKhuyenMai(this.tbKhuyenMai, null);
                MessageBox.getBox("Question", "Thêm khuyến mãi thành công", Alert.AlertType.INFORMATION).show();
                this.loadALL();
            } catch (SQLException ex) {
                MessageBox.getBox("Question", "Thêm khuyến mãi thất bại", Alert.AlertType.INFORMATION).show();
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            MessageBox.getBox("Question", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void deleteKhuyenMai(ActionEvent evt) {
        Object selectedObject = this.tbKhuyenMai.getSelectionModel().getSelectedItem();

        if (selectedObject != null) {
            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn xóa không?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    KhuyenMai khuyenMai = (KhuyenMai) selectedObject;
                    int idKhuyenMai = khuyenMai.getIdKhuyenMai();

                    try {
                        khuyenMaiService.deleteKhuyenMai(Integer.toString(idKhuyenMai));
                        this.loadTableDataKhuyenMai(this.tbKhuyenMai, null);
                        MessageBox.getBox("Question", "Xóa khuyến mãi thành công", Alert.AlertType.INFORMATION).show();
                    } catch (SQLException ex) {
                        MessageBox.getBox("Question", "Xóa khuyến mãi thất bại", Alert.AlertType.INFORMATION).show();
                        Logger.getLogger(FormQuanLyBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } else {
            MessageBox.getBox("Question", "Hãy chọn khuyến mãi cần xóa!", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void editKhuyenMai(ActionEvent evt) throws SQLException {
        Object selectedObject = this.tbKhuyenMai.getSelectionModel().getSelectedItem();
        if (selectedObject != null) {
            KhuyenMai khuyenMai = (KhuyenMai) selectedObject;
            if (khuyenMai.getIdKhuyenMai() == 0) {
                MessageBox.getBox("Question", "Bạn không được sửa giá trị này!", Alert.AlertType.INFORMATION).show();
            } else {
                this.txtKhuyenMai_id.setText(Integer.toString(khuyenMai.getIdKhuyenMai()));
                this.txtKhuyenMai_tenKhuyenMai.setText(khuyenMai.getTenKhuyenMai());
                this.txtKhuyenMai_giaTri.setText(Double.toString(khuyenMai.getGiaTri()));

//            Instant instantNgayBatDau = khuyenMai.getNgayBatDau().toInstant();
//            LocalDate ngayBatDau = instantNgayBatDau.atZone(ZoneId.systemDefault()).toLocalDate();
                this.dpKhuyenMai_ngayBatDau.setValue(khuyenMai.getNgayBatDau().toLocalDate());
                this.dpKhuyenMai_ngayKetThuc.setValue(khuyenMai.getNgayKetThuc().toLocalDate());
            }
        } else {
            MessageBox.getBox("Question", "Hãy chọn khuyến mãi cần sửa!", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void updateKhuyenMai(ActionEvent evt) {
        if (!this.txtKhuyenMai_id.getText().isEmpty()) {
            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn sửa không?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {

                    if (!this.txtKhuyenMai_tenKhuyenMai.getText().isEmpty() && !this.txtKhuyenMai_giaTri.getText().isEmpty()
                            && this.dpKhuyenMai_ngayBatDau.getValue() != null && this.dpKhuyenMai_ngayKetThuc.getValue() != null) {

                        String idKhuyenMai = this.txtKhuyenMai_id.getText();
                        String tenKhuyenMai = this.txtKhuyenMai_tenKhuyenMai.getText();
                        double giaTri = Double.parseDouble(this.txtKhuyenMai_giaTri.getText());
                        LocalDate ngayBatDauTemp = this.dpKhuyenMai_ngayBatDau.getValue();
                        Date ngayBatDau = Date.valueOf(ngayBatDauTemp);
                        LocalDate ngayKetThucTemp = this.dpKhuyenMai_ngayKetThuc.getValue();
                        Date ngayKetThuc = Date.valueOf(ngayKetThucTemp);

                        try {
                            khuyenMaiService.updateKhuyenMai(idKhuyenMai, tenKhuyenMai, giaTri, ngayBatDau, ngayKetThuc);
                            this.loadTableDataKhuyenMai(this.tbKhuyenMai, null);
                            MessageBox.getBox("Question", "Cập nhật khuyến mãi thành công", Alert.AlertType.INFORMATION).show();
                            this.loadALL();
                        } catch (SQLException ex) {
                            MessageBox.getBox("Question", "Cập nhật khuyến mãi thất bại", Alert.AlertType.INFORMATION).show();
                            Logger.getLogger(FormQuanLyBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        MessageBox.getBox("Question", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.INFORMATION).show();
                    }
                }
            });

        } else {
            MessageBox.getBox("Question", "Hãy chọn khuyến mãi cần cập nhật", Alert.AlertType.INFORMATION).show();
        }
    }

    private void addTextChangeDatePickerKhuyenMai(DatePicker datePicker1, DatePicker datePicker2) {
        datePicker1.valueProperty().addListener(e -> {

            if (datePicker2.getValue() != null) {
                Date ngayBatDau = Date.valueOf(datePicker1.getValue());
                Date ngayKetThuc = Date.valueOf(datePicker2.getValue());
                if (ngayBatDau.compareTo(ngayKetThuc) > 0) {
                    MessageBox.getBox("Question", "Ngày bắt đầu phải nhỏ hơn ngày kết thúc", Alert.AlertType.INFORMATION).show();
                    this.dpKhuyenMai_ngayBatDau.setValue(ngayKetThuc.toLocalDate().minusDays(1));
                }
            }
        });

        datePicker2.valueProperty().addListener(e -> {
            if (datePicker1.getValue() != null) {
                Date ngayBatDau = Date.valueOf(datePicker1.getValue());
                Date ngayKetThuc = Date.valueOf(datePicker2.getValue());
                if (ngayBatDau.compareTo(ngayKetThuc) > 0) {
                    MessageBox.getBox("Question", "Ngày kết thúc phải lớn hơn ngày bắt đầu", Alert.AlertType.INFORMATION).show();
                    this.dpKhuyenMai_ngayKetThuc.setValue(ngayBatDau.toLocalDate().plusDays(1));
                }
            }
        });
    }

    @FXML
    public void addSanPham(ActionEvent evt) throws SQLException {
        if (!this.txtSanPham_donVi.getText().isEmpty() && !this.txtSanPham_tenSanPham.getText().isEmpty()
                && !this.txtSanPham_gia.getText().isEmpty() && this.cbKhuyenMai.getValue() != null) {

            List<SanPham> sanPhams = sanPhamService.getSanPham(null);
            int idSanPham = sanPhams.get(sanPhams.size() - 1).getIdSanPham() + 1;
//            MessageBox.getBox(Integer.toString(idSanPham), Integer.toString(idSanPham), Alert.AlertType.CONFIRMATION);

            String tenSanPham = this.txtSanPham_tenSanPham.getText();
            double giaTri = Double.parseDouble(this.txtSanPham_gia.getText());
            String donVi = this.txtSanPham_donVi.getText();

            int idKhuyenMai = this.cbKhuyenMai.getSelectionModel().getSelectedItem().getIdKhuyenMai();

//            MessageBox.getBox(Integer.toString(idSanPham), Integer.toString(idKhuyenMai), Alert.AlertType.CONFIRMATION);
            SanPham sanPham = new SanPham(idSanPham, tenSanPham, giaTri, donVi, idKhuyenMai);

            try {
                sanPhamService.addSanPham(sanPham);
                this.loadTableDataSanPham(this.tbSanPham, null);
                MessageBox.getBox("Question", "Thêm sản phẩm thành công", Alert.AlertType.INFORMATION).show();
                this.loadALL();
            } catch (SQLException ex) {
                MessageBox.getBox("Question", "Thêm sản phẩm thất bại", Alert.AlertType.INFORMATION).show();
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            MessageBox.getBox("Question", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void deleteSanPham(ActionEvent evt) {
        Object selectedObject = this.tbSanPham.getSelectionModel().getSelectedItem();

        if (selectedObject != null) {
            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn xóa không?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    SanPham sanPham = (SanPham) selectedObject;
                    int idSanPham = sanPham.getIdSanPham();

                    try {
                        sanPhamService.deleteSanPham(Integer.toString(idSanPham));
                        this.loadTableDataSanPham(this.tbSanPham, null);
                        MessageBox.getBox("Question", "Xóa khuyến mãi thành công", Alert.AlertType.INFORMATION).show();
                    } catch (SQLException ex) {
                        MessageBox.getBox("Question", "Xóa khuyến mãi thất bại", Alert.AlertType.INFORMATION).show();
                        Logger.getLogger(FormQuanLyBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } else {
            MessageBox.getBox("Question", "Hãy chọn khuyến mãi cần xóa!", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void editSanPham(ActionEvent evt) throws SQLException {
        Object selectedObject = this.tbSanPham.getSelectionModel().getSelectedItem();
        if (selectedObject != null) {
            SanPham sanPham = (SanPham) selectedObject;
            this.txtSanPham_id.setText(Integer.toString(sanPham.getIdSanPham()));
            this.txtSanPham_tenSanPham.setText(sanPham.getTenSanPham());
            this.txtSanPham_gia.setText(Double.toString(sanPham.getGia()));
            this.txtSanPham_donVi.setText(sanPham.getDonVi());
            List<KhuyenMai> khuyenMai = khuyenMaiService.getKhuyenMai(Integer.toString(sanPham.getIdKhuyenMai()), null, null);
            this.cbKhuyenMai.setValue(khuyenMai.get(0));
        } else {
            MessageBox.getBox("Question", "Hãy chọn sản phẩm cần sửa!", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void updateSanPham(ActionEvent evt) {
        if (!this.txtSanPham_id.getText().isEmpty()) {
            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn sửa không?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {

                    if (!this.txtSanPham_donVi.getText().isEmpty() && !this.txtSanPham_tenSanPham.getText().isEmpty()
                            && !this.txtSanPham_gia.getText().isEmpty() && this.cbKhuyenMai.getValue() != null) {

                        String idSanPham = this.txtSanPham_id.getText();
                        String tenSanPham = this.txtSanPham_tenSanPham.getText();
                        double gia = Double.parseDouble(this.txtSanPham_gia.getText());
                        String donVi = this.txtSanPham_donVi.getText();
                        int idKhuyenMai = this.cbKhuyenMai.getValue().getIdKhuyenMai();

                        try {
                            sanPhamService.updateSanPham(idSanPham, tenSanPham, gia, donVi, idKhuyenMai);
                            this.loadTableDataKhuyenMai(this.tbKhuyenMai, null);
                            MessageBox.getBox("Question", "Cập nhật sản phẩm thành công", Alert.AlertType.INFORMATION).show();
                            this.loadALL();
                        } catch (SQLException ex) {
                            MessageBox.getBox("Question", "Cập nhật sản phẩm thất bại", Alert.AlertType.INFORMATION).show();
                            Logger.getLogger(FormQuanLyBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        MessageBox.getBox("Question", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.INFORMATION).show();
                    }
                }
            });

        } else {
            MessageBox.getBox("Question", "Hãy chọn sản phẩm cần cập nhật", Alert.AlertType.INFORMATION).show();
        }
    }
}
