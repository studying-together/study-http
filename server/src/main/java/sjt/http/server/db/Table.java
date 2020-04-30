package sjt.http.server.db;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yusik on 2020/04/29.
 */
@RequiredArgsConstructor
public class Table<T extends TableSchema> extends ConcurrentHashMap<String, T> {

    private final boolean debugMode;

    public Table() {
        this(false);
    }

    public T insert(T row) {
        showTable();
        T result = put(row.getKey(), row);
        showTable();
        return result;
    }

    public T select(String key) {
        T result = get(key);
        showTable();
        return result;
    }

    public T update(T row) {
        showTable();
        T result = computeIfPresent(row.getKey(), (id, old) -> row);
        showTable();
        return result;
    }

    public T delete(String key) {
        showTable();
        T result = remove(key);
        showTable();
        return result;
    }

    // for debug
    public void showTable() {
        if (debugMode && !isEmpty()) {
            System.out.printf("== TABLE(total=%d) ============\n", size());
            for (T row : values()) {
                System.out.println(row.printRow());
            }
            System.out.println("==============================\n");
        }
    }
}
