package vietquan37.com.example.projects.utils;

import jakarta.persistence.AttributeConverter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EnumListConverter<E extends Enum<E>> implements AttributeConverter<List<E>, String> {

    private final Class<E> enumType;

    protected EnumListConverter(Class<E> enumType) {
        this.enumType = enumType;
    }

    @Override
    public String convertToDatabaseColumn(List<E> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<E> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(dbData.split(","))
                .map(name -> Enum.valueOf(enumType, name))
                .collect(Collectors.toList());
    }
}