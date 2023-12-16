package com.example.coursework;

import com.example.coursework.DbClasses.Mark;
import com.example.coursework.DbClasses.Student;
import com.example.coursework.DbClasses.MyDate;
import com.example.coursework.DbControllers.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Класс статистика студента
 */
public class StudentStatisticsPageController implements Initializable {

    static final Logger logger = LogManager.getLogger(StudentStatisticsPageController.class.getName());

    @FXML
    private TableView<Mark> myTableView3;

    @FXML
    private TableColumn<MyDate, String> date3;

    @FXML
    private TableColumn<Mark, String> marks3;

    @FXML
    private TableColumn<Mark, String> type3;

    @FXML
    private TableColumn<Mark, String> attendance3;

    @FXML
    private TableView<Student> myTableView;

    @FXML
    private TableColumn<Student, Integer> ID;

    @FXML
    private TableColumn<Student, String> name;

    @FXML
    private TableView<MyDate> myTableView1;

    @FXML
    private TableColumn<MyDate, String> date1;

    @FXML
    private TableView<MyDate> myTableView2;

    @FXML
    private TableColumn<MyDate, String> date2;

    @FXML
    private Label classLabel2;

    @FXML
    private Label classLabel1;

    @FXML
    private RadioButton fromButtonId;

    @FXML
    private Label fromDateLabel;

    @FXML
    private Label groupLabel;

    @FXML
    private Label studentLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label teacherLabel;

    @FXML
    private TextField textFieldDay;

    @FXML
    private TextField textFieldMonth;

    @FXML
    private TextField textFieldYear;

    @FXML
    private RadioButton toButtonId;

    @FXML
    private Label toDateLabel;

    @FXML
    private Label hW2Label;

    @FXML
    private Label hW3Label;

    @FXML
    private Label hW4Label;

    @FXML
    private Label hW5Label;

    @FXML
    private Label lB2Label;

    @FXML
    private Label lB3Label;

    @FXML
    private Label lB4Label;

    @FXML
    private Label lB5Label;

    @FXML
    private Label pR2Label;

    @FXML
    private Label pR3Label;

    @FXML
    private Label pR4Label;

    @FXML
    private Label pR5Label;

    /**
     * Составление статистики студента по его оценкам
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public void marksSetting() throws SQLException, ClassNotFoundException {
        if (fromDateLabel.getText().trim().isEmpty() | toDateLabel.getText().trim().isEmpty() | studentLabel.getText().isEmpty())
            return;
        int[][] marksInfo = MarksTableController.getMarksInfo(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID, ApplicationStart.chosenStudentID, ApplicationStart.getChosenDateFromInt, ApplicationStart.getChosenDateToInt);
        classLabel1.setText(Integer.toString(marksInfo[3][0]));
        classLabel2.setText(Integer.toString(marksInfo[3][0] + marksInfo[3][1]));
        pR5Label.setText("5: " + Integer.toString(marksInfo[0][0]));
        pR4Label.setText("4: " + Integer.toString(marksInfo[0][1]));
        pR3Label.setText("3: " + Integer.toString(marksInfo[0][2]));
        pR2Label.setText("2: " + Integer.toString(marksInfo[0][3]));
        lB5Label.setText("5: " + Integer.toString(marksInfo[1][0]));
        lB4Label.setText("4: " + Integer.toString(marksInfo[1][1]));
        lB3Label.setText("3: " + Integer.toString(marksInfo[1][2]));
        lB2Label.setText("2: " + Integer.toString(marksInfo[1][3]));
        hW5Label.setText("5: " + Integer.toString(marksInfo[2][0]));
        hW4Label.setText("4: " + Integer.toString(marksInfo[2][1]));
        hW3Label.setText("3: " + Integer.toString(marksInfo[2][2]));
        hW2Label.setText("2: " + Integer.toString(marksInfo[2][3]));
    }

    /**
     * Выбор студента для статистики по датам
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void chooseButton() throws SQLException, ClassNotFoundException {

        if (textFieldYear.getText().trim().isEmpty() | textFieldMonth.getText().trim().isEmpty() | textFieldDay.getText().trim().isEmpty())
            return;

        ApplicationStart.chosenDateInt = DateAssembler.dateAssemblingToInt(textFieldDay.getText().trim(), textFieldMonth.getText().trim(), textFieldYear.getText().trim());

        if (ApplicationStart.chosenDateInt == 0)
            return;

        ApplicationStart.chosenDateStr = DateAssembler.dateAssemblingToString(ApplicationStart.chosenDateInt);

        if (fromButtonId.isSelected()) {
            fromDateLabel.setText(ApplicationStart.chosenDateStr);
            ApplicationStart.getChosenDateFromInt = ApplicationStart.chosenDateInt;
            ApplicationStart.getChosenDateFromStr = ApplicationStart.chosenDateStr;
        } else if (toButtonId.isSelected()) {
            toDateLabel.setText(ApplicationStart.chosenDateStr);
            ApplicationStart.getChosenDateToInt = ApplicationStart.chosenDateInt;
            ApplicationStart.getChosenDateToStr = ApplicationStart.chosenDateStr;
        }
        marksSetting();
        myTableView3.getItems().clear();
        myTableView3.setItems(MarksTableController.getMyDateList2(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID, ApplicationStart.chosenStudentID, ApplicationStart.getChosenDateFromInt, ApplicationStart.getChosenDateToInt));
    }

    /**
     * Импорт оценок выбранного студента в файл
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public void exportButton() throws SQLException, ClassNotFoundException {

        if (ApplicationStart.chosenStudentID == 0)
            return;
        try {
            String path = ApplicationStart.chosenExportPath + "\\" + StudentsTableController.studentInID(ApplicationStart.chosenStudentID).replace(" ", "") + "(" + ApplicationStart.chosenStudentID + ").txt";
            File file = new File(path);
            if (file.createNewFile()) {
                ArrayList<String> list = MarksTableController.getMarksForImport(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID, ApplicationStart.chosenStudentID, ApplicationStart.getChosenDateFromInt, ApplicationStart.getChosenDateToInt);
                FileWriter fw = new FileWriter(path);
                fw.write("Преподаватель: " + TeachersTableController.teacherInID(ApplicationStart.chosenTeacherID) + "\n");
                fw.write("Предмет: " + SubjectsTableController.subjectInID(ApplicationStart.chosenSubjectID) + "\n");
                fw.write("Группа: " + GroupsTableController.groupInID(ApplicationStart.chosenGroupID) + "\n");
                fw.write("Студент: " + StudentsTableController.studentInID(ApplicationStart.chosenStudentID) + "\n");
                fw.write("\n");
                for (int i = 0; i < list.size(); i = i + 4) {
                    fw.write("Дата: " + list.get(i) + "\n");
                    fw.write("Оценки: " + list.get(i + 1) + "\n");
                    fw.write("Тип: " + list.get(i + 2) + "\n");
                    fw.write("Посещение: " + list.get(i + 3) + "\n");
                    fw.write("\n");
                }
                logger.info("Был произведен экспорт");
                fw.close();
            }
        } catch (IOException e) {
            logger.error("Нет пути для экспорта");
        }

    }

    /**
     * Возвращение на главную рабочую страницу
     *
     * @param event нажатие на кнопку
     * @throws IOException исключение
     */
    @FXML
    public void backButton(ActionEvent event) throws IOException {
        ApplicationStart.getChosenDateFromInt = 0;
        ApplicationStart.getChosenDateToInt = 0;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainWorkPage.fxml")));
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
        ApplicationStart.getChosenDateFromInt = 0;
        ApplicationStart.getChosenDateToInt = 0;
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

        ID.setCellValueFactory(new PropertyValueFactory<Student, Integer>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        date1.setCellValueFactory(new PropertyValueFactory<MyDate, String>("date"));
        date2.setCellValueFactory(new PropertyValueFactory<MyDate, String>("date"));

        date3.setCellValueFactory(new PropertyValueFactory<MyDate, String>("date"));
        marks3.setCellValueFactory(new PropertyValueFactory<Mark, String>("mark"));
        type3.setCellValueFactory(new PropertyValueFactory<Mark, String>("type"));
        attendance3.setCellValueFactory(new PropertyValueFactory<Mark, String>("attendance"));

        try {
            myTableView.setItems(StudentsTableController.getNameListByGroup(GroupsTableController.groupInID(ApplicationStart.chosenGroupID)));
            myTableView1.setItems(MarksTableController.getMyDateList(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID));
            myTableView2.setItems(MarksTableController.getMyDateList(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID));
            teacherLabel.setText(TeachersTableController.teacherInID(ApplicationStart.chosenTeacherID));
            subjectLabel.setText(SubjectsTableController.subjectInID(ApplicationStart.chosenSubjectID));
            groupLabel.setText(GroupsTableController.groupInID(ApplicationStart.chosenGroupID));
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ошибка в отображении tableView");
            throw new RuntimeException(e);
        }

        TableView.TableViewSelectionModel<Student> selectionModel = myTableView.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observableValue, Student student, Student newVal) {
                if (newVal != null) {
                    studentLabel.setText(newVal.getName());
                    ApplicationStart.chosenStudentID = newVal.getID();
                    try {
                        marksSetting();
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    myTableView3.getItems().clear();
                    try {
                        myTableView3.setItems(MarksTableController.getMyDateList2(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID, ApplicationStart.chosenStudentID, ApplicationStart.getChosenDateFromInt, ApplicationStart.getChosenDateToInt));
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        TableView.TableViewSelectionModel<MyDate> selectionModel1 = myTableView1.getSelectionModel();
        selectionModel1.selectedItemProperty().addListener(new ChangeListener<MyDate>() {
            @Override
            public void changed(ObservableValue<? extends MyDate> observableValue, MyDate myDate, MyDate newVal) {
                if (newVal != null) {
                    fromDateLabel.setText(newVal.getDate());
                    ApplicationStart.getChosenDateFromInt = DateAssembler.dateAssemblingToIntFromTable(newVal.getDate());
                    ApplicationStart.getChosenDateFromStr = newVal.getDate();
                    try {
                        marksSetting();
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        TableView.TableViewSelectionModel<MyDate> selectionModel2 = myTableView2.getSelectionModel();
        selectionModel2.selectedItemProperty().addListener(new ChangeListener<MyDate>() {

            @Override
            public void changed(ObservableValue<? extends MyDate> observableValue, MyDate myDate, MyDate newVal) {
                if (newVal != null) {
                    toDateLabel.setText(newVal.getDate());
                    ApplicationStart.getChosenDateToInt = DateAssembler.dateAssemblingToIntFromTable(newVal.getDate());
                    ApplicationStart.getChosenDateToStr = newVal.getDate();
                    try {
                        marksSetting();
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

}
