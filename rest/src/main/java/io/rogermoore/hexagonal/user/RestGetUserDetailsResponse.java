package io.rogermoore.hexagonal.user;

import java.util.Objects;
import java.util.UUID;

public class RestGetUserDetailsResponse {

    private String userUuid;
    private String firstName;
    private String lastName;
    private int age;

    public RestGetUserDetailsResponse() {
        // for jackson
    }

    private RestGetUserDetailsResponse(Builder builder) {
        this.userUuid = builder.userUuid;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userUuid;
        private String firstName;
        private String lastName;
        private int age;

        public Builder withUserUuid(UUID userUuid) {
            this.userUuid = userUuid.toString();
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

        public RestGetUserDetailsResponse build() {
            return new RestGetUserDetailsResponse(this);
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
        RestGetUserDetailsResponse other = (RestGetUserDetailsResponse) obj;
        return Objects.equals(userUuid, other.userUuid)
                && Objects.equals(firstName, other.firstName)
                && Objects.equals(lastName, other.lastName)
                && Objects.equals(age, other.age);
    }

}