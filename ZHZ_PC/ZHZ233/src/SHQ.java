import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
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

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.json.JSONObject;


public class SHQ {

	private JFrame frmZhz;
	private JTextField textField_ddh;
	private Map<String, Key> keyMap;
	private KeyboardHook hook;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
					SHQ window = new SHQ();
					window.frmZhz.setVisible(true);
					ShutdownCallbackThread hook = new ShutdownCallbackThread();
			        Runtime.getRuntime().addShutdownHook(hook);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public SHQ() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		frmZhz = new JFrame();
		frmZhz.setResizable(false);
		frmZhz.setIconImage(Toolkit.getDefaultToolkit().getImage(SHQ.class.getResource("/img/sy_public_logo.gif")));
		frmZhz.setTitle("ZHZ233\u4E13\u4E1A\u4E0A\u53F7\u5668");
		frmZhz.setBounds(600, 150, 600, 450);
		frmZhz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmZhz.getContentPane().setLayout(null);
		
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
		frmZhz.getContentPane().add(panel);
		
		
		panel.setLayout(null);
		
		textField_ddh = new JTextField("输入您的订单号");
		textField_ddh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textField_ddh.setText("");
			}
		});
		textField_ddh.setColumns(10);
		textField_ddh.setBounds(273, 52, 304, 32);
		frmZhz.getContentPane().add(textField_ddh);
		
		
		JButton btnNewButton = new JButton("获取订单");
		btnNewButton.setBounds(361, 96, 97, 23);
		frmZhz.getContentPane().add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
			
		panel_1.setBounds(24, 162, 236, 125);
		frmZhz.getContentPane().add(panel_1);
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
		frmZhz.getContentPane().add(textArea);
		textArea.setText("游戏订单信息再此处显示");
		
		JButton button = new JButton("登录/注销");
		button.setBounds(472, 96, 97, 23);
		frmZhz.getContentPane().add(button);
		
		JButton button_1 = new JButton("获取帮助");
		
		button_1.setBounds(10, 315, 97, 23);
		frmZhz.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("我要投诉");
		button_2.setBounds(252, 315, 97, 23);
		frmZhz.getContentPane().add(button_2);
		
		JButton btnNewButton_1 = new JButton("我要续费");
		btnNewButton_1.setBounds(477, 315, 97, 23);
		frmZhz.getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("<html>温馨提示：1.上号前请先打开TGP或Steam游戏客户端<br>"+
										"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.点击“获取订单”后，将光标放在TGP或Steam的登录框处<br>"
										+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.使用快捷键F7，系统将自动为您登录账号</html>");
		lblNewLabel.setBounds(10, 348, 516, 55);
		frmZhz.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("当前登录账号：");
		label.setForeground(new Color(224, 74, 59));
		label.setBounds(274, 19, 107, 18);
		frmZhz.getContentPane().add(label);
		
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
		frmZhz.getContentPane().add(lblNewLabel_2);
		
		/**
		 * 获取订单按钮监听
		 */
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
						
						if(jsonObject.getString("game_name").equals("英雄联盟")) {
							ImageIcon imageIcon = new ImageIcon("src/img/LOL.jpg");
							imageIcon.setImage(imageIcon.getImage().getScaledInstance(226, 125,  Image.SCALE_DEFAULT));  
							lblNewLabel_1.setIcon(imageIcon);
						}else if(jsonObject.getString("game_name").equals("绝地求生")) {
							ImageIcon imageIcon = new ImageIcon("src/img/cj.jpg");
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
						//添加键盘快捷键F7，输入密码
						hook = new KeyboardHook(jsonObject.getString("goods_access"),passWord);
						Thread T = new Thread(hook);
						T.start();
						
					}else {
						textArea.setText("系统错误");
						ImageIcon icon1 = new ImageIcon("src/img/404.jpg");
						icon1.setImage(icon1.getImage().getScaledInstance(226, 125,  Image.SCALE_DEFAULT));  
						lblNewLabel_1.setIcon(icon1);
						hook.setHookOff();
						System.out.println("结束钩子");
						
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
						Login L = new Login(frmZhz);
						L.setVisible(true);
						lblNewLabel_2.setText(L.getLabelValue());
						return;
					}else {
						Cancel C = new Cancel(frmZhz);
						C.setVisible(true);
						if(C.isCancel()) {
							lblNewLabel_2.setText("");
							hook.setHookOff();
							QuartzManager.shutdownJobs();
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});	
		
	}
	
	static class ShutdownCallbackThread extends Thread {
		public void run() {
			System.out.println("程序要关闭啦");
			String closeSteam = "taskkill /f /t /im Steam.exe";
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(closeSteam);//执行CMD命令
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
