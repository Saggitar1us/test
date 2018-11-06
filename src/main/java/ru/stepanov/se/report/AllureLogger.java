package ru.stepanov.se.report;

import io.qameta.allure.Attachment;

/**
 * @author Aleksei Stepanov
 */


public class AllureLogger {

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String saveSimpleTextLog(String attachName, String message) {
        if (message == null) {
            message = "null";
        }
        return message;
    }

}
