package com.crossengage;

/**
 * Created by zorg9 on 22.06.2017.
 */
class ContactInfo {

    public enum ContactBy {
        NONE, EMAIL, PHONE, ALL
    }

    private String phone;
    private String email;
    private ContactBy contactBy;
    private boolean active;

    boolean isActive() {
        return active;
    }

    void setActive(boolean active) {
        this.active = active;
    }

    void setPhone(String phone) {this.phone = phone;}

    void setEmail(String email) {this.email = email;}

    ContactBy getContactBy() {return contactBy;}

    void setContactBy(ContactBy contactBy) {this.contactBy = contactBy;}

    String getPhone() {return phone;}

    String getEmail() {return email;}

}
