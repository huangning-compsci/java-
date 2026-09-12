package FR;
import java.util.Scanner;

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
    //id查询功能
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

    //删除程序如下
    static boolean deleteById(String id,Scanner sc) {

        equipment device = findById(id);

        // 查询不到设备
        if (device == null) {
            System.out.println("未找到设备：" + id);
            return false;
        }

        // 显示设备详细信息
        System.out.println("找到设备：");
        show_info(device);

        // 确认删除
        System.out.print("确定要删除该设备吗？(y/n)：");

        
        String choice = sc.next();

        if (!choice.equalsIgnoreCase("y")) {
            System.out.println("已取消删除！");
            return false;
        }

        String[] parts = id.trim().split("_");
        int number = Integer.parseInt(parts[1]);
        int index = number - 1;

        boolean result = false;

        switch (parts[0]) {

            case "PU":
                result = PumpingUnit.DEVICES.remove(index);
                break;

            case "CP":
                result = CentrifugalPump.DEVICES.remove(index);
                break;

            case "PS":
                result = PressureSensor.DEVICES.remove(index);
                break;

            case "TS":
                result = TemperatureSensor.DEVICES.remove(index);
                break;

            case "FM":
                result = FlowMeter.DEVICES.remove(index);
                break;

            default:
                System.out.println("设备ID格式错误！");
                return false;
        }

        

        return result;
    }
    //请黄宁同志思考，是否会出现数组越界

    //设备信息修改与补充代码如下
    static boolean modifyById(String id, Scanner sc) {

    equipment device = findById(id);

    // 查询不到设备
    if (device == null) {
        System.out.println("未找到设备：" + id);
        return false;
    }

    // 显示当前设备信息
    System.out.println("找到设备，当前信息如下：");
    show_info(device);

    sc.nextLine();  // 吃掉上一行残留的换行符

    // 逐项修改，直接回车表示不修改
    System.out.print("所属井场（当前：" + device.getWellsite() + "，回车跳过）：");
    String newWellsite = sc.nextLine().trim();
    if (!newWellsite.isEmpty()) {
        device.setWellsite(newWellsite);
    }

    System.out.print("安装日期（当前：" + device.getInstallDate() + "，回车跳过）：");
    String newInstallDate = sc.nextLine().trim();
    if (!newInstallDate.isEmpty()) {
        device.setInstallDate(newInstallDate);
    }

    System.out.print("设备型号（当前：" + device.getModel() + "，回车跳过）：");
    String newModel = sc.nextLine().trim();
    if (!newModel.isEmpty()) {
        device.setModel(newModel);
    }

    System.out.print("目前状态（当前：" + device.getStatus() + "，回车跳过）：");
    String newStatus = sc.nextLine().trim();
    if (!newStatus.isEmpty()) {
        device.setStatus(newStatus);
    }

    System.out.println("修改完成！修改后的信息如下：");
    show_info(device);
    return true;
}
}
    
    
