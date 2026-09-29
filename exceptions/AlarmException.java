package exceptions;

/**
 * 报警异常（FR-ALM-01 / FR-ALM-05）。
 * checkAlarm() 检测出异常工况时抛出，携带报警等级，
 * 监控线程 catch 后按等级执行提示 / 停机 / 生成运维任务。
 */
public class AlarmException extends OilfieldException {

    /** 报警等级（FR-ALM-02）。 */
    public enum Level {
        INFO("提示"), WARNING("警告"), CRITICAL("严重");

        private final String label;

        Level(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    private final String deviceId;
    private final Level level;

    public AlarmException(String deviceId, Level level, String message) {
        super("[" + level.getLabel() + "] 设备 " + deviceId + "：" + message);
        this.deviceId = deviceId;
        this.level = level;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Level getLevel() {
        return level;
    }
}