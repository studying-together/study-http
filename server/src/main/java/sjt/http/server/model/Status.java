package sjt.http.server.model;

public enum Status {
    OK(200, "OK"),
    NO_CONTENT(204, "No Content"),
    BAD_REQUEST(400, "Bad request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not found"),
    METHOD_NOT_ALLOWED(405, "Method not allowed"),
    INVALID_ENCRYPT_PROTOCOL(460, "Invalid encrypt protocol"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    BAD_GATEWAY(502, "Bad Gateway"),
    BAD_AUTHENTICATION(600, "Bad Authentication"),
    EXIST_ID(601, "exist Id"),
    NOT_FOUND_OPERATOR(602, "Not found operator"),
    NOT_FOUND_USER(603, "Not found user"),
    NOT_FOUND_SECURITY_INFO(604, "Not found security info"),
    OPERATOR_HAVE_CONSULT(605, "Operator have consult"),
    DOWNLOAD_COUPON_ERROR(606, "Download coupon error"),
    DUPLICATED_COUPON_ERROR(607, "Duplicated coupon error"),
    NOT_FOUND_ROOM(608, "Not found room"),
    LOCKED_USER(609, "Locked user"),
    INVALID_PHONE_NUMBER(610, "Invalid phone number"),
    LIMITED_SEND_SMS(611, "Limited send sms"),
    NOT_MATCHED_MDN(612, "Not matched mdn"),
    NOT_MATCHED_CODE(613, "Not matched code"),
    LIMITED_ENTER_CODE(614, "Limited enter code"),
    NOT_FOUND_SEND_SMS(615, "Not found send sms"),
    LIMITED_MAX_MEMBER(616, "Limited max member"),
    INVALID_INVITE_USER(617, "Invalid invite user"),
    NOT_FOUND_CHATBOT(618, "Not found chatbot"),
    NOT_ALLOW_SERVICE(619, "Not allow service"),
    NOT_FOUND_MESSAGE(620, "Not found message"),
    SCRAP_ERROR(621, "Scrap error"),
    CART_API_ERROR(622, "Cart API error"),
    EXPIRE_PASSWORD_ERROR(623, "Expire password error"),
    INVALID_UPLOAD_DATA(624, "Invalid upload data"),
    NEED_CHANGE_PASSWORD(625, "Need change password"),
    EXTERNAL_SERVER_ERROR(700, "External server error"),
    EXPIRE_TOKEN_ERROR(701, "Expire token error"),
    CLOSED_SERVICE_ERROR(702, "Closed service error"),
    NOT_ALLOW_BUDDY_FUNCTION(703, "Not allow buddy function"),
    MUST_UPDATE_APPLICATION(1001, "Must update application"),
    SERVER_MAINTENANCE(2001, "Server maintenance");
    ;

    private int code;
    private String description;

    Status(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusCode() { return code + " " + description; }
}
