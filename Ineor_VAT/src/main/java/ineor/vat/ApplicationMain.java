package ineor.vat;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@ComponentScan
/**
 * Main application launcher
 * @author Oleg
 */
public class ApplicationMain {
	    public static void main(String[] args) throws MalformedURLException, IOException, ParseException {
	    	SpringApplication.run(ApplicationMain.class, args);
	    }
	}
