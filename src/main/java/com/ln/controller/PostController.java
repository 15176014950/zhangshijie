package com.ln.controller;

import com.ln.entity.MeunBean;
import com.ln.entity.PostBean;
import com.ln.service.postService;
import com.ln.utils.Page;
import com.ln.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author： 张世杰
 * @date： 2021-04-08 11:23
 */
@RestController
@RequestMapping("post")
public class PostController {
    @Autowired
    private postService postService;

    @RequestMapping("getPostList")
    public Page<PostBean> getPostList(@RequestBody PostBean postBean, @RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "3")Integer pagSize){
        return postService.getPostAll(postBean,pageNum,pagSize);
    }

    @RequestMapping("getMeunListById")
    public List<MeunBean> getMeunListById(Long id){
        return postService.getPostAndMeunById(id);
    }

    @RequestMapping("savePostMeun")
    public ResultInfo savePostMeun(Long postid,@RequestBody Long[] ids){
        try {
            postService.savePostMeunByiD(postid,ids);
            return  new ResultInfo(true, "成功");
        }catch (Exception e){
            return  new ResultInfo(false, "失败");
        }
    }
}
