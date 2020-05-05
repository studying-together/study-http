package sjt.http.server.db;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yusik on 2020/04/29.
 */
@Slf4j
public class Table<T extends TableSchema> extends ConcurrentHashMap<String, T> {

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
        if (!isEmpty()) {
            log.debug("== TABLE(total={}}) ============", size());
            for (T row : values()) {
                log.debug("{}", row.printRow());
            }
            log.debug("\"==============================");
        }
    }
}
