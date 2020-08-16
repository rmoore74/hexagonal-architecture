package io.rogermoore.hexagonal.user;

import java.util.Objects;

public class RestCreateUserRequest {

    private String firstName;
    private String lastName;
    private int age;

    public RestCreateUserRequest() {
        // for jackson
    }

    private RestCreateUserRequest(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
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
        private String firstName;
        private String lastName;
        private int age;

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

        public RestCreateUserRequest build() {
            return new RestCreateUserRequest(this);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RestCreateUserRequest other = (RestCreateUserRequest) obj;
        return Objects.equals(firstName, other.firstName)
                && Objects.equals(lastName, other.lastName)
                && Objects.equals(age, other.age);
    }

}
