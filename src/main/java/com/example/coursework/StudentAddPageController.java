package com.example.coursework;

import com.example.coursework.DbClasses.Student;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Класс добавление студента
 */
public class StudentAddPageController implements Initializable {

    static final Logger logger = LogManager.getLogger(StudentAddPageController.class.getName());

    @FXML
    private TableView<Student> myTableView;

    @FXML
    private TableColumn<Student, Integer> ID;

    @FXML
    private TableColumn<Student, String> name;

    @FXML
    private TableColumn<Student, String> groupName;

    @FXML
    private TextField textFieldGroup;

    @FXML
    private TextField textFieldID;

    @FXML
    private TextField textFieldLastname;

    @FXML
    private TextField textFieldFirstname;

    @FXML
    private TextField textFieldSurname;

    /**
     * Добавление студента в таблицу
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void addButton() throws SQLException, ClassNotFoundException {
        if (textFieldLastname.getText().trim().isEmpty() | textFieldFirstname.getText().trim().isEmpty() | textFieldSurname.getText().trim().isEmpty() | textFieldGroup.getText().trim().isEmpty())
            return;
        StudentsTableController.insert(textFieldLastname.getText().trim(), textFieldFirstname.getText().trim(), textFieldSurname.getText().trim(), textFieldGroup.getText().trim());
        myTableView.getItems().clear();
        myTableView.setItems(StudentsTableController.getNameList());
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
     * Импорт студентов из файла
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void importButton() throws IOException, SQLException, ClassNotFoundException {

        String path;
        File folder;
        File[] listOfFiles;
        String fileStr;
        StringBuilder builder;
        StringBuilder group;
        BufferedReader reader;
        String line;
        String[] words;

        try {
            path = ApplicationStart.chosenImportPath;
            folder = new File(path);

            if (folder.isDirectory()) {
                listOfFiles = folder.listFiles();

                assert listOfFiles != null;
                for (File file : listOfFiles) {
                    fileStr = file.getName();
                    builder = new StringBuilder(fileStr);
                    builder.reverse();
                    if (builder.charAt(0) == 't' & builder.charAt(1) == 'x' & builder.charAt(2) == 't' & builder.charAt(3) == '.') {
                        builder.reverse();
                        group = new StringBuilder();
                        for (int i = 0; i < builder.length(); i++) {
                            if (builder.charAt(i) == '.') {
                                if (GroupsTableController.isGroupExistGroupName(group.toString())) {
                                    reader = new BufferedReader(new FileReader(path + "\\" + group.toString() + ".txt"));

                                    while ((line = reader.readLine()) != null) {
                                        words = line.split(" ");
                                        if (words.length < 3) {
                                            continue;
                                        }
                                        StudentsTableController.insert(words[0], words[1], words[2], group.toString());
                                    }
                                    logger.info("Был произведен импорт");
                                    myTableView.getItems().clear();
                                    myTableView.setItems(StudentsTableController.getNameList());

                                } else {
                                    break;
                                }
                            } else {
                                group.append(builder.charAt(i));
                            }
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        catch (NullPointerException e)
        {
            logger.error("Нет пути для импорта");
        }

    }

    /**
     * Удаление студента из таблицы
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void deleteButton() throws SQLException, ClassNotFoundException {
        if (textFieldID.getText().trim().isEmpty())
            return;
        StudentsTableController.delete(Integer.parseInt(textFieldID.getText().trim()));
        myTableView.getItems().clear();
        myTableView.setItems(StudentsTableController.getNameList());
    }

    /**
     * Работа со всеми tableView и отслеживание нажатия на них
     *
     * @param url URL
     * @param resourceBundle РесурсПакет
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ID.setCellValueFactory(new PropertyValueFactory<Student, Integer>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        groupName.setCellValueFactory(new PropertyValueFactory<Student, String>("groupName"));

        try {
            myTableView.setItems(StudentsTableController.getNameList());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        TableView.TableViewSelectionModel<Student> selectionModel = myTableView.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Student>() {

            @Override
            public void changed(ObservableValue<? extends Student> observableValue, Student student, Student newVal) {
                if (newVal != null) textFieldID.setText(Integer.toString(newVal.getID()));
            }

        });
    }

}
