package com.example.myapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarFragment extends Fragment {
    /*** This class provides:

     * creates a CalendarFragment instance using 'fragment_calendar' layout
     * Displays a calendar with which the user can interact:
        - can search for a date
        - can click on date to access the home page with trainings scheduled for that day

     ***/
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Brings up layout & identify widgets
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        CalendarView calendarView = (CalendarView) v.findViewById(R.id.simpleCalendarView);
        calendarView.setDate(getHomeDateMillisecs ());

        // When a date is clicked, switch to home page
        // Also, update current date value with the clicked on
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange (CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = setDateFormat(year, month, dayOfMonth);
                ((MainActivity)getActivity()).setHomeDate(selectedDate);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            }
        });
        return v;
    }

    private String setDateFormat (int year, int month, int dayOfMonth) {
        /*
            Returns the full date in the format yyyy-MM-dd
         */
        String date, day, monthStr;
        day = defaultOrAddZero(dayOfMonth);
        monthStr = defaultOrAddZero(month + 1);
        date = year + "-"  + monthStr + "-" + day;
        return date;
    }

    private String defaultOrAddZero (int dayOrmonth) {
        if (dayOrmonth < 10) {
            return "0" + dayOrmonth;
        }
        return "" + dayOrmonth;
    }

    private long getHomeDateMillisecs () {
        /*
            * Retrieve current home date value from Main activity
            * Convert this date into milliseconds
         */
        String date = ((MainActivity)getActivity()).getHomeDate();
        Date dateReformat = null;
        try {
            dateReformat = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateReformat.getTime();
    }
}
