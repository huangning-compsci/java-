package devices.MonitoringDevices;

import devices.DeviceArray;

import exceptions.AlarmException;
import exceptions.DeviceNotRunningException;
import interfaces.Alarmable;
import interfaces.Collectable;

/**
 * 温度传感器：实现 Collectable + Alarmable 两个接口。
 * 补全了原来的空壳 collectData()/checkAlarm()。
 */
public class TemperatureSensor extends MonitoringEquipment
        implements Collectable, Alarmable {

    private double tempRange;
    private String accuracy;
    private double currentTemp;
    private double upperLimit;
    private static final String NAME = "TS";
    private static int index;
    private static final DeviceArray<TemperatureSensor> DEVICES =
        new DeviceArray<>(new TemperatureSensor[10]);

    public TemperatureSensor() {
        super(
            NAME + '_' + (++index),
            "TemperatureSensor",
            "",
            "",
            "",
            "tempRange,accuracy,currentTemp,upperLimit"
        );
        this.tempRange = 100.0;
        this.accuracy = "A级";
        this.upperLimit = 80.0;   // 默认报警上限 ℃
    }

    public TemperatureSensor(
            String e_wellsite,
            String e_Install_date,
            String e_model
            ) {
        this();
        setModel(e_model);
        setWellsite(e_wellsite);
        setInstallDate(e_Install_date);
    }

    public static DeviceArray<TemperatureSensor> getDevices() {
        return DEVICES;
    }

    /** FR-MON-01 模拟数据采集：常温附近随机波动，约 10% 概率超温。 */
    @Override
    public void collectData() throws DeviceNotRunningException {
        if ("尚未启动".equals(getStatus())) {
            throw new DeviceNotRunningException(getId(), "温度采集");
        }
        double base = 20 + tempRange * 0.5 * Math.random();   // 20~70 ℃ 正常区间
        double spike = Math.random() < 0.1 ? 25 : 0;          // 偶发超温尖峰
        this.currentTemp = base + spike;
        System.out.printf("%s 温度采集完成：%.1f ℃%n", getId(), currentTemp);
    }

    /** FR-ALM-01 异常检测：超上限严重报警，接近上限（90%）提前警告。 */
    @Override
    public boolean checkAlarm() throws AlarmException {
        if (currentTemp > upperLimit) {
            throw new AlarmException(getId(), AlarmException.Level.CRITICAL,
                    String.format("温度 %.1f ℃ 超过上限 %.1f ℃", currentTemp, upperLimit));
        }
        if (currentTemp > upperLimit * 0.9) {
            throw new AlarmException(getId(), AlarmException.Level.WARNING,
                    String.format("温度 %.1f ℃ 接近上限 %.1f ℃，请注意观察", currentTemp, upperLimit));
        }
        return true;
    }

    // getter/setter 与原文件相同
    public double getTempRange() { return tempRange; }
    public void setTempRange(double tempRange) { this.tempRange = tempRange; }
    public String getAccuracy() { return accuracy; }
    public void setAccuracy(String accuracy) { this.accuracy = accuracy; }
    public double getCurrentTemp() { return currentTemp; }
    public void setCurrentTemp(double currentTemp) { this.currentTemp = currentTemp; }
    public double getUpperLimit() { return upperLimit; }
    public void setUpperLimit(double upperLimit) { this.upperLimit = upperLimit; }
}
