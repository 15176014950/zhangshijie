package com.ln.mapper;

import com.ln.entity.DeptBean;
import com.ln.entity.MeunBean;
import com.ln.entity.UserBean;
import com.ln.entity.UserBeanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    long countByExample(UserBeanExample example);

    int deleteByExample(UserBeanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserBean record);

    int insertSelective(UserBean record);

    List<UserBean> selectByExample(UserBeanExample example);

    UserBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByExample(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByPrimaryKeySelective(UserBean record);

    int updateByPrimaryKey(UserBean record);

    Long[] getUserAndDeptId(Long id);

    void deleteByDeptId(Long id);

    void deleteByPostId(Long id);

    void insertUserDept(@Param("userid") Long userid,@Param("deptid") Long deptid);

    List<DeptBean> getUserAndDeptById(Long id);

    void saveUserAndPostById(@Param("userid") Long id,@Param("postid") Long postids);

    List<UserBean> getLogin(UserBean userBean);

    List<MeunBean> getMeunByUserById(@Param("userid") Long id);
}