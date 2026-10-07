package interfaces;

import exceptions.DeviceNotRunningException;

/**
 * 数据采集能力（FR-MON-01）。
 *
 * 注意：方法签名与 Equipment 的抽象方法保持一致（void 返回值），
 * 这样实现类用一个方法同时满足抽象类和接口。
 *
 * 包含评分细则要求的三类成员：
 *   抽象方法   collectData()
 *   default 方法 collectSummary()   —— 子类可重写，不重写就用默认实现
 *   static 方法 collectAll()        —— 批量采集，展示接口多态
 */
public interface Collectable {

    /**
     * 采集一次数据并写入设备自身的当前值字段。
     * @throws DeviceNotRunningException 设备未启动时抛出
     */
    void collectData() throws DeviceNotRunningException;

    /**
     * default 方法：采集后的摘要信息。
     * 所有实现类免费获得该行为；需要个性化摘要的类可重写。
     */
    default String collectSummary() {
        return "数据采集已完成";
    }

    /**
     * 批量采集：遍历接口引用调用各设备自己的 collectData()（多态），
     * 未启动的设备跳过并提示，不中断整体采集。
     */
    static void collectAll(Collectable... devices) {
        for (Collectable d : devices) {
            if (d == null) {
                continue;
            }
            try {
                d.collectData();
                System.out.println(d.collectSummary());
            } catch (DeviceNotRunningException e) {
                System.out.println("采集跳过：" + e.getMessage());
            }
        }
    }
}
