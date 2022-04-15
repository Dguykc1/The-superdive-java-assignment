package com.udacity.jwdnd.course1.cloudstorage.services;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserCredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserCredentialsClass;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class UserCredentialService {
    private final UserCredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public UserCredentialService(UserCredentialMapper credentialMapper,
                                 EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<UserCredentialsClass> listAllCredentials(Integer userId){
        return credentialMapper.listAllTheCredentials(userId);
    }

    public UserCredentialsClass findOne(Integer credentialId){
        return credentialMapper.findOneUser(credentialId);
    }


    private void encryptPassword(UserCredentialsClass credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
    }

    public int create(UserCredentialsClass credential){
        encryptPassword(credential);
        return credentialMapper.create(credential);
    }

    public void update(UserCredentialsClass credential){
        encryptPassword(credential);
        credentialMapper.update(credential);
    }

    public void deleteCredential(Integer credentialId){
        credentialMapper.delete(credentialId);
    }


}
