package com.example.coursework;

import com.example.coursework.DbClasses.MyGroup;
import com.example.coursework.DbControllers.GroupsTableController;
import com.example.coursework.DbControllers.StudentsTableController;
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
 * Класс добавление группы
 */
public class GroupAddPageController implements Initializable {

    @FXML
    private TableView<MyGroup> myTableView;

    @FXML
    private TableColumn<MyGroup, Integer> ID;

    @FXML
    private TableColumn<MyGroup, String> name;

    @FXML
    private TextField textFieldID;

    @FXML
    private TextField textFieldName;

    /**
     * Добавление группы в таблицу
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void addButton() throws SQLException, ClassNotFoundException {
        if (textFieldName.getText().trim().isEmpty())
            return;
        GroupsTableController.insert(textFieldName.getText().trim());
        myTableView.getItems().clear();
        myTableView.setItems(GroupsTableController.getNameList());
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
     * Удаление группы из таблицы
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void deleteButton() throws SQLException, ClassNotFoundException {
        if (textFieldID.getText().trim().isEmpty())
            return;
        StudentsTableController.deleteByGroup(GroupsTableController.groupInID(Integer.parseInt(textFieldID.getText().trim())));
        GroupsTableController.delete(Integer.parseInt(textFieldID.getText().trim()));
        myTableView.getItems().clear();
        myTableView.setItems(GroupsTableController.getNameList());
    }

    /**
     * Работа со всеми tableView и отслеживание нажатия на них
     *
     * @param url URL
     * @param resourceBundle РесурсПакет
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ID.setCellValueFactory(new PropertyValueFactory<MyGroup, Integer>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<MyGroup, String>("name"));

        try {
            myTableView.setItems(GroupsTableController.getNameList());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        TableView.TableViewSelectionModel<MyGroup> selectionModel = myTableView.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<MyGroup>() {

            @Override
            public void changed(ObservableValue<? extends MyGroup> observableValue, MyGroup myGroup, MyGroup newVal) {
                if (newVal != null) textFieldID.setText(Integer.toString(newVal.getID()));
            }
        });
    }
}