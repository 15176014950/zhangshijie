package com.ln.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ln.entity.DeptBean;
import com.ln.entity.DeptBeanExample;
import com.ln.entity.PostBean;
import com.ln.mapper.DeptMapper;
import com.ln.mapper.PostMapper;
import com.ln.service.deptService;
import com.ln.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author： 张世杰
 * @date： 2021-04-08 10:35
 */
@Service
public class deptServiceImpl implements deptService {
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private PostMapper postMapper;

    public Page<DeptBean> getDeptAll(DeptBean deptBean, Integer pageNum, Integer pagSize) {
        PageHelper.startPage(pageNum, pagSize);
        DeptBeanExample example = new DeptBeanExample();
        DeptBeanExample.Criteria criteria = example.createCriteria();
        if (deptBean!=null){
            if (deptBean.getDeptname()!=null && deptBean.getDeptname().length()>=1 ){
                    criteria.andDeptnameLike("%"+deptBean.getDeptname()+"%");
            }
        }
        List<DeptBean> deptBeans = deptMapper.selectByExample(example);
        PageInfo<DeptBean> pageInfo = new PageInfo<DeptBean>(deptBeans);
        Long total = pageInfo.getTotal();
        Page<DeptBean> page = new Page<DeptBean>(pageInfo.getPageNum()+"",total.intValue(),pageInfo.getPageSize()+"");
        page.setList(deptBeans);
        return page;
    }

    public DeptBean getDeptAndDeptId(Long deptid) {
        DeptBean deptBean = deptMapper.selectByPrimaryKey(deptid);

        List<PostBean> postBeans = postMapper.selectByExample(null);
        deptBean.setPostlist(postBeans);

        Long[] postids=deptMapper.getDeptByPostId(deptid);
        deptBean.setPostid(postids);
        return deptBean;
    }

    public void saveDeptPostAndId(Long id, Long[] postid) {
        /**
         * 先删除tb_dept_post 的职位
         * 再新增职位
         */
        deptMapper.deletePostById(id);
        if (postid!=null && postid.length>=1){
            for (Long postids : postid) {
                deptMapper.insertDeptAndPost(id,postids);
            }
        }
    }
}
