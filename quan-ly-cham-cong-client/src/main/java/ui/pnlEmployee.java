package ui;

import dao.*;
import entities.Account;
import entities.Department;
import entities.Employee;
import entities.Timekeeping;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

import com.formdev.flatlaf.icons.FlatClearIcon;
import com.toedter.calendar.JDateChooser;

import controller.Controller;
import custom.Custom;
import custom.DateRenderer;
import net.miginfocom.swing.MigLayout;

/**
 * @author HOÀNG HIỆN & TRỌNG TÀI - PANEL QUẢN LÝ NHÂN VIÊN
 */
public class pnlEmployee extends JPanel implements ActionListener, MouseListener, ItemListener {

	public pnlEmployee() {
		int width = 1920;
		int height = (width * 9) / 16;
		setSize(width, height);
		setLayout(new BorderLayout(0, 0));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(new BorderLayout(0, 0));

		/* NORTH PANEL */
		JPanel pnlNorth = new JPanel();
		pnlNorth.setBackground(Color.CYAN);
		contentPane.add(pnlNorth, BorderLayout.NORTH);

		JLabel lblTittle = new JLabel("DANH SÁCH NHÂN VIÊN");

		lblTittle.setFont(new Font("Tahoma", Font.BOLD, 30));
		pnlNorth.add(lblTittle);

		/* CENTER PANEL - PANEL CHÍNH */
		JPanel pnlCenter = new JPanel();
		;
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(new BorderLayout(0, 0));

		JPanel pnlEInfo = new JPanel();
		pnlEInfo.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 0, 5),
				new TitledBorder(
						new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
						"Thông tin nhân viên", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))));
		pnlCenter.add(pnlEInfo, BorderLayout.NORTH);
		pnlEInfo.setLayout(new MigLayout("", "[grow][9px]", "[10px,grow]"));

		JPanel pnlEInfoContainer = new JPanel();
		pnlEInfoContainer.setBorder(new EmptyBorder(0, 100, 0, 50));
		pnlEInfo.add(pnlEInfoContainer, "cell 0 0,grow");
		pnlEInfoContainer.setLayout(new MigLayout("", "[][grow][grow 10][grow]", "[][][][][][][grow]"));

		/* THÔNG TIN VỀ NHÂN VIÊN - MÃ NHÂN VIÊN / TÊN NHÂN VIÊN... */
		JLabel lblID = new JLabel("Mã nhân viên:");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblID, "cell 0 0,alignx trailing");

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtID, "cell 1 0,growx");
		txtID.setColumns(10);

		JLabel lblPN = new JLabel("Số điện thoại:");
		lblPN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblPN, "cell 2 0,alignx trailing");

		txtPN = new JTextField();
		txtPN.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtPN, "cell 3 0,growx");
		txtPN.setColumns(10);

		JLabel lblName = new JLabel("Họ và tên:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblName, "cell 0 1,alignx trailing");

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtName, "cell 1 1,growx");
		txtName.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblEmail, "cell 2 1,alignx trailing");

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtEmail, "cell 3 1,growx");
		txtEmail.setColumns(10);

		JLabel lblDoB = new JLabel("Ngày sinh:");
		lblDoB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblDoB, "cell 0 2,alignx trailing");

		txtDoB = new JDateChooser();
		txtDoB.setDateFormatString("dd/MM/yyyy");
		txtDoB.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtDoB, "cell 1 2,growx");

		JLabel lblDepartment = new JLabel("Phòng ban:");
		lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblDepartment, "cell 2 2,alignx trailing");

		cmbDepartments = new JComboBox<>();
		cmbDepartments.addItemListener(this);
		cmbDepartments.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(cmbDepartments, "cell 3 2,growx");

		JLabel lblGender = new JLabel("Giới tính:");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblGender, "cell 0 3,alignx trailing");

		radM = new JRadioButton("Nam");
		radM.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(radM, "flowx,cell 1 3");

		radF = new JRadioButton("Nữ");
		radF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(radF, "cell 1 3");

		/* GROUP RADIO BUTTON GIỚI TÍNH */
		ButtonGroup group = new ButtonGroup();
		group.add(radM);
		group.add(radF);

		JLabel lblRole = new JLabel("Chức vụ:");
		lblRole.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblRole, "cell 2 3,alignx trailing");

		cmbRoles = new JComboBox<String>();

		cmbRoles.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(cmbRoles, "cell 3 3,growx");

		JLabel lblAddress = new JLabel("Địa chỉ:");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblAddress, "cell 0 4,alignx trailing");

		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtAddress, "cell 1 4,growx");
		txtAddress.setColumns(10);

		JLabel lblDoW = new JLabel("Ngày vào làm:");
		lblDoW.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblDoW, "cell 2 4,alignx trailing");

		txtDoW = new JDateChooser();
		txtDoW.setDateFormatString("dd/MM/yyyy");
		txtDoW.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtDoW, "cell 3 4,growx");

		JLabel lblBS = new JLabel("Lương cơ bản:");
		lblBS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblBS, "cell 2 5,alignx trailing");

		txtBS = new JTextField();
		txtBS.setEditable(false);
		txtBS.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtBS, "cell 3 5,growx");
		txtBS.setColumns(10);

		JPanel pnlEButton = new JPanel();
		pnlEButton.setBorder(new EmptyBorder(30, 0, 0, 0));
		pnlEInfoContainer.add(pnlEButton, "cell 0 7 4 1,grow");

		btnAdd = new JButton("Thêm");
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 24));
		pnlEButton.add(btnAdd);

		btnRemove = new JButton("Xoá");
		btnRemove.setFont(new Font("Tahoma", Font.BOLD, 24));
		pnlEButton.add(btnRemove);

		btnUpdate = new JButton("Cập Nhật");
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 24));
		pnlEButton.add(btnUpdate);

		JPanel pnlImg = new JPanel();
		pnlImg.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 100),
				new TitledBorder(
						new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
						"Ảnh nhân viên", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))));
		pnlEInfo.add(pnlImg, "cell 1 0,alignx left,aligny top");
		pnlImg.setLayout(new BorderLayout(0, 0));

		JPanel pnlImgContainer = new JPanel();
		pnlImg.add(pnlImgContainer);

		ImageIcon originalIcon = new ImageIcon(".\\src\\main\\resources\\img\\img.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(210, 280, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		lblImg = new JLabel(scaledIcon);
		pnlImgContainer.add(lblImg);

		JPanel pnlImgButton = new JPanel();
		pnlImg.add(pnlImgButton, BorderLayout.SOUTH);

		btnImg = new JButton("Hình ảnh");
		btnImg.setEnabled(false);
		btnImg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlImgButton.add(btnImg);

		/* PANEL SOUTH - DANH SÁCH NHÂN VIÊN */
		JPanel pnlSouth = new JPanel();
		contentPane.add(pnlSouth, BorderLayout.SOUTH);
		pnlSouth.setLayout(new BorderLayout(0, 0));

		JPanel pnlEList = new JPanel();
		pnlEList.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 0, 5),
				new TitledBorder(
						new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
						"Danh sách nhân viên", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))));
		pnlSouth.add(pnlEList);

		String[] header = { "#", "ID", "Họ và tên", "Ngày sinh", "Giới tính", "Địa chỉ", "Số điện thoại", "Email",
				"Phòng ban", "Chức vụ", "Ngày Vào Làm", "Lương cơ bản" };

		tableModel = new DefaultTableModel(header, 0);
		pnlEList.setLayout(new BorderLayout(0, 0));

		JPanel pnlEListContainer = new JPanel();
		pnlEList.add(pnlEListContainer);
		pnlEListContainer.setLayout(new BorderLayout(0, 0));

		tblTabble = new JTable(tableModel);
		tblTabble.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblTabble.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JScrollPane scrScrollPanel = new JScrollPane(tblTabble);
		scrScrollPanel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrScrollPanel.getVerticalScrollBar().setForeground(Color.RED);
		scrScrollPanel.getVerticalScrollBar().setBackground(Color.YELLOW);

		pnlEListContainer.add(scrScrollPanel);

		//

		JPanel pnlEListButton = new JPanel();
		FlowLayout fl_pnlEListButton = (FlowLayout) pnlEListButton.getLayout();
		fl_pnlEListButton.setAlignment(FlowLayout.RIGHT);
		pnlEList.add(pnlEListButton, BorderLayout.NORTH);

		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(this);

		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEListButton.add(txtSearch);
		txtSearch.setColumns(15);

		btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSearch.addActionListener(this);
		pnlEListButton.add(btnSearch);
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEListButton.add(btnRefresh);

		btnAdd.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnRemove.addActionListener(this);
		tblTabble.addMouseListener(this);

		setEnable(false);

		tblTabble.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer(Custom.dateFormat));
		tblTabble.getColumnModel().getColumn(10).setCellRenderer(new DateRenderer(Custom.dateFormat));

		alignTable();
		UpdateTabble();

		btnRemove.setEnabled(false);
		btnUpdate.setEnabled(false);

		cmbDepartments.setEnabled(false);
		cmbRoles.setEnabled(false);
//		Custom.restrictToNumbers(txtBS.getDocument());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnRefresh)) {
			UpdateTabble();
		}
		if (o.equals(btnSearch)) {
			search();
		}
		if (o.equals(btnAdd)) {
			if (btnAdd.getText().trim().equals("Thêm")) {
				btnAdd.setText("Lưu");
				btnRemove.setText("Huỷ");
				btnUpdate.setText("Xoá Trắng");
				clear();
				setEnable(true);
				Year currentYear = Year.now();
				String yearAsString = String.valueOf(currentYear.getValue());
				StringBuilder builder = new StringBuilder(yearAsString);

				int employeeCount = Controller.countEmployee();
				String idPrefix = Year.now().toString(); // Lấy 2 chữ số cuối của năm hiện tại
				idPrefix = idPrefix.length() == 1 ? "0" + idPrefix : idPrefix; // Đảm bảo idPrefix có 2 chữ số

				String idSuffix = "000";
				if (employeeCount >= 9 && employeeCount < 99) {
					idSuffix = "00";
				} else if (employeeCount >= 99) {
					// handle this case if necessary
				}

				String y = idPrefix + idSuffix + (employeeCount + 1);

				List<Employee> employees = Controller.allEmployees();
				for (Employee employee : employees) {
					if (employee.getId().equals(y)) {
						employeeCount++;
						idSuffix = "000";
						if (employeeCount >= 9 && employeeCount < 99) {
							idSuffix = "00";
						} else if (employeeCount >= 99) {
							// handle this case if necessary
						}
						y = idPrefix + idSuffix + (employeeCount + 1);
					}
				}

				txtID.setText(y);
				if (Controller.getAccount().getIsAdmin()) {
					cmbDepartments.setEnabled(true);
					cmbDepartments.removeAllItems();
					for (Department a : Controller.allDepartments()) {
						cmbDepartments.addItem(a);
					}
					cmbRoles.setEnabled(true);
					cmbRoles.removeAllItems();
					cmbRoles.addItem("Nhân Viên");
					cmbRoles.addItem("Trưởng Phòng");
				} else {
					Department d = Controller.getAccount().getEmployee().getDepartment();
					cmbDepartments.addItem(d);
					cmbRoles.removeAllItems();
					cmbRoles.addItem("Nhân Viên");
				}
				txtDoW.setDate(new Date());
				btnRemove.setEnabled(true);
				btnUpdate.setEnabled(true);
			} else {
				if (validateEmployeeData()) {
					btnAdd.setText("Thêm");
					btnRemove.setText("Xoá");
					btnUpdate.setText("Cập Nhật");
					btnRemove.setEnabled(false);
					btnUpdate.setEnabled(false);
					addAction();
				}
			}
		}
		if (o.equals(btnRemove)) {
			if (btnRemove.getText().trim().equals("Xoá")) {
				deleteAction();
			} else {
				btnAdd.setText("Thêm");
				btnRemove.setText("Xoá");
				btnUpdate.setText("Cập Nhật");
				btnRemove.setEnabled(false);
				btnUpdate.setEnabled(false);
				cmbDepartments.setEnabled(false);
				cmbRoles.setEnabled(false);
				setEnable(false);
				clear();
			}
		}

		if (o.equals(btnUpdate)) {
			if (btnUpdate.getText().trim().equals("Cập Nhật")) {
				updateAction();

			} else {
				clear();
			}
		}
	}

	private void search() {
		tableModel.setRowCount(0);

		String keyword = txtSearch.getText().trim().toLowerCase();

		List<Employee> list = null;
		if (Controller.getAccount().getIsAdmin()) {
			list = Controller.allEmployees();
		} else {
			list = Controller.employeeByDepartment(Controller.getAccount().getEmployee().getDepartment().getId());
		}

		for (Employee a : list) {
			if (!keyword.isEmpty() && (String.valueOf(a.getId()).toLowerCase().contains(keyword)
					|| a.getName().toLowerCase().contains(keyword))) {
				addRowToTable(a);
			} else if (keyword.isEmpty()) {
				addRowToTable(a);
			}
		}

	}

	private void addRowToTable(Employee a) {
		tableModel.addRow(new Object[] { tblTabble.getRowCount() + 1, a.getId(), a.getName(),
				new SimpleDateFormat("dd-MM-yyyy").format(a.getDateOfBirth()), a.isGender() ? "Nam" : "Nữ",
				a.getAddress(), a.getPhone(), a.getEmail(), a.getDepartment().getName(), a.getRole(),
				new SimpleDateFormat("dd-MM-yyyy").format(a.getDateOfJoin()), a.getSalary() });
	}

	private void deleteAction() {
		if (txtID.getText().equals(Controller.getAccount().getEmployee().getId())) {
			JOptionPane.showMessageDialog(this, "Bạn không thể xóa chính bạn!", null, JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			int choice = JOptionPane.showConfirmDialog(this, "Xác nhận xóa nhân viên?", "Xóa nhân viên",
					JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				if (Controller.removeEmployee(txtID.getText())) {
					JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!", null,
							JOptionPane.INFORMATION_MESSAGE);
					UpdateTabble();
					txtID.setText("");
					clear();
					setEnable(false);
					cmbDepartments.setEnabled(false);
					cmbRoles.setEnabled(false);
					return;
				}
				JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Xóa nhân viên", JOptionPane.ERROR_MESSAGE);
			}

		}
		System.out.println(txtID.getText());
	}

	private void UpdateTabble() {
		tableModel.setRowCount(0);
		List<Employee> list = null;
		if (Controller.getAccount().getIsAdmin()) {
			list = Controller.allEmployees();
		} else {
			list = Controller.employeeByDepartment(Controller.getAccount().getEmployee().getDepartment().getId());
		}
		for (Employee a : list) {
			tableModel.addRow(new Object[] { tblTabble.getRowCount() + 1, a.getId(), a.getName(),
					new SimpleDateFormat("dd-MM-yyyy").format(a.getDateOfBirth()), a.isGender() ? "Nam" : "Nữ",
					a.getAddress(), a.getPhone(), a.getEmail(), a.getDepartment().getName(), a.getRole(),
					new SimpleDateFormat("dd-MM-yyyy").format(a.getDateOfJoin()), a.getSalary() });
		}
	}

	private void addAction() {
		try {
			Boolean isManager = false;
			Employee e = new Employee(txtID.getText(), txtName.getText(), txtDoB.getDate(),
					(radM.isSelected() ? true : false), txtAddress.getText(), txtPN.getText(), txtEmail.getText(),
					txtDoW.getDate(), new BigDecimal(txtBS.getText()), isManager, cmbRoles.getSelectedItem().toString(),
					false, (Department) cmbDepartments.getSelectedItem());

			if (e.getRole().toLowerCase().equals("trưởng phòng")) {
				e.setIsManager(true);
			}

			if (Controller.createEmployee(e)) {
				JOptionPane.showMessageDialog(null, "Thêm thành công!");
				UpdateTabble();
			} else {
				JOptionPane.showMessageDialog(null, "Thêm thất bại!");
			}
			cmbDepartments.setEnabled(false);
			cmbRoles.setEnabled(false);
			txtID.setText("");
			clear();
			btnRemove.setEnabled(false);
			setEnable(false);
			btnAdd.setText("Thêm");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateAction() {

		try {
			JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
			clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clear() {
		txtID.setText("");
		txtName.setText("");
		txtDoB.setDate(null);
		radF.setSelected(false);
		radM.setSelected(false);
		txtAddress.setText("");
		txtPN.setText("");
		txtEmail.setText("");
		txtDoW.setDate(null);
		txtBS.setText("");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JTable target = (JTable) e.getSource();
		int row = target.getSelectedRow();
		if (target == tblTabble && (row >= 0 && row <= tblTabble.getRowCount() - 1)) {
			if (btnAdd.getText().equals("Lưu")) {
				int choice = JOptionPane.showConfirmDialog(this,
						"Bạn có chắc chắn muốn huỷ? Mọi thay đổi chưa được lưu sẽ bị mất.", "Xác nhận",
						JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					btnAdd.setText("Thêm");
					btnRemove.setText("Xóa");
					btnUpdate.setText("Cập nhật");
				}
			}
		}
		setEnable(true);
		btnUpdate.setEnabled(true);
		btnRemove.setEnabled(true);
		String id = tblTabble.getValueAt(row, 1).toString();
		Employee employee = Controller.employeeById(id);
		txtID.setText(id);
		txtName.setText(employee.getName());
		txtDoB.setDate(employee.getDateOfBirth());
		if (employee.isGender()) {
			radM.setSelected(true);
			radF.setSelected(false);
		} else {
			radM.setSelected(false);
			radF.setSelected(true);
		}
		txtAddress.setText(employee.getAddress());
		txtPN.setText(employee.getPhone());
		txtEmail.setText(employee.getEmail());
		cmbDepartments.removeAllItems();
		cmbDepartments.addItem(employee.getDepartment());
		cmbDepartments.setSelectedIndex(0);
		cmbRoles.removeAllItems();
		cmbRoles.addItem(employee.getRole());
		cmbRoles.setSelectedIndex(0);
		txtDoW.setDate(employee.getDateOfJoin());
		txtBS.setText(employee.getSalary().toEngineeringString());
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	private boolean validateEmployeeData() {
		if (txtName.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhân viên!", "Validation Error",
					JOptionPane.ERROR_MESSAGE);
			txtName.requestFocus();
			return false;
		}

		if (txtDoB.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!", "Validation Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!radF.isSelected() && !radM.isSelected()) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!", "Validation Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (txtAddress.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ!", "Validation Error",
					JOptionPane.ERROR_MESSAGE);
			txtAddress.requestFocus();
			return false;
		}

		if (!txtPN.getText().trim().matches("^0\\d{9}$")) {
			JOptionPane.showMessageDialog(this, "Số điện thoại phải bắt đầu là 0 và nhập đủ 10 số!", "Validation Error",
					JOptionPane.ERROR_MESSAGE);
			txtPN.requestFocus();
			return false;
		}

		if (txtEmail.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập email!", "Validation Error", JOptionPane.ERROR_MESSAGE);
			txtEmail.requestFocus();
			return false;
		}

		if (cmbRoles.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ của nhân viên!", "Validation Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (txtDoW.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày vào làm!", "Validation Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (txtBS.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng lương cơ bản!", "Validation Error",
					JOptionPane.ERROR_MESSAGE);
			txtBS.requestFocus();
			return false;
		}

		if (!txtBS.getText().trim().matches("\\d+")) {
			JOptionPane.showMessageDialog(this, "Lương cơ bản phải là số!", "Validation Error",
					JOptionPane.ERROR_MESSAGE);
			txtBS.requestFocus();
			return false;
		}

		return true;
	}

	private void setEnable(boolean state) {
		txtName.setEditable(state);
		txtDoB.setEnabled(state);
		radF.setEnabled(state);
		radM.setEnabled(state);
		txtAddress.setEditable(state);
		txtDoW.setEnabled(state);
		txtPN.setEditable(state);
		txtEmail.setEditable(state);
		txtBS.setEditable(state);
	}

	private void alignTable() {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tblTabble.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tblTabble.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tblTabble.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		tblTabble.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
		tblTabble.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		tblTabble.getColumnModel().getColumn(11).setCellRenderer(rightRenderer);
	}

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JDateChooser txtDoB;
	private JDateChooser txtDoW;
	private JTable tblTabble;
	private DefaultTableModel tableModel;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtAddress;
	private JTextField txtPN;
	private JTextField txtEmail;
	private JTextField txtBS;
	private JButton btnImg;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnUpdate;
	private JButton btnRefresh;
	private JLabel lblImg;
	private JComboBox<Department> cmbDepartments;
	private JComboBox<String> cmbRoles;
	private JRadioButton radF;
	private JRadioButton radM;
	private JTextField txtSearch;
	private JButton btnSearch;

	@Override
	public void itemStateChanged(ItemEvent e) {

	}
}
