
public class QuartzDoing implements Runnable {
	private String orderNo;

	public QuartzDoing(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public void run() {
	
		QuartzManager.shutdownJobs();
		System.out.println("��������������ʼ(ÿ60�����һ��)...");    
		QuartzManager.addJob(orderNo, QuartzJob.class, "*/20 * * * * ?");
		
		/*System.out.println("���Ƴ���ʱ����ʼ...");    
        QuartzManager.removeJob(job_name);*/
	}  
   
}
