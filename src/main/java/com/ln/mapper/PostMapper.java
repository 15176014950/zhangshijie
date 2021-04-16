package com.ln.mapper;

import com.ln.entity.PostBean;
import com.ln.entity.PostBeanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostMapper {
    long countByExample(PostBeanExample example);

    int deleteByExample(PostBeanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PostBean record);

    int insertSelective(PostBean record);

    List<PostBean> selectByExample(PostBeanExample example);

    PostBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PostBean record, @Param("example") PostBeanExample example);

    int updateByExample(@Param("record") PostBean record, @Param("example") PostBeanExample example);

    int updateByPrimaryKeySelective(PostBean record);

    int updateByPrimaryKey(PostBean record);

    List<Long> getPostMeunById(@Param("postid") Long postid);

    void deletePostMeunByPostId(Long postid);

    void insertPostMeun(@Param("postid") Long postid, @Param("meunid") Long meunid);
}