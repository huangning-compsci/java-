package devices;

import exceptions.AlarmException;
import exceptions.DeviceNotRunningException;
import interfaces.Alarmable;
import interfaces.Collectable;

/**
 * 抽油机：实现 Collectable + Alarmable 两个接口
 * FR-ALM-01 规则：当前载荷 > 额定载荷的 120% -> 严重报警（一级）；
 *                 当前载荷 > 额定载荷        -> 警告（二级）。
 */
public class PumpingUnit extends Equipment
        implements Collectable, Alarmable {

    private double ratedPower;
    private double stroke;
    private double strokeRate;
    private double currentLoad;
    private double ratedLoad;
    private static final String NAME = "PU";
    private static int index;
    private static final DeviceArray<PumpingUnit> DEVICES =
        new DeviceArray<>(new PumpingUnit[10]);

    public PumpingUnit() {
        super(
            NAME + '_' + (++index),
            "PumpingUnit",
            "",
            "",
            "",
            "stroke,strokeRate,currentLoad,ratedLoad,ratedPower"
        );
        this.ratedPower = 75.0;
        this.ratedLoad = 160.0;
    }

    public PumpingUnit(
            String e_wellsite,
            String e_Install_date,
            String e_model
            ) {
        this();
        setModel(e_model);
        setWellsite(e_wellsite);
        setInstallDate(e_Install_date);
    }

    public static DeviceArray<PumpingUnit> getDevices() {
        return DEVICES;
    }

    /**
     * FR-MON-01 模拟数据采集：载荷在额定值的 60%~110% 之间波动，
     * 约 10% 概率超载到 120% 以上（便于演示报警）。
     * 未启动时抛 DeviceNotRunningException。
     */
    @Override
    public void collectData() throws DeviceNotRunningException {
        if ("尚未启动".equals(getStatus())) {
            throw new DeviceNotRunningException(getId(), "载荷采集");
        }
        double ratio = 0.6 + 0.5 * Math.random();          // 0.6~1.1 正常区间
        if (Math.random() < 0.1) {
            ratio = 1.2 + 0.2 * Math.random();             // 偶发超载 1.2~1.4
        }
        this.currentLoad = ratedLoad * ratio;
        System.out.printf("%s 载荷采集完成：%.1f kN（额定 %.1f kN）%n",
                getId(), currentLoad, ratedLoad);
    }

    /** FR-ALM-01 异常检测：超载 120% 严重报警，超载 100% 警告。 */
    @Override
    public boolean checkAlarm() throws AlarmException {
        if (currentLoad > ratedLoad * 1.2) {
            throw new AlarmException(getId(), AlarmException.Level.CRITICAL,
                    String.format("载荷 %.1f kN 超过额定载荷的 120%%（额定 %.1f kN）",
                            currentLoad, ratedLoad));
        }
        if (currentLoad > ratedLoad) {
            throw new AlarmException(getId(), AlarmException.Level.WARNING,
                    String.format("载荷 %.1f kN 超过额定载荷 %.1f kN", currentLoad, ratedLoad));
        }
        return true;
    }

    public double getRatedPower() { return ratedPower; }
    public void setRatedPower(double ratedPower) { this.ratedPower = ratedPower; }
    public double getStroke() { return stroke; }
    public void setStroke(double stroke) { this.stroke = stroke; }
    public double getStrokeRate() { return strokeRate; }
    public void setStrokeRate(double strokeRate) { this.strokeRate = strokeRate; }
    public double getCurrentLoad() { return currentLoad; }
    public void setCurrentLoad(double currentLoad) { this.currentLoad = currentLoad; }
    public double getRatedLoad() { return ratedLoad; }
    public void setRatedLoad(double ratedLoad) { this.ratedLoad = ratedLoad; }
}
