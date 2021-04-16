package com.ln.controller;

import com.ln.entity.DeptBean;
import com.ln.service.deptService;
import com.ln.utils.Page;
import com.ln.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： 张世杰
 * @date： 2021-04-08 10:34
 */
@RestController
@RequestMapping("dept")
public class DeptController {
    @Autowired
    private deptService deptService;

    @RequestMapping("getDeptList")
    public Page<DeptBean> getDeptList(@RequestBody DeptBean deptBean, @RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "3")Integer pagSize){
            return deptService.getDeptAll(deptBean,pageNum,pagSize);
    }

    @RequestMapping("getDeptByDeotId")
    public DeptBean getDeptByDeotId(Long deptid){
        return deptService.getDeptAndDeptId(deptid);
    }

    @RequestMapping("saveDeptPostId")
    public ResultInfo saveDeptPostId(Long id,@RequestBody Long[] postid){
        try {
            deptService.saveDeptPostAndId(id,postid);
            return new ResultInfo(true,"成功");
        }catch (Exception e){
            return new ResultInfo(false, "失败");
        }
    }
}
