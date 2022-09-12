package com.lab2.electronicQueue.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        Locale ukraineLocale = new Locale("uk","UA");
        slr.setDefaultLocale(ukraineLocale);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

/*    @Bean(name = "messageSource")
    public MessageSource getMessageResource() {
        ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
        messageResource.setBasename("classpath:locale/messages");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
