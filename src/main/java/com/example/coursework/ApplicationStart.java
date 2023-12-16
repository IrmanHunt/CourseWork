package com.example.coursework;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Класс запуск анимации и приложения
 */
public class ApplicationStart extends Application {


    public static int chosenTeacherID;
    public static int chosenSubjectID;
    public static int chosenGroupID;
    public static int chosenStudentID;
    public static int chosenDateInt;
    public static String chosenDateStr;
    public static int chosenMark;
    public static String chosenMarkType = "ПР";
    public static String chosenAttendanceType = "П";
    public static int getChosenDateFromInt;
    public static String getChosenDateFromStr;
    public static int getChosenDateToInt;
    public static String getChosenDateToStr;
    public static String chosenExportPath;
    public static String chosenImportPath;

    /**
     * Cтарт приложения
     *
     * @param stage сцена для работы
     */
    @Override
    public void start(Stage stage) {
        AnimationPageController.animation();
    }

    /**
     * Запуск приложения
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        launch();
    }
}