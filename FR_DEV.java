class equipment{
    String e_ID;
    String e_name;
    String e_type;
    String e_model;
    String e_wellsite;
    String e_Install_date;
    String e_parameter;
    String e_Status;
    static void Fill_info(equipment equ){
        equ.e_ID="PU-1";
        equ.e_name="Stronger";
        equ.e_type="Pumping Unit";
        equ.e_model="pro";
        equ.e_wellsite="H23-1";
        equ.e_Install_date="2024-8-26";
        equ.e_parameter=null;
        equ.e_Status="Running";
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
        equipment[] Equ=new equipment [2];
        for (int i=0;i<Equ.length;i++){
            Equ[i]=new equipment();
        }
        for(equipment x:Equ){
            equipment.Fill_info(x);
        
        }
        equipment.show_info(Equ[0]);
        
    }
}