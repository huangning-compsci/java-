import java.util.*;

/**
 *   ArrayList vs LinkedList（尾部追加、头部插入、随机访问、头部删除、遍历）
 *   HashMap   vs TreeMap  （插入、查询、删除、遍历）
 *
 * 测试方法：
 * - 数据规模 N = 100,000；随机访问/查找各 100,000 次；头部插入 10,000 次；
 * - System.nanoTime() 计时；先完整预热一轮（JIT 编译到位），再正式测 3 轮取中位数；
 * - 随机数种子固定为 42，保证结果可复现；
 * - 每轮测试前重新准备数据，避免结构性差异影响结果。
 */
public class CollectionPerformanceTest {

    private static final int N = 100_000;      // 数据规模
    private static final int ROUNDS = 3;       // 正式测试轮数

    public static void main(String[] args) {
        System.out.println("JVM: " + System.getProperty("java.version"));
        System.out.println("数据规模 N=" + N );
        System.out.println();

        // ============ 预热（不计入结果） ============
        listTests(false);
        mapTests(false);

        // ============ 正式测试：3 轮取中位数 ============
        System.out.println("========== ArrayList vs LinkedList ==========");
        System.out.printf("%-26s %15s %15s %12s%n", "操作", "ArrayList(ms)", "LinkedList(ms)", "胜出");
        System.out.println("-".repeat(72));
        listTests(true);
        System.out.println();
        System.out.println("========== HashMap vs TreeMap ==========");
        System.out.printf("%-26s %15s %15s %12s%n", "操作", "HashMap(ms)", "TreeMap(ms)", "胜出");
        System.out.println("-".repeat(72));
        mapTests(true);
    }

    // ---------------- List 测试 ----------------
    private static void listTests(boolean print) {
        // 1. 尾部追加 N 条
        long arrayTail = median(() -> {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < N; i++) list.add(i);
        });
        long linkedTail = median(() -> {
            List<Integer> list = new LinkedList<>();
            for (int i = 0; i < N; i++) list.add(i);
        });

        // 2. 头部插入 1 万次（头部插入慢，规模缩小）
        long arrayHead = median(() -> {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 10_000; i++) list.add(0, i);
        });
        long linkedHead = median(() -> {
            List<Integer> list = new LinkedList<>();
            for (int i = 0; i < 10_000; i++) list.add(0, i);
        });

        // 3. 随机 get N 次（固定种子，两组用相同下标序列）
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
        int[] idx = new int[N];
        Random rnd = new Random(42);
        for (int i = 0; i < N; i++) idx[i] = rnd.nextInt(N);

        long arrayGet = median(() -> {
            long s = 0;
            for (int i : idx) s += arrayList.get(i);
            if (s == -1) System.out.print("");   // 防止死代码消除
        });
        long linkedGet = median(() -> {
            long s = 0;
            for (int i : idx) s += linkedList.get(i);
            if (s == -1) System.out.print("");
        });

        // 4. 头部删除 1 千次
        long arrayRemove = median(() -> {
            List<Integer> copy = new ArrayList<>(arrayList);
            for (int i = 0; i < 1_000; i++) copy.remove(0);
        });
        long linkedRemove = median(() -> {
            List<Integer> copy = new LinkedList<>(linkedList);
            for (int i = 0; i < 1_000; i++) copy.remove(0);
        });

        // 5. 迭代器遍历
        long arrayIter = median(() -> {
            long s = 0;
            for (int v : arrayList) s += v;
            if (s == -1) System.out.print("");
        });
        long linkedIter = median(() -> {
            long s = 0;
            for (int v : linkedList) s += v;
            if (s == -1) System.out.print("");
        });

        if (print) {
            printRow("尾部追加 10 万条", arrayTail, linkedTail, "ArrayList", "LinkedList");
            printRow("头部插入 1 万次", arrayHead, linkedHead, "ArrayList", "LinkedList");
            printRow("随机 get 10 万次", arrayGet, linkedGet, "ArrayList", "LinkedList");
            printRow("头部删除 1 千次", arrayRemove, linkedRemove, "ArrayList", "LinkedList");
            printRow("迭代器遍历", arrayIter, linkedIter, "ArrayList", "LinkedList");
        }
    }

    // ---------------- Map 测试 ----------------
    private static void mapTests(boolean print) {
        // 固定种子生成随机 key，两个 Map 用完全相同的数据与顺序
        Integer[] keys = new Integer[N];
        Random rnd = new Random(42);
        for (int i = 0; i < N; i++) keys[i] = rnd.nextInt(N * 10);

        // 1. put N 条
        Map<Integer, Integer> hashMap = new HashMap<>();
        Map<Integer, Integer> treeMap = new TreeMap<>();
        long hashPut = median(() -> {
            hashMap.clear();
            for (Integer k : keys) hashMap.put(k, k);
        });
        long treePut = median(() -> {
            treeMap.clear();
            for (Integer k : keys) treeMap.put(k, k);
        });

        // 2. get N 次
        long hashGet = median(() -> {
            long s = 0;
            for (Integer k : keys) {
                Integer v = hashMap.get(k);
                if (v != null) s += v;
            }
            if (s == -1) System.out.print("");
        });
        long treeGet = median(() -> {
            long s = 0;
            for (Integer k : keys) {
                Integer v = treeMap.get(k);
                if (v != null) s += v;
            }
            if (s == -1) System.out.print("");
        });

        // 3. remove 一半 key
        long hashRemove = median(() -> {
            Map<Integer, Integer> copy = new HashMap<>(hashMap);
            for (int i = 0; i < N; i += 2) copy.remove(keys[i]);
        });
        long treeRemove = median(() -> {
            Map<Integer, Integer> copy = new TreeMap<>(treeMap);
            for (int i = 0; i < N; i += 2) copy.remove(keys[i]);
        });

        // 4. 遍历 entrySet
        long hashIter = median(() -> {
            long s = 0;
            for (Map.Entry<Integer, Integer> e : hashMap.entrySet()) s += e.getValue();
            if (s == -1) System.out.print("");
        });
        long treeIter = median(() -> {
            long s = 0;
            for (Map.Entry<Integer, Integer> e : treeMap.entrySet()) s += e.getValue();
            if (s == -1) System.out.print("");
        });

        if (print) {
            printRow("put 10 万条", hashPut, treePut, "HashMap", "TreeMap");
            printRow("get 10 万次", hashGet, treeGet, "HashMap", "TreeMap");
            printRow("remove 5 万条", hashRemove, treeRemove, "HashMap", "TreeMap");
            printRow("遍历 entrySet", hashIter, treeIter, "HashMap", "TreeMap");
        }
    }

    private static void printRow(String op, long a, long b, String nameA, String nameB) {
        String winner = a < b ? nameA : (a > b ? nameB : "持平");
        System.out.printf("%-26s %15d %15d %12s%n", op, a, b, winner);
    }

    /** 执行 ROUNDS 轮，返回耗时中位数（毫秒） */
    private static long median(Runnable r) {
        long[] times = new long[ROUNDS];
        for (int i = 0; i < ROUNDS; i++) {
            long start = System.nanoTime();
            r.run();
            times[i] = (System.nanoTime() - start) / 1_000_000;
        }
        Arrays.sort(times);
        return times[ROUNDS / 2];
    }
}
