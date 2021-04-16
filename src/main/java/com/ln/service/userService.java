package com.ln.service;

import com.ln.entity.MeunBean;
import com.ln.entity.UserBean;
import com.ln.utils.Page;

import java.util.List;

/**
 * @author： 张世杰
 * @date： 2021-04-07 13:35
 */

public interface userService{
    List<UserBean> getSelectAll();

    Page<UserBean> getUserList(UserBean userBean,Integer pageNum, Integer pageSize);

   List<MeunBean> getMeunAndList(UserBean ub);

    UserBean getUserById(Long id);

    void saveUserDeptId(Long id, Long[] deptids);

    UserBean getUserAndDept(Long id);

    void saveUserByPost(UserBean userBean);

    UserBean getLoginAll(UserBean userBean);


}
