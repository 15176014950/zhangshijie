package com.ln.service;

import com.ln.entity.DeptBean;
import com.ln.utils.Page;

/**
 * @author： 张世杰
 * @date： 2021-04-08 10:34
 */
public interface deptService {
    Page<DeptBean> getDeptAll(DeptBean deptBean, Integer pageNum, Integer pagSize);

    DeptBean getDeptAndDeptId(Long deptid);

    void saveDeptPostAndId(Long id, Long[] postid);
}
