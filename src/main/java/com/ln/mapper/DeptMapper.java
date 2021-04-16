package com.ln.mapper;

import com.ln.entity.DeptBean;
import com.ln.entity.DeptBeanExample;
import com.ln.entity.PostBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptMapper {
    long countByExample(DeptBeanExample example);

    int deleteByExample(DeptBeanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeptBean record);

    int insertSelective(DeptBean record);

    List<DeptBean> selectByExample(DeptBeanExample example);

    DeptBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeptBean record, @Param("example") DeptBeanExample example);

    int updateByExample(@Param("record") DeptBean record, @Param("example") DeptBeanExample example);

    int updateByPrimaryKeySelective(DeptBean record);

    int updateByPrimaryKey(DeptBean record);

    Long[] getDeptByPostId(@Param("deptid") Long deptid);

    void deletePostById(Long id);

    void insertDeptAndPost(@Param("deptid") Long id,@Param("postid") Long postids);



    List<PostBean> getDeptAndPostById(@Param("deptid") Long deptid);

    Long[] getUserPostDeptBYId(@Param("userid") Long userid, @Param("deptid") Long deptid);
}