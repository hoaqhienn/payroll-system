package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.Controller;

public class frmMainFrame extends JFrame implements ActionListener, MouseListener {

	private pnlMyInfo pnlMyInfo;
	private pnlHistory pnlHistory;
	private pnlPayroll pnlPayroll;
	private pnlEmployee pnlEmployee;

	private pnlTimeKeepingManagment pnlTimeKeepingManagment;
	private pnlPayrollManagment pnlPayrollManagment;
	private pnlAccountManagment pnlAccountManagment;

	private JProgressBar progressBar;

	public frmMainFrame() {
		setTitle("QUẢN LÝ CHẤM CÔNG");
		setIconImage(Toolkit.getDefaultToolkit().getImage(".\\src\\main\\resources\\img\\icon.png"));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		int width = 1920;
		int height = (width * 9) / 16;
		setSize(width, height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// PANEL NORTH - CHỨA MENU BAR
		JPanel pnlNorth = new JPanel();
		pnlNorth.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) pnlNorth.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(pnlNorth, BorderLayout.NORTH);

		// MENU BAR
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		menuBar.setFont(new Font("Segoe UI", Font.BOLD, 20));
		pnlNorth.add(menuBar);

		mnProfile = new JMenu("THÔNG TIN CÁ NHÂN");
		mnProfile.addMouseListener(this);
		mnProfile.setIcon(new ImageIcon(frmMainFrame.class.getResource("/ui/images/employee.png")));
		mnProfile.setFont(new Font("Segoe UI", Font.BOLD, 24));
		menuBar.add(mnProfile);

		mnHistory = new JMenu("LỊCH SỬ CHẤM CÔNG");
		mnHistory.addMouseListener(this);
		mnHistory.setIcon(new ImageIcon(frmMainFrame.class.getResource("/ui/images/reload.png")));
		mnHistory.setFont(new Font("Segoe UI", Font.BOLD, 24));
		menuBar.add(mnHistory);

		mnPayroll = new JMenu("THÔNG TIN LƯƠNG");
		mnPayroll.addMouseListener(this);
		mnPayroll.setIcon(new ImageIcon(frmMainFrame.class.getResource("/ui/images/share.png")));
		mnPayroll.setFont(new Font("Segoe UI", Font.BOLD, 24));
		menuBar.add(mnPayroll);

		mnEmployee = new JMenu("DANH SÁCH NHÂN VIÊN");
		mnEmployee.setVisible(false);
		
		if (Controller.getAccount().getEmployee().getIsManager() || Controller.getAccount().getIsAdmin()) {
			mnEmployee.setVisible(true);
		}
		
		mnEmployee.addMouseListener(this);
		mnEmployee.setIcon(new ImageIcon(frmMainFrame.class.getResource("/ui/images/worker.png")));
		mnEmployee.setFont(new Font("Segoe UI", Font.BOLD, 24));
		menuBar.add(mnEmployee);

		mnManagment = new JMenu("QUẢN LÝ");
		mnManagment.setVisible(false);
		
		if (Controller.getAccount().getIsAdmin()) {
			mnManagment.setVisible(true);
		}
		
		mnManagment.addMouseListener(this);
		mnManagment.setIcon(new ImageIcon(frmMainFrame.class.getResource("/ui/images/setting.png")));
		mnManagment.setFont(new Font("Segoe UI", Font.BOLD, 24));
		menuBar.add(mnManagment);

		mnLogout = new JMenu("ĐĂNG XUẤT");
		mnLogout.addMouseListener(this);
		mnLogout.setIcon(new ImageIcon(frmMainFrame.class.getResource("/ui/images/close.png")));

		mntmTimeKeeping = new JMenuItem("Bảng Chấm Công");
		mntmTimeKeeping.addActionListener(this);
		mntmTimeKeeping.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnManagment.add(mntmTimeKeeping);

		mntmPayroll = new JMenuItem("Bảng Lương Nhân Viên");
		mntmPayroll.addActionListener(this);
		mntmPayroll.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnManagment.add(mntmPayroll);

		mntmAccount = new JMenuItem("Tài Khoản");
		mntmAccount.addActionListener(this);
		mntmAccount.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnManagment.add(mntmAccount);
		mnLogout.setFont(new Font("Segoe UI", Font.BOLD, 24));
		menuBar.add(mnLogout);

		/*
		 * PANNEL CENTER
		 */
		pnlCenter = new JPanel();
		contentPane.add(pnlCenter);
		cardLayout = new CardLayout();
		pnlCenter.setLayout(cardLayout);

		// ADD PANEL
		pnlMyInfo pnlMyInfo = new pnlMyInfo();
//		pnl00Home pnl00Home = new pnl00Home(a);
		pnlCenter.add(pnlMyInfo, "pnlMyInfo");

		//

		// PANEL SOUTH - THÔNG TIN USER - PROGRESSBAR - DATE TIME
		JPanel pnlSouth = new JPanel();
		pnlSouth.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(5, 10, 5, 10)));
		contentPane.add(pnlSouth, BorderLayout.SOUTH);
		pnlSouth.setLayout(new BorderLayout(0, 0));

		String admin = "";
		if (Controller.getAccount().getIsAdmin()) {
			admin = " (ADMIN)";
		}

		user = new JLabel(Controller.getAccount().getEmployee().getName() + admin);

		user.setHorizontalAlignment(SwingConstants.LEFT);
		user.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnlSouth.add(user, BorderLayout.WEST);

		JPanel pnlStatus = new JPanel();
		pnlSouth.add(pnlStatus, BorderLayout.EAST);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnlStatus.add(progressBar);

		// DATE TIME
		dateTime = new JLabel("TIME");
		dateTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnlStatus.add(dateTime);

		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				SwingUtilities.invokeLater(() -> {
					dateTime.setText(getCurrentDateTime());
				});
			}
		}, 0, 1000);
	}

	private String getCurrentDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
		return dateFormat.format(new Date());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == mntmTimeKeeping) {
			if (pnlTimeKeepingManagment == null) {
				pnlTimeKeepingManagment = new pnlTimeKeepingManagment();
				pnlCenter.add(pnlTimeKeepingManagment, "pnlTimeKeepingManagment");
			}
			showPanel("pnlTimeKeepingManagment");
		}
		if (o == mntmPayroll) {
			if (pnlPayrollManagment == null) {
				pnlPayrollManagment = new pnlPayrollManagment();
				pnlCenter.add(pnlPayrollManagment, "pnlPayrollManagment");
			}
			showPanel("pnlPayrollManagment");
		}
		if (o == mntmAccount) {
			if (pnlAccountManagment == null) {
				pnlAccountManagment = new pnlAccountManagment();
				pnlCenter.add(pnlAccountManagment, "pnlAccountManagment");
			}
			showPanel("pnlAccountManagment");
		}
	}

	private void showPanel(String pnl) {
		new SwingWorker<Void, Integer>() {
			@Override
			protected Void doInBackground() throws Exception {
				for (int i = 0; i <= 100; i++) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					publish(i);
				}
				return null;
			}

			@Override
			protected void process(List<Integer> chunks) {
				int value = chunks.get(chunks.size() - 1);
				progressBar.setValue(value);
			}

			@Override
			protected void done() {
				cardLayout.show(pnlCenter, pnl);
				progressBar.setValue(0);
			}
		}.execute();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(mnLogout)) {
			int confirm = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất?", "Đăng xuất",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				super.dispose();
				new frmLogin().setVisible(true);
			}
		}
		if (o.equals(mnProfile)) {
			if (pnlMyInfo == null) {
				pnlMyInfo = new pnlMyInfo();
				pnlCenter.add(pnlMyInfo, "pnlMyInfo");
			}
			showPanel("pnlMyInfo");
		}
		if (o.equals(mnHistory)) {
			if (pnlHistory == null) {
				pnlHistory = new pnlHistory();
				pnlCenter.add(pnlHistory, "pnlHistory");
			}
			showPanel("pnlHistory");
		}
		if (o.equals(mnPayroll)) {
			if (pnlPayroll == null) {
				pnlPayroll = new pnlPayroll();
				pnlCenter.add(pnlPayroll, "pnlPayroll");
			}
			showPanel("pnlPayroll");
		}
		if (o.equals(mnEmployee)) {
			if (pnlEmployee == null) {
				pnlEmployee = new pnlEmployee();
				pnlCenter.add(pnlEmployee, "pnlEmployee");
			}
			showPanel("pnlEmployee");
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
	public void dispose() {
		int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát ứng dụng?", "Xác nhận",
				JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel dateTime;

	private JPanel pnlCenter;
	private CardLayout cardLayout;
	private JLabel user;
	private JMenu mnLogout;
	private JMenu mnProfile;
	private JMenu mnHistory;
	private JMenu mnPayroll;
	private JMenu mnManagment;
	private JMenuItem mntmTimeKeeping;
	private JMenuItem mntmPayroll;
	private JMenuItem mntmAccount;
	private JMenu mnEmployee;

}
