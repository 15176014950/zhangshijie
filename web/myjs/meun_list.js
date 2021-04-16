var vm= new Vue({
    el:"#meunDiv",
    data:{
        meunlist:[],
        pid:1,
        entity:{}
    },
    methods:{
        getMeunListByPid:function (pid) {
            this.pid=pid;
            var _this=this;
            axios.get('../meun/getMeunList.do?pid='+pid).then(function (response) {
                    _this.meunlist=response.data;
            })
        },
        ByMeun:function () {
            //alert("gsgdsgdsg")
            this.entity.pid = this.pid;
            var _this=this;
            axios.post("../meun/getByMeun.do",_this.entity).then(function (response) {
                //console.log(_this.entity)
                if (response.data.flag){
                    _this.getMeunListByPid(_this.pid);
                    document.getElementById("meunupdatediv").style.display="none";
                }else {
                    alert(response.data.msg)
                }
            })
        },
        toAddMeun:function () {
            this.entity={};
            document.getElementById("meunupdatediv").style.display="block";
        },
        deleteMeunId:function (id) {
            var _this=this;
            axios.get('../meun/deleteMeunId.do?id='+id).then(function (response) {
                if (response.data.flag){
                    _this.getMeunListByPid(_this.pid);
                }else {
                    alert(response.data.msg)
                }
            })
        }
    },
})
vm.getMeunListByPid(1);