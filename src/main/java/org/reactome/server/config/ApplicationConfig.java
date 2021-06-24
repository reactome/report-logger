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

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

//    @Bean
//    public JavaMailSender getMailSender(@Value("${mail.host}") String host,
//                                        @Value("${mail.port}") String port,
//                                        @Value("${mail.username}") String username,
//                                        @Value("${mail.password}") String password,
//                                        @Value("${mail.enable.auth}") String enableAuth) {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(host);
//        mailSender.setPort(Integer.parseInt(port));
//        mailSender.setUsername(username);
//        mailSender.setPassword(password);
//
//        Properties javaMailProperties = new Properties();
//        javaMailProperties.put("mail.transport.protocol", "smtp");
//        javaMailProperties.put("mail.debug", "false");
//        javaMailProperties.put("mail.smtp.ssl.trust", "*");
//
//        mailSender.setJavaMailProperties(javaMailProperties);
//        return mailSender;
//    }
//
//    @Bean
//    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
//        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
//        bean.setTemplateLoaderPath("classpath:/templates/");
//        return bean;
//    }
}