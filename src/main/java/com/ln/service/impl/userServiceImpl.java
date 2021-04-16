package com.ln.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ln.entity.*;
import com.ln.mapper.DeptMapper;
import com.ln.mapper.MeunMapper;
import com.ln.mapper.UserMapper;
import com.ln.service.userService;
import com.ln.utils.MD5key;
import com.ln.utils.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author： 张世杰
 * @date： 2021-04-07 13:38
 */
@Service
public class userServiceImpl implements userService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MeunMapper meunMapper;
    @Autowired
    private DeptMapper deptMapper;

    /**
     * user查询
     *
     * @return
     */
    public List<UserBean> getSelectAll() {
        List<UserBean> list = userMapper.selectByExample(null);
        System.out.println(list);
        return list;
    }

    /**
     * 条件分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<UserBean> getUserList(UserBean userBean, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        UserBeanExample example = new UserBeanExample();
        UserBeanExample.Criteria criteria = example.createCriteria();
        if (userBean != null) {
            if (userBean.getUname() != null && userBean.getUname().length() >= 1) {
                criteria.andUnameLike("%" + userBean.getUname() + "%");
            }
            if (userBean.getAge() != null) {
                criteria.andAgeGreaterThanOrEqualTo(userBean.getAge());
            }
            if (userBean.getEage() != null) {
                criteria.andAgeLessThanOrEqualTo(userBean.getEage());
            }
        }
        List<UserBean> userBeans = userMapper.selectByExample(example);
        PageInfo<UserBean> pageInfo = new PageInfo<UserBean>(userBeans);
        Long total = pageInfo.getTotal();
        Page<UserBean> page = new Page<UserBean>(pageInfo.getPageNum() + "", total.intValue(), pageInfo.getPageSize() + "");
        page.setList(userBeans);
        return page;
    }

    /**
     * meun查询
     *
     * @return
     */
    public List<MeunBean> getMeunAndList(UserBean ub) {
        if (ub!=null){
            List<MeunBean> list=userMapper.getMeunByUserById(ub.getId());
            System.out.println(list+"菜单数据");
            return  list;
        }
        return null;
    }
    public List<MeunBean> getMeunAndList2(UserBean ub) {
        if(ub!=null){
            //菜单先展示不是按钮的菜单
            MeunBeanExample example  = new MeunBeanExample();
            MeunBeanExample.Criteria criteria = example.createCriteria();
            criteria.andIsbuttonEqualTo(0);
            List<MeunBean> list = meunMapper.selectByExample(example);
            return list;
        }

        return null;
    }

    public UserBean getUserById(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        System.out.println("====" + userBean);
        //查询这个用户又那些部门
        Long[] deptId = userMapper.getUserAndDeptId(id);
        userBean.setDeptids(deptId);
        List<DeptBean> deptBeans = deptMapper.selectByExample(null);
        userBean.setDlist(deptBeans);
        return userBean;
    }

    public void saveUserDeptId(Long id, Long[] deptids) {
        /**
         * 修改科室，要把该用户原来的科室删掉，再次新增
         * 还需要把原来的职位也删除掉（科室都变了，职位没了）
         */
        userMapper.deleteByDeptId(id);
        userMapper.deleteByPostId(id);
        if (deptids != null && deptids.length >= 1) {
            for (Long deltid : deptids) {
                userMapper.insertUserDept(id, deltid);
            }
        }

    }

    public UserBean getUserAndDept(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        //根据用户id查询下面有没有部门
        List<DeptBean> dlist = userMapper.getUserAndDeptById(id);
        //开始循环部门 查询部门里的职位
        if (dlist != null && dlist.size() >= 1) {
            for (DeptBean deptBean : dlist) {
                List<PostBean> plist = deptMapper.getDeptAndPostById(deptBean.getId());
                Long[] postids = deptMapper.getUserPostDeptBYId(id, deptBean.getId());
                deptBean.setPostid(postids);
                deptBean.setPostlist(plist);
            }
        }
        userBean.setDlist(dlist);
        return userBean;
    }

    public void saveUserByPost(UserBean userBean) {
        //先删除用户职位中间表数据
        if (userBean!=null){
            userMapper.deleteByPostId(userBean.getId());
            //先判断部门是否为空
            if (userBean.getDlist()!=null&&userBean.getDlist().size()>=1){
                for (DeptBean deptBean : userBean.getDlist()) {
                    //然后再判断部门下是否有职位
                    if (deptBean.getPostid()!=null&&deptBean.getPostid().length>=1){
                        for (Long postids : deptBean.getPostid()) {
                                userMapper.saveUserAndPostById(userBean.getId(),postids);
                        }
                    }
                }
            }
        }
    }

    public UserBean getLoginAll(UserBean userBean) {
        if (userBean!=null){
            //先用户用户名或者手机号，都是用用户名接收的，有可能用户输入的手机号，都是strint类型，无所谓
            List<UserBean> list=userMapper.getLogin(userBean);
            if (list!=null&&list.size()==1){
                //用户名或者手机号码查询到了
                //开始比对密码，比对密码之前需要加盐加密
                //加密算法有很多，常用的md5，bscript等
                UserBean userBeans = list.get(0);
                //页面用户输入的密码，进行加密加盐后和数据库里面的密码进行比较，相等正确，不相等就错误
                String pwd = userBean.getPwd();
                //也就是这里的加盐加密规则和注册的要一致，就好了，都是一个人负责的
                pwd=userBeans.getPwdsalt()+pwd+userBeans.getPwdsalt();
                MD5key md5key = new MD5key();
                String newpwd = md5key.getkeyBeanofStr(pwd);
                //相等就返回,其他的都是空
                if (newpwd.equals(userBeans.getPwd())){
                    return userBeans;
                }
            }

        }
        return null;
    }
    @Test
    public void test(){
        String pwd = "123456";
        pwd="1234"+pwd+"1234";
        MD5key md5key = new MD5key();
        String newPwd = md5key.getkeyBeanofStr(pwd);
        System.out.println(newPwd);
    }
    //回显
   /* public List<MeunBean> getMeunAndList1() {
        Long[] ids={1L,2L,3L};
        List<MeunBean> list = meunMapper.selectByExample(null);
        for (Long id:ids){
            for (MeunBean bean : list){
                if (id.equals(bean.getId())){
                    bean.setChecked(true);
                    break;
                }
            }
        }
        return list;
    }*/

}
