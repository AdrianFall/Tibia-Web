package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Adrian on 29/03/2016.
 */

@EnableWebMvc /* Equivalent to the xml version of <mvc:annotation-driven /> */
@ComponentScan(basePackages = {"core.controller.rest"})
@Configuration
public class WebConfig {
}
