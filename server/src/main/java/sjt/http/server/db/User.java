package sjt.http.server.db;

import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements TableSchema {
    private String id;
    private String name;
    private int age;

    @Override
    public String getKey() {
        return id;
    }

    @Override
    public String printRow() {
        return String.format("|%-3s\t|%-5s\t|%-3s\t|", id, name, age);
    }
}
