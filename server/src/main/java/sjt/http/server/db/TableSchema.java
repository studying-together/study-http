package sjt.http.server.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by yusik on 2020/04/29.
 */
public interface TableSchema {

    default String printRow() {
        return "row";
    }

    @JsonIgnore
    String getKey();
}
