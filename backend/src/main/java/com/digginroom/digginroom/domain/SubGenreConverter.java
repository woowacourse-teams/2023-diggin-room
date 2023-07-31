package com.digginroom.digginroom.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Converter
public class SubGenreConverter implements AttributeConverter<List<String>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(final List<String> attribute) {
        return String.join(DELIMITER, attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(final String dbData) {
        return Arrays.asList(dbData.split(DELIMITER));
    }
}
