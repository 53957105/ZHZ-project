
public class QuartzDoing implements Runnable {
	private String orderNo;

	public QuartzDoing(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public void run() {
	
		QuartzManager.shutdownJobs();
		System.out.println("【任务启动】开始(每60秒输出一次)...");    
		QuartzManager.addJob(orderNo, QuartzJob.class, "*/20 * * * * ?");
		
		/*System.out.println("【移除定时】开始...");    
        QuartzManager.removeJob(job_name);*/
	}  
   
}
