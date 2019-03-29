package com.skool360admin.Utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import anandniketan.com.bhadajadmin.R;

public class DateUtils {
    //region Constants

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * DateUtils.SECOND_MILLIS;
    private static final int HOUR_MILLIS   = 60 * DateUtils.MINUTE_MILLIS;
    private static final int DAY_MILLIS    = 24 * DateUtils.HOUR_MILLIS;

    //endregion

    private DateUtils() {
    }


    @SuppressLint("StringFormatInvalid")
    @SuppressWarnings("checkstyle:MagicNumber")
    @Nullable
    public static String toApproximateTime(@NonNull final Context context, final long time) {
        final long now = System.currentTimeMillis();

        if (time > now || time <= 0) return null;

        final long diff = now - time;

        if (diff < DateUtils.MINUTE_MILLIS) return context.getString(R.string.time_ago_now);
        if (diff < 2 * DateUtils.MINUTE_MILLIS) return context.getString(R.string.time_ago_minute);
        if (diff < 50 * DateUtils.MINUTE_MILLIS) return  String.valueOf(Math.round(diff / (double)DateUtils.MINUTE_MILLIS)+" "+context.getString(R.string.time_ago_minutes));
        if (diff < 90 * DateUtils.MINUTE_MILLIS) return context.getString(R.string.time_ago_hour);
        if (diff < 24 * DateUtils.HOUR_MILLIS) return String.valueOf(Math.round(diff / (double)DateUtils.HOUR_MILLIS) +" "+context.getString(R.string.time_ago_hours));
        if (diff < 48 * DateUtils.HOUR_MILLIS) return context.getString(R.string.time_ago_day);

    //    return String.format(context.getString(R.string.time_ago_days), String.valueOf(Math.round(diff /(double)DateUtils.DAY_MILLIS)));
        return String.valueOf(Math.round(diff /(double)DateUtils.DAY_MILLIS) + " " +context.getString(R.string.time_ago_days));

    }


    public static String convertDateIntoGivenFormat(String date, String format1, String returnFormat) throws Exception {
        SimpleDateFormat spf = new SimpleDateFormat(format1);
        Date newDate = spf.parse(date);
        spf = new SimpleDateFormat(returnFormat);
        String newDateString = spf.format(newDate);

        return newDateString;
    }

    public static String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String formattedDate = df.format(c);
        return  formattedDate;
    }




    public static String getCurrentDatePlusOneDay(){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy", Locale.ENGLISH);
        String formattedDate = df.format(dt);
        return formattedDate;
    }

    public static String getCurrentDateMinusOneMonth(){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MONTH, - 1);
        dt = c.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String formattedDate = df.format(dt);
        return formattedDate;
    }

    public static boolean checkDates(String fromDate, String toDate)   {
        SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");

        boolean b = false;
        try {
            if(dfDate.parse(fromDate).before(dfDate.parse(toDate))) {
                b = true;//If start date is before end date
            }
            else if(dfDate.parse(fromDate).equals(dfDate.parse(toDate))) {
                b = true;//If two dates are equal
            }
            else {
                b = false; //If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }


    public static boolean checkCurrentDateValidation(String fromDate)   {
        SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");

        boolean b = false;
        try {
            if(dfDate.parse(fromDate).before(dfDate.parse(Utils.getTodaysDate()))) {
                b = true;//If start date is before end date
            }
            else if(dfDate.parse(fromDate).equals(dfDate.parse(Utils.getTodaysDate()))) {
                b = true;//If two dates are equal
            }
            else {
                b = false; //If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }

    public static Date toLocalTime(String utcDate, SimpleDateFormat sdf) throws Exception {

        // create a new Date object using
        // the timezone of the specified city
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date localDate = sdf.parse(utcDate);

        sdf.setTimeZone(TimeZone.getDefault());
        String dateFormateInUTC = sdf.format(localDate);

        return sdf.parse(dateFormateInUTC);
    }

    public static Date parseDate(String date) {

        if (date == null) {
            return null;
        }

        StringBuffer sbDate = new StringBuffer();
        sbDate.append(date);
        String newDate = null;
        Date dateDT = null;

        try {
            newDate = sbDate.substring(0, 19).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String rDate = newDate.replace("T", " ");
        String nDate = rDate.replaceAll("-", "/");

        try {
            dateDT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(nDate);
            // Log.v( TAG, "#parseDate dateDT: " + dateDT );
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateDT;
    }

    public static int getPreviousYear() {
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, -1);
        return prevYear.get(Calendar.YEAR);
    }
    public static int getCurrentYear() {
        Calendar prevYear = Calendar.getInstance();
        return prevYear.get(Calendar.YEAR);
    }
    public static int getFutureYear() {
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, +1);
        return prevYear.get(Calendar.YEAR);
    }
    public static ArrayList<String> getMonthNames(){
        int[] months = {0,1,2,3,4,5,6,7,8,9,10,11};
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < months.length; i++) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            cal.set(Calendar.MONTH, months[i]);
            String month_name = month_date.format(cal.getTime());

            xVals.add(month_name);
        }
        return xVals;
    }
    public static String getCurrentMonthName(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        cal.get(Calendar.MONTH + 1);
        String month_name = month_date.format(cal.getTime());
        return month_name;

    }

    public static int getFirstDayOfMonth(int day, int month, int year) {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.DATE,day);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.DAY_OF_MONTH,day);
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
//                return "SUNDAY";
                return Calendar.SUNDAY;
            case Calendar.MONDAY:
                return Calendar.MONDAY;
            case Calendar.TUESDAY:
                return  Calendar.TUESDAY;
            case Calendar.WEDNESDAY:
                return  Calendar.WEDNESDAY;
            case Calendar.THURSDAY:
                return  Calendar.THURSDAY;
            case Calendar.FRIDAY:
                return  Calendar.FRIDAY;
            case Calendar.SATURDAY:
                return  Calendar.SATURDAY;
        }
        return 0;
    }
}
