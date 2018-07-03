import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Key;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.json.JSONObject;

public class WebSHQ extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_ddh;
	private Map<String, Key> keyMap;
	private String _access;
	private String _passWord;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
					WebSHQ frame = new WebSHQ();
					frame.setVisible(true);
					ShutdownCallbackThread hook = new ShutdownCallbackThread();
			        Runtime.getRuntime().addShutdownHook(hook);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	@SuppressWarnings("resource")
	public WebSHQ() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SHQ.class.getResource("/img/sy_public_logo.gif")));
		setBounds(600, 150, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		getContentPane().setLayout(null);
		
		
		JPanel panel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				ImageIcon imageIcon = new ImageIcon("src/img/sy_public_logo.gif");
				Image img = imageIcon.getImage();
				g.drawImage(img, 0, 0, 260, 155, imageIcon.getImageObserver());
			}
		};
		panel.setBounds(0, 0, 260, 155);
		panel.setLayout(null);
		this.getContentPane().add(panel);
		
		textField_ddh = new JTextField("输入您的订单号");
		textField_ddh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textField_ddh.setText("");
			}
		});
		textField_ddh.setColumns(10);
		textField_ddh.setBounds(273, 52, 304, 32);
		this.getContentPane().add(textField_ddh);
		
		
		JButton btnNewButton = new JButton("获取订单");
		btnNewButton.setBounds(374, 97, 97, 23);
		this.getContentPane().add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
			
		panel_1.setBounds(24, 162, 236, 125);
		this.getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel();
		ImageIcon icon1 = new ImageIcon("src/img/404.jpg");
		icon1.setImage(icon1.getImage().getScaledInstance(226, 125,  Image.SCALE_DEFAULT));  
		lblNewLabel_1.setIcon(icon1);
		panel_1.add(lblNewLabel_1, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setEditable(false);
		textArea.setBounds(274, 132, 284, 155);
		this.getContentPane().add(textArea);
		textArea.setText("游戏订单信息再此处显示");
		
		JButton button = new JButton("登录/注销");
		button.setBounds(480, 97, 97, 23);
		this.getContentPane().add(button);
		
		JButton button_1 = new JButton("获取帮助");
		
		button_1.setBounds(10, 315, 97, 23);
		this.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("我要投诉");
		button_2.setBounds(252, 315, 97, 23);
		this.getContentPane().add(button_2);
		
		JButton btnNewButton_1 = new JButton("我要续费");
		btnNewButton_1.setBounds(477, 315, 97, 23);
		this.getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("<html>温馨提示：1.上号前请先登录账号并获取订单<br>"+
										"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.点击“一键上号”后，上号器会自动打开浏览器并上号<br>"
										+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.使用前请确认系统已安装Firefox浏览器</html>");
		lblNewLabel.setBounds(10, 348, 516, 55);
		this.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("当前登录账号：");
		label.setForeground(new Color(224, 74, 59));
		label.setBounds(274, 19, 107, 18);
		this.getContentPane().add(label);
		
		JLabel lblNewLabel_2 = new JLabel(" ");
		lblNewLabel_2.setForeground(new Color(224, 74, 59));
		lblNewLabel_2.setBounds(374, 19, 152, 18);
		FileInputStream inputStream = new FileInputStream(new File("src/lib/UserInfo.mkey"));
		InputStreamReader reader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String userName = bufferedReader.readLine();
		String session = bufferedReader.readLine();
		if(userName!=null && session!=null) {
			lblNewLabel_2.setText(userName);
		}
		this.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton_2 = new JButton("一键上号");
		btnNewButton_2.setBounds(274, 97, 97, 23);
		getContentPane().add(btnNewButton_2);
		
		//订单获取按钮监听时间
		btnNewButton.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unused")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String userName = null;
				String session = null;
				try {
					FileInputStream inputStream = new FileInputStream(new File("src/lib/UserInfo.mkey"));
					InputStreamReader reader = new InputStreamReader(inputStream);
					BufferedReader bufferedReader = new BufferedReader(reader);
					userName = bufferedReader.readLine();
					session = bufferedReader.readLine();
					if(userName==null || session==null) {
						JOptionPane.showMessageDialog(new JPanel(), "登录后操作！", "警告",JOptionPane.WARNING_MESSAGE); 
						return;
					}//验证登陆为true
					
					String orderNo = textField_ddh.getText();
					if(!Utils.IsNumber(orderNo)) {
						JOptionPane.showMessageDialog(new JPanel(), "输入正确的订单号和账号！", "警告",JOptionPane.WARNING_MESSAGE); 
						return;
					}
					// RSA 获取公钥
					RSACoder RSA = new RSACoder();
					String publicKey = null;
					String privateKey = null;
					keyMap = RSA.initKey();
					publicKey = RSA.getPublicKey(keyMap);
					privateKey = RSA.getPrivateKey(keyMap);
					System.out.println(publicKey);

					// 获得密码
					MySocket mySocket = new MySocket();
					byte[] bytePass = null;
					byte[] data;
					try {
						data = mySocket.run(orderNo, publicKey, userName, session);
						if (data.length != 0) {
							bytePass = RSA.decryptByPrivateKey(data, privateKey);
						}else {
							bytePass = "NULL".getBytes();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String passWord = new String(bytePass);
					// 获取订单信息
					String orderInfo = mySocket.getOrderInfo(userName, session, orderNo);
					if(orderInfo.equals("用户信息过期，重新登陆")) {
						JOptionPane.showMessageDialog(new JPanel(), "用户信息过期，重新登录", "警告",JOptionPane.WARNING_MESSAGE);
						return;
					} else if (!orderInfo.isEmpty()) {
						JSONObject jsonObject = new JSONObject(orderInfo);
						textArea.setText(
								jsonObject.getString("goods_theme") + "\r\n" + "订单号：" + jsonObject.getString("order_no")
										+ "\r\n" + "开始时间：" + jsonObject.getString("order_start_time")
										+ "\r\n" + "结束时间：" + jsonObject.getString("order_end_time") 
										+ "\r\n" + "游戏名："+ jsonObject.getString("game_name")
										+ "\r\n" + "区服："+ jsonObject.getString("goods_area") + jsonObject.getString("goods_server")
										+ "\r\n" + "账号："+jsonObject.getString("goods_access"));
						_access = jsonObject.getString("goods_access");
						_passWord = passWord;
						
						if(jsonObject.getString("game_name").equals("英雄联盟")) {
							ImageIcon imageIcon = new ImageIcon("src/img/LOL.jpg");
							imageIcon.setImage(imageIcon.getImage().getScaledInstance(226, 125,  Image.SCALE_DEFAULT));  
							lblNewLabel_1.setIcon(imageIcon);
						}else if(jsonObject.getString("game_name").equals("绝地求生")) {
							ImageIcon imageIcon = new ImageIcon("src/img/cj.jpg");
							imageIcon.setImage(imageIcon.getImage().getScaledInstance(226, 125,  Image.SCALE_DEFAULT));  
							lblNewLabel_1.setIcon(imageIcon);
						}else if(jsonObject.getString("game_name").equals("4399")) {
							ImageIcon imageIcon = new ImageIcon("src/img/4399.jpg");
							imageIcon.setImage(imageIcon.getImage().getScaledInstance(226, 125,  Image.SCALE_DEFAULT));  
							lblNewLabel_1.setIcon(imageIcon);
						}else {
							ImageIcon imageIcon = new ImageIcon("src/img/404.jpg");
							imageIcon.setImage(imageIcon.getImage().getScaledInstance(226, 125,  Image.SCALE_DEFAULT));  
							lblNewLabel_1.setIcon(imageIcon);
						}
						//超时下线定时任务
						QuartzDoing rescue = new QuartzDoing(orderNo);
						new Thread(rescue).start();
					}else {
						textArea.setText("系统错误");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		/**
		 * 登陆注销按钮
		 */
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String userName = null;
				String session = null;
				FileInputStream inputStream;
				try {
					inputStream = new FileInputStream(new File("src/lib/UserInfo.mkey"));
					InputStreamReader reader = new InputStreamReader(inputStream);
					BufferedReader bufferedReader = new BufferedReader(reader);
					userName = bufferedReader.readLine();
					session = bufferedReader.readLine();
					if (userName == null || session == null) {
						Login L = new Login(WebSHQ.this);
						L.setVisible(true);
						lblNewLabel_2.setText(L.getLabelValue());
						return;
					}else {
						Cancel C = new Cancel(WebSHQ.this);
						C.setVisible(true);
						if(C.isCancel()) {
							lblNewLabel_2.setText("");
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		/**
		 * 一键上号按钮监听
		 */
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Schedul4399Utils Utils = new Schedul4399Utils();
		        Utils.Schedul4399(_access, _passWord);
			}
		});
	}
	
	static class ShutdownCallbackThread extends Thread {
		public void run() {
			System.out.println("程序要关闭啦");
			String closeSteam = "taskkill /f /t /im geckodriver.exe";
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(closeSteam);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//执行CMD命令
		}// 设置关闭筏值
	}
}
