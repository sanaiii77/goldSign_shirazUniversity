package root;


import android.content.Context;

import jango.DatetimeRange;
import jango.Django;

public class CheckTime {
    public  static String STUDENT_Time ="time_for_student";
    public  static String TEACHER_TIME ="time_for_teacher";
    public  static String NOT_START_TIME ="notStart";
    public  static String FINISH__TIME ="finish";

    public  static String checkTime (DatetimeRange student_date_range, DatetimeRange teacher_date_range ){
        if (CompareTwoDatesTest.comparison( student_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("after")){

            return NOT_START_TIME ;
        }
        //____________________________________________________________________________________________________________

        else if (CompareTwoDatesTest.comparison( student_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal")
                && CompareTwoDatesTest.comparison( student_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("after") ){
            return STUDENT_Time;
        }
        else if (CompareTwoDatesTest.comparison( student_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("before")
                && CompareTwoDatesTest.comparison( student_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("after") ){

            return STUDENT_Time;
        }

        else if (CompareTwoDatesTest.comparison( student_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("before")
                && CompareTwoDatesTest.comparison( student_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal") ){

            return STUDENT_Time;
        }
        else if (CompareTwoDatesTest.comparison( student_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal")
                && CompareTwoDatesTest.comparison( student_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal") ){

            return STUDENT_Time;
        }
        //________________
        //______________________________________________________________________________________________
        if (CompareTwoDatesTest.comparison( teacher_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal")
                && CompareTwoDatesTest.comparison( teacher_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("after") ){
            return TEACHER_TIME;

        }
        else if (CompareTwoDatesTest.comparison( teacher_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("before")
                && CompareTwoDatesTest.comparison( teacher_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("after") ){
            return TEACHER_TIME;

        }

        else if (CompareTwoDatesTest.comparison( teacher_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("before")
                && CompareTwoDatesTest.comparison( teacher_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal") ){
            return TEACHER_TIME;

        }
        else if (CompareTwoDatesTest.comparison( teacher_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal")
                && CompareTwoDatesTest.comparison( teacher_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal") ){
            return TEACHER_TIME;

        }
        //____________________________________________________________________________________________________________
        else {

            return FINISH__TIME;

        }


    }

}
