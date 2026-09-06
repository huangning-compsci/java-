package FR;
import java.util.Scanner;

public class FR_UI{
    public static void main(String[] args){
        System.out.println("=".repeat(30));
        System.out.println("SmartOil 油气井管理系统 v1.0");
        
        Scanner sc=new Scanner(System.in);
        
        boolean _continue=true;
        while(_continue){
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
                    System.out.println("请输入：设备类型");
                    System.out.println("1.抽油机(PU)");
                    System.out.println("2.传感器");
                    System.out.println("-".repeat(30));
                    switch (sc.next()) {
                        case "1":
                        case "抽油机":{
                            System.out.println("请依次输入：所属井场，安装日期，型号");
                        PumpingUnit device=new PumpingUnit(sc.next(),sc.next(),sc.next());
                            boolean Success=FR_DEV.add(PumpingUnit.DEVICES,device);
                            System.out.println();
                            System.out.println (Success ? "添加成功" : "提交失败：数组已满");
                            break;}
                        default:
                            System.out.println("暂不支持该设备");
                    }
                   
                    
                    break;
                case 2:
                    sc.nextLine();
                    System.out.println("请输入设备id,例如 PU_1:");
                    String id=sc.nextLine();
                    
                    equipment device =FR_DEV.findById(id);
                    if(device==null){
                        System.out.println("未找到相关设备");
                    }else{
                    System.out.println("-".repeat(30));
                    System.out.println("设备信息:");   
                    FR_DEV.show_info(device);  //改成编号为参数,泛型
                    }
                    break;
                case 3:
                    //修改或补充设备信息
                case 4:
                    //删除设备
                case 5:
                    _continue=false;
                    break;
            }
        }
        sc.close();
    }
    
}