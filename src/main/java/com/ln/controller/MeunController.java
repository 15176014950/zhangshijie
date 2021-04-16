package com.ln.controller;

import com.ln.entity.MeunBean;
import com.ln.service.meunService;
import com.ln.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author： 张世杰
 * @date： 2021-04-08 15:19
 */
@RestController
@RequestMapping("meun")
public class MeunController {
    @Autowired
    private meunService meunService;

    @RequestMapping("getMeunList")
    public List<MeunBean> getMeunList(@RequestParam(defaultValue = "1") Long pid){
            return meunService.getMeunAll(pid);
    }

    @RequestMapping("getByMeun")
    public ResultInfo getByMeun(@RequestBody MeunBean meunBean){
        try {
            meunService.saveByMeunAdd(meunBean);
            return new ResultInfo(true, "成功");
        }catch (Exception e){
            return new ResultInfo(false, "失败");
        }
    }

    @RequestMapping("deleteMeunId")
    public ResultInfo deleteMeunId(Long id){
        try {
            meunService.deleteById(id);
            return new ResultInfo(true, "成功");
        }catch (Exception e){
            return new ResultInfo(false, "失败");
        }
    }
}
