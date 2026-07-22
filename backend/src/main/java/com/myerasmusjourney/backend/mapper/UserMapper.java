package com.myerasmusjourney.backend.mapper;

import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
}
