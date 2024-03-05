package com.application.learnenglish.model.entity.converter;

import com.application.learnenglish.model.enums.Role;
import jakarta.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.shortName;
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        return Role.fromShortName(dbData);
    }
}