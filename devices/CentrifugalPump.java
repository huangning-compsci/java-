package devices;

import exceptions.AlarmException;
import exceptions.DeviceNotRunningException;
import interfaces.Alarmable;
import interfaces.Collectable;

/**
 * 离心泵：实现 Collectable + Alarmable 两个接口。
 *
 * 修复说明（"报不了警"的根因）：
 *   1) 原类没有 implements Alarmable，巡检时被 instanceof 过滤掉；
 *   2) 原 checkAlarm() 用 return false 表示异常，但报警链路只认
 *      "抛出 AlarmException"，返回值没有任何人接收；
 *   3) 原 collectData() 里 (int)Math.random() 恒等于 0（(int) 先作用于
 *      Math.random()），进口压力永远是 0.1，随机数根本没生效。
 *
 * FR-ALM-01 规则：出口压力 < 额定扬程对应的最低压力 -> 报警。
 */
public class CentrifugalPump extends Equipment
        implements Collectable, Alarmable {

    /** 扬程-压力换算系数（保留原设计的 1.176798）。 */
    private static final double HEAD_COEF = 1.176798;
    /** 额定扬程对应的最低扬程比例（保留原设计的 0.8）。 */
    private static final double MIN_HEAD_RATIO = 0.8;

    private double ratedFlow;
    private double ratedHead;
    private double inletPressure;
    private double outletPressure;
    private static int index;
    private static final String NAME = "CP";
    private static final DeviceArray<CentrifugalPump> DEVICES =
            new DeviceArray<>(new CentrifugalPump[10]);

    public CentrifugalPump() {
        super(
            NAME + '_' + (++index),
            "CentrifugalPump",
            "",
            "",
            "",
            "ratedFlow,ratedHead,inletPressure,outletpressure"
        );
        this.ratedFlow = 50.0;
        this.ratedHead = 120.0;
    }

    public CentrifugalPump(
            String e_wellsite,
            String e_Install_date,
            String e_model
            ) {
        this();
        setModel(e_model);
        setWellsite(e_wellsite);
        setInstallDate(e_Install_date);
    }

    public static DeviceArray<CentrifugalPump> getDevices() {
        return DEVICES;
    }

    /**
     * FR-MON-01 模拟数据采集。
     * 修复原 bug：(int)Math.random() 恒为 0，应写成 (int)(Math.random()*范围+基数)。
     * 约 20% 概率出口压力跌落（扬程不足），用于演示报警。
     */
    @Override
    public void collectData() throws DeviceNotRunningException {
        if ("尚未启动".equals(getStatus())) {
            throw new DeviceNotRunningException(getId(), "泵压采集");
        }
        this.inletPressure = (int) (Math.random() * 21 + 10) / 100.0;   // 0.10~0.30 MPa
        double k;
        if (Math.random() < 0.8) {
            k = (int) (Math.random() * 21 + 90) / 100.0;                // 0.90~1.10 正常扬程
        } else {
            k = (int) (Math.random() * 30 + 50) / 100.0;                // 0.50~0.79 扬程不足
        }
        this.outletPressure = inletPressure + k * HEAD_COEF;
        System.out.printf("%s 泵压采集完成：进口 %.3f MPa，出口 %.3f MPa%n",
                getId(), inletPressure, outletPressure);
    }

    /** FR-ALM-01 异常检测：出口压力低于额定扬程对应的最低压力 -> 报警（疑似汽蚀/抽空）。 */
    @Override
    public boolean checkAlarm() throws AlarmException {
        double minOutlet = inletPressure + MIN_HEAD_RATIO * HEAD_COEF;
        if (outletPressure < minOutlet) {
            throw new AlarmException(getId(), AlarmException.Level.WARNING,
                    String.format("出口压力 %.3f MPa 低于额定扬程对应的最低压力 %.3f MPa，疑似汽蚀或抽空",
                            outletPressure, minOutlet));
        }
        return true;
    }

    public double getRatedFlow() { return ratedFlow; }
    public void setRatedFlow(double ratedFlow) { this.ratedFlow = ratedFlow; }
    public double getRatedHead() { return ratedHead; }
    public void setRatedHead(double ratedHead) { this.ratedHead = ratedHead; }
    public double getInletPressure() { return inletPressure; }
    public void setInletPressure(double inletPressure) { this.inletPressure = inletPressure; }
    public double getOutletPressure() { return outletPressure; }
    public void setOutletPressure(double outletPressure) { this.outletPressure = outletPressure; }
}
