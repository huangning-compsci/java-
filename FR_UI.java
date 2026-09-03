package FR;
import java.util.Scanner;
public class FR_UI{
    public static void main(String[] args){
        equipment E[]=FR_DEV.e_Init(10);

        System.out.println("=".repeat(30));
        System.out.println("SmartOil 油气井管理系统 v1.0");
        
        Scanner sc=new Scanner(System.in);

        while(true){
            System.out.println("=".repeat(30));
            
            System.out.println("1.新增设备");
            System.out.println("2.查询设备");
            System.out.println("3.退出系统");

            System.out.println("=".repeat(30));

            System.out.println("请输入您的选择：");
            switch (sc.nextInt()) {
                
                case 1:
                    //加入容量不足的判断
                    sc.nextLine();//吃掉换行符，学以致用
                    System.out.println("请依次输入：设备名称，类型，型号，所属井场，安装日期，额定参数，设备状态");
                    FR_DEV.E_add(E,sc);
                    break;
                
                case 2:
                    System.out.println("-".repeat(30));
                    System.out.println("设备信息如下：");
                    for (int i=0;i<2;i++){  //循环次数待更改
                        System.out.println("-".repeat(30));   
                        equipment.show_info(E[i]);  //改成编号为参数
                    }
                    break;

                default:
                    break;
            }
               
        }
    }
}