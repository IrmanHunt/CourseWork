package com.example.coursework.DbClasses;

/**
 * Класс дата
 */
public class MyDate {

    private String date;

    /**
     * Конструктор - создание даты
     *
     * @param date дата
     */
    public MyDate(String date) {
        this.date = date;
    }

    /**
     * Получение поля date
     *
     * @return возвращает дату
     */
    public String getDate() {
        return date;
    }

    /**
     * Определение поля date
     *
     * @param date дата
     */
    public void setDate(String date) {
        this.date = date;
    }
}
