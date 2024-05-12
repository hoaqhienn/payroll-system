package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.Controller;
import entities.Account;
import entities.Employee;
import entities.Payroll;
import net.miginfocom.swing.MigLayout;

public class pnlPayrollManagment extends JPanel implements ActionListener, MouseListener {

	/**
	 * Create the panel.
	 */
	public pnlPayrollManagment() {
		int width = 1920;
		int height = (width * 9) / 16;
		setSize(width, height);
		setLayout(new BorderLayout(0, 0));

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(new BorderLayout(0, 0));

		//
		JPanel pnlNorth = new JPanel();
		pnlNorth.setBackground(Color.CYAN);
		contentPane.add(pnlNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("QUẢN LÝ BẢNG LƯƠNG LƯƠNG NHÂN VIÊN");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		pnlNorth.add(lblTitle);

		JPanel pnlCenter = new JPanel();
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(new BorderLayout(0, 0));

		JPanel pnlEInfo = new JPanel();
		pnlEInfo.setBorder(
				new CompoundBorder(new EmptyBorder(5, 5, 0, 5),
						new TitledBorder(
								new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255),
										new Color(160, 160, 160)),
								"Th\u00F4ng tin nh\u00E2n vi\u00EAn", TitledBorder.LEADING, TitledBorder.TOP, null,
								new Color(0, 0, 0))));
		pnlCenter.add(pnlEInfo, BorderLayout.CENTER);
		pnlEInfo.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		pnlEInfo.add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[grow][grow][grow]", "[grow]"));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));

		JLabel lblID = new JLabel("Mã nhân viên:");
		panel_1.add(lblID, "cell 0 0,alignx trailing");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtID = new JTextField();
		panel_1.add(txtID, "cell 1 0,growx");
		txtID.setEditable(false);
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtID.setColumns(20);

		JLabel lblName = new JLabel("Họ và tên:");
		panel_1.add(lblName, "cell 0 1,alignx trailing");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtName = new JTextField();
		panel_1.add(txtName, "cell 1 1,growx");
		txtName.setEditable(false);
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtName.setColumns(10);

		JLabel lblDepartment = new JLabel("Phòng ban:");
		panel_1.add(lblDepartment, "cell 0 2,alignx trailing");
		lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtDepartment = new JTextField();
		panel_1.add(txtDepartment, "cell 1 2,growx");
		txtDepartment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtDepartment.setEditable(false);
		txtDepartment.setColumns(10);

		JLabel lblRole = new JLabel("Chức vụ:");
		panel_1.add(lblRole, "cell 0 3,alignx trailing");
		lblRole.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtRole = new JTextField();
		panel_1.add(txtRole, "cell 1 3,growx");
		txtRole.setEditable(false);
		txtRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtRole.setColumns(10);

		JLabel lblSalary = new JLabel("Lương cơ bản:");
		panel_1.add(lblSalary, "cell 0 4,alignx trailing");
		lblSalary.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtSalary = new JTextField();
		panel_1.add(txtSalary, "cell 1 4,growx");
		txtSalary.setEditable(false);
		txtSalary.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSalary.setColumns(10);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2, "cell 1 0,grow");
		panel_2.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));

		JLabel lblDays = new JLabel("Số ngày làm việc:");
		panel_2.add(lblDays, "cell 0 0,alignx trailing");
		lblDays.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtDays = new JTextField();
		panel_2.add(txtDays, "cell 1 0,growx");
		txtDays.setEditable(false);
		txtDays.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtDays.setColumns(10);

		JLabel lblAllowance = new JLabel("Phụ cấp:");
		panel_2.add(lblAllowance, "cell 0 1,alignx trailing");
		lblAllowance.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtAllowance = new JTextField();
		panel_2.add(txtAllowance, "cell 1 1,growx");
		txtAllowance.setEditable(false);
		txtAllowance.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtAllowance.setColumns(20);

		JLabel lblHours = new JLabel("Số giờ làm:");
		panel_2.add(lblHours, "cell 0 2,alignx trailing");
		lblHours.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtHours = new JTextField();
		panel_2.add(txtHours, "cell 1 2,growx");
		txtHours.setEditable(false);
		txtHours.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtHours.setColumns(10);

		JLabel lblSalary2 = new JLabel("Tiền lương:");
		panel_2.add(lblSalary2, "cell 0 3,alignx trailing");
		lblSalary2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtSalary2 = new JTextField();
		panel_2.add(txtSalary2, "cell 1 3,growx");
		txtSalary2.setEditable(false);
		txtSalary2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSalary2.setColumns(10);

		JLabel lblTotal = new JLabel("Tổng tiền lương:");
		panel_2.add(lblTotal, "cell 0 4,alignx trailing");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtTotal = new JTextField();
		panel_2.add(txtTotal, "cell 1 4,growx");
		txtTotal.setEditable(false);
		txtTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtTotal.setColumns(10);

		JPanel panel_3 = new JPanel();
		panel.add(panel_3, "cell 2 0,grow");

		JPanel pnlESList = new JPanel();
		pnlESList
				.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 0, 5),
						new TitledBorder(
								new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255),
										new Color(160, 160, 160)),
								"Danh s\u00E1ch l\u01B0\u01A1ng nh\u00E2n vi\u00EAn", TitledBorder.LEADING,
								TitledBorder.TOP, null, new Color(0, 0, 0))));
		pnlCenter.add(pnlESList, BorderLayout.SOUTH);
		pnlESList.setLayout(new BorderLayout(0, 0));

		JPanel pnlESButton = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlESButton.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		pnlESList.add(pnlESButton, BorderLayout.NORTH);

		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(this);
		pnlESButton.add(btnRefresh);

		JPanel pnlEmpSList = new JPanel();
		pnlESList.add(pnlEmpSList);
		pnlEmpSList.setLayout(new BorderLayout(0, 0));
		String[] header2 = { "#", "Mã Nhân Viên", "Họ và Tên", "Phòng Ban", "Chức Vụ", "Tổng Tiền Lương" };

		tableModel2 = new DefaultTableModel(header2, 0);

		tblTabble2 = new JTable(tableModel2);
		tblTabble2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		tblTabble2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JScrollPane scrScrollPanel2 = new JScrollPane(tblTabble2);
		scrScrollPanel2.setFont(new Font("Tahoma", Font.PLAIN, 14));

		pnlEmpSList.add(scrScrollPanel2);

		JPanel pnlOptions = new JPanel();
		pnlCenter.add(pnlOptions, BorderLayout.NORTH);

		JPanel pnlEFunc = new JPanel();
		pnlEFunc.setBorder(new EmptyBorder(5, 0, 5, 0));
		pnlOptions.add(pnlEFunc, BorderLayout.SOUTH);

		JPanel pnlDate = new JPanel();
		pnlEFunc.add(pnlDate);

		JPanel pnlFunc = new JPanel();
		pnlEFunc.add(pnlFunc);

		JLabel lblMonth = new JLabel("Tháng:");
		lblMonth.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlDate.add(lblMonth);

		cmbMonth = new JComboBox<>();
		cmbMonth.setModel(new DefaultComboBoxModel<>(Month.values()));

		cmbMonth.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnlDate.add(cmbMonth);

		JLabel lblYear = new JLabel("Năm:");
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnlDate.add(lblYear);

		List<Year> years = new ArrayList<>();
		for (int i = 2020; i <= 2030; i++) {
			years.add(Year.of(i));
		}
		cmbYear = new JComboBox<>();
		cmbYear.setModel(new DefaultComboBoxModel<>(years.toArray(new Year[0])));
		cmbYear.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmbYear.setSelectedIndex(4);
		pnlDate.add(cmbYear);

		btnSelect = new JButton("Tìm Kiếm");
		btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlDate.add(btnSelect);

		btnCaculate = new JButton("Tính Lương");
		btnCaculate.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlFunc.add(btnCaculate);

		btnPrint = new JButton("In Bảng Lương");
		btnPrint.setEnabled(false);
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlFunc.add(btnPrint);

		btnDetail = new JButton("Xem Chi Tiết");
		btnDetail.setEnabled(false);
		btnDetail.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlFunc.add(btnDetail);

		btnHelp = new JButton("?");
		btnHelp.setEnabled(false);
		btnHelp.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlFunc.add(btnHelp);

		/**/
		btnCaculate.addActionListener(this);
		btnSelect.addActionListener(this);
		tblTabble2.addMouseListener(this);

		alignTable();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnSelect)) {
			SelectedPayroll();
		}
		if (o.equals(btnCaculate)) {
			CreatePayroll();
		}
	}

	private void CreatePayroll() {
		int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn tính tiền lương cho các nhân viên?",
				"Tính Lương", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			Month month = (Month) cmbMonth.getSelectedItem();
			Year year = (Year) cmbYear.getSelectedItem();
			if (Controller.createPayroll(month, year)) {
				JOptionPane.showMessageDialog(this, "Tính lương thành công!", null, JOptionPane.INFORMATION_MESSAGE);
				SelectedPayroll();
				return;
			}
			JOptionPane.showMessageDialog(this, "Tính lương thất bại!", null, JOptionPane.WARNING_MESSAGE);
		}

	}

	private void SelectedPayroll() {
		tableModel2.setRowCount(0);
		Month month = (Month) cmbMonth.getSelectedItem();
		Year year = (Year) cmbYear.getSelectedItem();
		List<Payroll> payrolls = Controller.payrollByDate(month, year);
		if (payrolls.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không có dữ liệu!", null, JOptionPane.WARNING_MESSAGE);
			return;
		}
		for (Payroll p : payrolls) {
			Employee e = p.getEmployee();
			tableModel2.addRow(new Object[] { tblTabble2.getRowCount() + 1, e.getId(), e.getName(),
					e.getDepartment().getName(), e.getRole(), p.getTotal() });
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Month month = (Month) cmbMonth.getSelectedItem();
		Year year = (Year) cmbYear.getSelectedItem();
		Payroll p = Controller.payrollByIdAndDate(String.valueOf(tblTabble2.getValueAt(tblTabble2.getSelectedRow(), 1)),
				month, year);
		txtID.setText(p.getEmployeeId());
		Employee a = p.getEmployee();
		txtName.setText(a.getName());
		txtDepartment.setText(a.getDepartment().getName());
		txtRole.setText(a.getRole());
		txtSalary.setText(a.getSalary().toEngineeringString());
		txtDays.setText(String.valueOf(p.getDaysWorked()));
		txtAllowance.setText(p.getAllowance().toEngineeringString());
		txtHours.setText(String.valueOf(p.getHoursWorked()));
		txtSalary2.setText(p.getSalary().toEngineeringString());
		txtTotal.setText(p.getTotal().toEngineeringString());
	}

	private void alignTable() {
//		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//		tblTabble2.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
//		tblTabble2.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
//
//		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
//		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
//		tblTabble2.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
//		tblTabble2.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);

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

	DecimalFormat decimalFormat = new DecimalFormat("#,###");
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel2;
	private JTable tblTabble2;
	private JTextField txtID;
	private JTextField txtDays;
	private JTextField txtSalary;
	private JTextField txtRole;
	private JTextField txtName;
	private JTextField txtAllowance;
	private JTextField txtHours;
	private JTextField txtSalary2;
	private JButton btnHelp;
	private JButton btnDetail;
	private JButton btnPrint;
	private JButton btnCaculate;
	private JComboBox<Month> cmbMonth;
	private JComboBox<Year> cmbYear;
	private JButton btnSelect;
	private JTextField txtTotal;
	private JTextField txtDepartment;
	private JButton btnRefresh;
}
