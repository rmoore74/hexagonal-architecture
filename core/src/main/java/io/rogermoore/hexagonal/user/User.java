package io.rogermoore.hexagonal.user;

import java.util.Objects;
import java.util.UUID;

public class User {

    private final UUID userUuid;
    private final String firstName;
    private final String lastName;
    private final int age;

    private User(Builder builder) {
        this.userUuid = builder.userUuid;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID userUuid;
        private String firstName;
        private String lastName;
        private int age;

        public Builder withUserUuid(UUID userUuid) {
            this.userUuid = userUuid;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withAge(int age) {
            this.age = age;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(userUuid, firstName, lastName, age);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return Objects.equals(userUuid, other.userUuid)
                && Objects.equals(firstName, other.firstName)
                && Objects.equals(lastName, other.lastName)
                && Objects.equals(age, other.age);
    }

}
