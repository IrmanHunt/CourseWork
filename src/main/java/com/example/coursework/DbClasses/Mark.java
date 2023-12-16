package com.example.coursework.DbClasses;

/**
 * Класс оценка
 */
public class Mark {

    private int ID;
    private String student;
    private String mark;
    private String type;
    private String attendance;
    private String date;

    /**
     * Конструктор - создание оценки
     *
     * @param ID         уникальный идентификатор оценки
     * @param student    студент оценки
     * @param mark       значение оценки
     * @param type       тип оценки
     * @param attendance посещение занятия, во время получения оценки
     * @param date       дата получения оценки
     */
    public Mark(int ID, String student, String mark, String type, String attendance, String date) {
        this.ID = ID;
        this.student = student;
        this.mark = mark;
        this.type = type;
        this.attendance = attendance;
        this.date = date;
    }

    /**
     * Получение поля ID
     *
     * @return возвращает уникальный идентификатор оценки
     */
    public int getID() {
        return ID;
    }

    /**
     * Определение поля ID
     *
     * @param ID уникальный идентификатор оценки
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Получение поля student
     *
     * @return возвращает студента оценки
     */
    public String getStudent() {
        return student;
    }

    /**
     * Определение поля student
     *
     * @param student студент оценки
     */
    public void setStudent(String student) {
        this.student = student;
    }

    /**
     * Получение поля mark
     *
     * @return возвращает значение оценки
     */
    public String getMark() {
        return mark;
    }

    /**
     * Определение поля mark
     *
     * @param mark значение оценки
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * Получение поля type
     *
     * @return возвращает тип оценки
     */
    public String getType() {
        return type;
    }

    /**
     * Определение поля type
     *
     * @param type тип оценки
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Получение поля attendance
     *
     * @return возвращает посещение занятия, во время получения оценки
     */
    public String getAttendance() {
        return attendance;
    }

    /**
     * Определение поля attendance
     *
     * @param attendance посещение занятия, во время получения оценки
     */
    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    /**
     * Получение поля date
     *
     * @return возвращает дату получения оценки
     */
    public String getDate() {
        return date;
    }

    /**
     * Определение поля date
     *
     * @param date дата посещения оценки
     */
    public void setDate(String date) {
        this.date = date;
    }
}
