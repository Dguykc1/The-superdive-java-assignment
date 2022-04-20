package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapperInterface;
import com.udacity.jwdnd.course1.cloudstorage.model.UserModelClass;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
@Service
public class UserClientService {
    private final UserMapperInterface userMapperInterface;
    private final HashService hashService;

    public UserClientService(UserMapperInterface userMapper, HashService hashService) {
        this.userMapperInterface = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapperInterface.getUserClient(username) == null;
    }



    public UserModelClass getUser(String username) {
        return userMapperInterface.getUserClient(username);
    }
    public int createUser(UserModelClass user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapperInterface.insert(new UserModelClass(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstname(), user.getLastname()));
    }
}
