package org.benevolat.models;

import java.util.Objects;

public class User {
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getName(), user.getName()) && Objects.equals(getPassword(), user.getPassword()) && getType() == user.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPassword(), getType());
    }

    private String password;
    private UserType type;

    public User(String name, String password, UserType type) {
        this.name = name;
        this.password = password;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public UserType getType() {
        return type;
    }
}
