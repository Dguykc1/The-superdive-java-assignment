package com.udacity.jwdnd.course1.cloudstorage.mappers;
//assignment submitted done by Eze Christian
import com.udacity.jwdnd.course1.cloudstorage.model.UserModelClass;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapperInterface {
    //I Eze Christian Kelechi the owner of this work decided to use small letters for writing my sql queries
   @Select("select *from USERS where username=#{username}")
    UserModelClass getUserClient(String username);
   @Insert("insert into USERS(username,salt,password,firstname,lastname) values(#{username},#{salt},#{password},#{firstname},#{lastname})")
    @Options(useGeneratedKeys = true,keyProperty = "userid")
    int insert(UserModelClass userClient);
}
