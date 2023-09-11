package com.digginroom.digginroom.domain.track;

import com.digginroom.digginroom.exception.SubGenreConvertingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;

@Converter
public class SubGenreConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(final List<String> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new SubGenreConvertingException();
        }
    }

    @Override
    public List<String> convertToEntityAttribute(final String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new SubGenreConvertingException();
        }
    }
}
