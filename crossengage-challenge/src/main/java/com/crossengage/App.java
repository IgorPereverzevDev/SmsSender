package com.crossengage;

import java.io.File;
import java.io.IOException;


public class App {

    public static void main(String[] args) throws IOException {

        UserRepository repository = new UserRepository(new File(args[0]));
        Sender smsSender = new SmsSender();
        Sender emailSender = new EmailSender();
        SenderComposer allSender = new SenderComposer();

        allSender.addSender(smsSender);
        allSender.addSender(emailSender);

        repository.getActiveUsersByContact(ContactInfo.ContactBy.ALL).forEach(allSender::sendMessageTo);
        repository.getActiveUsersByContact(ContactInfo.ContactBy.PHONE).forEach(smsSender::sendMessageTo);
        repository.getActiveUsersByContact(ContactInfo.ContactBy.EMAIL).forEach(emailSender::sendMessageTo);

    }
}

