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
 * Spring's component which actually registers converters in {@link com.maxclay.converter.ConversionServiceFactory}.
 * Allows converters to be Spring's components.
 *
 * @author Vlad Glinskiy
 */
@Component
public class ConverterRigistryComponent {

    @Autowired
    @Qualifier("conversionService")
    private ConversionServiceFactoryBean conversionServiceFactoryBean;

    @Autowired
    @Qualifier("userToUserDto")
    private Converter userToUserDto;

    @Autowired
    @Qualifier("userToUserDtoInverse")
    private Converter userToUserDtoInverse;

    @PostConstruct
    private void init() {

        ConversionService conversionService = conversionServiceFactoryBean.getObject();
        ConverterRegistry registry = (ConverterRegistry) conversionService;

        registry.addConverter(this.userToUserDto);
        registry.addConverter(this.userToUserDtoInverse);
    }

}
