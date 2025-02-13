package com.luand.luand.converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import com.luand.luand.entities.Size;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SizeSetConverter implements AttributeConverter<Set<Size>, String> {

        @Override
        public String convertToDatabaseColumn(Set<Size> sizes) {
                return sizes == null ? null : sizes.stream().map(Enum::name).collect(Collectors.joining(","));
        }

        @Override
        public Set<Size> convertToEntityAttribute(String dbData) {
                return dbData == null ? Collections.emptySet()
                                : Arrays.stream(dbData.split(",")).map(Size::valueOf).collect(Collectors.toSet());
        }

}
