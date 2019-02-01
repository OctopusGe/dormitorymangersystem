package dormitorymanagersystem;

import java.io.File;

/**
 * @author zhangyuge
 *<p>测试类
 */
public class Test {
	//传入源文件
	static File sourceFile = new File("src/网络一班宿舍信息表.xls");
	
	
	public static void main(String[] args) {
		Manager manager = new Manager();
		manager.init(sourceFile);
		manager.menu();
	}
}
