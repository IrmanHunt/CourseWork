package com.example.coursework.DbControllers;

import com.example.coursework.DbClasses.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс для взаимодействия с таблицей students
 */
public class StudentsTableController extends JDBC {

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
     * Добавление студента в таблицу
     *
     * @param lastname  фамилия студента
     * @param firstname имя студента
     * @param surname   отчество студента
     * @param groupName группа студента
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void insert(String lastname, String firstname, String surname, String groupName) throws SQLException, ClassNotFoundException {
        if (GroupsTableController.isGroupExistGroupName(groupName)) {
            open();
            String query = "INSERT INTO students (lastname, firstname, surname, groupName) " +
                    "VALUES ('" + lastname + "', '" + firstname + "', '" + surname + "', '" + groupName + "');";
            con.createStatement().execute(query);
            close();
        }
    }

    /**
     * Удаление студента из таблицы
     *
     * @param ID уникальный идентификатор студента
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void delete(Integer ID) throws SQLException, ClassNotFoundException {
        open();
        String query = "DELETE FROM students WHERE ID = " + ID + ";";
        con.createStatement().execute(query);
        query = "DELETE FROM marks WHERE studentID = " + ID + ";";
        con.createStatement().execute(query);
        close();
    }

    /**
     * Получение информации о всех студентах для tableView
     *
     * @return возвращает лист со всеми студентами
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static ObservableList<Student> getNameList() throws SQLException, ClassNotFoundException {
        open();
        ObservableList<Student> list = FXCollections.observableArrayList();
        String query = "Select ID, lastname, firstname, surname, groupName " +
                "FROM students ORDER BY lastname;";
        ResultSet rs = con.createStatement().executeQuery(query);
        while (rs.next()) {
            int ID = rs.getInt("ID");
            String lastName = rs.getString("lastname");
            String firstName = rs.getString("firstname");
            String surname = rs.getString("surname");
            String name = lastName + " " + firstName.charAt(0) + ". " + surname.charAt(0) + ".";
            String groupName = rs.getString("groupName");
            list.add(new Student(ID, name, groupName));
        }
        rs.close();
        close();
        return list;
    }


    /**
     * Получение информации о всех студентах для tableView по группе студента
     *
     * @param groupName выбранная группа
     * @return возвращает лист со всеми студентами определенной группы
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static ObservableList<Student> getNameListByGroup(String groupName) throws SQLException, ClassNotFoundException {
        open();
        ObservableList<Student> list = FXCollections.observableArrayList();
        String query = "Select ID, lastname, firstname, surname, groupName FROM students WHERE groupName = '" + groupName + "';";
        ResultSet rs = con.createStatement().executeQuery(query);
        while (rs.next()) {
            int ID = rs.getInt("ID");
            String lastName = rs.getString("lastname");
            String firstName = rs.getString("firstname");
            String surname = rs.getString("surname");
            String name = lastName + " " + firstName.charAt(0) + ". " + surname.charAt(0) + ".";
            String myGroupName = rs.getString("groupName");
            list.add(new Student(ID, name, myGroupName));
        }
        rs.close();
        close();
        return list;
    }

    /**
     * Получение ID студентов по группе
     *
     * @param groupName выбранная группа
     * @return возвращает лист с ID студентов по группе
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static ArrayList<Integer> getIDListByGroup(String groupName) throws SQLException, ClassNotFoundException {
        open();
        ArrayList<Integer> list = new ArrayList<>();
        String query = "Select ID " +
                "FROM students WHERE groupName = '" + groupName + "' ORDER BY lastname;";
        ResultSet rs = con.createStatement().executeQuery(query);
        while (rs.next()) {
            list.add(rs.getInt("ID"));
        }
        rs.close();
        close();
        return list;
    }

    /**
     * Получение полного имени студента по его ID
     *
     * @param ID уникальный идентификатор студента
     * @return возвращает полное имя студента
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static String studentInID(int ID) throws SQLException, ClassNotFoundException {
        open();
        String query = "SELECT lastname, firstname, surname FROM students WHERE ID = '" + ID + "'";
        ResultSet rs = con.createStatement().executeQuery(query);
        String lastName = rs.getString("lastname");
        String firstName = rs.getString("firstname");
        String surname = rs.getString("surname");
        String name = lastName + " " + firstName.charAt(0) + ". " + surname.charAt(0) + ".";
        rs.close();
        close();
        return name;
    }

    /**
     * Удаление студентов из таблицы по группе
     *
     * @param groupName название группы
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void deleteByGroup(String groupName) throws SQLException, ClassNotFoundException {
        open();
        String query = "DELETE FROM students WHERE groupName = '" + groupName + "';";
        con.createStatement().execute(query);
        close();
    }

}
