var vm = new Vue({
    el:'#logindiv',
    data:{
        userBean:{}
    },
    methods:{
        getLogin:function () {
            var _this = this;
            axios.post("user/getLogin.do",_this.userBean).then(function (response) {
               if (response.data.flag){
                   location.href="/pages/main.html";
               }else {
                   alert(response.data.msg);
               }
            });
        }
    }
});