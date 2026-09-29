package devices;

import exceptions.AlarmException;
import exceptions.DeviceNotRunningException;
import interfaces.Alarmable;
import interfaces.Collectable;

/**
 * 压力传感器：实现 Collectable + Alarmable 两个接口（接口多实现）。
 * 补全了原来的空壳 collectData()/checkAlarm()，并设置了默认报警上下限。
 *
 * 配套要求（必须一起改，否则编译不过）：
 *   Equipment 的抽象方法要加 throws：
 *     public abstract void collectData() throws DeviceNotRunningException;
 *     public abstract boolean checkAlarm() throws AlarmException;
 */
public class PressureSensor extends Equipment
        implements Collectable, Alarmable {

    private double pressureRange;
    private String accuracy;
    private double currentPressure;
    private double upperLimit;
    private double lowerLimit;
    private static final String NAME = "PS";
    private static int index;
    private static final DeviceArray<PressureSensor> DEVICES =
        new DeviceArray<>(new PressureSensor[10]);

    public PressureSensor() {
        super(
            NAME + '_' + (++index),
            "PressureSensor",
            "",
            "",
            "",
            "pressureRange,accuracy,currentPressure,upperLimit,lowerLimit"
        );
        this.pressureRange = 4.0;
        this.accuracy = "0.5级";
        this.upperLimit = 3.5;   // 默认报警上限 MPa
        this.lowerLimit = 0.5;   // 默认报警下限 MPa（低于此值疑似泄漏）
    }

    public PressureSensor(
            String e_wellsite,
            String e_Install_date,
            String e_model
            ) {
        this();
        setModel(e_model);
        setWellsite(e_wellsite);
        setInstallDate(e_Install_date);
    }

    public static DeviceArray<PressureSensor> getDevices() {
        return DEVICES;
    }

    /**
     * FR-MON-01 模拟数据采集：量程内随机值，约 10% 概率超限（便于演示报警）。
     * 未启动时抛 DeviceNotRunningException。
     */
    @Override
    public void collectData() throws DeviceNotRunningException {
        if ("尚未启动".equals(getStatus())) {
            throw new DeviceNotRunningException(getId(), "压力采集");
        }
        double base = pressureRange * Math.random();
        double noise = Math.random() < 0.1 ? pressureRange * 0.3 : 0;
        this.currentPressure = base + noise;
        System.out.printf("%s 压力采集完成：%.3f MPa%n", getId(), currentPressure);
    }

    /** FR-ALM-01 异常检测：超上限严重报警，低于下限警告（疑似泄漏）。 */
    @Override
    public boolean checkAlarm() throws AlarmException {
        if (currentPressure > upperLimit) {
            throw new AlarmException(getId(), AlarmException.Level.CRITICAL,
                    String.format("压力 %.3f MPa 超过上限 %.3f MPa", currentPressure, upperLimit));
        }
        if (currentPressure < lowerLimit) {
            throw new AlarmException(getId(), AlarmException.Level.WARNING,
                    String.format("压力 %.3f MPa 低于下限 %.3f MPa，疑似泄漏", currentPressure, lowerLimit));
        }
        return true;
    }

    // getter/setter 与原文件相同
    public double getPressureRange() { return pressureRange; }
    public void setPressureRange(double pressureRange) { this.pressureRange = pressureRange; }
    public String getAccuracy() { return accuracy; }
    public void setAccuracy(String accuracy) { this.accuracy = accuracy; }
    public double getCurrentPressure() { return currentPressure; }
    public void setCurrentPressure(double currentPressure) { this.currentPressure = currentPressure; }
    public double getUpperLimit() { return upperLimit; }
    public void setUpperLimit(double upperLimit) { this.upperLimit = upperLimit; }
    public double getLowerLimit() { return lowerLimit; }
    public void setLowerLimit(double lowerLimit) { this.lowerLimit = lowerLimit; }
}
