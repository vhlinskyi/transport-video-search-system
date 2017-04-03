package com.maxclay.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Spring's component which actually registers converters in conversionService. Allows converters
 * to be Spring's components.
 *
 * @author Vlad Glinskiy
 */
@Component("converterRegistryComponent")
public class ConverterRegistryComponent {

    @Autowired
    @Qualifier("conversionService")
    private ConversionServiceFactoryBean conversionServiceFactoryBean;

    @Autowired
    @Qualifier("jsonNodeToPlateSearchRequest")
    private Converter jsonNodeToPlateSearchRequest;

    @PostConstruct
    private void init() {

        ConversionService conversionService = conversionServiceFactoryBean.getObject();
        ConverterRegistry registry = (ConverterRegistry) conversionService;

        registry.addConverter(this.jsonNodeToPlateSearchRequest);
    }
}
