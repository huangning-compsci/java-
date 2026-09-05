package FR;




public class FR_DEV{
    
    //添加的代码应该写成equipment.add(),然后每个子类的add都不一样
    //加入id查询系统，将参数改成id
    static void show_info(equipment e){
        System.out.println("id:"+e.e_ID);
        System.out.println("type:"+e.e_type);
        System.out.println("model:"+e.e_model);
        System.out.println("wellsite:"+e.e_wellsite);
        System.out.println("Status:"+e.e_Status);
    }

    static <T extends equipment> boolean add(
        DeviceArray<T> target,
        T device){
        
            return target.store(device);
        
    }

    static boolean showinfo(equipment device){
        if(device==null){
            return false;
        }
        System.out.println(device.e_ID);
        System.out.println(device.e_Install_date);
        System.out.println(device.e_Status);
        System.out.println(device.e_model);
        return true;
        }
    
    static equipment findById(String id){
        if (id==null){
            return null;
        }

        String[] parts=id.trim().split("_",-1);
        if (parts.length !=2){
            return null;
        }
        int number;
        try{
            number=Integer.parseInt(parts[1]);
        }catch (NumberFormatException e){
            return null;
        }

        if(number<=0){
            return null;
        }
        int index=number-1;

        switch (parts[0]) {
            case "PU":
                return PumpingUnit.DEVICES.get(index);
        
            default:
                return null;
        }
    }
    
    }
