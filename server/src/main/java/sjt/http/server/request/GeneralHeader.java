package sjt.http.server.request;

import java.time.LocalDateTime;

public class GeneralHeader {

    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
