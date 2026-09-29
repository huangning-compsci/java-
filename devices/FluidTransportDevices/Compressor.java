package devices.FluidTransportDevices;

import devices.DeviceArray;

import exceptions.AlarmException;
import exceptions.DeviceNotRunningException;
import interfaces.Alarmable;
import interfaces.Collectable;

/**
 * 压缩机：实现 Collectable + Alarmable 两个接口。
 *
 * 修复说明（"报不了警"的根因）：
 *   1) 原类没有 implements Alarmable，巡检时被 instanceof 过滤掉；
 *   2) 原 checkAlarm() 用 return false 表示异常，但报警链路只认
 *      "抛出 AlarmException"，返回值没有任何人接收；
 *   3) 原 collectData() 里 (int)Math.random() 恒等于 0，排气压力永远是 6.0。
 *
 * FR-ALM-01 规则：排气压力超出 6.5~8.0 MPa 安全范围 -> 严重报警；
 *                 排气流量低于 2.5 -> 警告。
 */
public class Compressor extends FluidTransportEquipment
        implements Collectable, Alarmable {

    private double exhaustPressure;
    private double exhaustFlow;
    private double power;
    private static final String NAME = "CM";
    private static int index;
    private static final DeviceArray<Compressor> DEVICES =
        new DeviceArray<>(new Compressor[10]);

    public Compressor() {
        super(
            NAME + '_' + (++index),
            "Compressor",
            "",
            "",
            "",
            "exhaustPressure,exhaustFlow,power"
        );
        this.power = 37.0;
    }

    public Compressor(
            String e_wellsite,
            String e_Install_date,
            String e_model
            ) {
        this();
        setModel(e_model);
        setWellsite(e_wellsite);
        setInstallDate(e_Install_date);
    }

    public static DeviceArray<Compressor> getDevices() {
        return DEVICES;
    }

    /**
     * FR-MON-01 模拟数据采集。
     * 修复原 bug：(int)Math.random() 恒为 0，应写成 (int)(Math.random()*范围+基数)。
     * 排气压力约 25% 概率越出 6.5~8.0 安全范围，用于演示报警。
     */
    @Override
    public void collectData() throws DeviceNotRunningException {
        if ("尚未启动".equals(getStatus())) {
            throw new DeviceNotRunningException(getId(), "排气参数采集");
        }
        if (Math.random() < 0.75) {
            this.exhaustPressure = (int) (Math.random() * 150 + 650) / 100.0;  // 6.50~8.00 正常
        } else {
            this.exhaustPressure = (int) (Math.random() * 160 + 600) / 100.0;  // 6.00~7.60 可能越限
        }
        this.exhaustFlow = (int) (Math.random() * 120 + 220) / 100.0;          // 2.20~3.40
        System.out.printf("%s 排气参数采集完成：压力 %.2f MPa，流量 %.2f%n",
                getId(), exhaustPressure, exhaustFlow);
    }

    /** FR-ALM-01 异常检测：排气压力越限严重报警，排气流量过低警告。 */
    @Override
    public boolean checkAlarm() throws AlarmException {
        if (exhaustPressure < 6.5 || exhaustPressure > 8.0) {
            throw new AlarmException(getId(), AlarmException.Level.CRITICAL,
                    String.format("排气压力 %.2f MPa 超出安全范围 6.5~8.0 MPa", exhaustPressure));
        }
        if (exhaustFlow < 2.5) {
            throw new AlarmException(getId(), AlarmException.Level.WARNING,
                    String.format("排气流量 %.2f 低于下限 2.5", exhaustFlow));
        }
        return true;
    }

    public double getExhaustPressure() { return exhaustPressure; }
    public void setExhaustPressure(double exhaustPressure) { this.exhaustPressure = exhaustPressure; }
    public double getExhaustFlow() { return exhaustFlow; }
    public void setExhaustFlow(double exhaustFlow) { this.exhaustFlow = exhaustFlow; }
    public double getPower() { return power; }
    public void setPower(double power) { this.power = power; }
}
