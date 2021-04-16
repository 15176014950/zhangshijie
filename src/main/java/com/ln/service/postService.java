package com.ln.service;

import com.ln.entity.MeunBean;
import com.ln.entity.PostBean;
import com.ln.utils.Page;

import java.util.List;

/**
 * @author： 张世杰
 * @date： 2021-04-08 11:24
 */
public interface postService {
    Page<PostBean> getPostAll(PostBean postBean, Integer pageNum, Integer pagSize);

    List<MeunBean> getPostAndMeunById(Long id);

    void savePostMeunByiD(Long postid, Long[] ids);
}
