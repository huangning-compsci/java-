package devices;

import exceptions.AlarmException;
import exceptions.DeviceNotRunningException;
import interfaces.Alarmable;
import interfaces.Collectable;

/**
 * 流量计：实现 Collectable + Alarmable 两个接口。
 * 修复了原 collectData() 的 bug：
 *   (int)Math.random()*500 中 (int) 先作用于 Math.random()（恒为 0），
 *   导致 currentFlow 永远是 1.00；改为 (int)(Math.random()*500+100)/100.0。
 */
public class FlowMeter extends Equipment
        implements Collectable, Alarmable {

    private double flowRange;
    private String accuracy;
    private double currentFlow;
    private double totalFlow;
    private static final String NAME = "FM";
    private static int index;
    private static final DeviceArray<FlowMeter> DEVICES =
        new DeviceArray<>(new FlowMeter[10]);

    public FlowMeter() {
        super(
            NAME + '_' + (++index),
            "FlowMeter",
            "",
            "",
            "",
            "flowRange,accuracy,currentFlow,totalFlow"
        );
        this.flowRange = 10.0;
        this.accuracy = "0.5级";
    }

    public FlowMeter(
            String e_wellsite,
            String e_Install_date,
            String e_model
            ) {
        this();
        setModel(e_model);
        setWellsite(e_wellsite);
        setInstallDate(e_Install_date);
    }

    public static DeviceArray<FlowMeter> getDevices() {
        return DEVICES;
    }

    /** FR-MON-01 模拟数据采集：瞬时流量 1.00~6.00，并累加总流量。 */
    @Override
    public void collectData() throws DeviceNotRunningException {
        if ("尚未启动".equals(getStatus())) {
            throw new DeviceNotRunningException(getId(), "流量采集");
        }
        this.currentFlow = (int) (Math.random() * 500 + 100) / 100.0;
        this.totalFlow += currentFlow;
        System.out.printf("%s 流量采集完成：瞬时 %.2f m³/h，累计 %.2f m³%n",
                getId(), currentFlow, totalFlow);
    }

    /** FR-ALM-01 异常检测：保留原设计的 1.50~4.50 正常区间。 */
    @Override
    public boolean checkAlarm() throws AlarmException {
        if (currentFlow < 1.50) {
            throw new AlarmException(getId(), AlarmException.Level.WARNING,
                    String.format("流量 %.2f m³/h 过低，疑似管路堵塞或停输", currentFlow));
        }
        if (currentFlow > 4.50) {
            throw new AlarmException(getId(), AlarmException.Level.CRITICAL,
                    String.format("流量 %.2f m³/h 过高，超过安全输送能力", currentFlow));
        }
        return true;
    }

    // getter/setter 与原文件相同
    public double getFlowRange() { return flowRange; }
    public void setFlowRange(double flowRange) { this.flowRange = flowRange; }
    public String getAccuracy() { return accuracy; }
    public void setAccuracy(String accuracy) { this.accuracy = accuracy; }
    public double getCurrentFlow() { return currentFlow; }
    public void setCurrentFlow(double currentFlow) { this.currentFlow = currentFlow; }
    public double getTotalFlow() { return totalFlow; }
    public void setTotalFlow(double totalFlow) { this.totalFlow = totalFlow; }
}
