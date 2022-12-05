package com.bjpowernode.p2p.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {
    public static void main(String[] args) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMMM-dd-EEEE");
//        Date date = new Date();
//        System.out.println(simpleDateFormat.format(date));
//        Calendar instance = Calendar.getInstance();
//        int year = instance.get(Calendar.YEAR);
//        System.out.println(year);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);


//一周第一天是否为星期天
        boolean isFirstSunday = (cal.getFirstDayOfWeek() == Calendar.SUNDAY);
//获取周几
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
//若一周第一天为星期天，则-1
        if(isFirstSunday){
            weekDay = weekDay - 1;
            if(weekDay == 0){
                weekDay = 7;
            }
        }
        System.out.println("今天是"+year+"年"+month+"月"+day+"日"+"星期"+weekDay);
//        switch (weekDay){
//            case (1):
//                System.out.println("今天是"+year+"年"+month+"月"+day+"日"+"星期"+weekDay); break;
//            case (2):
//                System.out.println("今天是"+year+"年"+month+"月"+day+"日"+"星期"+weekDay); break;
//            case (3) :
//                System.out.println("今天是"+year+"年"+month+"月"+day+"日"+"星期"+weekDay); break;
//            case (4) :
//                System.out.println("今天是"+year+"年"+month+"月"+day+"日"+"星期"+weekDay); break;
//            case (5) :
//                System.out.println("今天是"+year+"年"+month+"月"+day+"日"+"星期"+weekDay); break;
//            case (6) :
//                System.out.println("今天是"+year+"年"+month+"月"+day+"日"+"星期"+weekDay); break;
//
//        }
//打印周几
//        System.out.println(weekDay);
    }
}
