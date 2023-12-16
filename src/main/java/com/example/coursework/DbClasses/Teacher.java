package com.example.coursework.DbClasses;

/**
 * Класс преподаватель
 */
public class Teacher {

    private int ID;
    private String name;

    /**
     * Конструктор - создание преподавателя
     *
     * @param ID уникальный идентификатор преподавателя
     * @param name полное имя преподавателя
     */
    public Teacher(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    /**
     * Получение поля ID
     *
     * @return возвращает уникальный идентификатор преподавателя
     */
    public int getID() {
        return ID;
    }

    /**
     * Определение поля ID
     *
     * @param ID уникальный идентификатор преподавателя
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Получение поля name
     *
     * @return возвращает полное имя преподавателя
     */
    public String getName() {
        return name;
    }

    /**
     * Определение поля name
     *
     * @param name полное имя преподавателя
     */
    public void setName(String name) {
        this.name = name;
    }
}
