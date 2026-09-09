package FR;
import FR.devices.CentrifugalPump;
import FR.devices.FlowMeter;
import FR.devices.PressureSensor;
import FR.devices.PumpingUnit;
import FR.devices.TemperatureSensor;
import FR.devices.equipment;
import java.util.Scanner;

public class FR_UI{
    public static void main(String[] args){
        System.out.println("=".repeat(30));
        System.out.println("SmartOil 油气井管理系统 v1.0");
        
        Scanner sc=new Scanner(System.in);
        boolean continue_=true;
        
        while (continue_){
            boolean _continue=true;
            System.out.println("\t    首页");
            System.out.println("=".repeat(30));

            System.out.println("1.设备管理");
            System.out.println("2.数据采集与监控");
            System.out.println("3.报警管理");
            System.out.println("4.运维任务管理");
            System.out.println("5.数据统计与报表");
            System.out.println("0.退出系统");
            System.out.println("请输入您的选择：");
            switch (sc.next()) {
                case "1":
                                while(_continue){
                System.out.println("\t   设备管理");
                System.out.println("=".repeat(30));
                
                System.out.println("1.新增设备");
                System.out.println("2.查询设备");
                System.out.println("3.修改或补充设备信息");
                System.out.println("4.删除设备");
                System.out.println("5.退出");
                System.out.println("6.返回上一级菜单");

                System.out.println("=".repeat(30));

                System.out.println("请输入您的选择：");
                switch (sc.next()) {
                    
                    case "1":
                        //加入容量不足的判断
                        sc.nextLine();//吃掉换行符，学以致用
                        System.out.println("请输入：设备类型");
                        System.out.println("1.抽油机(PU)");
                        System.out.println("2.离心泵(CP)");
                        System.out.println("3.压力传感器(PS)");
                        System.out.println("4.温度传感器(TS)");
                        System.out.println("5.流量计(FM)");
                        System.out.println("-".repeat(30));
                        switch (sc.next().toLowerCase(java.util.Locale.ROOT)) {
                            case "1":
                            case "pu":
                            case "pumpingunit":
                            case "抽油机器":
                            case "抽油几":
                            case "抽油机":{
                                System.out.println("请依次输入：所属井场，安装日期，型号");
                                PumpingUnit device=new PumpingUnit(sc.next(),sc.next(),sc.next());
                                boolean Success=FR_DEV.add(PumpingUnit.DEVICES,device);
                                System.out.println();
                                System.out.println (Success ? "添加成功" : "提交失败：数组已满");
                                break;}
                            
                            case "2":
                            case "cp":
                            case "centrifugalpump":
                            case "离心水泵":
                            case "离心磅":
                            case "离心泵":{
                                System.out.println("请依次输入：所属井场，安装日期，型号");
                                CentrifugalPump device=new CentrifugalPump(sc.next(),sc.next(),sc.next());
                                boolean Success=FR_DEV.add(CentrifugalPump.DEVICES,device);
                                System.out.println();
                                System.out.println(Success?"添加成功":"添加失败：数组已满");
                                break;
                            }
                            case "3":
                            case "ps":
                            case "pressuresensor":
                            case "压力感应器":
                            case "压力传感":
                            case "压力传感器":{
                                System.out.println("请依次输入：所属井场，安装日期，型号");
                                PressureSensor device=new PressureSensor(sc.next(),sc.next(),sc.next());
                                boolean Success=FR_DEV.add(PressureSensor.DEVICES,device);
                                System.out.println();
                                System.out.println(Success?"添加成功":"添加失败：数组已满");
                                break;
                            }
                            case "4":
                            case "ts":
                            case "temperaturesensor":
                            case "温度感应器":
                            case "温度传感":
                            case "温度传感器":{
                                System.out.println("请依次输入：所属井场，安装日期，型号");
                                TemperatureSensor device=new TemperatureSensor(sc.next(),sc.next(),sc.next());
                                boolean Success=FR_DEV.add(TemperatureSensor.DEVICES,device);
                                System.out.println();
                                System.out.println(Success?"添加成功":"添加失败：数组已满");
                                break;
                            }
                            case "5":
                            case "fm":
                            case "flowmeter":
                            case "流量表":
                            case "流量记":
                            case "流量计":{
                                System.out.println("请依次输入：所属井场，安装日期，型号");
                                FlowMeter device=new FlowMeter(sc.next(),sc.next(),sc.next());
                                boolean Success=FR_DEV.add(FlowMeter.DEVICES,device);
                                System.out.println();
                                System.out.println(Success?"添加成功":"添加失败：数组已满");
                                break;
                            }
                            default:
                                System.out.println("暂不支持该设备，是否回到首页重新选择");
                                boolean Continue1_=true;
                                System.out.println("1.回到首页");
                                System.out.println("2.退出");
                                while (Continue1_==true){
                                    switch (sc.next()) {
                                        case "1":
                                            Continue1_=false;
                                            break;
                                        case "2":
                                            Continue1_=false;
                                            _continue=false;
                                            break;
                                        default:
                                        System.out.println("请输入正确的数字！");    
                                        break;
                                    }
                                }
                        }
                    
                        
                        break;
                    case "2":
                        sc.nextLine();
                        System.out.println("请输入设备id,例如 PU_1:");
                        String id=sc.nextLine();
                        
                        equipment device =FR_DEV.findById(id);
                        if(device==null){
                            System.out.println("未找到相关设备");
                        }else{
                        System.out.println("-".repeat(30)+"设备信息"+"-".repeat(30));
                        FR_DEV.show_info(device);  //改成编号为参数,泛型
                        System.out.println("-".repeat(68));
                        }
                        break;
                    case "3":
                        //修改或补充设备信息
                    case "4":
                        //删除设备
                    case "5":
                        _continue=false;
                        continue_=false;
                        break;
                    case "6":
                        _continue=false;
                        break;
                    default:
                        System.out.println("请输入正确的数字！");
                }
            }
                    break;
                //此处case属于最大的switch
                case "2":
                    //数据采集与监控
                    
                    break;
                case "3":
                    //报警管理
                    break;
                case "4":
                    //运维任务管理
                    break;
                case "5":
                    //数据统计与报表
                    break;
                case "0":
                    //退出系统
                    continue_=false;
                    break;
                default:
                    System.out.println("输入的数字有误，是否重新输入");
                    boolean continue2_ =true;
                    System.out.println("1.重新输入");
                    System.out.println("2.退出");
                    while(continue2_){
                        switch (sc.next()) {
                            case "1":
                                continue2_=false;
                                break;
                            case "2":
                                continue2_=false;
                                continue_=false;
                                break;
                            default:
                                System.out.println("请输入正确的数字！");
                                break;
                        }
                    }
                    break;
            }

        }
        sc.close();
    }
    
}
