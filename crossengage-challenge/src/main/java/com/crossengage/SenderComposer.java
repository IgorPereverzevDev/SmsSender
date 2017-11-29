package com.crossengage;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zorg9 on 22.06.2017.
 */
public class SenderComposer implements Sender {

    private List<Sender> context = new LinkedList<>();

    @Override
    public void sendMessageTo(ContactInfo contactInfo) {
        for (Sender sender:context) {
            sender.sendMessageTo(contactInfo);
        }
    }

    void addSender(Sender sender) {
        context.add(sender);
    }
}
