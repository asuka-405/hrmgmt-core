package server.core.enums;

public enum EmployeeField {
    ID("id"),
    USERNAME("username"),
    NAME("name"),
    EMAIL("email"),
    PHONE("phone"),
    ADDRESS("address"),
    DEPARTMENT("department"),
    POSITION("position"),
    DOB("dob"),
    DOH("doh"),
    SALARY("salary"),
    CREATED_AT("created_at"),
    UPDATED_AT("updated_at"),
    PASSWORD_HASH("password_hash");

    private final String fieldName;

    EmployeeField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
