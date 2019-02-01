package dormitorymanagersystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * @author zhangyuge
 * <p>管理类
 */
public class Manager {

	Scanner input = new Scanner(System.in);
	Dormitory[] dormitories = new Dormitory[15];		//创建15个宿舍
	private int size = 0;								//记录存在的宿舍个数
	private int reNameSize = 0;							//记录重名个数
	private int[] a = new int[10];						//记录一个姓名所在宿舍下标值
	private int[] b = new int[10];						//记录一个姓名在一间宿舍里的下标值，这两个值组合在一起就决定了一个成员
	//初始化源文件
	public void init(File f) {
		System.out.println("正在初始化。。。\n网络16-1班宿舍信息");
		try {
			Workbook book = Workbook.getWorkbook(f);				//读取源文件
			Sheet sheet = book.getSheet(0);							//获取表头
			size = sheet.getRows() - 1;								//行数减一即为已存在的宿舍个数
			for (int i = 1; i < sheet.getRows(); i++) {				//循环读取每一行的数据
				dormitories[i-1] = new Dormitory();					//每读取一行就新建一个宿舍
				Cell cell = sheet.getCell(0, i);					//读取每行第一个单元格里面的数据
				String str1 = cell.getContents();					//用字符串去接收
				String[] s1 = str1.split("（");						//以半边圆括号进行拆分
				dormitories[i-1].setDormitoryId(s1[0]);				//将s[0]写入属性：宿舍编号
				if (s1[1].startsWith("女")) {						//判断s[1]的值
					dormitories[i-1].setDormitoryType(0);			//如果是 女 ，属性宿舍类型写入的值 0
				} else {
					dormitories[i-1].setDormitoryType(1);			//如果是 男 ，属性宿舍类型写入得值 1
				}
				int s = 0;											//记录每行第二个单元格往后非空的值的个数
				for (int j = 1; j < sheet.getColumns(); j++) {		//循环读取第二个以及第二个往后的单元格的值
					cell = sheet.getCell(j, i);
					String str2 = cell.getContents();				//用字符串去接收每个单元格这里面的值
					if ((str2 != null) && (!str2.equals(""))) {		//防止写入空值
						s++;										
						String[] s2 = str2.split("（");				//以半边圆括号进行拆分
						if (s2.length > 1) {						//大于1
							dormitories[i-1].setMemberType(1, s-1);			//写入宿舍长
							dormitories[i-1].setMemberName(s2[0], s-1);		//写入姓名
						} else {														//小于1
							dormitories[i-1].setMemberName(cell.getContents(), s-1);	//写入姓名
							dormitories[i-1].setMemberType(0, s-1);						//写入普通成员
						}
						dormitories[(i - 1)].setBedNumber(s, s-1);						//写入床位号
						dormitories[(i - 1)].setPpCount(this.dormitories[i-1].getPpCount()+1);		//记录人数
					}
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("初始化完毕。。。");
	}
	//主菜单
	public void menu() {
		int flag = 1;
		do {
			System.out.println("==================欢迎使用宿舍管理系统===============");
			System.out.println("1、查\t询");
			System.out.println("2、添\t加");
			System.out.println("3、删\t除");
			System.out.println("4、修\t改");
			System.out.println("5、保\t存");
			System.out.println("6、退出系统");
			System.out.print("请选择：");
			int num;
			while (true) {									//设置死循环，直到输入的值为数字，以下还有很多类似的代码
				try {
					num = input.nextInt();
					break;
				} catch (InputMismatchException e) {
					System.out.print("请输入一个数字：");
					input = new Scanner(System.in);
				}
			}
			switch (num) {
			case 1:
				System.out.println("===================1、查询===================");
				System.out.println("（1）查询全部");
				System.out.println("（2）查询单个宿舍");
				System.out.println("（3）查询单个成员");
				System.out.println("（4）查询宿舍概况");
				System.out.println("（5）退出查询");
				System.out.print("请输入查询选项：");
				int num1;
				while (true) {
					try {
						num1 = input.nextInt();
						break;
					} catch (InputMismatchException e) {
						System.out.print("请输入一个数字：");
						input = new Scanner(System.in);
					}
				}
				switch (num1) {
				case 1:
					showAll();
					break;
				case 2:
					System.out.print("请输入宿舍编号：");
					String dormitoryId = input.next();
					showDormitory(dormitoryId);
					break;
				case 3:
					System.out.print("请输入名字：");
					String memberName = input.next();
					showMember(memberName);
					break;
				case 4:
					showDormitoriesStatus();
					break;
				case 5:
					break;
				default:
					System.out.println("输入有误！");
				}
				break;
			case 2:
				System.out.println("===================2、添加===================");
				System.out.println("（1）添加宿舍");
				System.out.println("（2）添加单个成员");
				System.out.println("（3）退出添加");
				System.out.print("请输入添加选项：");
				int num2;
				while (true) {
					try {
						num2 = input.nextInt();
						break;
					} catch (InputMismatchException e) {
						System.out.print("请输入一个数字：");
						input = new Scanner(System.in);
					}
				}
				switch (num2) {
				case 1:
					System.out.print("请输入宿舍编号：");
					addDormitory(input.next());
					break;
				case 2:
					System.out.print("请输入要添加到的宿舍编号：");
					String dormitoryId = input.next();
					addMember(dormitoryId);
					break;
				case 3:
					break;
				default:
					System.out.println("输入有误！");
				}
				break;
			case 3:
				System.out.println("===================3、删除===================");
				System.out.println("（1）删除整个宿舍");
				System.out.println("（2）删除单个成员");
				System.out.println("（3）退出删除");
				System.out.print("请输入删除选项：");
				int num3;
				while (true) {
					try {
						num3 = input.nextInt();
						break;
					} catch (InputMismatchException e) {
						System.out.print("请输入一个数字：");
						input = new Scanner(System.in);
					}
				}
				switch (num3) {
				case 1:
					System.out.print("请输入宿舍编号：");
					String dormitoryId = input.next();
					System.out.print("确认删除（Y/N）：");
					if (input.next().equalsIgnoreCase("Y")) {
						delDormitory(dormitoryId);
					} else {
						System.out.println("删除中止！");
					}
					break;
				case 2:
					System.out.print("请输入名字：");
					String memberName = input.next();
					System.out.print("确认删除（Y/N）：");
					if (input.next().equalsIgnoreCase("Y")) {
						delMember(memberName);
					} else {
						System.out.println("删除中止！");
					}
					break;
				case 3:
					break;
				default:
					System.out.println("输入有误！");
				}
				break;
			case 4:
				System.out.println("===================4、修改==================");
				System.out.println("（1）修改宿舍编号");
				System.out.println("（2）修改成员姓名");
				System.out.println("（3）修改成员所在的宿舍编号");
				System.out.println("（4）修改成员床位号");
				System.out.println("（5）修改宿舍成员类型");
				System.out.println("（6）退出修改");
				System.out.print("请输入修改选项：");
				int num4;
				while (true) {
					try {
						num4 = input.nextInt();
						break;
					} catch (InputMismatchException e) {
						System.out.print("请输入一个数字：");
						input = new Scanner(System.in);
					}
				}
				switch (num4) {
				case 1:
					System.out.print("请输入原宿舍编号：");
					String oldDormitoryId = input.next();
					System.out.print("请输入新的编号：");
					String newDormitoryId = input.next();
					alterDormitoryId(oldDormitoryId, newDormitoryId);
					break;
				case 2:
					System.out.print("请输入名字：");
					String oldName = input.next();
					System.out.print("请输入新的名字：");
					String newName = input.next();
					alterMemberName(oldName, newName);
					break;
				case 3:
					System.out.print("请输入名字：");
					String memberName = input.next();
					System.out.print("请输入新的宿舍编号：");
					String newDormitoryId1 = input.next();
					alterMemberDormitoryId(memberName, newDormitoryId1);
					break;
				case 4:
					System.out.print("请输入宿舍编号：");
					String dormitoryId = input.next();
					System.out.print("请输入原床位号：");
					int oldNumber = setBedNumber();
					System.out.print("请输入新的床位号：");
					int newNumber = setBedNumber();
					alterBedNumber(dormitoryId, oldNumber, newNumber);
					break;
				case 5:
					System.out.print("请输入姓名：");
					String memberName1 = input.next();
					System.out.print("请输入新的成员类型（0代表普通成员，1代表宿舍长）：");
					int memberType = setMemberTypeNumber();
					alterMemberType(memberName1, memberType);
					break;
				case 6:
					break;
				default:
					System.out.println("输入有误！");
				}
				break;
			case 5:
				System.out.println("===================5、保存===================");
				try {
					File test = new File("src/网络16-1班宿舍信息（修改版）.txt");
					test.createNewFile();									//创建txt文件
					FileWriter out = new FileWriter(test);					//写入到txt文件
					out.write("网络16-1班宿舍信息表\r\n");						//写入第一行
					out.write("宿舍编号\t姓名\t性别\t成员类型\t床位号\r\n");			//写入第二行
					char sex;
					String str = "";
					String memberType;
					for (int i = 0; i < size; i++) {						//循环写入宿舍里每个人的信息
						for (int j = 0; j < dormitories[i].getPpCount(); j++) {		//每行写入一人的信息
							if (dormitories[i].getDormitoryType() == 0) {
								sex = '女';
							} else {
								sex = '男';
							}
							if (dormitories[i].getMemberType(j) == 0) {
								memberType = "普通成员";
							} else {
								memberType = "宿舍长";
							}
							str = dormitories[i].getDormitoryId() + "\t" + dormitories[i].getMemberName(j) + "\t"
								+ sex + "\t" + memberType + "\t" + dormitories[i].getBedNumber(j) + "\r\n";
							out.write(str);				
						}
					}
					out.close();				//关闭输出流
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("保存成功！");
				System.out.println("已在src目录下生成.txt文件");
				break;
			case 6:
				flag--;
				System.out.println("欢迎下次使用！");
				break;
			default:
				System.out.println("您的输入有误，请重新输入！");
			}
			if (flag > 0) {
				System.out.print("是否继续（Y/N）：");
				if (input.next().equalsIgnoreCase("N")) {
					System.out.println("欢迎下次使用！");
					flag--;
				}
			}
		} while (flag != 0);
	}

	/**
	 * @param dormitoryId
	 * @author 
	 * <p>传入宿舍编号，添加成员到指定宿舍
	 */
	private void addMember(String dormitoryId) {
		if (searchDormitory(dormitoryId) != -1) {
			if (searchDormitoryPpcount(dormitoryId) < 6) {
				String ch = "Y";
				do {
					System.out.print("请输入名字：");
					String memberName = input.next();
					if (searchMember(memberName) != -1) {
						showMember(memberName);
						System.out.print("该姓名已存在，是否继续添加（Y/N）:");
						if (input.next().equalsIgnoreCase("N")) {
							System.out.println("已退出添加！");
							break;
						}
					}
					System.out.print("请输入性别（0代表女，1代表男）：");
					int sex = setSexNumber();
					if (sex == searchDormitoryType(dormitoryId)) {
						System.out.print("请输入成员类型（0代表普通成员，1代表宿舍长）：");
						int memberType = setMemberTypeNumber();
						if ((seachHeadOfDormitory(dormitoryId) > -1) && (memberType == 1)) {
							System.out.println("宿舍长已存在，不能添加！");
						} else {
							System.out.print("请输入床位号：");
							int bedNumber = setBedNumber();
							if (searchBedNumber(dormitoryId, bedNumber) == -1) {
								addMember(dormitoryId, memberName, memberType, bedNumber);
							} else {
								System.out.println("该床位号已存在，不能添加！");
							}
						}
					} else {
						String memberSex;
						String memberType;
						if (sex == 0) {
							memberSex = "女生";
						} else {
							memberSex = "男生";
						}
						if (searchDormitoryType(dormitoryId) == 0) {
							memberType = "女生";
						} else {
							memberType = "男生";
						}
						System.out.println("添加错误！");
						System.out.println(dormitoryId + "宿舍是" + memberType + "宿舍，不能把" + memberSex + "添加到"
								+ memberType + "宿舍！");
					}
					System.out.print("是否继续添加（Y/N）：");
					ch = input.next();
					if (!ch.equalsIgnoreCase("Y")) {
						System.out.println("已退出添加！");
					}
					if (searchDormitoryPpcount(dormitoryId) >= 6) {
						break;
					}
				} while (ch.equalsIgnoreCase("Y"));
			} else {
				System.out.println("成员已满，不能添加！");
			}
		} else {
			System.out.println("找不到该宿舍信息！");
		}
	}
	
	/**
	 * @return
	 * @author 
	 * <p>设置床位号，返回正确的床位号
	 */
	private int setBedNumber() {
		int bedNumber = 0;
		while (true) {
			try {
				bedNumber = input.nextInt();
				if ((bedNumber <= 6) && (bedNumber >= 1)) {
					break;
				}
				throw new MyInputMismatchException("输入有误！");
			} catch (InputMismatchException e) {
				System.out.println("输入有误！");
				System.out.print("请输入一个数字：");
				input = new Scanner(System.in);
			} catch (MyInputMismatchException e) {
				System.out.println(e.getMessage());
				System.out.print("请输入正确的床位号（1-6）：");
				input = new Scanner(System.in);
			}
		}
		return bedNumber;
	}

	/**
	 * @return
	 * @author 
	 * <p>设置成员类型，返回正确的数值
	 */
	private int setMemberTypeNumber() {
		int memberType = 0;
		while (true) {
			try {
				memberType = input.nextInt();
				if ((memberType <= 1) && (memberType >= 0)) {
					break;
				}
				throw new MyInputMismatchException("输入有误！");
			} catch (InputMismatchException e) {
				System.out.println("输入有误！");
				System.out.print("请输入一个数字：");
				input = new Scanner(System.in);
			} catch (MyInputMismatchException e) {
				System.out.println(e.getMessage());
				System.out.print("请输入0或者1（0代表普通成员，1代表宿舍长）：");
				input = new Scanner(System.in);
			}
		}
		return memberType;
	}

	/**
	 * @return
	 * <p>设置性别编号，返回正确的数值
	 */
	private int setSexNumber() {
		int sex;
		while (true) {
			try {
				sex = input.nextInt();
				if ((sex <= 1) && (sex >= 0)) {
					break;
				}
				throw new MyInputMismatchException("输入有误！");
			} catch (InputMismatchException e) {
				System.out.println("输入有误！");
				System.out.print("请输入一个数字：");
				this.input = new Scanner(System.in);
			} catch (MyInputMismatchException e) {
				System.out.println(e.getMessage());
				System.out.print("请输入0或者1（0代表女，1代表男）：");
				this.input = new Scanner(System.in);
			}
		}
		return sex;
	}

	/**
	 * @param memberName  
	 * @param memberType
	 * @return
	 * @author 
	 * <p>修改成员类型，当输入的修改记录编号超出范围时，停止修改，返回null
	 */
	private Object alterMemberType(String memberName, int memberType) {
		delReNameSize();
		searchReNameMember(memberName, 0, 0);
		if (reNameSize >= 1) {
			int i = a[0];
			int j = b[0];
			if (reNameSize > 1) {
				showMember(memberName);
				System.out.print("请输入要修改的记录编号：");
				int num = 0;
				while (true) {
					try {
						num = input.nextInt();
						break;
					} catch (InputMismatchException e) {
						System.out.print("请输入一个数字：");
						input = new Scanner(System.in);
					}
				}
				if (num > reNameSize || num < 1) {
					System.out.println("您输入的编号不在范围内！");
					return null;
				}
				i = a[num - 1];
				j = b[num - 1];
			}
			int k = seachHeadOfDormitory(this.dormitories[i].getDormitoryId());
			if ((k != -1) && (memberType == 1)) {
				System.out.print(this.dormitories[i].getDormitoryId() + "宿舍已有宿舍长，是否需要重置（Y/N）：");
				if (this.input.next().equalsIgnoreCase("Y")) {
					this.dormitories[i].setMemberType(0, k);
					this.dormitories[i].setMemberType(memberType, j);
					System.out.println("设置成功！");
				}
			} else {
				this.dormitories[i].setMemberType(memberType, j);
				System.out.println("修改成功！");
			}
		} else {
			System.out.println("找不到该学生信息！");
		}
		return null;
	}

	/**
	 * @param dormitoryId
	 * @param oldNumber
	 * @param newNumber
	 * @author 
	 * <p> 修改床位号
	 */
	private void alterBedNumber(String dormitoryId, int oldNumber, int newNumber) {
		int i = searchDormitory(dormitoryId);
		if (i != -1) {
			int j = searchBedNumber(dormitoryId, oldNumber);
			if (j != -1) {
				int k = searchBedNumber(dormitoryId, newNumber);
				if (k == -1) {
					dormitories[i].setBedNumber(newNumber, j);
					System.out.println("修改成功！");
				} else {
					System.out.print(newNumber + "号床位号已存在，是否需要交换床位号（Y/N）：");
					if (input.next().equalsIgnoreCase("Y")) {
						dormitories[i].setBedNumber(newNumber, j);
						dormitories[i].setBedNumber(oldNumber, k);
						System.out.println("交换成功！");
					} else {
						System.out.println("交换中止！");
					}
				}
			} else {
				System.out.println("您输入的原床位号不存在！");
			}
		} else {
			System.out.println("找不到该宿舍编号信息！");
		}
	}

	/**
	 * @param memberName
	 * @param newDormitoryId
	 * @author 
	 * <p>修改成员宿舍编号
	 */
	private void alterMemberDormitoryId(String memberName, String newDormitoryId) {
		int i = searchMember(memberName);
		int j = searchDormitory(newDormitoryId);
		int k = searchMemberInDormitory(memberName);
		if (i != -1) {
			if (j != -1) {
				if (dormitories[j].getDormitoryType() == dormitories[k].getDormitoryType()) {
					if (dormitories[j].getPpCount() < 6) {
						System.out.println("================================");
						System.out.println("正在进行原宿舍删除，新宿舍添加工作");
						delMember(memberName);
						addMember(newDormitoryId, memberName, 0, dormitories[j].getPpCount() + 1);
						System.out.println("================================");
						System.out.println("修改成功！");
					} else {
						System.out.println(newDormitoryId + "宿舍已住满，不能修改！");
					}
				} else {
					String memberSex;
					String dormitoryType;
					if (dormitories[k].getDormitoryType() == 0) {
						memberSex = "女生";
					} else {
						memberSex = "男生";
					}
					if (dormitories[j].getDormitoryType() == 0) {
						dormitoryType = "女生";
					} else {
						dormitoryType = "男生";
					}
					System.out.println("修改有误！");
					System.out.println("不能把" + memberSex + "修改到" + dormitoryType + "宿舍里！");
				}
			} else {
				System.out.println("您输入的宿舍编号不存在！");
			}
		} else {
			System.out.println("找不到该学生信息！");
		}
	}

	/**
	 * @param oldName
	 * @param newName
	 * @return
	 * @author 
	 * <p>修改成员姓名 ，支持同名修改
	 */
	private Object alterMemberName(String oldName, String newName) {
		delReNameSize();
		searchReNameMember(oldName, 0, 0);
		if (reNameSize >= 1) {
			int i = a[0];
			int j = b[0];
			if (this.reNameSize > 1) {
				showMember(oldName);
				System.out.print("请输入要修改的记录编号：");
				int num = 0;
				while (true) {
					try {
						num = input.nextInt();
						break;
					} catch (InputMismatchException e) {
						System.out.print("请输入一个数字：");
						input = new Scanner(System.in);
					}
				}
				if ((num > reNameSize) || (num < 1)) {
					System.out.println("您输入的编号不在范围内！");
					return 0;
				}
				i = a[num - 1];
				j = b[num - 1];
			}
			delReNameSize();
			searchReNameMember(newName, 0, 0);
			if (reNameSize >= 1) {
				System.out.print(newName + "同学的信息已存在，是否交换信息（Y/N）：");
				if (input.next().equalsIgnoreCase("Y")) {
					int p = a[0];
					int q = b[0];
					if (reNameSize > 1) {
						showMember(newName);
						System.out.print("请输入要修改的记录编号：");
						int num1 = 0;
						while (true) {
							try {
								num1 = input.nextInt();
								break;
							} catch (InputMismatchException e) {
								System.out.print("请输入一个数字：");
								input = new Scanner(System.in);
							}
						}
						if ((num1 > reNameSize) || (num1 < 1)) {
							System.out.println("您输入的编号不在范围内！");
							return 0;
						}
						p = this.a[num1 - 1];
						q = this.b[num1 - 1];
					}
					dormitories[i].setMemberName(newName, j);
					dormitories[p].setMemberName(oldName, q);
					System.out.println("交换信息成功！");
				} else {
					dormitories[i].setMemberName(newName, j);
					System.out.println("修改成功！");
				}
			} else {
				dormitories[i].setMemberName(newName, j);
				System.out.println("修改成功！");
			}
		} else {
			System.out.println("找不到该学生信息！");
		}
		return null;
	}

	/**
	 * @param oldDormitoryId
	 * @param newDormitoryId
	 * @author 
	 * <p>修改宿舍编号
	 */
	private void alterDormitoryId(String oldDormitoryId, String newDormitoryId) {
		int i = searchDormitory(oldDormitoryId);
		if (i != -1) {
			int j = searchDormitory(newDormitoryId);
			if (j == -1) {
				this.dormitories[i].setDormitoryId(newDormitoryId);
				System.out.println("修改成功！");
			} else {
				System.out.print(newDormitoryId + "宿舍已存在，是否交换编号（Y/N）：");
				if (this.input.next().equalsIgnoreCase("Y")) {
					this.dormitories[i].setDormitoryId(newDormitoryId);
					this.dormitories[j].setDormitoryId(oldDormitoryId);
					System.out.println("交换编号信息成功！");
				}
			}
		} else {
			System.out.println("原宿舍编号不存在！");
		}
	}

	/**
	 * @param dormitoryId
	 * @param bedNumber
	 * @return
	 * @author 
	 * <p>查询床位号，返回其在宿舍里的下标值，查不到返回-1
	 */
	private int searchBedNumber(String dormitoryId, int bedNumber) {
		int i = searchDormitory(dormitoryId);
		for (int j = 0; j < this.dormitories[i].getPpCount(); j++) {
			if (this.dormitories[i].getBedNumber(j) == bedNumber) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * @param dormitoryId
	 * @author 
	 * <p>添加一间宿舍
	 */
	private void addDormitory(String dormitoryId) {
		if (this.size < this.dormitories.length) {
			if (searchDormitory(dormitoryId) == -1) {
				this.dormitories[this.size] = new Dormitory();
				this.dormitories[this.size].setDormitoryId(dormitoryId);
				System.out.print("请输入宿舍类型（0代表女生，1代表男生）：");
				this.dormitories[this.size].setDormitoryType(setSexNumber());
				this.size += 1;
				System.out.println("添加成功！");
			} else {
				System.out.println("该宿舍已存在！");
			}
		} else {
			System.out.println("没有空的宿舍了，不能添加！");
		}
	}

	/**
	 * @param dormitoryId
	 * @param memberName
	 * @param memberType
	 * @param bedNumber
	 * @author 
	 * <p>根据传入的四个参数，添加一位成员及其信息
	 */
	private void addMember(String dormitoryId, String memberName, int memberType, int bedNumber) {
		int i = searchDormitory(dormitoryId);
		int j = searchDormitoryPpcount(dormitoryId);
		dormitories[i].setMemberName(memberName, j);
		dormitories[i].setMemberType(memberType, j);
		dormitories[i].setBedNumber(bedNumber, j);
		dormitories[i].setPpCount(dormitories[i].getPpCount() + 1);
		System.out.println("添加成功！");
	}

	/**
	 * @param memberName
	 * @return
	 * @author 
	 * <p>删除成员，支持同名删除
	 */
	private int delMember(String memberName) {
		delReNameSize();
		searchReNameMember(memberName, 0, 0);
		if (reNameSize >= 1) {
			int i = this.a[0];
			int j = this.b[0];
			int num;
			if (reNameSize > 1) {
				showMember(memberName);
				System.out.print("请输入要删除的记录编号：");
				num = 0;
				while (true) {
					try {
						num = input.nextInt();
						break;
					} catch (InputMismatchException e) {
						System.out.print("请输入一个数字：");
						input = new Scanner(System.in);
					}
				}
				if (num > reNameSize || num < 1) {
					System.out.println("您输入的编号不在范围内！");
					return 0;
				}
				i = this.a[(num - 1)];
				j = this.b[(num - 1)];
			}
			//后面同学的信息覆盖要删除同学的位置
			for (; j < this.dormitories[i].getPpCount() - 1; j++) {
				this.dormitories[i].setMemberName(this.dormitories[i].getMemberName(j + 1), j);
				this.dormitories[i].setMemberType(this.dormitories[i].getMemberType(j + 1), j);
				this.dormitories[i].setBedNumber(this.dormitories[i].getBedNumber(j + 1), j);
			}
			//将原先最后一位同学的信息设为初始值
			this.dormitories[i].setMemberName(null, this.dormitories[i].getPpCount() - 1);
			this.dormitories[i].setMemberType(0, this.dormitories[i].getPpCount() - 1);
			this.dormitories[i].setBedNumber(0, this.dormitories[i].getPpCount() - 1);
			this.dormitories[i].setPpCount(this.dormitories[i].getPpCount() - 1);
			System.out.println("已删除！");
		} else {
			System.out.println("找不到该学生信息！");
		}
		return 1;
	}

	/**
	 * @param dormitoryId
	 * @author 
	 * <p>删除一个宿舍
	 */
	private void delDormitory(String dormitoryId) {
		int i = searchDormitory(dormitoryId);
		if (i != -1) {
			for (; i < this.size - 1; i++) {
				this.dormitories[i] = this.dormitories[(i + 1)];
			}
			this.dormitories[(this.size - 1)] = null;
			this.size -= 1;
			System.out.println("已删除！");
		} else {
			System.out.println("找不到该宿舍编号信息！");
		}
	}

	/**
	 * @param dormitoryId
	 * @return
	 * @author 
	 * <p>查询宿舍长，返回其下标值，查不到返回-1
	 */
	private int seachHeadOfDormitory(String dormitoryId) {
		int i = searchDormitory(dormitoryId);
		for (int j = 0; j < this.dormitories[i].getPpCount(); j++) {
			if (this.dormitories[i].getMemberType(j) == 1) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * @author 
	 * <p>打印所有宿舍的状态信息
	 */
	private void showDormitoriesStatus() {
		for (int i = 0; i < this.size; i++) {
			System.out.print(this.dormitories[i].getDormitoryId());
			if (this.dormitories[i].getDormitoryType() == 0) {
				System.out.print("（女）");
			} else {
				System.out.print("（男）");
			}
			System.out.print("\t已住" + this.dormitories[i].getPpCount() + "人");
			if (this.dormitories[i].getPpCount() == 6) {
				System.out.println("（已满）");
			} else {
				System.out.println();
			}
		}
	}

	/**
	 * @param dormitoryNumber
	 * @param memberNumber
	 * @author 
	 * <p>根据宿舍下标值和在宿舍里的下标值打印同学的信息
	 */
	private void showMessage(int dormitoryNumber, int memberNumber) {
		System.out.print(this.dormitories[dormitoryNumber].getDormitoryId() + "\t");
		System.out.print(this.dormitories[dormitoryNumber].getMemberName(memberNumber) + "\t");
		if (this.dormitories[dormitoryNumber].getDormitoryType() == 0) {
			System.out.print("女\t");
		} else {
			System.out.print("男\t");
		}
		if (this.dormitories[dormitoryNumber].getMemberType(memberNumber) == 1) {
			System.out.print("宿舍长\t");
		} else {
			System.out.print("普通成员\t");
		}
		System.out.println(this.dormitories[dormitoryNumber].getBedNumber(memberNumber));
	}

	/**
	 * @param memberName
	 * @author 
	 * <p>打印所有该姓名同学的信息
	 */
	private void showMember(String memberName) {
		delReNameSize();
		searchReNameMember(memberName, 0, 0);
		System.out.println("一共找到" + this.reNameSize + "条记录");
		if (this.reNameSize > 1) {
			System.out.println("记录\t宿舍编号\t姓名\t性别\t成员类型\t床位号");
			for (int i = 0; i < this.reNameSize; i++) {
				System.out.print(i + 1 + "\t");
				showMessage(this.a[i], this.b[i]);
			}
		} else if (this.reNameSize == 1) {
			System.out.println("记录\t宿舍编号\t姓名\t性别\t成员类型\t床位号");
			System.out.print("1\t");
			showMessage(this.a[0], this.b[0]);
		} else {
			System.out.println("找不到该学生信息！");
		}
	}

	/**
	 * @param memberName
	 * @param toffset
	 * @param ooffset
	 * @return
	 * @author 
	 * <p>递归查询所有宿舍姓名为传入的姓名的信息，并记录其下标值，返回同名个数
	 */
	private Object searchReNameMember(String memberName, int toffset, int ooffset) {
		int i = searchMemberInDormitory(memberName, toffset, ooffset);
		int j = searchMember(memberName, toffset, ooffset);
		if ((i != -1) && (j != -1)) {
			this.a[this.reNameSize] = i;
			this.b[this.reNameSize] = j;
			this.reNameSize += 1;
			return j == this.dormitories[i].getPpCount() - 1 ? searchReNameMember(memberName, i + 1, 0)
					: searchReNameMember(memberName, i, j + 1);
		}
		return Integer.valueOf(this.reNameSize);
	}

	/**
	 * @author 
	 * <p>重置同名个数
	 */
	private void delReNameSize() {
		this.reNameSize = 0;
	}

	/**
	 * @param dormitoryId
	 * @author 
	 * <p>打印一间宿舍所有同学的信息
	 */
	private void showDormitory(String dormitoryId) {
		if (searchDormitory(dormitoryId) != -1) {
			int i = searchDormitory(dormitoryId);
			if (this.dormitories[i].getPpCount() == 0) {
				System.out.println(this.dormitories[i].getDormitoryId() + "宿舍为新建宿舍，还未住人。");
			} else {
				System.out.println("宿舍编号\t姓名\t性别\t成员类型\t床位号");
				for (int j = 0; j < this.dormitories[i].getPpCount(); j++) {
					showMessage(i, j);
				}
			}
		} else {
			System.out.println("您输入的宿舍编号不存在！");
		}
	}

	/**
	 * @author 
	 * <p>打印所有同学的信息
	 */
	private void showAll() {
		System.out.println("宿舍编号\t姓名\t性别\t成员类型\t床位号");
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.dormitories[i].getPpCount(); j++) {
				showMessage(i, j);
			}
		}
	}

	/**
	 * @param dormitoryId
	 * @return
	 * @author 
	 * <p>查询宿舍人数，返回宿舍人数
	 */
	private int searchDormitoryPpcount(String dormitoryId) {
		return this.dormitories[searchDormitory(dormitoryId)].getPpCount();
	}

	/**
	 * @param dormitoryId
	 * @return
	 * @author 
	 * <p>查询宿舍，返回其下标值，查不到返回-1
	 */
	private int searchDormitory(String dormitoryId) {
		for (int i = 0; i < this.size; i++) {
			if (this.dormitories[i].getDormitoryId().equals(dormitoryId)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @param dormitoryId
	 * @return
	 * @author 
	 * <p>查询宿舍类型，返回宿舍类型编号值
	 */
	private int searchDormitoryType(String dormitoryId) {
		return this.dormitories[searchDormitory(dormitoryId)].getDormitoryType();
	}

	/**
	 * @param memberName
	 * @return
	 * @author 
	 * <p>查询成员是否存在，返回其所在宿舍的下标值，查不到返回-1
	 */
	private int searchMemberInDormitory(String memberName) {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.dormitories[i].getPpCount(); j++) {
				if (this.dormitories[i].getMemberName(j).equals(memberName)) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * @param memberName
	 * @param toffset
	 * @param ooffset
	 * @return
	 * @author 
	 * <p>从指定下标值开始查找成员，返回其所在宿舍的下标值，查不到返回-1
	 */
	private int searchMemberInDormitory(String memberName, int toffset, int ooffset) {
		for (int i = toffset; i < this.size; i++) {
			for (int j = ooffset; j < this.dormitories[i].getPpCount(); j++) {
				if (this.dormitories[i].getMemberName(j).equals(memberName)) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * @param memberName
	 * @return
	 * @author 
	 * <p>查询成员，返回其在宿舍里面的下标值，查不到返回-1
	 */
	private int searchMember(String memberName) {
		int num = searchMemberInDormitory(memberName);
		if (num != -1) {
			for (int j = 0; j < this.dormitories[num].getPpCount(); j++) {
				if (this.dormitories[num].getMemberName(j).equals(memberName)) {
					return j;
				}
			}
		}
		return -1;
	}

	/**
	 * @param memberName
	 * @param toffset
	 * @param ooffset
	 * @return
	 * @author 
	 * <p>按照指定下标值开始查询成员，返回其在宿舍里面的下标值，查不到返回-1
	 */
	private int searchMember(String memberName, int toffset, int ooffset) {
		int num = searchMemberInDormitory(memberName, toffset, ooffset);
		if (num != -1) {
			for (int j = ooffset; j < this.dormitories[num].getPpCount(); j++) {
				if (this.dormitories[num].getMemberName(j).equals(memberName)) {
					return j;
				}
			}
		}
		return -1;
	}
}
