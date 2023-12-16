package com.example.coursework;

import java.util.Date;

/**
 * Класс обработка дат
 */
public class DateAssembler extends Date {

    /**
     * Преобразование введенной даты из textFields
     *
     * @param day   введенный день
     * @param month введенный месяц
     * @param year  введенный год
     * @return возвращает дату в виде, пригодном для хранения
     * в базе дынных
     */
    public static int dateAssemblingToInt(String day, String month, String year) {
        if (Integer.parseInt(year) < 2000 |
                Integer.parseInt(year) > 2050 |
                Integer.parseInt(month) < 1 |
                Integer.parseInt(month) > 12 |
                Integer.parseInt(day) < 1 |
                Integer.parseInt(day) > 31)
            return 0;
        if (month.length() == 1)
            month = "0" + month;
        if (day.length() == 1)
            day = "0" + day;
        return Integer.parseInt(year + month + day);
    }

    /**
     * Преобразование даты типа "day.month.year"
     *
     * @param date полученная дата
     * @return возвращает дату в виде, пригодном для хранения
     * в базе дынных
     */
    public static int dateAssemblingToIntFromTable(String date) {
        String day = new StringBuilder().append(date.charAt(0)).append(date.charAt(1)).toString();
        String month = new StringBuilder().append(date.charAt(3)).append(date.charAt(4)).toString();
        String year = new StringBuilder().append(date.charAt(6)).append(date.charAt(7)).append(date.charAt(8)).append(date.charAt(9)).toString();
        return Integer.parseInt(year + month + day);
    }

    /**
     * Преобразование даты из базы данных
     *
     * @param date полученная дата
     * @return возвращает дату в удобном для
     * пользователя виде
     */
    public static String dateAssemblingToString(int date) {
        String newdate = Integer.toString(date);
        String day = new StringBuilder().append(newdate.charAt(6)).append(newdate.charAt(7)).toString();
        String month = new StringBuilder().append(newdate.charAt(4)).append(newdate.charAt(5)).toString();
        String year = new StringBuilder().append(newdate.charAt(0)).append(newdate.charAt(1)).append(newdate.charAt(2)).append(newdate.charAt(3)).toString();
        return day + "." + month + "." + year;
    }

    /**
     * Преобразование даты для Labels
     *
     * @param date полученная дата
     * @return возвращает массив из 3 элементов, каждый
     * из которых используется для dayLabel, monthLabel и yearLabel
     */
    public static String[] dateAssemblingToLabels(String date) {
        String day = new StringBuilder().append(date.charAt(0)).append(date.charAt(1)).toString();
        String month = new StringBuilder().append(date.charAt(3)).append(date.charAt(4)).toString();
        String year = new StringBuilder().append(date.charAt(6)).append(date.charAt(7)).append(date.charAt(8)).append(date.charAt(9)).toString();
        return new String[]{day, month, year};
    }

}
