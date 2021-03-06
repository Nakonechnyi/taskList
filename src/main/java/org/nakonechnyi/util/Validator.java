package org.nakonechnyi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @autor A_Nakonechnyi
 * @date 14.10.2016.
 */
public class Validator {

    public static boolean isTaskNameValid(String taskName) {
        return (taskName == null)||(taskName.length() == 0) ? false : true;
    }

    public static boolean isDateStrValid(String dateStr) {
        if(dateStr == null){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(AppProperties.DATE_FORMAT);
        sdf.setLenient(false);

        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public static boolean isPriorityValid(String priority) {
        try{
            int num = Integer.parseInt(priority);
            return num > 0 ? true : false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
