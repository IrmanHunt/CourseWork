package com.example.coursework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Главный класс
 */
public class Main {

    static final Logger logger = LogManager.getLogger(Main.class.getName());

    /**
     * Запуск приложения
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        logger.info("Приложение было запущено");
        ApplicationStart.main(args);
    }
}
