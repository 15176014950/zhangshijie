package com.ln.service;

import com.ln.entity.MeunBean;

import java.util.List;

/**
 * @author： 张世杰
 * @date： 2021-04-08 15:19
 */
public interface meunService {
    List<MeunBean> getMeunAll(Long pid);

    void saveByMeunAdd(MeunBean meunBean);

    void deleteById(Long id);
}
