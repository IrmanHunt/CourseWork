package com.example.coursework;

import com.example.coursework.DbClasses.Subject;
import com.example.coursework.DbControllers.SubjectsTableController;
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
 * Класс добавление предмета
 */
public class SubjectAddPageController implements Initializable {

    @FXML
    private TableView<Subject> myTableView;

    @FXML
    private TableColumn<Subject, Integer> ID;

    @FXML
    private TableColumn<Subject, String> name;

    @FXML
    private TextField textFieldID;

    @FXML
    private TextField textFieldName;

    /**
     * Добавление предмета в таблицу
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void addButton() throws SQLException, ClassNotFoundException {
        if (textFieldName.getText().trim().isEmpty())
            return;
        SubjectsTableController.insert(textFieldName.getText().trim());
        myTableView.getItems().clear();
        myTableView.setItems(SubjectsTableController.getNameList());
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
     * Удаление предмета из таблицы
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void deleteButton() throws SQLException, ClassNotFoundException {
        if (textFieldID.getText().trim().isEmpty())
            return;
        SubjectsTableController.delete(Integer.parseInt(textFieldID.getText().trim()));
        myTableView.getItems().clear();
        myTableView.setItems(SubjectsTableController.getNameList());
    }

    /**
     * Работа со всеми tableView и отслеживание нажатия на них
     *
     * @param url URL
     * @param resourceBundle РесурсПакет
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ID.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<Subject, String>("name"));

        try {
            myTableView.setItems(SubjectsTableController.getNameList());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        TableView.TableViewSelectionModel<Subject> selectionModel = myTableView.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Subject>() {

            @Override
            public void changed(ObservableValue<? extends Subject> observableValue, Subject subject, Subject newVal) {
                if (newVal != null) textFieldID.setText(Integer.toString(newVal.getID()));
            }
        });
    }
}
