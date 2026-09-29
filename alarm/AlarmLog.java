package alarm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import exceptions.AlarmException;

/**
 * 报警记录仓库（内存版，FR-ALM-01 / FR-ALM-03）。
 * 和设备池（DeviceArray）一样用静态结构保存，程序运行期间有效。
 */
public final class AlarmLog {

    private static final List<AlarmRecord> RECORDS = new ArrayList<>();

    private AlarmLog() {
    }

    /**
     * 捕获到 AlarmException 时调用：自动生成一条报警记录并入库。
     * @return 生成的记录（方便调用方提示"已生成报警记录 ALM_x"）
     */
    public static AlarmRecord record(AlarmException e) {
        AlarmRecord r = AlarmRecord.from(e);
        RECORDS.add(r);
        return r;
    }

    public static List<AlarmRecord> getAll() {
        return Collections.unmodifiableList(RECORDS);
    }

    public static int size() {
        return RECORDS.size();
    }

    /** FR-ALM-03 报警查询：以表格形式打印全部报警记录。 */
    public static void printAll() {
        if (RECORDS.isEmpty()) {
            System.out.println("暂无报警记录");
            return;
        }
        String[] headers = {"报警编号", "设备编号", "报警类型", "等级", "报警时间", "报警状态", "报警描述"};
        String[][] rows = new String[RECORDS.size()][];
        for (int i = 0; i < RECORDS.size(); i++) {
            AlarmRecord r = RECORDS.get(i);
            rows[i] = new String[]{r.getAlarmId(), r.getDeviceId(), r.getType(), r.getLevel(),
                    r.getAlarmTime(), r.getStatus(), r.getDescription()};
        }
        int[] widths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            widths[i] = displayWidth(headers[i]);
            for (String[] row : rows) {
                widths[i] = Math.max(widths[i], displayWidth(row[i]));
            }
        }
        StringBuilder headerLine = new StringBuilder();
        for (int i = 0; i < headers.length; i++) {
            headerLine.append(headers[i]).append(" ".repeat(widths[i] + 3 - displayWidth(headers[i])));
        }
        System.out.println(headerLine.toString().stripTrailing());
        for (String[] row : rows) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < row.length; i++) {
                line.append(row[i]).append(" ".repeat(widths[i] + 3 - displayWidth(row[i])));
            }
            System.out.println(line.toString().stripTrailing());
        }
        System.out.println("共 " + RECORDS.size() + " 条报警记录");
    }

    // 与 FrDev.showInfo 相同的等宽对齐逻辑：中文汉字和全角字符占两格
    private static int displayWidth(String text) {
        return text.codePoints().map(c ->
                Character.UnicodeScript.of(c) == Character.UnicodeScript.HAN
                || (c >= 0x3000 && c <= 0x303F)
                || (c >= 0xFF01 && c <= 0xFF60)
                || (c >= 0xFFE0 && c <= 0xFFE6) ? 2 : 1).sum();
    }
}
