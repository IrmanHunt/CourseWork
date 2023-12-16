package com.example.coursework;

import com.example.coursework.DbClasses.MyDate;
import com.example.coursework.DbClasses.Mark;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Класс дневник студента
 */
public class GroupDiaryPageController implements Initializable {

    static final Logger logger = LogManager.getLogger(GroupDiaryPageController.class.getName());

    @FXML
    private TableView<MyDate> myTableView2;

    @FXML
    private TableColumn<MyDate, String> date;

    @FXML
    private TableView<Mark> myTableView;

    @FXML
    private TableColumn<Mark, Integer> ID;

    @FXML
    private TableColumn<Mark, String> student;

    @FXML
    private TableColumn<Mark, String> mark;

    @FXML
    private TableColumn<Mark, String> type;

    @FXML
    private TableColumn<Mark, String> attendance;

    @FXML
    private RadioButton attendanceButtonId;

    @FXML
    private Label dateLabel;

    @FXML
    private Label groupLabel;

    @FXML
    private RadioButton illButtonId;

    @FXML
    private RadioButton laboratoryWorkButtonId;

    @FXML
    private RadioButton practicalWorkButtonId;

    @FXML
    private RadioButton respectButtonId;

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
    private RadioButton homeworkButtonId;

    @FXML
    private RadioButton absenceButtonId;

    /**
     * Определение практическую работу типом работы
     *
     */
    @FXML
    public void practicalWorkButton() {
        if (practicalWorkButtonId.isSelected())
            ApplicationStart.chosenMarkType = "ПР";
    }

    /**
     * Определение лабораторную работу типом работы
     *
     */
    @FXML
    public void laboratoryWorkButton() {
        if (laboratoryWorkButtonId.isSelected())
            ApplicationStart.chosenMarkType = "ЛБ";
    }

    /**
     * Определение домашнюю работу типом работы
     *
     */
    @FXML
    public void homeworkButton() {
        if (homeworkButtonId.isSelected())
            ApplicationStart.chosenMarkType = "ДЗ";
    }

    /**
     * Выбор или добавление даты с оценками
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void chooseAddButton() throws SQLException, ClassNotFoundException {

        if (textFieldYear.getText().trim().isEmpty() | textFieldMonth.getText().trim().isEmpty() | textFieldDay.getText().trim().isEmpty())
            return;

        ApplicationStart.chosenDateInt = DateAssembler.dateAssemblingToInt(textFieldDay.getText().trim(), textFieldMonth.getText().trim(), textFieldYear.getText().trim());

        if (ApplicationStart.chosenDateInt == 0)
            return;

        ApplicationStart.chosenDateStr = DateAssembler.dateAssemblingToString(ApplicationStart.chosenDateInt);

        dateLabel.setText(ApplicationStart.chosenDateStr);
        MarksTableController.insert(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID, ApplicationStart.chosenStudentID, ApplicationStart.chosenDateInt);
        updateTables();
    }

    /**
     * Удаление даты с оценками
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void deleteButton() throws SQLException, ClassNotFoundException {
        if (dateLabel.getText().trim().isEmpty())
            return;
        MarksTableController.delete(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID, ApplicationStart.chosenDateInt);
        updateTables();
    }

    /**
     * Удаление оценки выбранного студента
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void deleteMarkButton() throws SQLException, ClassNotFoundException {
        if(ApplicationStart.chosenStudentID == 0)
            return;
        MarksTableController.deleteMark(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID, ApplicationStart.chosenStudentID, ApplicationStart.chosenDateInt);
        updateTables();
    }

    /**
     * Поставить студенту 5
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void fiveButton() throws SQLException, ClassNotFoundException {
        if(ApplicationStart.chosenStudentID == 0)
            return;
        ApplicationStart.chosenMark = 5;
        MarksTableController.addMark(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID, ApplicationStart.chosenStudentID, ApplicationStart.chosenDateInt);
        updateTables();
    }

    /**
     * Поставить студенту 4
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void fourButton() throws SQLException, ClassNotFoundException {
        if(ApplicationStart.chosenStudentID == 0)
            return;
        ApplicationStart.chosenMark = 4;
        MarksTableController.addMark(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID, ApplicationStart.chosenStudentID, ApplicationStart.chosenDateInt);
        updateTables();
    }

    /**
     * Поставить студенту 3
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void threeButton() throws SQLException, ClassNotFoundException {
        if(ApplicationStart.chosenStudentID == 0)
            return;
        ApplicationStart.chosenMark = 3;
        MarksTableController.addMark(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID, ApplicationStart.chosenStudentID, ApplicationStart.chosenDateInt);
        updateTables();
    }

    /**
     * Поставить студенту 2
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void twoButton() throws SQLException, ClassNotFoundException {
        if(ApplicationStart.chosenStudentID == 0)
            return;
        ApplicationStart.chosenMark = 2;
        MarksTableController.addMark(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID, ApplicationStart.chosenStudentID, ApplicationStart.chosenDateInt);
        updateTables();
    }

    /**
     * Добавление посещения студента
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    @FXML
    public void addAttendanceButton() throws SQLException, ClassNotFoundException {
        MarksTableController.addAttendance(ApplicationStart.chosenAttendanceType, ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID, ApplicationStart.chosenStudentID, ApplicationStart.chosenDateInt);
        updateTables();
    }

    /**
     * Возвращение на главную рабочую страницу
     *
     * @param event нажатие на кнопку
     * @throws IOException исключение
     */
    @FXML
    public void backButton(ActionEvent event) throws IOException {
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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Обновление всех tableView
     *
     * @throws SQLException           ошибка в SQL запросе
     * @throws ClassNotFoundException не найден файл
     */
    public void updateTables() throws SQLException, ClassNotFoundException {
        myTableView.getItems().clear();
        myTableView.setItems(MarksTableController.getNameList(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID, ApplicationStart.chosenDateInt));
        myTableView2.getItems().clear();
        myTableView2.setItems(MarksTableController.getMyDateList(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID));
    }

    /**
     * Работа со всеми tableView и отслеживание нажатия на них
     *
     * @param url URL
     * @param resourceBundle РесурсПакет
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ID.setCellValueFactory(new PropertyValueFactory<Mark, Integer>("ID"));
        student.setCellValueFactory(new PropertyValueFactory<Mark, String>("student"));
        mark.setCellValueFactory(new PropertyValueFactory<Mark, String>("mark"));
        type.setCellValueFactory(new PropertyValueFactory<Mark, String>("type"));
        attendance.setCellValueFactory(new PropertyValueFactory<Mark, String>("attendance"));

        date.setCellValueFactory(new PropertyValueFactory<MyDate, String>("date"));

        try {
            myTableView2.setItems(MarksTableController.getMyDateList(ApplicationStart.chosenTeacherID, ApplicationStart.chosenSubjectID, ApplicationStart.chosenGroupID));
            teacherLabel.setText(TeachersTableController.teacherInID(ApplicationStart.chosenTeacherID));
            subjectLabel.setText(SubjectsTableController.subjectInID(ApplicationStart.chosenSubjectID));
            groupLabel.setText(GroupsTableController.groupInID(ApplicationStart.chosenGroupID));
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ошибка в отображении tableView");
            throw new RuntimeException(e);
        }

        TableView.TableViewSelectionModel<Mark> selectionModel = myTableView.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Mark>() {

            @Override
            public void changed(ObservableValue<? extends Mark> observableValue, Mark mark, Mark newVal) {
                if (newVal != null) {
                    studentLabel.setText(newVal.getStudent());
                    ApplicationStart.chosenStudentID = newVal.getID();
                    if (attendanceButtonId.isSelected())
                        ApplicationStart.chosenAttendanceType = "П";
                    else if (illButtonId.isSelected())
                        ApplicationStart.chosenAttendanceType = "Б";
                    else if (respectButtonId.isSelected())
                        ApplicationStart.chosenAttendanceType = "У";
                    else if (absenceButtonId.isSelected())
                        ApplicationStart.chosenAttendanceType = "Н";
                }
            }
        });

        TableView.TableViewSelectionModel<MyDate> selectionModel2 = myTableView2.getSelectionModel();
        selectionModel2.selectedItemProperty().addListener(new ChangeListener<MyDate>() {

            @Override
            public void changed(ObservableValue<? extends MyDate> observableValue, MyDate myDate, MyDate newVal) {
                if (newVal != null) {
                    dateLabel.setText(newVal.getDate());
                    textFieldDay.setText(DateAssembler.dateAssemblingToLabels(newVal.getDate())[0]);
                    textFieldMonth.setText(DateAssembler.dateAssemblingToLabels(newVal.getDate())[1]);
                    textFieldYear.setText(DateAssembler.dateAssemblingToLabels(newVal.getDate())[2]);
                }
            }
        });
    }
}
