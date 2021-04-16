package com.ln.entity;

import java.util.List;

public class DeptBean {
    private Long id;

    private String deptname;

    private Long[] postid;

    private List<PostBean> postlist;

    public Long[] getPostid() {
        return postid;
    }

    public void setPostid(Long[] postid) {
        this.postid = postid;
    }

    public List<PostBean> getPostlist() {
        return postlist;
    }

    public void setPostlist(List<PostBean> postlist) {
        this.postlist = postlist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
    }

}