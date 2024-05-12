package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import entities.Account;
import entities.Employee;
import entities.Payroll;
import net.miginfocom.swing.MigLayout;

public class pnlPayroll extends JPanel implements ActionListener, MouseListener {

	/**
	 * Create the panel.
	 */
	public pnlPayroll() {
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

		JLabel lblTitle = new JLabel("THÔNG TIN LƯƠNG");
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
								"Th\u00F4ng tin l\u01B0\u01A1ng", TitledBorder.LEADING, TitledBorder.TOP, null,
								new Color(0, 0, 0))));
		pnlCenter.add(pnlEInfo, BorderLayout.CENTER);
		pnlEInfo.setLayout(new MigLayout("", "[1888px,grow]", "[grow]"));

		JPanel pnlInfo = new JPanel();
		pnlEInfo.add(pnlInfo, "cell 0 0,grow");
		pnlInfo.setLayout(new MigLayout("", "[grow][grow][grow]", "[grow]"));

		JPanel p1 = new JPanel();
		pnlInfo.add(p1, "cell 0 0,grow");
		p1.setLayout(new MigLayout("", "[][grow,fill]", "[][][][][]"));

		JLabel lblID = new JLabel("Mã nhân viên:");
		p1.add(lblID, "cell 0 0,alignx trailing");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtID = new JTextField();
		p1.add(txtID, "cell 1 0,growx");
		txtID.setEditable(false);
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtID.setColumns(20);

		JLabel lblName = new JLabel("Họ và tên:");
		p1.add(lblName, "cell 0 1,alignx trailing");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtName = new JTextField();
		p1.add(txtName, "cell 1 1,growx");
		txtName.setEditable(false);
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtName.setColumns(10);

		JLabel lblNewLabel = new JLabel("Phòng ban:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p1.add(lblNewLabel, "cell 0 2,alignx trailing");

		txtDepartment = new JTextField();
		txtDepartment.setEditable(false);
		txtDepartment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p1.add(txtDepartment, "flowx,cell 1 2,growx");
		txtDepartment.setColumns(10);

		JLabel lblRole = new JLabel("Chức vụ:");
		p1.add(lblRole, "cell 0 3,alignx trailing");
		lblRole.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtRole = new JTextField();
		p1.add(txtRole, "cell 1 3");
		txtRole.setEditable(false);
		txtRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtRole.setColumns(10);

		JLabel lblSalary = new JLabel("Lương cơ bản:");
		p1.add(lblSalary, "cell 0 4,alignx trailing");
		lblSalary.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtSalary = new JTextField();
		p1.add(txtSalary, "cell 1 4");
		txtSalary.setEditable(false);
		txtSalary.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSalary.setColumns(10);

		JPanel p2 = new JPanel();
		pnlInfo.add(p2, "cell 1 0,grow");
		p2.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));

		JLabel lblDays = new JLabel("Số ngày làm việc:");
		lblDays.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2.add(lblDays, "cell 0 0,alignx trailing");

		txtDays = new JTextField();
		txtDays.setEditable(false);
		txtDays.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2.add(txtDays, "cell 1 0,growx");
		txtDays.setColumns(10);

		JLabel lblAllowance = new JLabel("Phụ cấp:");
		lblAllowance.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2.add(lblAllowance, "cell 0 1,alignx trailing");

		txtAllowance = new JTextField();
		txtAllowance.setEditable(false);
		txtAllowance.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2.add(txtAllowance, "cell 1 1,growx");
		txtAllowance.setColumns(10);

		JLabel lblHours = new JLabel("Số giờ làm:");
		p2.add(lblHours, "cell 0 2,alignx trailing");
		lblHours.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtHours = new JTextField();
		p2.add(txtHours, "cell 1 2,growx");
		txtHours.setEditable(false);
		txtHours.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtHours.setColumns(20);

		JLabel lblSalary2 = new JLabel("Tiền lương:");
		lblSalary2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2.add(lblSalary2, "cell 0 3,alignx trailing");

		txtSalary2 = new JTextField();
		txtSalary2.setEditable(false);
		txtSalary2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2.add(txtSalary2, "cell 1 3,growx");
		txtSalary2.setColumns(10);

		JLabel lblTotal = new JLabel("Tổng tiền lương:");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2.add(lblTotal, "cell 0 4,alignx trailing");

		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		txtTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		p2.add(txtTotal, "cell 1 4,growx");
		txtTotal.setColumns(10);

		JPanel p3 = new JPanel();
		pnlInfo.add(p3, "cell 2 0,grow");
		p3.setLayout(new MigLayout("", "[]", "[]"));

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
		btnSelect.addActionListener(this);
		btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlDate.add(btnSelect);

		btnPreview = new JButton("Xem Phiếu Lương");
		btnPreview.setEnabled(false);
		btnPreview.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlFunc.add(btnPreview);

		btnPrint = new JButton("In Phiếu Lương");
		btnPrint.setEnabled(false);
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlFunc.add(btnPrint);

		btnHelp = new JButton("?");
		btnHelp.setEnabled(false);
		btnHelp.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pnlFunc.add(btnHelp);

		alignTable();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnSelect)) {
			findMyPayroll();
		}
	}

	private void findMyPayroll() {
		Employee e = Controller.getAccount().getEmployee();

		Month month = (Month) cmbMonth.getSelectedItem();
		Year year = (Year) cmbYear.getSelectedItem();

		Payroll p = Controller.payrollByIdAndDate(Controller.getAccount().getId(), month, year);
		if (p == null) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy!", null, JOptionPane.WARNING_MESSAGE);
			return;
		}

		txtID.setText(e.getId());
		txtName.setText(e.getName());
		txtDepartment.setText(e.getDepartment().getName());
		txtRole.setText(e.getRole());
		txtSalary.setText(e.getSalary().toEngineeringString());
		txtDays.setText(String.valueOf(p.getDaysWorked()));
		txtAllowance.setText(String.valueOf(p.getAllowance()));
		txtHours.setText(String.valueOf(p.getHoursWorked()));
		txtSalary2.setText(String.valueOf(p.getSalary()));
		txtTotal.setText(String.valueOf(p.getTotal()));
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	private void alignTable() {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

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

	private static final long serialVersionUID = 1L;

	DecimalFormat decimalFormat = new DecimalFormat("#,###");
	private JTextField txtID;
	private JTextField txtSalary;
	private JTextField txtRole;
	private JTextField txtName;
	private JTextField txtHours;
	private JButton btnHelp;
	private JButton btnPreview;
	private JButton btnPrint;
	private JComboBox<Month> cmbMonth;
	private JComboBox<Year> cmbYear;
	private JButton btnSelect;
	private JTextField txtAllowance;
	private JTextField txtSalary2;
	private JTextField txtDays;
	private JTextField txtDepartment;
	private JTextField txtTotal;

}
