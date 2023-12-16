package com.example.coursework.DbClasses;

/**
 * Класс студент
 */
public class Student {

    private int ID;
    private String name;
    private String groupName;

    /**
     * Конструктор - создание студента
     *
     * @param ID        уникальный идентификатор студента
     * @param name      полное имя студента
     * @param groupName группа студента
     */
    public Student(int ID, String name, String groupName) {
        this.ID = ID;
        this.name = name;
        this.groupName = groupName;
    }

    /**
     * Получение поля ID
     *
     * @return возвращает уникальный идентификатор студента
     */
    public int getID() {
        return ID;
    }

    /**
     * Определение поля ID
     *
     * @param ID уникальный идентификатор студента
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Получение поля name
     *
     * @return возвращает полное имя студента
     */
    public String getName() {
        return name;
    }

    /**
     * Определение поля name
     *
     * @param name полное имя студента
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получение поля groupName
     *
     * @return возвращает группу студента
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Определение поля groupName
     *
     * @param groupName группа студента
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
