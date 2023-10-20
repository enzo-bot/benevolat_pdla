package org.benevolat.models;

public enum UserType {
    Voluntary(1), Asker(2), Checker(3);
    private int id;

    UserType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    //TODO Exception spécifique
    public static UserType fromInt(int id) throws Exception{
        return switch (id) {
            case 1 -> UserType.Voluntary;
            case 2 -> UserType.Asker;
            case 3 -> UserType.Checker;
            default -> throw new Exception("no type corresponding to the id");
        };
    }

}
