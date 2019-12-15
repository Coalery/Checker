
public class J_Job {
	private J_JobType jobType;
	private J_JobParent job;
	
	public J_Job(J_JobType jobType, J_JobParent job) {
		this.jobType = jobType;
		this.job = job;
	}
	
	public J_JobType getJobType() { return jobType; }
	public J_JobParent getJob() { return job; }
	
	@Override
	public String toString() { return jobType.toString(); }
	
	public enum J_JobType {
		CREATE, REMOVE, VALUE_CHANGE
	}
}
