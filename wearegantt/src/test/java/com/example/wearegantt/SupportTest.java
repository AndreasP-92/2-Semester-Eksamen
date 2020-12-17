package com.example.wearegantt;

import com.example.wearegantt.model.SupportTicket;
import com.example.wearegantt.services.ObjectManager;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SupportTest {

    ObjectManager objectManager = new ObjectManager();

    @Test
    public void getAllTickets(){
//        System.out.println(objectManager.ticketRepo.getAllUserTickets(58));
        List<SupportTicket> supportTicketList = objectManager.ticketRepo.getAllTickets();

        System.out.println(supportTicketList);

    }

    @Test
    public void getAllUserTickets(){
        System.out.println(objectManager.ticketRepo.getAllUserTickets(58));

    }

    @Test
    public void getAllMessagesWId(){
        System.out.println(objectManager.ticketRepo.getAllMessagesWTicketId(8));

    }
}
