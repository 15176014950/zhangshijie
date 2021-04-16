var vm = new Vue({
    el:"#deptDiv",
    data:{
        deptlist:[],
        pageNum:1,
        pageSize:3,
        page:{},
        searchEntity:{},
        entity:{},
        postid:[],
        postlist:[]
    },
    methods:{
        getDeptListConn:function () {
            var _this = this;
            axios.post("../dept/getDeptList.do?pageNum="+_this.pageNum+"&pageSize="+_this.pageSize,_this.searchEntity).then(function (response) {
                _this.deptlist = response.data.list;
                _this.pageNum = response.data.currentPage;
                _this.pageSize = response.data.pageSize;
                _this.page = response.data;
            });
        },
        paging:function (pageNum) {
            this.pageNum = pageNum;
            this.getDeptListConn();
        },
        getDeptPostConn:function (deptid) {
                var _this = this;
                axios.get('../dept/getDeptByDeotId.do?deptid='+deptid).then(function (response) {
                    _this.entity=response.data;
                    _this.postlist=response.data.postlist;
                    _this.postid=response.data.postid;
                    document.getElementById("deptPostDiv").style.display="block";
                })
        },
        saveDeptPostId:function () {
            var _this=this;
            axios.post('../dept/saveDeptPostId.do?id='+_this.entity.id,_this.postid).then(function (response) {
                if (response.data.flag){
                    alert("成功");
                    _this.getDeptListConn();
                    document.getElementById("deptPostDiv").style.display="none";
                }else{
                    alert(response.data.msg);
                }
            })
        }
    }
})
vm.getDeptListConn();