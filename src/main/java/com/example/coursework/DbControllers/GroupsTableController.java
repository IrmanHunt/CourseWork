package com.example.coursework.DbControllers;

import com.example.coursework.DbClasses.MyGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для взаимодействия с таблицей groups
 */
public class GroupsTableController extends JDBC {

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
     * Внесение группы в таблицу
     *
     * @param name название группы
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void insert(String name) throws SQLException, ClassNotFoundException {
        open();
        String query = "INSERT INTO groups (name) " +
                "VALUES ('" + name + "');";
        con.createStatement().execute(query);
        close();
    }

    /**
     * Удаление группы из таблицы по ID
     *
     * @param ID ID удаляемой таблицы
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void delete(Integer ID) throws SQLException, ClassNotFoundException {
        open();
        String query = "DELETE FROM groups WHERE ID = " + ID + ";";
        con.createStatement().execute(query);
        close();
    }

    /**
     * Получение групп для tableView
     *
     * @return лист со всеми группами
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static ObservableList<MyGroup> getNameList() throws SQLException, ClassNotFoundException {
        open();
        ObservableList<MyGroup> list = FXCollections.observableArrayList();
        String query = "Select ID, name FROM groups ORDER BY name;";
        ResultSet rs = con.createStatement().executeQuery(query);
        while (rs.next()) {
            int ID = rs.getInt("ID");
            String name = rs.getString("name");
            list.add(new MyGroup(ID, name));
        }
        rs.close();
        close();
        return list;
    }

    /**
     * Проверка наличия группы в таблице по названию
     *
     * @param groupName название группы
     * @return true, если группа существует, иначе false
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static Boolean isGroupExistGroupName(String groupName) throws SQLException, ClassNotFoundException {
        open();
        String query = "SELECT name FROM groups WHERE name = '" + groupName + "';";
        ResultSet rs = con.createStatement().executeQuery(query);
        boolean flag = rs.next();
        rs.close();
        close();
        return flag;
    }

    /**
     * Получение названия группы по ID
     *
     * @param ID ID группы
     * @return название группы
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static String groupInID(int ID) throws SQLException, ClassNotFoundException {
        open();
        String query = "SELECT name FROM groups WHERE ID = '" + ID + "';";
        ResultSet rs = con.createStatement().executeQuery(query);
        String name = rs.getString("name");
        rs.close();
        close();
        return name;
    }

}
