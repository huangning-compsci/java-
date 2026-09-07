package FR;
import FR.devices.CentrifugalPump;
import FR.devices.DeviceArray;
import FR.devices.FlowMeter;
import FR.devices.PressureSensor;
import FR.devices.PumpingUnit;
import FR.devices.TemperatureSensor;
import FR.devices.equipment;




public class FR_DEV{
    
    //添加的代码应该写成equipment.add(),然后每个子类的add都不一样
    //加入id查询系统，将参数改成id
   

    static <T extends equipment> boolean add(
        DeviceArray<T> target,
        T device){
        
            return target.store(device);
        
    }

    static boolean show_info(equipment device){
        if(device==null){
            return false;
        }
        String[] headers={"设备id","安装日期","目前状态","设备型号","所处井场"};
        String[] values={device.getId(),device.getInstallDate(),device.getStatus(),
            device.getModel(),device.getWellsite()};
        StringBuilder headerRow=new StringBuilder();
        StringBuilder valueRow=new StringBuilder();
        for(int i=0;i<headers.length;i++){
            String value=String.valueOf(values[i]);
            int headerWidth=displayWidth(headers[i]);
            int valueWidth=displayWidth(value);
            int width=Math.max(headerWidth,valueWidth)+4;
            headerRow.append(headers[i]).append(" ".repeat(width-headerWidth));
            valueRow.append(value).append(" ".repeat(width-valueWidth));
        }
        System.out.println(headerRow.toString().stripTrailing());
        System.out.println(valueRow.toString().stripTrailing());
        return true;
        }

    // 等宽终端中，中文汉字和常用全角字符占两格，其余字符占一格。
    private static int displayWidth(String text){
        return text.codePoints().map(c ->
            Character.UnicodeScript.of(c)==Character.UnicodeScript.HAN
            || (c>=0x3000 && c<=0x303F)
            || (c>=0xFF01 && c<=0xFF60)
            || (c>=0xFFE0 && c<=0xFFE6) ? 2 : 1).sum();
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
            case "CP":
                return CentrifugalPump.DEVICES.get(index);
            case "PS":
                return PressureSensor.DEVICES.get(index);
            case "TS":
                return TemperatureSensor.DEVICES.get(index);
            case "FM":
                return FlowMeter.DEVICES.get(index);
            default:
                return null;
        }
    }
    
    }
