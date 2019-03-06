package ineor.vat;

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
	    public static void main(String[] args) {
	    	SpringApplication.run(ApplicationMain.class, args);
	    }
	}
