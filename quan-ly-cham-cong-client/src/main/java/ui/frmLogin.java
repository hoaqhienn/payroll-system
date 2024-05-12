package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import controller.Controller;

//import dao.EmployeeDAO;
//import dao.LoginDAO;
//import entity.Empolyee;

import net.miginfocom.swing.MigLayout;

public class frmLogin extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private javax.swing.JPanel pnlWest;
	private JTextField uid;
	private JPasswordField pwd;
	private JButton btnHelp;
	private JButton btnLogin;

	private String uidString = "ADMIN";
	private String pwdString = "ADMIN";

	/**
	 * MÀN HÌNH ĐĂNG NHẬP
	 */
	public frmLogin() {
		setTitle("QUẢN LÝ CHẤM CÔNG");
		setIconImage(Toolkit.getDefaultToolkit().getImage(".\\src\\main\\resources\\img\\icon.png"));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// THÔNG B�?O �?ÓNG ỨNG DỤNG
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn đóng ứng dụng?",
						"Xác nhận đóng ứng dụng", JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		int width = 720;
		int height = (width * 9) / 16;
		setSize(width, height);
		setResizable(false);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// PANEL TIÊU ĐỀ
		JPanel pnlNorth = new JPanel();
		contentPane.add(pnlNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("CHẤM CÔNG - ĐĂNG NHẬP");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		pnlNorth.add(lblTitle);

		// PANEL ẢNH
		pnlWest = new JPanel();
		contentPane.add(pnlWest, BorderLayout.WEST);
		pnlWest.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = pnlWest.getHeight();
				int newHeight = (int) (panelHeight);
				ImageIcon originalIcon = new ImageIcon(".\\src\\main\\resources\\img\\img.png");
				Image scaledImage = originalIcon.getImage().getScaledInstance((newHeight * 3) / 4, newHeight,
						Image.SCALE_SMOOTH);
				ImageIcon scaledIcon = new ImageIcon(scaledImage);
				JLabel lbl = new JLabel(scaledIcon);
				pnlWest.removeAll();
				pnlWest.add(lbl);
				pnlWest.revalidate();
				pnlWest.repaint();
			}
		});

		// PANEL CH�?NH
		JPanel pnlCenter = new JPanel();
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(new BorderLayout(0, 0));

		JPanel pnlLoginInformation = new JPanel();
		pnlLoginInformation.setBorder(new EmptyBorder(50, 50, 100, 50));
		pnlCenter.add(pnlLoginInformation, BorderLayout.NORTH);
		pnlLoginInformation.setLayout(new BorderLayout(0, 0));

		JPanel pnlInfo = new JPanel();
		pnlLoginInformation.add(pnlInfo, BorderLayout.NORTH);
		pnlInfo.setLayout(new MigLayout("", "[][grow]", "[][]"));

		JLabel lblUid = new JLabel("Tên đăng nhập:");
		lblUid.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlInfo.add(lblUid, "cell 0 0,alignx trailing");

		uid = new JTextField();
		uid.setText(uidString);
		uid.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlInfo.add(uid, "cell 1 0,growx");
		uid.setColumns(10);

		JLabel lblPwd = new JLabel("Mật khẩu:");
		lblPwd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlInfo.add(lblPwd, "cell 0 1,alignx trailing");

		pwd = new JPasswordField();
		pwd.setText(pwdString);
		pwd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlInfo.add(pwd, "cell 1 1,growx");
		pwd.setColumns(10);

		JPanel pnlFunction = new JPanel();
		pnlCenter.add(pnlFunction, BorderLayout.CENTER);

		btnLogin = new JButton("ĐĂNG NHẬP");

		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 20));

		// KIỂM TRA ĐĂNG NHẬP
		Action loginAction = new AbstractAction("ĐĂNG NHẬP") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Controller.login(uid.getText(), new String(pwd.getPassword()));

					if (Controller.getAccount() != null) {
						dispose();
						new frmSplashScreen();
					} else {
						JOptionPane.showMessageDialog(null, "Sai thông tin đăng nhập", "?",
								JOptionPane.INFORMATION_MESSAGE);
						uid.requestFocus();
					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		};

		KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

		// Thêm KeyStroke và Action vào InputMap và ActionMap của JButton
		btnLogin.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(enterKey, "loginAction");
		btnLogin.getActionMap().put("loginAction", loginAction);

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginAction.actionPerformed(null);
			}
		});

		// button help
		btnHelp = new JButton("?");
		btnHelp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "?", "?", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnHelp.setFont(new Font("Tahoma", Font.BOLD, 20));
		pnlFunction.add(btnLogin);
		pnlFunction.add(btnHelp);
	}
}