package com.crossengage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class UserRepository {

    private File data;
    private final String noneContactBy = "none";
    private final String emailContactBy = "email";
    private final String phoneContactBy = "phone";
    private final String delimiter = ",";

    UserRepository(File data) {
        this.data = data;
    }

   Stream <ContactInfo> getActiveUsersWithContacts() throws IOException {
        return Files.lines(data.toPath())
                .skip(1)
                .parallel()
                .map((line) -> {
                    ContactInfo contactInfo = new ContactInfo();
                    int beginFieldPosition = line.indexOf(delimiter) + 1;
                    int endFieldPosition = line.indexOf(delimiter, beginFieldPosition);

                    //read active string from line
                    String active = line.substring(beginFieldPosition, endFieldPosition);
                    contactInfo.setActive(Boolean.parseBoolean(active.toLowerCase()));

                    //read contactBy from line
                    beginFieldPosition = endFieldPosition + 1;
                    endFieldPosition = line.indexOf(delimiter, beginFieldPosition);
                    String contactBy = line.substring(beginFieldPosition, endFieldPosition);
                    contactInfo.setContactBy(stringToContactBy(contactBy.toLowerCase()));

                    //read email from line
                    beginFieldPosition = endFieldPosition + 1;
                    endFieldPosition = line.indexOf(delimiter, beginFieldPosition);
                    String email = line.substring(beginFieldPosition, endFieldPosition);
                    contactInfo.setEmail(email);

                    //read phone from line
                    beginFieldPosition = endFieldPosition + 1;
                    String phone = line.substring(beginFieldPosition);
                    contactInfo.setPhone(phone);
                    return contactInfo;
                })
                .filter(ContactInfo::isActive)
                .filter(contactInfo -> contactInfo.getContactBy() != ContactInfo.ContactBy.NONE);

    }


    List<ContactInfo> getActiveUsersByContact(ContactInfo.ContactBy contactBy) throws IOException {
        return getActiveUsersWithContacts()
                .filter(contactInfo -> contactInfo.getContactBy() == contactBy)
                .collect(Collectors.toList());
    }

    private ContactInfo.ContactBy stringToContactBy(String strContactBy) {
        switch (strContactBy) {
            case noneContactBy:
                return ContactInfo.ContactBy.NONE;
            case emailContactBy:
                return ContactInfo.ContactBy.EMAIL;
            case phoneContactBy:
                return ContactInfo.ContactBy.PHONE;
            default:
                return ContactInfo.ContactBy.ALL;
        }
    }

}
