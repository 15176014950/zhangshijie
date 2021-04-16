package com.ln.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ln.entity.*;
import com.ln.mapper.MeunMapper;
import com.ln.mapper.PostMapper;
import com.ln.service.postService;
import com.ln.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author： 张世杰
 * @date： 2021-04-08 11:24
 */
@Service
public class postServiceImpl implements postService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private MeunMapper meunMapper;


    public Page<PostBean> getPostAll(PostBean postBean, Integer pageNum, Integer pagSize) {
        PageHelper.startPage(pageNum, pagSize);
        PostBeanExample example = new PostBeanExample();
        PostBeanExample.Criteria criteria = example.createCriteria();
        if (postBean!=null){
            if (postBean.getPostname()!=null && postBean.getPostname().length()>=1 ){
                criteria.andPostnameLike("%"+postBean.getPostname()+"%");
            }
        }
        List<PostBean> postBeans = postMapper.selectByExample(example);
        PageInfo<PostBean> pageInfo = new PageInfo<PostBean>(postBeans);
        Long total = pageInfo.getTotal();
        Page<PostBean> page = new Page<PostBean>(pageInfo.getPageNum()+"",total.intValue(),pageInfo.getPageSize()+"");
        page.setList(postBeans);
        return page;
    }

    public List<MeunBean> getPostAndMeunById(Long postid) {
        //全查菜单
        List<MeunBean> list = meunMapper.selectByExample(null);
        //当前职位拥有那些菜单，去中间表查询，主要id
        List<Long> meunid=postMapper.getPostMeunById(postid);
        if (meunid!=null&& meunid.size()>=1){
            for (Long meunids : meunid) {
                for (MeunBean bean:list){
                    if (meunids.equals(bean.getId())){
                        bean.setChecked(true);
                        break;
                    }

                }
            }

        }
        return list;
    }

    public void savePostMeunByiD(Long postid, Long[] ids) {
        //先去删除中间表的数据
        postMapper.deletePostMeunByPostId(postid);

        if (ids!=null&&ids.length>=1){
            for (Long meunid : ids) {
                postMapper.insertPostMeun(postid,meunid);
            }
        }
    }
}
