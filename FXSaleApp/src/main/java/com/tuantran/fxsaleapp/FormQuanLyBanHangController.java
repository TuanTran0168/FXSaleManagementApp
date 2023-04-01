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
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author TuanTran
 */
public class FormQuanLyBanHangController implements Initializable {

    static final ChiNhanhService chiNhanhService = new ChiNhanhService();
    static final NhanVienService nhanVienService = new NhanVienService();
    static final KhuyenMaiService khuyenMaiService = new KhuyenMaiService();
    static final SanPhamService sanPhamService = new SanPhamService();

    @FXML
    private TableView<ChiNhanh> tbChiNhanh;
    @FXML
    private TableView<NhanVien> tbNhanVien;
    @FXML
    private TableView<KhuyenMai> tbKhuyenMai;
    @FXML
    private TableView<SanPham> tbSanPham;

    @FXML
    private TextField txtChiNhanh;
    @FXML
    private TextField txtNhanVien_hoNhanVien;
    @FXML
    private TextField txtNhanVien_tenNhanVien;
    @FXML
    private TextField txtNhanVien_taiKhoan;
    @FXML
    private TextField txtNhanVien_matKhau;

    @FXML
    private TextField txtKhuyenMai_tenKhuyenMai;
    @FXML
    private TextField txtKhuyenMai_giaTri;

    @FXML
    private TextField txtSanPham_tenSanPham;
    @FXML
    private TextField txtSanPham_donVi;
    @FXML
    private TextField txtSanPham_gia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            this.loadTableColumnChiNhanh(tbChiNhanh);
            this.loadTableColumnNhanVien(tbNhanVien);
            this.loadTableColumnKhuyenMai(tbKhuyenMai);
            this.loadTableColumnSanPham(tbSanPham);

            this.loadTableDataChiNhanh(tbChiNhanh, null);
            this.loadTableDataNhanVien(tbNhanVien, null);
            this.loadTableDataKhuyenMai(tbKhuyenMai, null);
            this.loadTableDataSanPham(tbSanPham, null);

            this.addTextChangeChiNhanh(this.txtChiNhanh, tbChiNhanh);
            this.addTextChangeNhanVien(this.txtNhanVien_hoNhanVien, this.txtNhanVien_tenNhanVien, this.txtNhanVien_taiKhoan, this.txtNhanVien_matKhau, tbNhanVien);
            this.addTextChangeKhuyenMai(this.txtKhuyenMai_tenKhuyenMai, this.txtKhuyenMai_giaTri, tbKhuyenMai);
            this.addTextChangeSanPham(null, this.txtSanPham_tenSanPham, this.txtSanPham_gia, this.txtSanPham_donVi, this.tbSanPham);
        } catch (SQLException ex) {
            Logger.getLogger(FormQuanLyBanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadTableColumnChiNhanh(TableView tableView) {
        TableColumn colIdChiNhanh = new TableColumn("Mã chi nhánh");
        TableColumn colDiaChi = new TableColumn("Địa chỉ");
//        pojo
        colIdChiNhanh.setCellValueFactory(new PropertyValueFactory("idChiNhanh"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory("diaChi"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colIdChiNhanh, colDiaChi);
    }

    public void loadTableColumnNhanVien(TableView tableView) {
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

        tableView.getColumns().addAll(colIdThanhVien, colHoThanhVien, colTenThanhVien, colIdChiNhanh, colTaiKhoan, colMatKhau, colQuanLy);
    }

    public void loadTableColumnKhuyenMai(TableView tableView) {
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

        tableView.getColumns().addAll(colIdKhuyenMai, colTenKhuyenMai, colGiaTri, colNgayBatDau, colNgayKetThuc);
    }

    public void loadTableColumnSanPham(TableView tableView) {
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

    public void loadTableDataChiNhanh(TableView tableView, String keyword) throws SQLException {
        List<ChiNhanh> chiNhanhs = chiNhanhService.getChiNhanhs(keyword);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(chiNhanhs));
    }

    public void loadTableDataNhanVien(TableView tableView, String keyword) throws SQLException {
        List<NhanVien> nhanViens = nhanVienService.getNhanViens(keyword);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(nhanViens));
    }

    public void loadTableDataNhanVien(TableView tableView, String keyword_hoNhanVien, String keyword_tenNhanVien, String keyword_taiKhoan, String keyword_matKhau) throws SQLException {
        List<NhanVien> nhanViens = nhanVienService.getNhanViens(keyword_hoNhanVien, keyword_tenNhanVien, keyword_taiKhoan, keyword_matKhau);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(nhanViens));
    }

    public void loadTableDataKhuyenMai(TableView tableView, String keyword) throws SQLException {
        List<KhuyenMai> khuyenMais = khuyenMaiService.getKhuyenMai(keyword);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(khuyenMais));
    }

    public void loadTableDataKhuyenMai(TableView tableView, String keyword_tenKhuyenMai, String keyword_giaTri) throws SQLException {
        List<KhuyenMai> khuyenMais = khuyenMaiService.getKhuyenMai(keyword_tenKhuyenMai, keyword_giaTri);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(khuyenMais));
    }

    public void loadTableDataSanPham(TableView tableView, String keyword) throws SQLException {
        List<SanPham> sanPhams = sanPhamService.getSanPham(keyword);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(sanPhams));
    }

    public void loadTableDataSanPham(TableView tableView, String keyword_id, String keyword_tenSanPham, String keyword_gia, String keyword_donVi) throws SQLException {
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
}
