//package com.example.wearegantt;
//import com.example.wearegantt.model.SupportTicket;
//import com.example.wearegantt.repository.SupportTicketRepo;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.List;
//
//public class TicketTest {
//
//    SupportTicketRepo contactRepo = new SupportTicketRepo();
//
//    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSSSSS");
//
//    private static final SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
//    private static final SimpleDateFormat time = new SimpleDateFormat("HH.mm");
//
//    @Test
//    public void timeStampTest(){
////        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
//        System.out.println(timestamp);
//
//
////        System.out.println(sdf.format(timestamp));
//        System.out.println(date.format(timestamp));
//        System.out.println(time.format(timestamp));
//    }
//
//    // GET ONE TICKET
//    @Test
//    public void getOneTicket(){
//        SupportTicketRepo contactObj = new SupportTicketRepo();
//
//        System.out.println(contactObj);
//    }
//
//
//    // GET ALL TICKET
//    @Test
//    public void getAllTickets(){
//        List<SupportTicket> contactList = contactRepo.getAllTickets();
//
//        System.out.println(contactList);
//    }
//
//    // INSERT ONE TICKET
//
//    @Test
//    public void InsertOneTicket(){
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        String date = sdf.format(timestamp);
//        System.out.println(date);
//
////        System.out.println(timestamp);
////        contactRepo.InsertTicket("name3","desc3", date, 1);
//
//    }
//
//
//}
