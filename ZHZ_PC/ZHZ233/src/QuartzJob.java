import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob implements Job {  
  
    @Override  
    public void execute(JobExecutionContext content) throws JobExecutionException {  
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "������������");    
       /* String jobName = content.getJobDetail().getDescription();
        JobDataMap dataMap = content.getJobDetail().getJobDataMap();  
        String param = dataMap.getString("param");  
        System.out.println("���ݵĲ�����="+param +"����������="+jobName);*/
        String orderNo = content.getJobDetail().getJobDataMap().getString("jobName");
        System.out.println(orderNo);
        
        //����URL������HTTP����
        String url = "http://localhost:8080/wlh/JudgeTimeout?";
        url+="orderNo="+orderNo;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpGet = new HttpPost(url);
		CloseableHttpResponse response = null;

		try {
			//��ȡ��������
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {//����ɹ�Do
				HttpEntity entity = response.getEntity();
				String data = EntityUtils.toString(entity);
				//��ʱ����
				if(data.equals("0")) {
					System.out.println("Ҫ������");
					String closeWeb = "taskkill /f /t /im geckodriver.exe";
					String closeSteam = "taskkill /f /t /im Steam.exe";
					Runtime runtime = Runtime.getRuntime();
					runtime.exec(closeSteam);
					runtime.exec(closeWeb);	
				}
			}else {
				System.out.println("������200,������");
				String closeWeb = "taskkill /f /t /im geckodriver.exe";
				String closeSteam = "taskkill /f /t /im Steam.exe";
				Runtime runtime = Runtime.getRuntime();
				runtime.exec(closeSteam);
				runtime.exec(closeWeb);
			}
			//�ر�
			response.close();
			httpClient.close();
		} catch (IOException e) {
			try {
				System.out.println("����ʧ��,������");
				String closeWeb = "taskkill /f /t /im geckodriver.exe";
				String closeSteam = "taskkill /f /t /im Steam.exe";
				Runtime runtime = Runtime.getRuntime();
				runtime.exec(closeSteam);
				runtime.exec(closeWeb);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
    }  
}  