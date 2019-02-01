package dormitorymanagersystem;

/**
 * @author zhangyuge
 * 
 *<p>宿舍类，一个类代表一个宿舍
 */
public class Dormitory {
	//私有属性
	private String dormitoryId;	   						//宿舍编号
	private int dormitoryType;							//宿舍类型（男生宿舍/女生宿舍）
	private String[] memberName = new String[6];		//成员姓名
	private int[] bedNumber = new int[6];				//床位号
	private int[] memberType = new int[6];				//成员类型
	private int ppcount;								//宿舍人数
	
	//封装属性：宿舍编号
	public String getDormitoryId() {
		return dormitoryId;
	}
	public void setDormitoryId(String dormitoryId) {
		this.dormitoryId = dormitoryId;
	}
	//封装属性：宿舍类型
	public int getDormitoryType() {
		return dormitoryType;
	}
	public void setDormitoryType(int dormitoryType) {
		if (dormitoryType > 1 || dormitoryType < 0) {
			System.out.println("您输入的宿舍类型编号有误，请重新输入！");
		} else {
			this.dormitoryType = dormitoryType;
		}
	}
	//封装属性：成员姓名
	public String getMemberName(int no) {
		return memberName[no];
	}
	public void setMemberName(String memberName, int no) {
		this.memberName[no] = memberName;
	}
	//封装属性：床位号
	public int getBedNumber(int no) {
		return bedNumber[no];
	}
	public void setBedNumber(int bedNumber, int no) {
		if (bedNumber > 6 || bedNumber < 0) {
			System.out.println("您输入的床位号有误，请重新输入！");
		} else {
			this.bedNumber[no] = bedNumber;
		}
	}
	//封装属性：成员类型
	public int getMemberType(int no) {
		return memberType[no];
	}
	public void setMemberType(int memberType, int no) {
		if (memberType > 1 || memberType < 0) {
			System.out.println("您输入的成员类型编号有误，请重新输入！");
		} else {
			this.memberType[no] = memberType;
		}
	}
	//封装属性：宿舍人数
	public int getPpCount() {
		return ppcount;
	}
	public void setPpCount(int ppcount) {
		this.ppcount = ppcount;
	}
}
