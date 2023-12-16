package com.example.coursework.DbControllers;

import com.example.coursework.DbClasses.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для взаимодействия с таблицей subjects
 */
public class SubjectsTableController extends JDBC {

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
     * Добавление предмета в таблицу
     *
     * @param name название предмета
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void insert(String name) throws SQLException, ClassNotFoundException {
        open();
        String query = "INSERT INTO subjects (name) " +
                "VALUES ('" + name + "');";
        con.createStatement().execute(query);
        close();
    }

    /**
     * Удаление предмета из таблицы по ID
     *
     * @param ID уникальный идентификатор предмета
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void delete(Integer ID) throws SQLException, ClassNotFoundException {
        open();
        String query = "DELETE FROM subjects WHERE ID = " + ID + ";";
        con.createStatement().execute(query);
        close();
    }

    /**
     * Получение информации о всех предметах для tableView
     *
     * @return возвращает лист со всеми студентами предметами
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static ObservableList<Subject> getNameList() throws SQLException, ClassNotFoundException {
        open();
        ObservableList<Subject> list = FXCollections.observableArrayList();
        String query = "Select ID, name " +
                "FROM subjects ORDER BY name;";
        ResultSet rs = con.createStatement().executeQuery(query);
        while (rs.next()) {
            int ID = rs.getInt("ID");
            String name = rs.getString("name");
            list.add(new Subject(ID, name));
        }
        rs.close();
        close();
        return list;
    }

    /**
     * Получение название предмета по его ID
     *
     * @param ID уникальный идентификатор предмета
     * @return возвращает название предмета
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static String subjectInID(int ID) throws SQLException, ClassNotFoundException {
        open();
        String query = "SELECT name FROM subjects WHERE ID = '" + ID + "'";
        ResultSet rs = con.createStatement().executeQuery(query);
        String name = rs.getString("name");
        close();
        return name;
    }

}
