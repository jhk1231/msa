package com.example.userservice.module.service;

import com.example.userservice.module.domain.UserEntity;
import com.example.userservice.module.dto.UserDto;
import com.example.userservice.module.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        final ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        final UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPassword(encoder.encode(userDto.getPassword()));
        userRepository.save(userEntity);

        return createUserDto(userEntity);
    }

    private UserDto createUserDto(UserEntity userEntity) {
        final UserDto serviceResponseDto = new UserDto();
        serviceResponseDto.setName(userEntity.getName());
        serviceResponseDto.setEmail(userEntity.getEmail());
        serviceResponseDto.setUserId(userEntity.getUserId());
        serviceResponseDto.setPassword("password");
        serviceResponseDto.setEncryptedPassword(userEntity.getEncryptedPassword());
        return serviceResponseDto;
    }
}
