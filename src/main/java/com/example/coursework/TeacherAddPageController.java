package com.example.coursework;

import com.example.coursework.DbClasses.Teacher;
import com.example.coursework.DbControllers.TeachersTableController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Класс добавление преподавателя
 */
public class TeacherAddPageController implements Initializable {

    @FXML
    private TableView<Teacher> myTableView;

    @FXML
    private TableColumn<Teacher, Integer> ID;

    @FXML
    private TableColumn<Teacher, String> name;

    @FXML
    private TextField textFieldID;

    @FXML
    private TextField textFieldLastname;

    @FXML
    private TextField textFieldFirstname;

    @FXML
    private TextField textFieldSurname;

    /**
     * Добавление преподавателя в таблицу
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void addButton() throws SQLException, ClassNotFoundException {
        if (textFieldLastname.getText().trim().isEmpty() | textFieldFirstname.getText().trim().isEmpty() | textFieldSurname.getText().trim().isEmpty())
            return;
        TeachersTableController.insert(textFieldLastname.getText().trim(), textFieldFirstname.getText().trim(), textFieldSurname.getText().trim());
        myTableView.getItems().clear();
        myTableView.setItems(TeachersTableController.getNameList());
    }

    /**
     * Возвращение на главную страницу
     *
     * @param event нажатие на кнопку
     * @throws IOException исключение
     */
    @FXML
    public void backButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Удаление преподавателя из таблицы
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void deleteButton() throws SQLException, ClassNotFoundException {
        if (textFieldID.getText().trim().isEmpty())
            return;
        TeachersTableController.delete(Integer.parseInt(textFieldID.getText().trim()));
        myTableView.getItems().clear();
        myTableView.setItems(TeachersTableController.getNameList());
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
     * Работа со всеми tableView и отслеживание нажатия на них
     *
     * @param url URL
     * @param resourceBundle РесурсПакет
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ID.setCellValueFactory(new PropertyValueFactory<Teacher, Integer>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<Teacher, String>("name"));

        try {
            myTableView.setItems(TeachersTableController.getNameList());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        TableView.TableViewSelectionModel<Teacher> selectionModel = myTableView.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Teacher>() {

            @Override
            public void changed(ObservableValue<? extends Teacher> observableValue, Teacher teacher, Teacher newVal) {
                if (newVal != null) textFieldID.setText(Integer.toString(newVal.getID()));
            }
        });
    }
}
