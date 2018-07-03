import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Login extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private FileWriter fileWriter;
	private String labelUserName;



	/**
	 * Create the dialog.
	 */
	public Login(JFrame shq) {
		super(shq,"denglu",true);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/sy_public_logo.gif")));
		setTitle("\u7528\u6237\u767B\u9646");
		setBounds(700, 250, 391, 251);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(136, 45, 145, 21);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("\u7528\u6237\u540D");
		label.setBounds(68, 48, 58, 15);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6  \u7801");
		label_1.setBounds(68, 84, 58, 15);
		contentPanel.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(136, 81, 145, 21);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton button = new JButton("\u767B\u9646");
		
		button.setBounds(66, 138, 97, 23);
		contentPanel.add(button);
		
		JButton button_1 = new JButton("\u6CE8\u518C");
		button_1.setBounds(204, 138, 97, 23);
		contentPanel.add(button_1);
		
		/**
		 * 登陆按钮点击事件
		 */
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String url = "http://localhost:8080/wlh/Login?";
					String username = textField.getText();
					String password = textField_1.getText();
					if(Utils.isNullString(username) || Utils.isNullString(password)) {
						JOptionPane.showMessageDialog(null, "用户名密码不能为空", "提示", JOptionPane.ERROR_MESSAGE);
					}
					/*if(!Utils.isLogonInfo(username) || !Utils.isLogonInfo(password)) {
						JOptionPane.showMessageDialog(null, "输入正确的账号，密码", "提示", JOptionPane.ERROR_MESSAGE);
					}*/
					//构建URL，发起HTTP请求
					url+="UserName="+username+"&PassWord="+password;
					CloseableHttpClient httpClient = HttpClientBuilder.create().build();
					HttpPost httpGet = new HttpPost(url);
					CloseableHttpResponse response = null;
					//获取返回数据
					response = httpClient.execute(httpGet);
					if (response.getStatusLine().getStatusCode() == 200) {//请求成功Do
						HttpEntity entity = response.getEntity();
						String data = EntityUtils.toString(entity);
						if (data == "用户名和密码不能为空" || data == "找不到用户或密码错误") {
							JOptionPane.showMessageDialog(null, data, "提示", JOptionPane.ERROR_MESSAGE);
						}
						// 写入文件
						File file = new File("src/lib/UserInfo.mkey");
						if (!file.exists()) {
							file.createNewFile();
						}
						fileWriter = new FileWriter(file);
						fileWriter.write(username + "\r\n");
						fileWriter.write(data);
						//添加显示
						setLabelValue(username);
					}else {
						fileWriter.write("");
						JOptionPane.showMessageDialog(null, "登录失败", "提示", JOptionPane.ERROR_MESSAGE);
					}
					//关闭
					fileWriter.flush();
					fileWriter.close();
					response.close();
					httpClient.close();
					dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void setLabelValue(String username) {
		this.labelUserName = username;
	}
	
	public String getLabelValue() {
		return this.labelUserName;
	}
}
