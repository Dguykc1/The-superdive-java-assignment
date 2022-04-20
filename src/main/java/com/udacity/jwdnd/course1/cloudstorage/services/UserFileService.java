package com.udacity.jwdnd.course1.cloudstorage.services;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserFileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserFileClassModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserFileService {
    private final Logger logger = LoggerFactory.getLogger(UserFileService.class);
    private final UserFileMapper fileMapper;

    public UserFileService(UserFileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<UserFileClassModel> listAll(Integer userId){

        return fileMapper.list(userId);
    }

    public UserFileClassModel findById(Integer fileId){
        return fileMapper.findById(fileId);
    }

    public UserFileClassModel findOneFile(String fileName){
        return fileMapper.findOneFile(fileName);
    }



    public void delete(Integer fileId){
        fileMapper.delete(fileId);
    }
    public int upload(UserFileClassModel file, MultipartFile multipartFile) throws Exception{
        file.setFilename(multipartFile.getOriginalFilename());
        file.setContenttype(multipartFile.getContentType());
        file.setFilesize(String.valueOf(multipartFile.getSize()));
        file.setFiledata(multipartFile.getBytes());

        return fileMapper.upload(file);
    }
}
