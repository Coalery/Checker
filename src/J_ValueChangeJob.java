
public class J_ValueChangeJob extends J_JobParent {
	
	private O_HCObject.Options changeType; // Position Move -> POS_X
	
	private O_HCObject target;
	private Object privValue; // 변경 전 값
	private Object curValue; // 변경 후 값
	
	public J_ValueChangeJob(O_HCObject.Options changeType, O_HCObject target, Object privValue, Object curValue) {
		this.changeType = changeType;
		this.target = target;
		this.privValue = privValue;
		this.curValue = curValue;
	}
	
	public O_HCObject.Options getChangeType() { return changeType; }
	public O_HCObject getTarget() { return target; }
	public Object getPrivValue() { return privValue; }
	public Object getCurrentValue() { return curValue;}
	
}
