package com.example.coursework.DbControllers;

import com.example.coursework.ApplicationStart;
import com.example.coursework.DbClasses.Mark;
import com.example.coursework.DbClasses.MyDate;
import com.example.coursework.DateAssembler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс для взаимодействия с таблицей marks
 */
public class MarksTableController extends JDBC {

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
     * Определение места в таблице для оценок студента
     *
     * @param teacherID ID выбранного преподавателя
     * @param subjectID ID выбранного предмета
     * @param groupID   ID выбранной группы
     * @param studentID ID выбранного студента
     * @param dateInt   выбранная дата
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void insert(int teacherID, int subjectID, int groupID, int studentID, int dateInt) throws SQLException, ClassNotFoundException {
        open();
        ArrayList<Integer> list = StudentsTableController.getIDListByGroup(GroupsTableController.groupInID(groupID));
        for (Integer integer : list) {
            String query = "SELECT * FROM marks WHERE teacherID = " + teacherID + " AND subjectID = " + subjectID + " AND groupID = " + groupID + " AND studentID = " + integer + " AND date = " + dateInt + ";";
            ResultSet rs = con.createStatement().executeQuery(query);
            if (!rs.next()) {
                String query2 = "INSERT INTO marks (teacherID, subjectID, groupID, studentID, date) " +
                        "VALUES ('" + teacherID + "', '" + subjectID + "', '" + groupID + "', '" + integer + "','" + dateInt + "');";
                con.createStatement().execute(query2);
            }
            rs.close();
        }
        close();
    }

    /**
     * Добавление одной оценки в таблицу
     *
     * @param teacherID ID выбранного преподавателя
     * @param subjectID ID выбранного предмета
     * @param groupID   ID выбранной группы
     * @param studentID ID выбранного студента
     * @param dateInt   выбранная дата
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void addMark(int teacherID, int subjectID, int groupID, int studentID, int dateInt) throws SQLException, ClassNotFoundException {
        open();
        String query = "SELECT mark FROM marks WHERE teacherID = " + teacherID + " AND subjectID = " + subjectID + " AND groupID = " + groupID + " AND studentID = " + studentID + " AND date = " + dateInt + ";";
        ResultSet rs = con.createStatement().executeQuery(query);
        String newMark;
        if (rs.getString("mark").isEmpty()) {
            newMark = "  " + ApplicationStart.chosenMark;
        } else {
            newMark = rs.getString("mark") + ", " + ApplicationStart.chosenMark;
        }
        rs.close();
        query = "SELECT type FROM marks WHERE teacherID = " + ApplicationStart.chosenTeacherID + " AND subjectID = " + ApplicationStart.chosenSubjectID + " AND groupID = " + ApplicationStart.chosenGroupID + " AND studentID = " + ApplicationStart.chosenStudentID + " AND date = " + ApplicationStart.chosenDateInt + ";";
        rs = con.createStatement().executeQuery(query);
        String newType;
        if (rs.getString("type").isEmpty()) {
            newType = "  " + ApplicationStart.chosenMarkType;
        } else {
            newType = rs.getString("type") + ", " + ApplicationStart.chosenMarkType;
        }
        rs.close();
        query = "UPDATE marks SET mark = '" + newMark + "', type = '" + newType + "' WHERE teacherID = " + ApplicationStart.chosenTeacherID + " AND subjectID = " + ApplicationStart.chosenSubjectID + " AND groupID = " + ApplicationStart.chosenGroupID + " AND studentID = " + ApplicationStart.chosenStudentID + " AND date = " + ApplicationStart.chosenDateInt + ";";
        con.createStatement().execute(query);
        close();
    }

    /**
     * Удаление одной оценки
     *
     * @param teacherID ID выбранного преподавателя
     * @param subjectID ID выбранного предмета
     * @param groupID   ID выбранной группы
     * @param studentID ID выбранного студента
     * @param dateInt   выбранная дата
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void deleteMark(int teacherID, int subjectID, int groupID, int studentID, int dateInt) throws SQLException, ClassNotFoundException {
        open();
        String query = "SELECT mark FROM marks WHERE teacherID = " + teacherID + " AND subjectID = " + subjectID + " AND groupID = " + groupID + " AND studentID = " + studentID + " AND date = " + dateInt + ";";
        ResultSet rs = con.createStatement().executeQuery(query);
        if (rs.getString("mark").isEmpty()) {
            rs.close();
            close();
            return;
        }
        StringBuilder sb = new StringBuilder(rs.getString("mark"));
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        String newMark = sb.toString();
        rs.close();
        query = "SELECT type FROM marks WHERE teacherID = " + teacherID + " AND subjectID = " + subjectID + " AND groupID = " + groupID + " AND studentID = " + studentID + " AND date = " + dateInt + ";";
        rs = con.createStatement().executeQuery(query);
        if (rs.getString("type").isEmpty()) {
            rs.close();
            close();
            return;
        }
        sb = new StringBuilder(rs.getString("type"));
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        String newType = sb.toString();
        rs.close();
        query = "UPDATE marks SET mark = '" + newMark + "', type = '" + newType + "' WHERE teacherID = " + teacherID + " AND subjectID = " + subjectID + " AND groupID = " + groupID + " AND studentID = " + studentID + " AND date = " + dateInt + ";";
        con.createStatement().execute(query);
        close();
    }

    /**
     * Добавление информации о посещении студента в таблицу
     *
     * @param attendanceType тип посещения занятия
     * @param teacherID      ID выбранного преподавателя
     * @param subjectID      ID выбранного предмета
     * @param groupID        ID выбранной группы
     * @param studentID      ID выбранного студента
     * @param dateInt        выбранная дата
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void addAttendance(String attendanceType, int teacherID, int subjectID, int groupID, int studentID, int dateInt) throws SQLException, ClassNotFoundException {
        open();
        String query = "UPDATE marks SET attendance = '" + attendanceType + "' WHERE teacherID = " + teacherID + " AND subjectID = " + subjectID + " AND groupID = " + groupID + " AND studentID = " + studentID + " AND date = " + dateInt + ";";
        con.createStatement().execute(query);
        close();
    }

    /**
     * Удаление места в таблице для оценок студента
     *
     * @param teacherID ID выбранного преподавателя
     * @param subjectID ID выбранного предмета
     * @param groupID   ID выбранной группы
     * @param dateInt   выбранная дата
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static void delete(int teacherID, int subjectID, int groupID, int dateInt) throws SQLException, ClassNotFoundException {
        open();
        String query = "DELETE FROM marks WHERE teacherID = " + teacherID + " AND subjectID = " + subjectID + " AND groupID = " + groupID + " AND date = " + dateInt + ";";
        con.createStatement().execute(query);
        close();
    }

    /**
     * Получение информации для работы tableView с оценками
     *
     * @param teacherID ID выбранного преподавателя
     * @param subjectID ID выбранного предмета
     * @param groupID   ID выбранной группы
     * @param dateInt   выбранная дата
     * @return возвращает лист с оценками
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static ObservableList<Mark> getNameList(int teacherID, int subjectID, int groupID, int dateInt) throws SQLException, ClassNotFoundException {
        open();
        ObservableList<Mark> list = FXCollections.observableArrayList();
        String query = "Select mark, type, attendance, studentID, date " +
                "FROM marks WHERE teacherID = " + teacherID + " AND subjectID = " + subjectID + " AND groupID = " + groupID + " AND date = " + dateInt + ";";
        ResultSet rs = con.createStatement().executeQuery(query);
        while (rs.next()) {
            int myStudentID = rs.getInt("studentID");
            String student = StudentsTableController.studentInID(myStudentID);
            String mark = rs.getString("mark");
            String type = rs.getString("type");
            String attendance = rs.getString("attendance");
            String date = DateAssembler.dateAssemblingToString(rs.getInt("date"));
            list.add(new Mark(myStudentID, student, mark, type, attendance, date));
        }
        rs.close();
        close();
        return list;
    }


    /**
     * Получение информации для работы tableView с датами оценок
     *
     * @param teacherID ID выбранного преподавателя
     * @param subjectID ID выбранного предмета
     * @param groupID   ID выбранной группы
     * @return возвращает лист с датами оценок
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static ObservableList<MyDate> getMyDateList(int teacherID, int subjectID, int groupID) throws SQLException, ClassNotFoundException {
        open();
        ObservableList<MyDate> list = FXCollections.observableArrayList();
        String query = "SELECT date " +
                "FROM marks WHERE teacherID = " + teacherID + " AND subjectID = " + subjectID + " AND groupID = " + groupID + " GROUP BY date ORDER BY date DESC;";
        ResultSet rs = con.createStatement().executeQuery(query);
        while (rs.next()) {
            list.add(new MyDate(DateAssembler.dateAssemblingToString(rs.getInt("date"))));
        }
        rs.close();
        close();
        return list;
    }

    /**
     * Получение информации для работы tableView с определенными датами
     *
     * @param teacherID   ID выбранного преподавателя
     * @param subjectID   ID выбранного предмета
     * @param groupID     ID выбранной группы
     * @param studentID   ID выбранного студента
     * @param dateFromInt выбранная дата от
     * @param dateToInt   выбранная дата до
     * @return возвращает лист с определенными датами оценок
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static ObservableList<Mark> getMyDateList2(int teacherID, int subjectID, int groupID, int studentID, int dateFromInt, int dateToInt) throws SQLException, ClassNotFoundException {
        open();
        ObservableList<Mark> list = FXCollections.observableArrayList();
        String query = "SELECT ID, mark, type, attendance, studentID, date " +
                "FROM marks WHERE teacherID = " + teacherID + " AND subjectID = " + subjectID + " AND groupID = " + groupID + " AND studentID = " + studentID + " AND date >= " + dateFromInt + " AND date <= " + dateToInt + " GROUP BY date ORDER BY date DESC;";
        ResultSet rs = con.createStatement().executeQuery(query);
        while (rs.next()) {
            int MyStudentID = rs.getInt("studentID");
            String student = StudentsTableController.studentInID(MyStudentID);
            String mark = rs.getString("mark");
            String type = rs.getString("type");
            String attendance = rs.getString("attendance");
            String date = DateAssembler.dateAssemblingToString(rs.getInt("date"));
            list.add(new Mark(studentID, student, mark, type, attendance, date));
        }
        rs.close();
        close();
        return list;
    }

    /**
     * Получение информации с оценками для экспорта в файл
     *
     * @param teacherID   ID выбранного преподавателя
     * @param subjectID   ID выбранного предмета
     * @param groupID     ID выбранной группы
     * @param studentID   ID выбранного студента
     * @param dateFromInt выбранная дата от
     * @param dateToInt   выбранная дата до
     * @return возвращает лист с оценками студента
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static ArrayList<String> getMarksForImport(int teacherID, int subjectID, int groupID, int studentID, int dateFromInt, int dateToInt) throws SQLException, ClassNotFoundException {
        open();
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT ID, mark, type, attendance, studentID, date " +
                "FROM marks WHERE teacherID = " + teacherID + " AND subjectID = " + subjectID + " AND groupID = " + groupID + " AND studentID = " + studentID + " AND date >= " + dateFromInt + " AND date <= " + dateToInt + " GROUP BY date ORDER BY date DESC;";
        ResultSet rs = con.createStatement().executeQuery(query);
        while (rs.next()) {
            String mark = rs.getString("mark");
            String type = rs.getString("type");
            String attendance = rs.getString("attendance");
            String date = DateAssembler.dateAssemblingToString(rs.getInt("date"));
            list.add(date);
            list.add(mark);
            list.add(type);
            list.add(attendance);
        }
        rs.close();
        close();
        return list;
    }

    /**
     * Получение статистики студента
     *
     * @param teacherID   ID выбранного преподавателя
     * @param subjectID   ID выбранного предмета
     * @param groupID     ID выбранной группы
     * @param studentID   ID выбранного студента
     * @param dateFromInt выбранная дата от
     * @param dateToInt   выбранная дата до
     * @return возвращает двумерный массив с информацией об оценках студента
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public static int[][] getMarksInfo(int teacherID, int subjectID, int groupID, int studentID, int dateFromInt, int dateToInt) throws SQLException, ClassNotFoundException {
        open();
        String query = "SELECT mark, type, attendance FROM marks WHERE teacherID = " + teacherID + " AND subjectID = " + subjectID + " AND groupID = " + groupID + " AND studentID = " + studentID + " AND date >= " + dateFromInt + " AND date <= " + dateToInt + ";";
        ResultSet rs = con.createStatement().executeQuery(query);
        int[][] marksInfo = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                marksInfo[i][j] = 0;
            }
        }

        while (rs.next()) {

            String marks = rs.getString("mark");
            String marksType = rs.getString("type");
            String attendances = rs.getString("attendance");
            if (marks.isEmpty()) {
                marks = "0";
            }
            String[] marksArray = marks.replace(" ", "").split(",");
            String[] marksTypeArray = marksType.replace(" ", "").split(",");


            if (!marks.equals("0")) {
                for (int i = 0; i < marksArray.length; i++) {
                    if (Integer.parseInt(marksArray[i]) == 5 && marksTypeArray[i].equals("ПР"))
                        marksInfo[0][0] += 1;
                    if (Integer.parseInt(marksArray[i]) == 4 && marksTypeArray[i].equals("ПР"))
                        marksInfo[0][1] += 1;
                    if (Integer.parseInt(marksArray[i]) == 3 && marksTypeArray[i].equals("ПР"))
                        marksInfo[0][2] += 1;
                    if (Integer.parseInt(marksArray[i]) == 2 && marksTypeArray[i].equals("ПР"))
                        marksInfo[0][3] += 1;
                    if (Integer.parseInt(marksArray[i]) == 5 && marksTypeArray[i].equals("ЛБ"))
                        marksInfo[1][0] += 1;
                    if (Integer.parseInt(marksArray[i]) == 4 && marksTypeArray[i].equals("ЛБ"))
                        marksInfo[1][1] += 1;
                    if (Integer.parseInt(marksArray[i]) == 3 && marksTypeArray[i].equals("ЛБ"))
                        marksInfo[1][2] += 1;
                    if (Integer.parseInt(marksArray[i]) == 2 && marksTypeArray[i].equals("ЛБ"))
                        marksInfo[1][3] += 1;
                    if (Integer.parseInt(marksArray[i]) == 5 && marksTypeArray[i].equals("ДЗ"))
                        marksInfo[2][0] += 1;
                    if (Integer.parseInt(marksArray[i]) == 4 && marksTypeArray[i].equals("ДЗ"))
                        marksInfo[2][1] += 1;
                    if (Integer.parseInt(marksArray[i]) == 3 && marksTypeArray[i].equals("ДЗ"))
                        marksInfo[2][2] += 1;
                    if (Integer.parseInt(marksArray[i]) == 2 && marksTypeArray[i].equals("ДЗ"))
                        marksInfo[2][3] += 1;
                }
            }

            if (attendances.equals("П")) {
                marksInfo[3][0]++;
            } else {
                marksInfo[3][1]++;
            }

        }
        rs.close();
        close();
        return marksInfo;
    }

}
