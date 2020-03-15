package model.header;

import model.parser.JsonParser;
import model.parser.Parser;

import java.util.function.Supplier;

public enum ContentType {
    JSON("application/json", JsonParser::new),
    ;

    private String vaule;
    private Supplier<Parser> parserSupplier;

    ContentType (String contentType, Supplier<Parser> parserSupplier) {
        this.vaule = contentType;
        this.parserSupplier = parserSupplier;
    }

    public static ContentType fromStirng(String str) {
        for (ContentType c : ContentType.values()) {
            if (c.vaule.equals(str)) {
                return c;
            }
        }
        return null;
    }

    public Parser getParser() {
        return parserSupplier.get();
    }

}
