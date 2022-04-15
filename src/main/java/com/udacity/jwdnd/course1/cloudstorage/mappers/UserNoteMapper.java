package com.udacity.jwdnd.course1.cloudstorage.mappers;


import com.udacity.jwdnd.course1.cloudstorage.model.UserNoteModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserNoteMapper {
    //I used small letters for my query
    @Select("select * from NOTES where userid = #{userid}")
    List<UserNoteModel> getNotes(Integer userid);

    @Insert("insert into NOTES (notetitle, notedescription, userid) VALUES(#{notetitle},#{notedescription},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(UserNoteModel note);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    void updateNote(UserNoteModel note);

    @Delete("delete from NOTES where noteid =#{noteid}")
    void deleteNote(Integer noteid);

    @Select("select * from NOTES where noteid = #{noteid}")
    UserNoteModel findOne(Integer noteid);

    @Select("select * from NOTES WHERE notetitle = #{notetitle} and notedescription =#{notedescription} " )
    UserNoteModel findByTitleAndDesc(String notetitle, String notedescription);


}
