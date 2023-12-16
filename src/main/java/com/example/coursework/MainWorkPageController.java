package com.example.coursework;

import com.example.coursework.DbControllers.GroupsTableController;
import com.example.coursework.DbControllers.SubjectsTableController;
import com.example.coursework.DbControllers.TeachersTableController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Класс главная рабочая страница
 */
public class MainWorkPageController implements Initializable {

    @FXML
    private Label groupLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label teacherLabel;

    /**
     * Возвращение на страницу выбора
     *
     * @param event нажатие на кнопку
     * @throws IOException исключение
     */
    @FXML
    public void backButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ChoosePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Переход на страницу дневник студента
     *
     * @param event нажатие на кнопку
     * @throws IOException исключение
     */
    @FXML
    public void diaryButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GroupDiaryPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Возвращение на главную страницу
     *
     * @param event нажатие на кнопку
     * @throws IOException исключение
     */
    @FXML
    public void homeButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Переход на страницу статистики студента
     *
     * @param event нажатие на кнопку
     * @throws IOException исключение
     */
    @FXML
    public void statisticsButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StudentStatisticsPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Работа с Labels страницы
     *
     * @param url URL
     * @param resourceBundle РесурсПакет
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            teacherLabel.setText(TeachersTableController.teacherInID(ApplicationStart.chosenTeacherID));
            subjectLabel.setText(SubjectsTableController.subjectInID(ApplicationStart.chosenSubjectID));
            groupLabel.setText(GroupsTableController.groupInID(ApplicationStart.chosenGroupID));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
