package entity;

import java.io.Serializable;
import java.util.Objects;

public class Contact implements Serializable {
    private String phoneNumber;
    private String group;
    private String fullName;
    private String gender;
    private String address;
    private String email;
    private String birthday;

    public Contact() {}

    public Contact(String phoneNumber, String group, String fullName, String gender, String address) {
        this.phoneNumber = phoneNumber;
        this.group = group;
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
    }

    public Contact(String phoneNumber, String group, String fullName, String gender, String address, String email, String birthday) {
        this.phoneNumber = phoneNumber;
        this.group = group;
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(phoneNumber, contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(phoneNumber);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", group='" + group + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
