package com.example.coursework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Класс настройки импорта и экспорта
 */
public class SettingsPageController implements Initializable {

    static final Logger logger = LogManager.getLogger(SettingsPageController.class.getName());

    @FXML
    private Label exportLabel;

    @FXML
    private TextField exportPathTextField;

    @FXML
    private Label importLabel;

    @FXML
    private TextField importPathTextField;

    /**
     * Сохранение пути для экспорта
     *
     * @throws IOException исключение
     */
    @FXML
    public void exportSaveButton() throws IOException {
        String path;
        File folder;
        path = exportPathTextField.getText();
        folder = new File(path);
        if (folder.isDirectory()) {
            ApplicationStart.chosenExportPath = path;
            exportLabel.setTextFill(Color.web("#008000"));
            exportLabel.setText("Путь успешно сохранен!");
            FileWriter fw = new FileWriter("export.txt");
            fw.write(path);
            fw.close();
            logger.info("Был сохранен путь для экспорта");
        } else {
            exportLabel.setTextFill(Color.web("#FF0000"));
            exportLabel.setText("Ошибка в пути!");
        }
    }

    /**
     * Сохранение пути для импорта
     *
     * @throws IOException исключение
     */
    @FXML
    public void importSaveButton() throws IOException {
        String path = importPathTextField.getText();
        File folder = new File(path);
        if (folder.isDirectory()) {
            ApplicationStart.chosenImportPath = path;
            importLabel.setTextFill(Color.web("#008000"));
            importLabel.setText("Путь успешно сохранен!");
            FileWriter fw = new FileWriter("import.txt");
            fw.write(path);
            fw.close();
            logger.info("Был сохранен путь для импорта");
        } else {
            importLabel.setTextFill(Color.web("#FF0000"));
            importLabel.setText("Ошибка в пути!");
        }
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
     * Работа Labels с уже сохраненными путями
     *
     * @param url URL
     * @param resourceBundle РесурсПакет
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            File file;
            FileReader fr;
            Scanner scan;
            file = new File("export.txt");
            if (file.length() != 0) {
                fr = new FileReader("export.txt");
                scan = new Scanner(fr);
                ApplicationStart.chosenExportPath = scan.nextLine();
                exportLabel.setText(ApplicationStart.chosenExportPath);
            }
            file = new File("import.txt");
            if (file.length() != 0) {
                fr = new FileReader("import.txt");
                scan = new Scanner(fr);
                ApplicationStart.chosenImportPath = scan.nextLine();
                importLabel.setText(ApplicationStart.chosenImportPath);
            }
        } catch (FileNotFoundException e) {
            logger.error("Файл не был найден");
            throw new RuntimeException(e);
        }
    }
}
