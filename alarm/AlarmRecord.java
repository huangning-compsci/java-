package alarm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import exceptions.AlarmException;

/**
 * 报警记录（FR-ALM-01）。
 * 捕获到 AlarmException 时自动生成（见 AlarmLog.record），
 * 包含需求要求的六个字段：报警编号、设备编号、报警类型、报警描述、报警时间、报警状态，
 * 另附带报警等级（FR-ALM-02），是 FR-ALM-03 报警查询的数据来源。
 */
public class AlarmRecord {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** 报警编号自增序号（内存计数，程序重启后归零）。 */
    private static int seq;

    private final String alarmId;       // 报警编号
    private final String deviceId;      // 设备编号
    private final String type;          // 报警类型
    private final String level;         // 报警等级（附带信息，FR-ALM-02）
    private final String description;   // 报警描述
    private final String alarmTime;     // 报警时间
    private String status;              // 报警状态：未处理 / 已处理

    private AlarmRecord(String alarmId, String deviceId, String type, String level,
            String description, String alarmTime, String status) {
        this.alarmId = alarmId;
        this.deviceId = deviceId;
        this.type = type;
        this.level = level;
        this.description = description;
        this.alarmTime = alarmTime;
        this.status = status;
    }

    /** 工厂方法：从捕获到的报警异常自动生成一条记录。 */
    public static AlarmRecord from(AlarmException e) {
        return new AlarmRecord(
                "ALM_" + (++seq),
                e.getDeviceId(),
                typeOf(e.getDeviceId()),
                e.getLevel().getLabel(),
                e.getDescription(),
                LocalDateTime.now().format(FMT),
                "未处理");
    }

    /**
     * 按设备 id 前缀推断报警类型。
     * 与 FrDev.findById 的前缀路由是同一套约定（PU/CP/PS/TS/FM/CM）。
     */
    private static String typeOf(String deviceId) {
        if (deviceId == null || !deviceId.contains("_")) {
            return "未知类型";
        }
        return switch (deviceId.split("_", -1)[0]) {
            case "PU" -> "载荷异常";
            case "CP" -> "出口压力异常";
            case "CM" -> "排气参数异常";
            case "PS" -> "压力越限";
            case "TS" -> "温度越限";
            case "FM" -> "流量异常";
            default -> "未知类型";
        };
    }

    /** 处理报警（FR-ALM-05 预留）：状态由"未处理"改为"已处理"。 */
    public void markHandled() {
        this.status = "已处理";
    }

    public String getAlarmId() { return alarmId; }
    public String getDeviceId() { return deviceId; }
    public String getType() { return type; }
    public String getLevel() { return level; }
    public String getDescription() { return description; }
    public String getAlarmTime() { return alarmTime; }
    public String getStatus() { return status; }
}
