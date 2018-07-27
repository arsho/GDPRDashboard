package enums;

public enum DefaultFileEnum {
    USER_DEFAULT_FILE("src/data/user-data.csv"),
    POLICY_DEFAULT_FILE("src/data/policy-data.csv");

    private final String filePath;

    private DefaultFileEnum(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

}
