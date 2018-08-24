package controller.mvc;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class LoggerController {


    @GetMapping("/seeLogs")
    public String seeLogs(Model model, @RequestParam(required = false) Integer linesPerError ) {

        linesPerError = (linesPerError == null ? 10 : linesPerError);

        final File logFile = getLogfile();
        model.addAttribute("logEntries", readFile(logFile, linesPerError));

        return "logs";
    }


    // Ugly hack like everything else, will probably implement option to select which of the logs that is wanted to be used
    private File getLogfile() {
        LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();
        for (Logger logger : context.getLoggerList()) {
            for (Iterator<Appender<ILoggingEvent>> index = logger.iteratorForAppenders(); index.hasNext();) {
                Appender<ILoggingEvent> appender = index.next();
                if(appender instanceof FileAppender) {
                    FileAppender fileAppender = (FileAppender) appender;
                    return new File(fileAppender.getFile());
                }
            }
        }
        return null;
    }

    private List<String> readFile(final File file, int linesPerError) {
        final List<String> logLines = new ArrayList<>();
        int linePerErrorCounter = 0;
        try {
            if(file != null) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if(line.contains("INFO") || line.contains("WARNING") || line.contains("ERROR")) {
                        linePerErrorCounter = 0;
                    }
                    else {
                        linePerErrorCounter++;
                    }

                    if(linePerErrorCounter < linesPerError) {
                        logLines.add(0, line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logLines;
    }

}
