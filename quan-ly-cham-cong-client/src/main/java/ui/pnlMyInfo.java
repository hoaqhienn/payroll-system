package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import controller.Controller;
import entities.Account;
import entities.Employee;
import entities.Timekeeping;
import net.miginfocom.swing.MigLayout;

/**
 * @author HOÀNG HIỆN - PANEL QUẢN LÝ NHÂN VIÊN
 */
public class pnlMyInfo extends JPanel implements ActionListener, MouseListener, ItemListener {

	private JTextField txtDepartments;
	private Boolean visible = false;

	public pnlMyInfo() {

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

		JLabel lblTittle = new JLabel("THÔNG TIN CÁ NHÂN");

		lblTittle.setFont(new Font("Tahoma", Font.BOLD, 30));
		pnlNorth.add(lblTittle);

		/* CENTER PANEL - PANEL CHÍNH */
		JPanel pnlCenter = new JPanel();
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(new BorderLayout(0, 0));

		JPanel pnlEInfo = new JPanel();
		pnlEInfo.setBorder(
				new CompoundBorder(new EmptyBorder(20, 5, 0, 5),
						new TitledBorder(
								new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255),
										new Color(160, 160, 160)),
								"Th\u00F4ng tin c\u1EE7a t\u00F4i", TitledBorder.LEADING, TitledBorder.TOP, null,
								new Color(0, 0, 0))));
		pnlCenter.add(pnlEInfo, BorderLayout.NORTH);
		pnlEInfo.setLayout(new MigLayout("", "[grow][9px]", "[10px,grow][]"));

		JPanel pnlEInfoContainer = new JPanel();
		pnlEInfoContainer.setBorder(new EmptyBorder(0, 100, 0, 100));
		pnlEInfo.add(pnlEInfoContainer, "cell 0 0,grow");
		pnlEInfoContainer.setLayout(new MigLayout("", "[grow][grow]", "[][][][][][][][][][][][grow][grow]"));

		/* THÔNG TIN VỀ NHÂN VIÊN - MÃ NHÂN VIÊN / TÊN NHÂN VIÊN... */
		JLabel lblID = new JLabel("Mã nhân viên:");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblID, "cell 0 0,alignx trailing");

		// ID
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtID, "cell 1 0,growx");
		txtID.setColumns(10);

		JLabel lblName = new JLabel("Họ và tên:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblName, "cell 0 1,alignx trailing");

		txtName = new JTextField();
		txtName.setEditable(false);
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtName, "cell 1 1,growx");
		txtName.setColumns(10);

		JLabel lblDoB = new JLabel("Ngày sinh:");
		lblDoB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblDoB, "cell 0 2,alignx trailing");

		txtDoB = new JTextField();
		txtDoB.setEditable(false);
		txtDoB.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtDoB, "cell 1 2,growx");

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

		JLabel lblAddress = new JLabel("Địa chỉ:");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblAddress, "cell 0 4,alignx trailing");

		txtAddress = new JTextField();
		txtAddress.setEditable(false);
		txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtAddress, "cell 1 4,growx");
		txtAddress.setColumns(10);

		JLabel lblPN = new JLabel("Số điện thoại:");
		lblPN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblPN, "cell 0 5,alignx trailing");

		txtPN = new JTextField();
		txtPN.setEditable(false);
		txtPN.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtPN, "cell 1 5,growx");
		txtPN.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblEmail, "cell 0 6,alignx trailing");

		txtEmail = new JTextField();
		txtEmail.setEditable(false);
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtEmail, "cell 1 6,growx");
		txtEmail.setColumns(10);

		JLabel lblDepartment = new JLabel("Phòng ban:");
		lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblDepartment, "cell 0 7,alignx trailing");

		txtDepartments = new JTextField();
		txtDepartments.setEditable(false);
		txtDepartments.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtDepartments, "cell 1 7,growx");
		txtEmail.setColumns(10);

		JLabel lblRole = new JLabel("Chức vụ:");
		lblRole.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblRole, "cell 0 8,alignx trailing");

		txtRole = new JTextField();
		txtRole.setEditable(false);
		txtRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtRole, "cell 1 8,growx");

		JLabel lblDoW = new JLabel("Ngày vào làm:");
		lblDoW.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblDoW, "cell 0 9,alignx trailing");

		txtDoW = new JTextField();
		txtDoW.setEditable(false);
		txtDoW.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtDoW, "cell 1 9,growx");

		JLabel lblBS = new JLabel("Lương cơ bản:");
		lblBS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlEInfoContainer.add(lblBS, "cell 0 10,alignx trailing");

		txtBS = new JTextField();
		txtBS.setEditable(false);
		txtBS.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlEInfoContainer.add(txtBS, "cell 1 10,growx");
		txtBS.setColumns(10);

		JPanel pnlImg = new JPanel();
		pnlImg.setBorder(
				new CompoundBorder(new EmptyBorder(0, 0, 0, 100),
						new TitledBorder(
								new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255),
										new Color(160, 160, 160)),
								"\u1EA2nh \u0111\u1EA1i di\u1EC7n", TitledBorder.LEADING, TitledBorder.TOP, null,
								new Color(0, 0, 0))));
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

		btnImg = new JButton("CẬP NHẬT");
		btnImg.setEnabled(false);
		btnImg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlImgButton.add(btnImg);

		JPanel panel = new JPanel();
		pnlEInfo.add(panel, "cell 0 1 2 1,growx");
		panel.setBorder(new EmptyBorder(10, 1, 50, 0));

		btnCheckIn = new JButton("Check In");
		btnCheckIn.addActionListener(this);
		btnCheckIn.setBackground(Color.GREEN);
		btnCheckIn.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(btnCheckIn);

		btnCheckOut = new JButton("Check Out");
		btnCheckOut.addActionListener(this);
		btnCheckOut.setBackground(Color.YELLOW);
		btnCheckOut.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(btnCheckOut);

		/* PANEL SOUTH - DANH SÁCH NHÂN VIÊN */
		JPanel pnlSouth = new JPanel();
		contentPane.add(pnlSouth, BorderLayout.SOUTH);
		pnlSouth.setLayout(new BorderLayout(0, 0));

		JPanel pnlPWD = new JPanel();
		pnlPWD.setBorder(
				new CompoundBorder(new EmptyBorder(5, 5, 20, 5),
						new TitledBorder(
								new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255),
										new Color(160, 160, 160)),
								"\u0110\u1ED5i m\u1EADt kh\u1EA9u", TitledBorder.LEADING, TitledBorder.TOP, null,
								new Color(0, 0, 0))));
		pnlSouth.add(pnlPWD, BorderLayout.CENTER);
		pnlPWD.setLayout(new MigLayout("", "[grow][46px,grow][grow]", "[33px,grow]"));

		JPanel leftPadding = new JPanel();
		pnlPWD.add(leftPadding, "cell 0 0,grow");

		JPanel pnlPWDContainer = new JPanel();
		pnlPWDContainer.setBorder(new EmptyBorder(0, 100, 0, 100));
		pnlPWD.add(pnlPWDContainer, "cell 1 0,grow");
		pnlPWDContainer.setLayout(new MigLayout("", "[][grow]", "[][][][]"));

		JLabel lblNewLabel = new JLabel("Mật khẩu hiện tại:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlPWDContainer.add(lblNewLabel, "cell 0 0,alignx trailing");

		txtCurrentPwd = new JPasswordField();
		txtCurrentPwd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlPWDContainer.add(txtCurrentPwd, "cell 1 0,growx");
		txtCurrentPwd.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Mật khẩu mới:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlPWDContainer.add(lblNewLabel_1, "cell 0 1,alignx trailing");

		txtNewPwd = new JPasswordField();
		txtNewPwd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlPWDContainer.add(txtNewPwd, "cell 1 1,growx");
		txtNewPwd.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Nhập lại mật khẩu mới:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlPWDContainer.add(lblNewLabel_2, "cell 0 2,alignx trailing");

		txtConfirmPwd = new JPasswordField();
		txtConfirmPwd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlPWDContainer.add(txtConfirmPwd, "cell 1 2,growx");
		txtConfirmPwd.setColumns(10);

		JPanel pnlCPContainer = new JPanel();
		pnlCPContainer.setBorder(new EmptyBorder(10, 0, 50, 0));
		pnlPWDContainer.add(pnlCPContainer, "flowx,cell 0 3 2 1,growx");

		btnChangePwd = new JButton("Đổi mật khẩu");
		btnChangePwd.addActionListener(this);
		btnChangePwd.setBackground(Color.ORANGE);
		btnChangePwd.setForeground(Color.BLACK);
		pnlCPContainer.add(btnChangePwd);
		btnChangePwd.setFont(new Font("Tahoma", Font.BOLD, 20));

		btnShowPwd = new JButton("");
		btnShowPwd.addActionListener(this);
		btnShowPwd.setIcon(new ImageIcon(pnlMyInfo.class.getResource("/ui/images/eye.png")));
		btnShowPwd.setFont(new Font("Tahoma", Font.BOLD, 20));
		pnlCPContainer.add(btnShowPwd);

		JPanel rightPadding = new JPanel();
		pnlPWD.add(rightPadding, "cell 2 0,grow");

		Account a = Controller.getAccount();
		Employee e = a.getEmployee();

		txtID.setText(e.getId());
		txtName.setText(e.getName());
		txtDoB.setText(new SimpleDateFormat("dd-MM-yyyy").format(e.getDateOfBirth()));
		txtAddress.setText(e.getAddress());

		if (e.isGender()) {
			radM.setSelected(true);
			radF.setSelected(false);
		} else {
			radM.setSelected(false);
			radF.setSelected(true);
		}

		txtPN.setText(e.getPhone());
		txtEmail.setText(e.getEmail());

		txtDepartments.setText(e.getDepartment().getName());
		txtRole.setText(e.getRole());
		txtDoW.setText(new SimpleDateFormat("dd-MM-yyyy").format(e.getDateOfJoin()));
		txtBS.setText(e.getSalary().toEngineeringString() + " VND");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnChangePwd)) {
			if (txtCurrentPwd.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu hiện tại!", null,
						JOptionPane.WARNING_MESSAGE);
				txtCurrentPwd.requestFocus();
				return;
			}
			if (txtNewPwd.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu mới!", null, JOptionPane.WARNING_MESSAGE);
				txtNewPwd.requestFocus();
				return;
			}
			if (txtConfirmPwd.getText().equals("") || !txtConfirmPwd.getText().equals(txtNewPwd.getText())) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập lại mật khẩu mới!", null,
						JOptionPane.WARNING_MESSAGE);
				txtConfirmPwd.requestFocus();
				return;
			}

			if (!txtCurrentPwd.getText().equals(Controller.getAccount().getPassword())) {
				JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không chính xác!", null,
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (txtCurrentPwd.getText().equals(txtNewPwd.getText())) {
				JOptionPane.showMessageDialog(this, "Mật khẩu mới không được trùng với mật khẩu hiện tại!", null,
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận đổi mật khẩu?", "Đổi mật khẩu",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				Controller.changePassword(txtConfirmPwd.getText());
			}
		}
		if (o.equals(btnCheckIn)) {
			Timekeeping t = null;
			t = Controller.myTimekeeping(txtID.getText(), new Date());
			if (t != null) {
				JOptionPane.showMessageDialog(this, "Đã Check In: " + t.getStartTime(), null,
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			LocalTime currentTime = LocalTime.now();
			LocalTime roundedTime = currentTime.truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
			t = new Timekeeping(txtID.getText(), new Date(), 0, roundedTime, roundedTime, 0, 0,
					Controller.getAccount().getEmployee());
			if (Controller.createTimekeeping(t)) {
				JOptionPane.showMessageDialog(this, "Check In: " + roundedTime, null, JOptionPane.INFORMATION_MESSAGE);
			}

		}
		if (o.equals(btnCheckOut)) {
			int confirm = JOptionPane.showConfirmDialog(null, "Xác Nhận Check Out?", "Check Out",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				Timekeeping t = Controller.myTimekeeping(txtID.getText(), new Date());
				if (t.getStatus() == 1) {
					JOptionPane.showMessageDialog(this, "Đã Check Out: " + t.getEndTime(), null,
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				LocalTime currentTime = LocalTime.now();
				LocalTime roundedTime = currentTime.truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
				t.setEndTime(roundedTime);
				t.setStatus(1);

				Duration duration = Duration.between(t.getStartTime(), t.getEndTime());

				int ot = (int) duration.toMinutes();
				t.setTotalHours(ot);

				if (Controller.updateTimekeeping(t)) {
					JOptionPane.showMessageDialog(this, "Check Out: " + roundedTime, null,
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		if (o.equals(btnShowPwd)) {
			if (visible) {
				visible = false;
				((JPasswordField) txtCurrentPwd).setEchoChar('\u2022');
				((JPasswordField) txtNewPwd).setEchoChar('\u2022');
				((JPasswordField) txtConfirmPwd).setEchoChar('\u2022');
			} else {
				visible = true;
				((JPasswordField) txtCurrentPwd).setEchoChar((char) 0);
				((JPasswordField) txtNewPwd).setEchoChar((char) 0);
				((JPasswordField) txtConfirmPwd).setEchoChar((char) 0);
			}

		}
	}

	private void alignTable() {

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		// RIGHT
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

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
	public void itemStateChanged(ItemEvent e) {
		Object object = e.getSource();
		if (cmbDepartments != null && object.equals(cmbDepartments)) {
			Object selectedItem = cmbDepartments.getSelectedItem();
			if (selectedItem != null && !selectedItem.equals(e.getItemSelectable())) {
			}
		}
	}

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtDoB;
	private JTextField txtDoW;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtAddress;
	private JTextField txtPN;
	private JTextField txtEmail;
	private JTextField txtBS;
	private JTextField txtRole;
	private JButton btnImg;
	private JLabel lblImg;
	private JComboBox<Account> cmbDepartments;
	private JRadioButton radF;
	private JRadioButton radM;
	private JTextField txtCurrentPwd;
	private JTextField txtNewPwd;
	private JTextField txtConfirmPwd;
	private JButton btnChangePwd;
	private JButton btnCheckIn;
	private JButton btnCheckOut;
	private JButton btnShowPwd;
}
