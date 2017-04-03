package com.maxclay.converter;

import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.stereotype.Service;

/**
 * Conversion service factory bean which used for registration converters.
 * Created to be Spring's service that extends {@link org.springframework.context.support.ConversionServiceFactoryBean}.
 *
 * @author Vlad Glinskiy
 */
@Service("conversionService")
public class ConversionServiceFactory extends ConversionServiceFactoryBean {
}
