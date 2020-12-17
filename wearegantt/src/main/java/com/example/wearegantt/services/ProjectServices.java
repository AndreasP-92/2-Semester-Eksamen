package com.example.wearegantt.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class ProjectServices {


    public String formatDate(String date){
        DateTimeFormatter OLD_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate localDate = LocalDate.parse(date, OLD_FORMAT);

        DateTimeFormatter NEW_FORMAT = DateTimeFormatter.ofPattern("dd/mm/yyyy");
        String result = localDate.format(NEW_FORMAT);

        return result;
    }

    public String calcEndDate(LocalDate date, int workdays){
        if(workdays < 1){
            return formatDate(date.toString());
        }

        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < workdays){
            result = result.plusDays(1);
            if(!(result.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    result.getDayOfWeek() == DayOfWeek.SUNDAY)){
                ++addedDays;
            }
        }

        return result.toString();
    }

    public long calcTotalDays(String startDate, String endDate){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        String firstInput = startDate;
        String secondInput = endDate;
        LocalDate firstDate = LocalDate.parse(firstInput);
        LocalDate secondDate = LocalDate.parse(secondInput);
        long days = ChronoUnit.DAYS.between(firstDate, secondDate);
        System.out.println("Days between: " + days);

        return days;
    }

    public int calcTotalDays2(String startDate, String endDate) throws ParseException {
        Date date1=new SimpleDateFormat("yyyy-mm-dd").parse(startDate);
        Date date2=new SimpleDateFormat("yyyy-mm-dd").parse(endDate);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        int numberOfDays = 0;
        while (cal1.before(cal2)) {
            if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                    &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                numberOfDays++;
            }
            cal1.add(Calendar.DATE,1);
        }
        System.out.println(numberOfDays);
        return numberOfDays;
    }
    public String returnTime(Timestamp timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        return sdf.format(timestamp);
    }
}
