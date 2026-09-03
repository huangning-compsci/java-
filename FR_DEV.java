import java.util.Scanner;

class equipment{
    String e_ID;
    String e_name;
    String e_type;
    String e_model;
    String e_wellsite;
    String e_Install_date;
    String e_parameter;
    String e_Status;
    //目前还有id自动填的功能未实现：设备缩写有，编号还没
    equipment(String e_name,String e_type
            ,String e_model,String e_wellsite,
            String e_Install_date,String e_parameter,String e_Status){
        
        this.e_Install_date=e_Install_date;
        this.e_Status=e_Status;
        this.e_model=e_model;
        this.e_name=e_name;
        this.e_parameter=e_parameter;
        this.e_type=e_type;
        this.e_wellsite=e_wellsite;
    }


    static void show_info(equipment e){
        System.out.println("id:"+e.e_ID);
        System.out.println("name:"+e.e_name);
        System.out.println("type:"+e.e_type);
        System.out.println("model:"+e.e_model);
        System.out.println("wellsite:"+e.e_wellsite);
        System.out.println("Status:"+e.e_Status);    
    }

}

public class FR_DEV{
    
    public static void main(String[] args){
        equipment[] Equ=new equipment[2];
        
        //以后要把这个print转移到控制面板上
        System.out.println("请依次输入：设备名称，类型，型号，所属井场，安装日期，额定参数，设备状态");
        Scanner sc=new Scanner(System.in);
        Equ[0]=new equipment(
        sc.nextLine(),sc.nextLine(),
        sc.nextLine(),sc.nextLine(),
        sc.nextLine(),sc.nextLine(),sc.nextLine());
        sc.close();
        equipment.show_info(Equ[0]);
        }
        
                
    }
