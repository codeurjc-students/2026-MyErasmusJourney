package com.myerasmusjourney.backend.mapper;

import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserSimpleDTO toSimpleDTO(User user);
}
