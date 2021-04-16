package com.ln.controller;

import com.ln.entity.MeunBean;
import com.ln.entity.UserBean;
import com.ln.service.userService;
import com.ln.utils.Page;
import com.ln.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author： 张世杰
 * @date： 2021-04-07 13:35
 */
@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private userService userService;

 /*   @RequestMapping("/getData")
    public Page<UserBean> getData(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "3") Integer pageSize){
        UserBean userBean = new UserBean();
        userBean.setUname("a");
        userBean.setAge(12);
        return userService.getUserList(userBean,pageNum,pageSize);
    }
*/
    @RequestMapping("getUserList")
    public Page<UserBean> getUserList(@RequestBody UserBean userBean,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "3") Integer pageSize){
        return userService.getUserList(userBean,pageNum, pageSize);
    }

    @RequestMapping("getLogin")
    public ResultInfo getLogin(@RequestBody UserBean userBean, HttpServletRequest request){
        UserBean userBean1 = userService.getLoginAll(userBean);
        if(userBean1==null){
            return  new ResultInfo(false, "登录失败,用户名或者密码错误");
        }else {
            request.getSession().setAttribute("ub", userBean1);
            return  new ResultInfo(true, "成功");
        }
    }

    @RequestMapping("getMeunList")
    public List<MeunBean> getMeunList(HttpServletRequest request){
        /*
        在这里需要知道，是谁登录的
        还要查询出不是按钮的菜单
         */
        UserBean ub = (UserBean) request.getSession().getAttribute("ub");
        System.out.println(ub+"session");
        return userService.getMeunAndList(ub);
    }
    /*
    去给用户选择部门
     */
    @RequestMapping("getUserVoById")
    public UserBean getUserVoById(Long id){
        return  userService.getUserById(id);
    }

    @RequestMapping("saveUserDeptById")
    public ResultInfo saveUserDeptById(Long id,@RequestBody Long[] deptids){
        try {
            userService.saveUserDeptId(id,deptids);
            return  new ResultInfo(true, "成功");
        }catch (Exception e){
            return  new ResultInfo(false, "失败");
        }
    }
    /*
    去给用户分配职位
     */
    @RequestMapping("getUserDeptById")
    public UserBean getUserDeptById(Long id){
        return  userService.getUserAndDept(id);
    }

    /*
    保存用户分配职位
     */
    @RequestMapping("saveUserAndPostId")
    public ResultInfo saveUserAndPostId(@RequestBody UserBean userBean){
        try {
            userService.saveUserByPost(userBean);
            return  new ResultInfo(true, "成功");
        }catch (Exception e){
            return  new ResultInfo(false, "失败");
        }
    }
}
