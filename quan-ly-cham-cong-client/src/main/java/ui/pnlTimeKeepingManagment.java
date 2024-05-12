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
import java.text.SimpleDateFormat;
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
import entities.Employee;
import entities.Timekeeping;
import net.miginfocom.swing.MigLayout;

public class pnlTimeKeepingManagment extends JPanel
		implements ActionListener, MouseListener, ItemListener, PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private ReadOnlyTableModel tableModel2;
	private JTextField txtName;
	private JDateChooser datechoose;
	private JTextField txtID;
	private JTextField txtSearch;
	private JTextField txtRole;

	private int selectedRow = -1;

	private JTable tbl;
	private JTextField txtOT;
	private JButton btnRefresh;
	private JTextField txtDate;
	private JTextField txtDepartment;
	private JTextField txtTotal;
	private JTextField txtIn;
	private JTextField txtOut;
	private JButton btnSearch;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings("deprecation")
	public pnlTimeKeepingManagment() {
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

		JLabel lblTitle = new JLabel("QUẢN LÝ BẢNG CHẤM CÔNG");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		pnlNorth.add(lblTitle);

		JPanel pnlCenter = new JPanel();
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(new BorderLayout(0, 0));

		JPanel pnlEmployeesContainer = new JPanel();
		pnlEmployeesContainer.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 0, 5),
				new TitledBorder(
						new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
						"Danh sách nhân viên", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))));
		pnlCenter.add(pnlEmployeesContainer, BorderLayout.CENTER);
		pnlEmployeesContainer.setLayout(new BorderLayout(0, 0));

		JPanel pnlEmployeeList = new JPanel();
		pnlEmployeesContainer.add(pnlEmployeeList, BorderLayout.CENTER);

		pnlEmployeeList.setLayout(new BorderLayout(0, 0));
		String[] header2 = { "#", "Ngày", "Mã Nhân Viên", "Họ Và Tên", "Giờ Vào", "Giờ Ra", "Làm Thêm",
				"Tổng Thời Gian" };
		tableModel2 = new ReadOnlyTableModel(header2, 0);
		tbl = new JTable(tableModel2);
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
								"Thông tin chấm công nhân viên", TitledBorder.LEADING, TitledBorder.TOP, null,
								new Color(0, 0, 0))));
		pnlCenter.add(pnlTimekeepingContainer, BorderLayout.EAST);
		pnlTimekeepingContainer.setLayout(new BorderLayout(0, 0));

		JPanel pnlTimekeepingInfo = new JPanel();
		pnlTimekeepingInfo.setBorder(new EmptyBorder(0, 30, 0, 30));
		pnlTimekeepingContainer.add(pnlTimekeepingInfo, BorderLayout.NORTH);
		pnlTimekeepingInfo.setLayout(new MigLayout("", "[][grow]", "[][][][][][grow][][][]"));

		JLabel lblNewLabel = new JLabel("Ngày:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblNewLabel, "cell 0 0,alignx trailing");

		txtDate = new JTextField();
		txtDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtDate.setEditable(false);
		pnlTimekeepingInfo.add(txtDate, "cell 1 0,growx");
		txtDate.setColumns(10);

		JLabel lblID = new JLabel("Mã nhân viên:");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblID, "cell 0 1,alignx trailing");

		txtID = new JTextField();
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtID, "cell 1 1,growx");
		txtID.setColumns(10);
		txtID.setEditable(false);

		JLabel lblName = new JLabel("Họ và tên:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblName, "cell 0 2,alignx trailing");

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtName, "cell 1 2,growx");
		txtName.setColumns(20);
		txtName.setEditable(false);

		JLabel lblDepartment = new JLabel("Phòng ban:");
		lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblDepartment, "cell 0 3,alignx trailing");

		Vector<Time> checkInDate = new Vector<>();
		Vector<Time> checkOutDate = new Vector<>();
		for (int i = 8; i <= 20; i++) {
			checkInDate.add(new Time(i, 0, 0));
			checkOutDate.add(new Time(i, 0, 0));
		}

		txtDepartment = new JTextField();
		txtDepartment.setEditable(false);
		txtDepartment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtDepartment, "cell 1 3,growx");
		txtDepartment.setColumns(10);

		JLabel lblRole = new JLabel("Chức vụ:");
		lblRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblRole, "cell 0 4,alignx trailing");

		txtRole = new JTextField();
		txtRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtRole, "cell 1 4,growx");
		txtRole.setColumns(10);
		txtRole.setEditable(false);

		JLabel lblCheckIn = new JLabel("Giờ ra:");
		lblCheckIn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblCheckIn, "cell 0 5,alignx trailing");

		txtIn = new JTextField();
		txtIn.setEditable(false);
		txtIn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtIn, "cell 1 5,growx");
		txtIn.setColumns(10);

		JLabel lblCheckOut = new JLabel("Giờ vào:");
		lblCheckOut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblCheckOut, "cell 0 6,alignx trailing");

		txtOut = new JTextField();
		txtOut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtOut.setEditable(false);
		pnlTimekeepingInfo.add(txtOut, "cell 1 6,growx");
		txtOut.setColumns(10);

		JLabel lblOT = new JLabel("Làm thêm:");
		lblOT.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblOT, "cell 0 7,alignx trailing");

		txtOT = new JTextField();
		txtOT.setEditable(false);
		txtOT.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtOT, "cell 1 7,growx");
		txtOT.setColumns(10);

		JLabel lblTotal = new JLabel("Tổng thời gian:");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblTotal, "cell 0 8,alignx trailing");

		txtTotal = new JTextField();
		txtTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtTotal.setEditable(false);
		pnlTimekeepingInfo.add(txtTotal, "cell 1 8,growx");
		txtTotal.setColumns(10);

		JPanel pnlTimekeepingUpdate = new JPanel();
		pnlTimekeepingUpdate.setBorder(new EmptyBorder(30, 10, 100, 10));
		pnlTimekeepingContainer.add(pnlTimekeepingUpdate, BorderLayout.CENTER);

		JPanel pnlEmployeeFunc = new JPanel();
		FlowLayout fl_pnlEmployeeFunc = (FlowLayout) pnlEmployeeFunc.getLayout();
		fl_pnlEmployeeFunc.setAlignment(FlowLayout.LEFT);
		pnlEmployeesContainer.add(pnlEmployeeFunc, BorderLayout.NORTH);

		datechoose = new JDateChooser();
		datechoose.setDateFormatString("dd/MM/yyyy");
		datechoose.setDate(new Date());
		datechoose.setFont(new Font("Tahoma", Font.PLAIN, 16));
		datechoose.setPreferredSize(new Dimension(128, 28));
		datechoose.addPropertyChangeListener(this);
		pnlEmployeeFunc.add(datechoose);

//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.DATE, 1);
//		datechoose.setMinSelectableDate(cal.getTime());
//		cal.setTime(new Date());
//		datechoose.setMaxSelectableDate(cal.getTime());

		JLabel lblSearch = new JLabel("Tìm Kiếm:");
		pnlEmployeeFunc.add(lblSearch);
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtSearch = new JTextField();
		pnlEmployeeFunc.add(txtSearch);
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSearch.setColumns(10);

		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(this);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(this);
		pnlEmployeeFunc.add(btnSearch);
		pnlEmployeeFunc.add(btnRefresh);

		tbl.addMouseListener(this);

		alignTable();
		updateTable();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		selectedRow = tbl.getSelectedRow();

		if (selectedRow >= 0 && selectedRow < tbl.getRowCount()) {
			txtDate.setText(tbl.getValueAt(selectedRow, 1).toString());
			txtID.setText(tbl.getValueAt(selectedRow, 2).toString());
			txtName.setText(tbl.getValueAt(selectedRow, 3).toString());
			Employee ee = Controller.employeeById(txtID.getText());
			txtDepartment.setText(ee.getDepartment().getName());
			txtRole.setText(ee.getRole());

			txtIn.setText(tbl.getValueAt(selectedRow, 4).toString());
			txtOut.setText(tbl.getValueAt(selectedRow, 5).toString());
			txtOT.setText(tbl.getValueAt(selectedRow, 6).toString());
			txtTotal.setText(tbl.getValueAt(selectedRow, 7).toString());

		} else {
			selectedRow = -1;
		}
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
			updateTable();
		}
		
		if (o.equals(btnSearch)) {
			search();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	}

	private void updateTable() {
		tableModel2.setRowCount(0);

		List<Timekeeping> list = Controller.allTimekeepings();
		
		for (Timekeeping t : list) {
			tableModel2.addRow(
					new Object[] { tbl.getRowCount() + 1, new SimpleDateFormat("dd-MM-yyyy").format(t.getDate()),
							t.getEmployeeId(), t.getEmployee().getName(), t.getStartTime(), t.getEndTime(),
							t.getOverTime() + " Phút", t.getTotalHours() + " Phút" }); 
		}
	}
	
	private void search() {
	    tableModel2.setRowCount(0);

	    List<Timekeeping> list = Controller.allTimekeepings();

	    Date selectedDate = datechoose.getDate();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	    String keyword = txtSearch.getText().trim().toLowerCase();

	    for (Timekeeping t : list) {
	        if (selectedDate != null && sdf.format(t.getDate()).equals(sdf.format(selectedDate))) {
	            addRowToTable(t);
	        }
	        else if (!keyword.isEmpty() &&
	                (String.valueOf(t.getEmployeeId()).toLowerCase().contains(keyword) ||
	                        t.getEmployee().getName().toLowerCase().contains(keyword))) {
	            addRowToTable(t);
	        }
	        else if (selectedDate == null && keyword.isEmpty()) {
	            addRowToTable(t);
	        }
	    }
	}

	private void addRowToTable(Timekeeping t) {
	    tableModel2.addRow(
	            new Object[]{tbl.getRowCount() + 1, new SimpleDateFormat("dd-MM-yyyy").format(t.getDate()),
	                    t.getEmployeeId(), t.getEmployee().getName(), t.getStartTime(), t.getEndTime(),
	                    t.getOverTime() + " Phút", t.getTotalHours() + " Phút"});
	}


	private void alignTable() {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbl.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbl.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		tbl.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tbl.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		tbl.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		tbl.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		tbl.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
	}
}
