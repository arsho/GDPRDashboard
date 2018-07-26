package gdprdashboard;

public enum UserChoiceEnum {
    COMPLY,
    OPT_OUT;

    boolean value() {
        switch (this) {
            case COMPLY:
                return true;
            case OPT_OUT:
                return false;
            default:
                throw new AssertionError("Unknown choice " + this);
        }
    }

}
