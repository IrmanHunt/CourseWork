package com.example.coursework.DbClasses;

/**
 * Класс предмет
 */
public class Subject {

    private int ID;
    private String name;

    /**
     * Конструктор - создание предмета
     *
     * @param ID   уникальный идентификатор предмета
     * @param name название предмета
     */
    public Subject(int ID, String name) {
        this.ID = ID;
        this.name = name;

    }

    /**
     * Получения поля ID
     *
     * @return возвращает уникальный идентификатор предмета
     */
    public int getID() {
        return ID;
    }

    /**
     * Определение поля ID
     *
     * @param ID уникальный идентификатор предмета
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Получение поля name
     *
     * @return возвращает название предмета
     */
    public String getName() {
        return name;
    }

    /**
     * Определение поля name
     *
     * @param name название группы
     */
    public void setName(String name) {
        this.name = name;
    }
}
