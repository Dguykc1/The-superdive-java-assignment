package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.UserCredentialsClass;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserCredentialMapper {
    //I used small letters in writing my querries
    @Select("select * from CREDENTIALS where userid = #{userid}")
    List<UserCredentialsClass> listAllTheCredentials(Integer userid);

    @Select("select * from CREDENTIALS WHERE credentialid = #{credentialid}")
    UserCredentialsClass findOneUser(Integer credentialid);

    @Insert("insert into CREDENTIALS(url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    Integer create(UserCredentialsClass credential);

    @Delete("delete from CREDENTIALS where credentialid = #{credentialid}")
    void  delete(Integer credentialid);

    @Update("update CREDENTIALS SET url=#{url}, username =#{username}, key =#{key}, password =#{password} where credentialid =#{credentialid}")
    void update(UserCredentialsClass credential);
}
