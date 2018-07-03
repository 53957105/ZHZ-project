import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class MySocket {
	public byte[] run(String orderNo,String publicKey,String userName,String session) throws IOException {
		
		String url= "http://localhost:8080/wlh/Encrypt?";
		url += "orderNo="+orderNo+"&publicKey="+publicKey+"&userName="+userName+"&session="+session;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		System.out.println(url);
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "联网失败，请检查网络", "提示", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		System.out.println(response.getStatusLine());
		byte[] data = null;
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				data = EntityUtils.toByteArray(entity);
			}
		}else {
			JOptionPane.showMessageDialog(null, "获取信息失败", "提示", JOptionPane.ERROR_MESSAGE);
		}
		response.close();
		httpClient.close();
		return data;
	}
	
	public String getOrderInfo(String userName,String session,String orderNo) throws ClientProtocolException, IOException {
		String url= "http://localhost:8080/wlh/getOrderInfo?";
		url += "orderNo="+orderNo+"&userName="+userName+"&session="+session;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		System.out.println(url);
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		response = httpClient.execute(httpGet);
		System.out.println(response.getStatusLine());
		HttpEntity entity;
		if(response.getStatusLine().getStatusCode()==200) {
			entity = response.getEntity();
			return EntityUtils.toString(entity);
		}else {
			return null;
		}
	}

}
