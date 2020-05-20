package com.github.dariux2016.converter.web;

import org.springframework.core.convert.converter.Converter;

import com.github.dariux2016.model.EnItemType;

@RequestParameterConverter
public class StringToEnItemTypeConverter implements Converter<String, EnItemType> {
    
	@Override
    public EnItemType convert(String source) {
        return EnItemType.decode(source);
    }
}