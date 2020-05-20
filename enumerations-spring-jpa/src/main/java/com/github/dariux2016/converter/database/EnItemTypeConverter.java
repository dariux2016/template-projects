package com.github.dariux2016.converter.database;

import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.stereotype.Component;

import com.github.dariux2016.model.EnItemType;

/**
 * AttributeConvertEnItemTypeype, String>. Implements the following methods :
 * <ul>
 * <li>convertToDatabaseColumn : (given an Enum returns a String)
 * <li>convertToEntityAttribute : (given a String returns an Enum)
 * </ul>
 */
@Component
@Converter(autoApply = true)
public class EnItemTypeConverter implements AttributeConverter<EnItemType, String> {

	@Override
	public String convertToDatabaseColumn(final EnItemType attribute) {
		return Optional.ofNullable(attribute).map(EnItemType::getCode).orElse(null);
	}

	@Override
	public EnItemType convertToEntityAttribute(final String dbData) {
		return EnItemType.decode(dbData);
	}
}