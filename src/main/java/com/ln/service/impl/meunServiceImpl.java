package com.ln.service.impl;

import com.ln.entity.MeunBean;
import com.ln.entity.MeunBeanExample;
import com.ln.mapper.MeunMapper;
import com.ln.service.meunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： 张世杰
 * @date： 2021-04-08 15:20
 */
@Service
public class meunServiceImpl implements meunService {
    @Autowired
    private MeunMapper meunMapper;

    public List<MeunBean> getMeunAll(Long pid) {
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        return meunMapper.selectByExample(example);
    }

    public void saveByMeunAdd(MeunBean meunBean) {

        if(meunBean!=null){
            System.out.println("===========");
            if(meunBean.getId()!=null){
                System.out.println("修改11111111");
            }else{
                int i = meunMapper.insertSelective(meunBean);
                System.out.println(i+"354555555555");
            }
        }
    }

    List<Long> ids = new ArrayList<Long>();
    public void deleteById(Long id) {
        getMeunAllToDelete(id);

        for(Long id1:ids){
            System.out.println(ids);
            meunMapper.deleteByPrimaryKey(id1);
        }
    }

    private void getMeunAllToDelete(Long pid) {
        ids.add(pid);
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        List<MeunBean> list = meunMapper.selectByExample(example);
        if (list!=null && list.size()>=1){
            for (MeunBean bean : list) {
                getMeunAllToDelete(bean.getId());
            }
        }
    }
}
