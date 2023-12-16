package com.example.coursework;

import com.example.coursework.DbClasses.MyGroup;
import com.example.coursework.DbClasses.Subject;
import com.example.coursework.DbClasses.Teacher;
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
 * Класс выбор преподавателя, предмета и группы
 */
public class ChoosePageController implements Initializable {


    @FXML
    private TableView<Teacher> myTableView;

    @FXML
    private TableView<Subject> myTableView1;

    @FXML
    private TableView<MyGroup> myTableView2;

    @FXML
    private TableColumn<Teacher, Integer> ID;

    @FXML
    private TableColumn<Subject, Integer> ID1;

    @FXML
    private TableColumn<MyGroup, Integer> ID2;

    @FXML
    private TableColumn<Teacher, String> name;

    @FXML
    private TableColumn<Subject, String> name1;

    @FXML
    private TableColumn<MyGroup, String> name2;

    @FXML
    private TextField textFieldID;

    @FXML
    private TextField textFieldID1;

    @FXML
    private TextField textFieldID2;

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
     * Переход на главную рабочую страницу
     *
     * @param event нажатие на кнопку
     * @throws IOException исключение
     */
    @FXML
    public void chooseButton(ActionEvent event) throws IOException {
        if (textFieldID.getText().trim().isEmpty() | textFieldID1.getText().trim().isEmpty() | textFieldID2.getText().trim().isEmpty())
            return;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWorkPage.fxml")));
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

        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        ID1.setCellValueFactory(new PropertyValueFactory<>("ID"));
        name1.setCellValueFactory(new PropertyValueFactory<>("name"));

        ID2.setCellValueFactory(new PropertyValueFactory<>("ID"));
        name2.setCellValueFactory(new PropertyValueFactory<>("name"));

        try {
            myTableView.setItems(TeachersTableController.getNameList());
            myTableView1.setItems(SubjectsTableController.getNameList());
            myTableView2.setItems(GroupsTableController.getNameList());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        TableView.TableViewSelectionModel<Teacher> selectionModel = myTableView.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((observableValue, teacher, newVal) -> {
            if (newVal != null) {
                textFieldID.setText(newVal.getName());
                ApplicationStart.chosenTeacherID = newVal.getID();
            }
        });

        TableView.TableViewSelectionModel<Subject> selectionModel1 = myTableView1.getSelectionModel();
        selectionModel1.selectedItemProperty().addListener((observableValue, subject, newVal) -> {
            if (newVal != null) {
                textFieldID1.setText(newVal.getName());
                ApplicationStart.chosenSubjectID = newVal.getID();
            }
        });

        TableView.TableViewSelectionModel<MyGroup> selectionModel2 = myTableView2.getSelectionModel();
        selectionModel2.selectedItemProperty().addListener((observableValue, myGroup, newVal) -> {
            if (newVal != null) {
                textFieldID2.setText(newVal.getName());
                ApplicationStart.chosenGroupID = newVal.getID();
            }
        });
    }
}
