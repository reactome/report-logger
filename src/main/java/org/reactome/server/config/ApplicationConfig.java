package org.reactome.server.config;

import org.reactome.server.ReportLoggerApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:report.properties"})
@ComponentScan(basePackageClasses = ReportLoggerApplication.class)
@Import(value = CustomSecurityConfiguration.class)
class ApplicationConfig {

}