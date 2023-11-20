package org.benevolat.models;

import org.benevolat.InvalidUserTypeIdException;

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
    public static UserType fromInt(int id) throws InvalidUserTypeIdException {
        return switch (id) {
            case 1 -> UserType.Voluntary;
            case 2 -> UserType.Asker;
            case 3 -> UserType.Checker;
            default -> throw new InvalidUserTypeIdException();
        };
    }

    @Override
    public String toString() {
        return switch (this.id) {
            case 1 -> "Bénévole";
            case 2 -> "Demandeur";
            case 3 -> "Vérifieur";
            default -> "";
        };
    }
}
