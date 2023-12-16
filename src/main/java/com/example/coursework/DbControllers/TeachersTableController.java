package com.example.coursework.DbControllers;

import com.example.coursework.DbClasses.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для взаимодействия с таблицей teachers
 */
public class TeachersTableController extends JDBC {

    private static Connection con = null;

    /**
     * Открытие базы данных
     *
     * @throws ClassNotFoundException не найден файл
     * @throws SQLException           ошибка в SQL запросе
     */
    private static void open() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:database.db");
    }

    /**
     * Закрытие базы данных
     *
     * @throws SQLException ошибка в SQL запросе
     */
    private static void close() throws SQLException {
        con.close();
    }

    /**
     * Добавление преподавателя в таблицу
     *
     * @param lastname фамилия преподавателя
     * @param firstname имя преподавателя
     * @param surname отчество преподавателя
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void insert(String lastname, String firstname, String surname) throws SQLException, ClassNotFoundException {
        open();
        String query = "INSERT INTO teachers (lastname, firstname, surname) " +
                "VALUES ('" + lastname + "', '" + firstname + "', '" + surname + "');";
        con.createStatement().execute(query);
        close();
    }

    /**
     * Удаление преподавателя из таблицы
     *
     * @param ID уникальный идентификатор преподавателя
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void delete(Integer ID) throws SQLException, ClassNotFoundException {
        open();
        String query = "DELETE FROM teachers WHERE ID = " + ID + ";";
        con.createStatement().execute(query);
        close();
    }

    /**
     * Получение информации о всех преподавателях для tableView
     *
     * @return возвращает лист со всеми преподавателями
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static ObservableList<Teacher> getNameList() throws SQLException, ClassNotFoundException {
        open();
        ObservableList<Teacher> list = FXCollections.observableArrayList();
        String query = "Select ID, lastname, firstname, surname " +
                "FROM teachers ORDER BY lastname;";
        ResultSet rs = con.createStatement().executeQuery(query);
        while (rs.next()) {
            int ID = rs.getInt("ID");
            String lastName = rs.getString("lastname");
            String firstName = rs.getString("firstname");
            String surname = rs.getString("surname");
            String name = lastName + " " + firstName.charAt(0) + ". " + surname.charAt(0) + ".";
            list.add(new Teacher(ID, name));
        }
        rs.close();
        close();
        return list;
    }

    /**
     * Получение полного имени преподавателя по его ID
     *
     * @param ID уникальный идентификатор преподавателя
     * @return возвращает полное имя преподавателя
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static String teacherInID(int ID) throws SQLException, ClassNotFoundException {
        open();
        String query = "SELECT lastname, firstname, surname FROM teachers WHERE ID = '" + ID + "'";
        ResultSet rs = con.createStatement().executeQuery(query);
        String lastName = rs.getString("lastname");
        String firstName = rs.getString("firstname");
        String surname = rs.getString("surname");
        String name = lastName + " " + firstName.charAt(0) + ". " + surname.charAt(0) + ".";
        close();
        return name;
    }

}