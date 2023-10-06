package org.example.converter;

import org.example.dto.CredentialsDto;
import org.example.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserConverter {

  CredentialsDto toDto(User user);

  User toUser(CredentialsDto userDto);
}

