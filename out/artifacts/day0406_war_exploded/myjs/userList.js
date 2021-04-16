var vm= new Vue({
    el:"#userListDiv",
    data:{
        userList:[],
        dlist:[{postid:[]}],
        deptids:[],
        entity:{},
        page:{},
        pageNum:1,
        pageSize:3,
        mingziselect:{}
    },
    methods:{
        getMohuConn:function () {
                var _this=this;
                axios.post("../user/getUserList.do?pageNum="+_this.pageNum+"&pageSize="+_this.pageSize,_this.mingziselect).then(function (response) {
                    //console.log(response.data)
                    _this.userList=response.data.list;
                    _this.pageNum = response.data.currentPage;
                    _this.pageSize = response.data.pageSize;
                    _this.page = response.data;
                });
        },
        paging:function (pageNum) {
            this.pageNum = pageNum;
            this.getMohuConn();
        },
        toUserDept:function (id) {
            //alert("dsgdsgdsg")
            //查出这个用户已经拥有的部门id，和全部部门，查询一个对象，完全可以， 也可以直接查询一个user
            var _this=this;
            axios.get('../user/getUserVoById.do?id='+id).then(function (response) {
                _this.entity=response.data;
                _this.deptids=response.data.deptids;
                _this.dlist=response.data.dlist;
                document.getElementById("userDeptDiv").style.display="block";
            })
        },
        saveUserDept:function () {
            /*var name = document.getElementsByName("deptids");
            for(var x=0;x<name.length;x++){
                if(name[x].checked){
                    this.deptids.push(name[x].value);
                }
            }
            alert(this.deptids);*/
            var _this=this;
            axios.post('../user/saveUserDeptById.do?id='+_this.entity.id,_this.deptids).then(function (response) {
                if(response.data.flag){
                    alert("成功");
                    document.getElementById("userDeptDiv").style.display="none";
                    _this.getMohuConn();
                }else{
                    alert(response.data.msg);
                }
            })
        },
        toUserPost:function (id) {
            var _this=this;
            axios.get('../user/getUserDeptById.do?id='+id).then(function (response) {
              _this.entity= response.data;
              _this.dlist=response.data.dlist;
              document.getElementById("userPostDiv").style.display="block";
            })
        },
        saveUserDeptPost:function () {
           this.entity.dlist=this.dlist;
           var _this=this;
           axios.post('../user/saveUserAndPostId.do',_this.entity).then(function (response) {
               if (response.data.flag){
                   alert(response.data.msg);
                   document.getElementById("userPostDiv").style.display="none";
               }else{
                   alert(response.data.msg);
               }
           })
        }
    }
})
vm.getMohuConn();