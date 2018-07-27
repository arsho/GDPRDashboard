package enums;

public enum UserMenuEnum {
    VIEW_ALL_CONSENT(1, "View all users' dashboard"),
    UPDATE_ONE_CONSENT(2, "Update an user consent"),
    VIEW_ONE_CONSENT(3, "View an user's dashboard"),
    ADD_POLICY(4, "Add new policy"),
    DELETE_POLICY(5, "Delete policy"),
    ADD_USER(6, "Add new user"),
    DELETE_USER(7, "Delete user"),
    QUIT(0, "Quit");

    private final int index;
    private final String description;

    private UserMenuEnum(int index, String description) {
        this.index = index;
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }

    public static UserMenuEnum getMenuFromIndex(int index) {
        for (UserMenuEnum item : UserMenuEnum.values()) {
            if (item.getIndex() == index) {
                return item;
            }
        }
        return UserMenuEnum.QUIT;
    }

}
