function showError(ele) {
    var text=ele.text();
    if(!text){
        ele.css("display","none");
    }
    else{
        ele.css("display","block");
    }
}
$(function () {
    /*输入框得到焦点进行错误信息隐藏*/
    $(".form-control").focus(function () {
        var errorId=$(this).attr("id")+"Error";
        $("#"+errorId).text("");
    });

    /*输入框失去焦点进行校验*/
    $(".form-control").blur(function () {
        var id=$(this).attr("id");
        var method_name="valid"+id;
        eval(method_name+"()");
    });
});
/*电子邮箱校验*/
function validemail() {
    var id="email";
    var value=$("#"+id).val();
    /*非空校验*/
    if(!value){
        $("#"+id+"Error").text("邮箱不能为空");
        showError($("#"+id+"Error"));
        return false;
    }
    /*格式校验*/
    if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(value)){
        $("#"+id+"Error").text("邮箱格式错误");
        showError($("#"+id+"Error"));
        return false;
    }
    var email=$("#"+id).val();
    var flag;
    $.ajax({
        url:"/things_web/UserController/findUserByEmail.action",
        data:{email:email},
        type:"POST",
        dataType:"json",
        async:false,
        caches:false,
        success:function (result) {
            if(result){
                flag=true;
            }
            else{
                $("#"+id+"Error").text("该邮箱未被注册");
                showError($("#"+id+"Error"));
                flag=false;
            }
        }
    })
    return flag;
}
/*密码校验*/
function validpassword() {
    var id="password"
    var value=$("#"+id).val();
    /*非空校验*/
    if(!value){
        $("#"+id+"Error").text("密码不能为空");
        showError($("#"+id+"Error"));
        return false;
    }
    /*长度校验*/
    if(value.length<8||value.length>20){
        $("#"+id+"Error").text("密码长度必须在8-20之间");
        showError($("#"+id+"Error"));
        return false;
    }
    return true;


}


/*二次密码校验*/
function validconfirmpassword() {
    var id="confirmpassword";
    var value=$("#"+id).val();
    var password=$("#password").val();
    /*非空校验*/
    if(!value){
        $("#"+id+"Error").text("确认密码不能为空");
        showError($("#"+id+"Error"));
        return false;
    }
    /*长度校验*/
    if(value!=password){
        $("#"+id+"Error").text("两次密码输入不一样");
        showError($("#"+id+"Error"));
        return false;
    }
    return true;
}




$("#registForm").submit(function () {
    if(validemail()===false){
        return false;
    }
    if(validpassword()===false){
        return false;
    }
    if(validconfirmpassword()===false){
        return false;
    }
    return true;
});