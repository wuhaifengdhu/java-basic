/**
 * 
 */
package com.haifwu.learning.java.core;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author haifwu
 *
 */
public class CalendarExample {

    /**
     * @param args
     */

    public static void main(String[] args) {
        showBasicFields();
        showCalendar();
        DateFormatSymbols symbols = DateFormatSymbols.getInstance();
        printArray("AMPM:", symbols.getAmPmStrings());
        printArray("eras:", symbols.getEras());
        printArray("months:", symbols.getMonths());
        printArray("short months:", symbols.getShortMonths());
        printArray("short week days:", symbols.getShortWeekdays());
        printArray("week days:", symbols.getWeekdays());
        printArray("Availabel locales:", Arrays.stream(DateFormatSymbols.getAvailableLocales()).map(x -> x.toString())
                .collect(Collectors.toList()).toArray(new String[0]));
        printArray2("Zone Strings:", symbols.getZoneStrings());
        printStr("Local Pattern chars:" + symbols.getLocalPatternChars());
    }

    private static void printArray2(String promoteMsg, String[][] zoneStrings) {
        System.out.print(promoteMsg);
        for (int i = 0; i < zoneStrings.length; i++) {
            printArray(i + ":", zoneStrings[i]);
        }
    }

    private static void printArray(String promoteMsg, String[] amPmStrings) {
        System.out.print(promoteMsg);
        for (String s : amPmStrings) {
            System.out.print(s + ", ");
        }
        System.out.print("\n");
    }

    private static void showBasicFields() {
        Locale.setDefault(Locale.ITALY);
        GregorianCalendar dCalendar = new GregorianCalendar(2020, 3, 30);
        for (int i = 0; i < 12; i++) {
            System.out.println("Current Data:" + dCalendar.getTime().toString());
            System.out.println("ERA:" + dCalendar.get(Calendar.ERA));
            System.out.println("Week of year:" + dCalendar.get(Calendar.WEEK_OF_YEAR));
            System.out.println("Week of month:" + dCalendar.get(Calendar.WEEK_OF_MONTH));
            System.out.println("day of week:"
                    + DateFormatSymbols.getInstance().getWeekdays()[dCalendar.get(Calendar.DAY_OF_WEEK)]);
            System.out.println("day of week in month:" + dCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
            System.out.println("Zone offset:" + dCalendar.get(Calendar.ZONE_OFFSET) / 3600000);
            System.out.println("DST offset:" + dCalendar.get(Calendar.DST_OFFSET));
            System.out.println("");
            dCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

    }

    @SuppressWarnings({ "resource" })
    private static void showCalendar() {
        printStr("This program is to show calendar. Exit use command: exit\n");
        showCurrentCalendar(0);
        Scanner in = new Scanner(System.in);
        showPromoteMessage();
        String name = in.nextLine();
        while (!"exit".equals(name)) {
            try {
                int d = Integer.parseInt(name);
                showCurrentCalendar(d);
            } catch (NumberFormatException e) {
                printStr("\nCan not parse " + name + " to integer, please input int value");
            }
            showPromoteMessage();
            name = in.nextLine();
            System.out.printf("\nname is #%s#\n", name);
        }
        System.out.println("program exit success!");
    }

    private static void showPromoteMessage() {
        System.out.println("\nInput a int value x to show the calendar next x month. (exit to exit program)");
    }

    private static void showCurrentCalendar(int monthFromNow) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, monthFromNow);
        showHeader(calendar);
        showBody(calendar);
    }

    private static void showBody(GregorianCalendar calendar) {
        // Step 1. Print first line as there maybe some empty space
        int currentMonth = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        int localFirstWeek = calendar.getFirstDayOfWeek();
        for (int i = 0; i < 7; i++) {
            if (localFirstWeek != week) {
                printStr(" \t");
                localFirstWeek = (localFirstWeek + 1) % 7;
                continue;
            }
            printDay(calendar.get(Calendar.DAY_OF_MONTH));
            printStr(i == 6 ? "\n" : "\t");
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Step 2. Print the following lines
        while (calendar.get(Calendar.MONTH) == currentMonth) {
            for (int i = 0; i < 7 && calendar.get(Calendar.MONTH) == currentMonth; i++) {
                printDay(calendar.get(Calendar.DAY_OF_MONTH));
                printStr(i == 6 ? "\n" : "\t");
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
    }

    private static void showHeader(GregorianCalendar calendar) {
        printStr("Current month: " + (calendar.get(Calendar.MONTH) + 1) + "\n");
        int localFirstWeek = calendar.getFirstDayOfWeek();
        String[] weekDays = DateFormatSymbols.getInstance().getShortWeekdays();
        for (int i = 0; i < weekDays.length; i++) {
            printStr(weekDays[(localFirstWeek++) % weekDays.length]);
            printStr(i == weekDays.length - 1 ? "\n" : "\t");
        }
    }

    private static void printStr(String s) {
        System.out.printf("%3s", s);
    }

    private static void printDay(int d) {
        System.out.printf("%2d", d);
    }
}
