//get（）用于获取下标设备，在get()后加以下代码
public boolean remove(int index) {
    if (index < 0 || index >= count || devices[index] == null) {
        return false;
    }

    devices[index] = null;
    return true;
}

//删除程序如下
static boolean deleteById(String id) {

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

    java.util.Scanner scanner = new java.util.Scanner(System.in);
    String choice = scanner.nextLine();

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

    if (result) {
        System.out.println("设备 " + id + " 删除成功！");
    } else {
        System.out.println("设备删除失败！");
    }

    return result;
}
//请黄宁同志思考，是否会出现数组越界