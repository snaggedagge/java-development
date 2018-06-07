package rpi.controller.rest;



import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import portalconnector.model.WebsiteDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Dag Karlsson on 25-Oct-17.
 */


/*
    Controller that handles ajax calls from server
 */
@RestController
public class ProjectRESTController {


    public ProjectRESTController() {
        //ApplicationContext ctx = new AnnotationConfigApplicationContext(WebConfig.class);
        //this.heaterDTO = ctx.getBean(SynchronizedHeaterDTO.class);
    }


}


