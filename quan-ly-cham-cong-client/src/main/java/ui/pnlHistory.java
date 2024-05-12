package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
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
import entities.Timekeeping;
import net.miginfocom.swing.MigLayout;

public class pnlHistory extends JPanel implements ActionListener, MouseListener, PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private ReadOnlyTableModel tableModel2;
	private JTextField txtIn;
	private JDateChooser txtDateFilter;
	private JTextField txtDate;
	private JTextField txtOut;
	private int selectedRow = -1;

	private JTable tbl;
	private JTextField txtOverTime;
	private JTextField txtTotal;
	private JButton btnRefresh;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings("deprecation")
	public pnlHistory() {
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

		JLabel lblTitle = new JLabel("LỊCH SỬ CHẤM CÔNG");
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
								"L\u1ECBch s\u1EED ch\u1EA5m c\u00F4ng", TitledBorder.LEADING, TitledBorder.TOP, null,
								new Color(0, 0, 0))));
		pnlCenter.add(pnlEmployeesContainer, BorderLayout.CENTER);
		pnlEmployeesContainer.setLayout(new BorderLayout(0, 0));

		JPanel pnlEmployeeList = new JPanel();
		pnlEmployeesContainer.add(pnlEmployeeList, BorderLayout.CENTER);

		pnlEmployeeList.setLayout(new BorderLayout(0, 0));
		String[] header2 = { "Ngày", "Giờ Vào", "Giờ Ra", "Làm Thêm", "Tổng Thời Gian" };
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
								"Th\u00F4ng tin ch\u1EA5m c\u00F4ng", TitledBorder.LEADING, TitledBorder.TOP, null,
								new Color(0, 0, 0))));
		pnlCenter.add(pnlTimekeepingContainer, BorderLayout.EAST);
		pnlTimekeepingContainer.setLayout(new BorderLayout(0, 0));

		JPanel pnlTimekeepingInfo = new JPanel();
		pnlTimekeepingInfo.setBorder(new EmptyBorder(0, 30, 0, 30));
		pnlTimekeepingContainer.add(pnlTimekeepingInfo, BorderLayout.NORTH);
		pnlTimekeepingInfo.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));

		JLabel lblDate = new JLabel("Ngày:");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblDate, "cell 0 0,alignx trailing");

		txtDate = new JTextField();
		txtDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtDate, "cell 1 0,growx");
		txtDate.setColumns(10);
		txtDate.setEditable(false);

		JLabel lblIn = new JLabel("Giờ Vào:");
		lblIn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblIn, "cell 0 1,alignx trailing");

		txtIn = new JTextField();
		txtIn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtIn, "cell 1 1,growx");
		txtIn.setColumns(20);
		txtIn.setEditable(false);

		JLabel lblOut = new JLabel("Giờ Ra:");
		lblOut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblOut, "cell 0 2,alignx trailing");

		txtOut = new JTextField();
		txtOut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtOut, "cell 1 2,growx");
		txtOut.setColumns(10);
		txtOut.setEditable(false);

		JLabel lblOverTime = new JLabel("Giờ Làm Thêm:");
		lblOverTime.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblOverTime, "cell 0 3,alignx trailing");

		txtOverTime = new JTextField();
		txtOverTime.setEditable(false);
		txtOverTime.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtOverTime, "cell 1 3,growx");
		txtOverTime.setColumns(10);

		JLabel lblTotal = new JLabel("Tổng Thời Gian:");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(lblTotal, "cell 0 4,alignx trailing");

		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		txtTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlTimekeepingInfo.add(txtTotal, "cell 1 4,growx");
		txtTotal.setColumns(10);

		Vector<Time> checkInDate = new Vector<>();
		Vector<Time> checkOutDate = new Vector<>();
		for (int i = 8; i <= 20; i++) {
			checkInDate.add(new Time(i, 0, 0));
			checkOutDate.add(new Time(i, 0, 0));
		}

		JPanel pnlEmployeeFunc = new JPanel();
		FlowLayout fl_pnlEmployeeFunc = (FlowLayout) pnlEmployeeFunc.getLayout();
		fl_pnlEmployeeFunc.setAlignment(FlowLayout.LEFT);
		pnlEmployeesContainer.add(pnlEmployeeFunc, BorderLayout.NORTH);

		txtDateFilter = new JDateChooser();
		txtDateFilter.setDateFormatString("dd/MM/yyyy");
		txtDateFilter.setDate(new Date());
		txtDateFilter.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtDateFilter.setPreferredSize(new Dimension(128, 28));
		txtDateFilter.addPropertyChangeListener(this);

		JLabel lblNewLabel = new JLabel("Tìm Kiếm");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pnlEmployeeFunc.add(lblNewLabel);
		pnlEmployeeFunc.add(txtDateFilter);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		txtDateFilter.setMinSelectableDate(cal.getTime());
		cal.setTime(new Date());
		txtDateFilter.setMaxSelectableDate(cal.getTime());
		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(this);
		pnlEmployeeFunc.add(btnRefresh);

		tbl.addMouseListener(this);

		alignTable();
		updateTable();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		selectedRow = tbl.getSelectedRow();

		if (selectedRow >= 0 && selectedRow < tbl.getRowCount()) {

			txtDate.setText(tbl.getValueAt(selectedRow, 0).toString());
			txtIn.setText(tbl.getValueAt(selectedRow, 1).toString());
			txtOut.setText(tbl.getValueAt(selectedRow, 2).toString());
			txtOverTime.setText(tbl.getValueAt(selectedRow, 3).toString());
			txtTotal.setText(tbl.getValueAt(selectedRow, 4).toString());

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
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Object o = evt.getSource();
		if (o.equals(txtDateFilter)) {
		}
	}

	private void updateTable() {
		System.out.println("updateTable");
		tableModel2.setRowCount(0);
//		List<Timekeeping> list = Controller.getAccount().getEmployee().getTimekeepings();
		List<Timekeeping> list = Controller.allMyTimekeeping(Controller.getAccount().getId());
		
		if (list != null) {
			for (Timekeeping t : list) {
				System.out.println(t);
				tableModel2.addRow(new Object[] { new SimpleDateFormat("dd-MM-yyyy").format(t.getDate()),
						t.getStartTime(), t.getEndTime(), t.getOverTime() + " Phút", t.getTotalHours() + " Phút" });
			}
		}

	}

	private void alignTable() {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbl.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbl.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		tbl.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tbl.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tbl.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
//		tbl.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
	}
}
