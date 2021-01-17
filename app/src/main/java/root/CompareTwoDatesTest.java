package root;

import java.text.*;
import java.util.Date;
public class CompareTwoDatesTest {
    public static String comparison(String time , String today)  {
       try {

           SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
           Date d1 = sdformat.parse(time);
           Date d2 = sdformat.parse(today);
           System.out.println("The date 1 is: " + sdformat.format(d1));
           System.out.println("The date 2 is: " + sdformat.format(d2));
           if(d1.compareTo(d2) > 0) {
               System.out.println("Date 1 occurs after Date 2");
               return "after";
           } else if(d1.compareTo(d2) < 0) {
               System.out.println("Date 1 occurs before Date 2");
               return "before";
           } else if(d1.compareTo(d2) == 0) {
               System.out.println("Both dates are equal");
               return  "equal";
           }
       } catch (java.text.ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }


        return "";
    }

    public static String  convertTimeToString (int y , int m , int d){
        String time = "" ;
        time += y +"-";

        if(m >0 && m<10){
            time += "0"+m+"-";
        }
        else {
            time+=m+"-";
        }
        if(d >0 && d<10){
            time += "0"+d;
        }
        else {
            time+=d;
        }

        return time;


    }



}