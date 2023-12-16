package com.example.coursework.DbClasses;

/**
 * Класс группа
 */
public class MyGroup {

    private int ID;
    private String name;

    /**
     * Конструктор - создание группы
     *
     * @param ID уникальный идентификатор группы
     * @param name название группы
     */
    public MyGroup(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    /**
     * Получение поля ID
     *
     * @return возвращает уникальный идентификатор группы
     */
    public int getID() {
        return ID;
    }

    /**
     * Определение поля ID
     *
     * @param ID уникальный идентификатор группы
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Получение поля name
     *
     * @return возвращает название группы
     */
    public String getName() {
        return name;
    }

    /**
     * Определяет поле name
     *
     * @param name название группы
     */
    public void setName(String name) {
        this.name = name;
    }
}
