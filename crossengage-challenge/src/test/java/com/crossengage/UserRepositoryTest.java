package com.crossengage;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class UserRepositoryTest {

    @Test
    public void testGetActiveUsersWithContactsParsedCorrectly() throws Exception {
        //adjust
        File file = File.createTempFile("testParsed.txt", ".tmp");
        FileWriter writer = new FileWriter(file);
        writer.write("id,active,contactBy,email,phone\n");
        writer.write("1,true,email,test1@mail.com,+999999999999\n");
        writer.close();

        UserRepository repository = new UserRepository(file);

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setPhone("+999999999999");
        contactInfo.setActive(true);
        contactInfo.setContactBy(ContactInfo.ContactBy.EMAIL);
        contactInfo.setEmail("test1@mail.com");

        List<ContactInfo> expectedListByEmails = new LinkedList<>();
        expectedListByEmails.add(contactInfo);

        //action
        List<ContactInfo> actualListByEmails = repository.getActiveUsersWithContacts().collect(Collectors.toList());

        //assert
        assertTrue(CompareTwoLists(expectedListByEmails, actualListByEmails));

        file.deleteOnExit();
    }

    private boolean CompareTwoLists(List<ContactInfo> expectedList, List<ContactInfo> actualList) {
        if (expectedList.size() != actualList.size()) {
            return false;
        }

        for (int i = 0; i < expectedList.size(); ++i) {
            if (!Objects.equals(expectedList.get(i).getPhone(), actualList.get(i).getPhone())) {
                return false;
            }
            if (!Objects.equals(expectedList.get(i).getEmail(), actualList.get(i).getEmail())) {
                return false;
            }
            if (!Objects.equals(expectedList.get(i).getContactBy(), actualList.get(i).getContactBy())) {
                return false;
            }
            if (expectedList.get(i).isActive() != actualList.get(i).isActive()) {
                return false;
            }
        }
        return true;
    }


    @Test
    public void testGetActiveUsersByContactIfContactBySpecified() throws IOException {

        //adjust
        File file = File.createTempFile("testContactBy.txt", ".tmp");
        FileWriter writer = new FileWriter(file);
        writer.write("id,active,contactBy,email,phone\n");
        writer.write("1,true,email,test1@mail.com,+999999999999\n");
        writer.write("2,true,none,test2@mail.com,+999999999998\n");
        writer.write("3,false,phone,test3mail.com,+999999999997\n");
        writer.write("4,true,all,test4@mail.com,+999999999996");
        writer.close();

        UserRepository repository = new UserRepository(file);

        List<ContactInfo> expectedListByEmails = new LinkedList<>();

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setActive(true);
        contactInfo.setContactBy(ContactInfo.ContactBy.EMAIL);
        contactInfo.setEmail("test1@mail.com");
        contactInfo.setPhone("+999999999999");
        expectedListByEmails.add(contactInfo);

        /**
         contactInfo.setActive(true);
         contactInfo.setContactBy(ContactInfo.ContactBy.NONE);
         contactInfo.setEmail("test2@mail.com");
         contactInfo.setPhone("+999999999998");
         expectedListByEmails.add(contactInfo);

         contactInfo.setActive(false);
         contactInfo.setContactBy(ContactInfo.ContactBy.PHONE);
         contactInfo.setEmail("test3mail.com");
         contactInfo.setPhone("+999999999998");
         expectedListByEmails.add(contactInfo);

         contactInfo.setActive(true);
         contactInfo.setContactBy(ContactInfo.ContactBy.ALL);
         contactInfo.setEmail("test4@mail.com");
         contactInfo.setPhone("+999999999997");
         expectedListByEmails.add(contactInfo);

         List<ContactInfo> actualListByPhones = repository.getActiveUsersByContact(ContactInfo.ContactBy.PHONE);
         List<ContactInfo> actualListNoneContactBy = repository.getActiveUsersByContact(ContactInfo.ContactBy.NONE);
         List<ContactInfo> actualListByAll = repository.getActiveUsersByContact(ContactInfo.ContactBy.ALL);
         */

        //action
        List<ContactInfo> actualListByEmails = repository.getActiveUsersByContact(ContactInfo.ContactBy.EMAIL);
        //assert
        assertTrue(CompareTwoLists(expectedListByEmails, actualListByEmails));

        file.deleteOnExit();

    }

}
