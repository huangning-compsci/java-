package FR;
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
    static int count;  //以后细分每个类都有独自的count
    //目前还有id自动填的功能未完全实现：设备缩写由类提供，编号作为static成员
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
        count++;
    }

//加入id查询系统，将参数改成id
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
    static equipment[] e_Init(int count){
        equipment E[]=new equipment[count];
        return E;
    }
    public static equipment E_add(equipment E[],Scanner sc){
        
        int n=equipment.count;
        E[n]=new equipment(
                        sc.nextLine(),sc.nextLine(),
                        sc.nextLine(),sc.nextLine(),
                        sc.nextLine(),sc.nextLine(),sc.nextLine());
        
        E[n].e_ID=E[n].e_type+"_"+(n+1);
        return E[n];
    }
    
        
                
    }
