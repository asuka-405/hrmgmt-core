package server.core.enums;

public enum Position {
    INTERN("intern"),
    JUNIOR_DEVELOPER("juniordev"),
    SENIOR_DEVELOPER("seniordev"),
    MANAGER("manager"),
    DIRECTOR("director"),
    VICE_PRESIDENT("vp"),
    PRESIDENT("president"),
    CEO("ceo"),
    EMPLOYEE("employee");

    private final String stringValue;

    private Position(String stringValue){
        this.stringValue = stringValue;
    }

    public String getStringValue(){
        return this.stringValue;
    }

}
