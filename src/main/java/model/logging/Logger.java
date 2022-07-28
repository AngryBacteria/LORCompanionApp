package model.logging;

import model.card.CardService;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple Logger Class that can be created by giving it a Path on where to save the Logs. A log entry consists of an
 * Operation which is an Enum and the Text to it. It saves the entry with the current time.
 */
public class Logger {

    public enum Operation {
        STARTUP,
        SHUTDOWN,
        CREATECARD,
        DELETECARD,
        IO_OPERATION,
        EXCEPTION
    }

    private final String fileName;
    private final String fileDestination;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Logger(String fileName) {
        this.fileName = fileName;
        this.fileDestination = CardService.logsPath + fileName;
    }


    public void writeLog(Operation operation, String description){

        try(FileWriter writer = new FileWriter(fileDestination, true); BufferedWriter bufferedWriter = new BufferedWriter(writer)){
            bufferedWriter.write(operation + " : " + description + " [" + LocalDateTime.now().format(formatter) + "]\n");
        }
        catch (IOException e) {
            System.out.println("Logging error");
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileDestination() {
        return fileDestination;
    }

    public void getFullLog(){
        try(FileReader reader = new FileReader(fileDestination); BufferedReader bufferedReader = new BufferedReader(reader)){
            bufferedReader.lines().forEach(System.out::println);
        }
        catch (IOException e) {
            System.out.println("Reading went wrong");
            e.printStackTrace();
        }
    }
}
