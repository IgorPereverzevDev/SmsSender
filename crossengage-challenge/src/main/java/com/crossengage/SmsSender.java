package com.crossengage;

/**
 * Created by zorg9 on 22.06.2017.
 */
public class SmsSender implements Sender {
    public void sendMessageTo(ContactInfo contactInfo) {
        ConsoleChannel consoleChannel = new ConsoleChannel();
        consoleChannel.showMessage(contactInfo.getPhone());
    }
}
