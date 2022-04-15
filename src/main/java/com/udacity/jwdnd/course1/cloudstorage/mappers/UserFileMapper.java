package com.udacity.jwdnd.course1.cloudstorage.mappers;
import com.udacity.jwdnd.course1.cloudstorage.model.UserFileClassModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserFileMapper {
//I used small letters to write my querries
    @Select("select * from FILES WHERE userid = #{userid}")
    List<UserFileClassModel> list(Integer userid);

    @Select("select * from FILES WHERE fileId = #{fileId}")
    UserFileClassModel findById(Integer fileId);

    @Select("select * from FILES where filename = #{filename}")
    UserFileClassModel findOneFile(String filename);

    @Insert("insert into FILES(filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int upload(UserFileClassModel file);


    @Delete("delete from FILES where fileId = #{fileId}")
    void delete(Integer fileId);
}
