package com.hfad.scorelist;

import java.util.Calendar;

public class DayTimeHelper {

    public String getDateDayTime() {
        StringBuilder builder = new StringBuilder();

        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String date = String.valueOf(cal.get(Calendar.DATE));
        String dayOfWeek = getKorDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));

        String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        int min = cal.get(Calendar.MINUTE);
        String minute = String.valueOf(min);
        if (min  < 10) {
            minute = "0" + minute;
        }

        builder.append(year);
        builder.append(". ");
        builder.append(month);
        builder.append(". ");
        builder.append(date);
        builder.append(dayOfWeek);
        builder.append("  ");
        builder.append(hour);
        builder.append(":");
        builder.append(minute);

        return builder.toString();
    }

    private String getKorDayOfWeek(int val) {
        switch (val) {
            case 1:
                return "(일)";
            case 2:
                return "(월)";
            case 3:
                return "(화)";
            case 4:
                return "(수)";
            case 5:
                return "(목)";
            case 6:
                return "(금)";
            case 7:
                return "(토)";
        }
        return "";
    }
}
