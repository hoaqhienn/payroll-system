package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.toedter.calendar.JDateChooser;

import controller.Controller;
import custom.ReadOnlyTableModel;
import custom.StatusCellRenderer;
import entities.Account;
import entities.Employee;
import entities.Timekeeping;
import net.miginfocom.swing.MigLayout;

public class pnlAccountManagment extends JPanel
		implements ActionListener, MouseListener, ItemListener, PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(pnlAccountManagment.class.getName());

//	private EmployeeTimekeeping chamCong = null;
	private ReadOnlyTableModel tableModel2;

	private JTextField txtName;
	private JTextField txtID;
	private JButton btnUpdate;
	private JTextField txtSearch;
	private JTextField txtRole;
	private JComboBox<String> cmbSelectedDepartment = null;

	private int selectedRow = -1;

	private JTable tbl;
	private JButton btnRefresh;
	private JTextField txtDepartment;
	private JButton btnRemove;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings("deprecation")
	public pnlAccountManagment() {
		int width = 1920;
		int height = (width * 9) / 16;
		setSize(width, height);
		setLayout(new BorderLayout(0, 0));

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel pnlNorth = new JPanel();
		pnlNorth.setBackground(Color.CYAN);
		contentPane.add(pnlNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("QUẢN LÝ TÀI KHOẢN");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		pnlNorth.add(lblTitle);

		JPanel pnlCenter = new JPanel();
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(new BorderLayout(0, 0));

		JPanel pnlEmployeesContainer = new JPanel();
		pnlEmployeesContainer
				.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 0, 5),
						new TitledBorder(
								new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255),
										new Color(160, 160, 160)),
								"Danh s\u00E1ch t\u00E0i kho\u1EA3n", TitledBorder.LEADING, TitledBorder.TOP, null,
								new Color(0, 0, 0))));
		pnlCenter.add(pnlEmployeesContainer, BorderLayout.CENTER);
		pnlEmployeesContainer.setLayout(new BorderLayout(0, 0));

		JPanel pnlEmployeeList = new JPanel();
		pnlEmployeesContainer.add(pnlEmployeeList, BorderLayout.CENTER);

		pnlEmployeeList.setLayout(new BorderLayout(0, 0));
		String[] header2 = { "#", "Mã Nhân Viên", "Họ Và Tên", "Phòng Ban", "Chức Vụ", "Mật Khẩu" };
		tableModel2 = new ReadOnlyTableModel(header2, 0);
		tbl = new JTable(tableModel2);
		tbl.getColumnModel().getColumn(5).setCellRenderer(new StatusCellRenderer());
		tbl.setAutoCreateRowSorter(true);
		tbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JScrollPane scr = new JScrollPane(tbl);
		scr.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEmployeeList.add(scr);

		JPanel pnlTimekeepingContainer = new JPanel();
		pnlTimekeepingContainer
				.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 0, 5),
						new TitledBorder(
								new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255),
										new Color(160, 160, 160)),
								"Th\u00F4ng tin t\u00E0i kho\u1EA3n", TitledBorder.LEADING, TitledBorder.TOP, null,
								new Color(0, 0, 0))));
		pnlCenter.add(pnlTimekeepingContainer, BorderLayout.EAST);
		pnlTimekeepingContainer.setLayout(new BorderLayout(0, 0));

		JPanel pnlTimekeepingInfo = new JPanel();
		pnlTimekeepingInfo.setBorder(new EmptyBorder(0, 30, 0, 30));
		pnlTimekeepingContainer.add(pnlTimekeepingInfo, BorderLayout.NORTH);
		pnlTimekeepingInfo.setLayout(new MigLayout("", "[][grow]", "[][][][]"));

		JLabel lblID = new JLabel("Mã nhân viên:");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblID, "cell 0 0,alignx trailing");

		txtID = new JTextField();
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtID, "cell 1 0,growx");
		txtID.setColumns(10);
		txtID.setEditable(false);

		JLabel lblName = new JLabel("Họ và tên:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblName, "cell 0 1,alignx trailing");

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtName, "cell 1 1,growx");
		txtName.setColumns(20);
		txtName.setEditable(false);

		JLabel lblNewLabel = new JLabel("Phòng ban:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblNewLabel, "cell 0 2,alignx trailing");

		txtDepartment = new JTextField();
		txtDepartment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtDepartment.setEditable(false);
		pnlTimekeepingInfo.add(txtDepartment, "cell 1 2,growx");
		txtDepartment.setColumns(10);

		JLabel lblRole = new JLabel("Chức Vụ:");
		lblRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblRole, "cell 0 3,alignx trailing");

		txtRole = new JTextField();
		txtRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtRole, "cell 1 3,growx");
		txtRole.setColumns(10);
		txtRole.setEditable(false);

		Vector<Time> checkInDate = new Vector<>();
		Vector<Time> checkOutDate = new Vector<>();
		for (int i = 8; i <= 20; i++) {
			checkInDate.add(new Time(i, 0, 0));
			checkOutDate.add(new Time(i, 0, 0));
		}

		JPanel pnlTimekeepingUpdate = new JPanel();
		pnlTimekeepingUpdate.setBorder(new EmptyBorder(30, 10, 100, 10));
		pnlTimekeepingContainer.add(pnlTimekeepingUpdate, BorderLayout.CENTER);

		btnUpdate = new JButton("Khôi Phục Mật Khẩu");
		btnUpdate.addActionListener(this);
		btnUpdate.setBackground(Color.YELLOW);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 20));
		pnlTimekeepingUpdate.add(btnUpdate);

		JPanel pnlEmployeeFunc = new JPanel();
		FlowLayout fl_pnlEmployeeFunc = (FlowLayout) pnlEmployeeFunc.getLayout();
		fl_pnlEmployeeFunc.setAlignment(FlowLayout.LEFT);
		pnlEmployeesContainer.add(pnlEmployeeFunc, BorderLayout.NORTH);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.setTime(new Date());

		cmbSelectedDepartment = new JComboBox<>();
		cmbSelectedDepartment.setModel(new DefaultComboBoxModel<>(new String[] { "Tất Cả Phòng Ban" }));
		cmbSelectedDepartment.addItemListener(this);
		cmbSelectedDepartment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEmployeeFunc.add(cmbSelectedDepartment);

		JLabel lblSearch = new JLabel("Tìm Kiếm:");
		pnlEmployeeFunc.add(lblSearch);
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtSearch = new JTextField();
		pnlEmployeeFunc.add(txtSearch);
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSearch.setColumns(10);

		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(this);
		pnlEmployeeFunc.add(btnRefresh);

		tbl.addMouseListener(this);
		btnUpdate.setEnabled(false);

		btnRemove = new JButton("Xóa Tài Khoản");
		btnRemove.addActionListener(this);
		btnRemove.setBackground(Color.ORANGE);
		btnRemove.setEnabled(false);
		btnRemove.setFont(new Font("Tahoma", Font.BOLD, 20));
		pnlTimekeepingUpdate.add(btnRemove);

		UpdateTable();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		Account a = Controller.findAccount(tbl.getValueAt(tbl.getSelectedRow(), 1).toString());
		System.out.println(a);

		txtID.setText(a.getId());
		txtName.setText(a.getEmployee().getName());
		txtDepartment.setText(a.getEmployee().getDepartment().getName());
		txtRole.setText(a.getEmployee().getRole());

		btnRemove.setEnabled(true);
		btnUpdate.setEnabled(true);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnRefresh)) {
			UpdateTable();
		}
		if (o.equals(btnUpdate)) {
			int confirm = JOptionPane.showConfirmDialog(null, "Xác Nhận Khôi Phục Mật Khẩu?", "Khôi Phục Mật Khẩu",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				Controller.resetPassword(Controller.findAccount(txtID.getText()));
				txtID.setText("");
				txtName.setText("");
				txtDepartment.setText("");
				txtRole.setText("");
				btnRemove.setEnabled(false);
				btnUpdate.setEnabled(false);
				UpdateTable();
			}
		}
		if (o.equals(btnRemove)) {
			if (txtID.getText().equals(Controller.getAccount().getId())) {
				JOptionPane.showMessageDialog(this, "Không thể xóa tài khoản này!", null, JOptionPane.WARNING_MESSAGE);
				return;
			}

			int confirm = JOptionPane.showConfirmDialog(null, "Xác Nhận Xóa Tài Khoản?", "Xóa Tài Khoản",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				if (Controller.removeEmployee(txtID.getText())) {
					JOptionPane.showMessageDialog(this, "Xóa Thành Công!", null, JOptionPane.INFORMATION_MESSAGE);
					btnRemove.setEnabled(false);
					btnUpdate.setEnabled(false);
					UpdateTable();
					return;
				}
				JOptionPane.showMessageDialog(this, "Xóa Thất Bại!", null, JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
		}

	}

	private void UpdateTable() {
		List<Account> accounts = Controller.allAccount();

		tableModel2.setRowCount(0);
		for (Account a : accounts) {
			tableModel2.addRow(new Object[] { tbl.getRowCount() + 1, a.getId(), a.getEmployee().getName(),
					a.getEmployee().getDepartment().getName(), a.getEmployee().getRole(), a.getPassword() });
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}
}
