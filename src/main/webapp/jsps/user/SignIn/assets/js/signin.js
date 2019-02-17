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

/*注册名校验*/
function validusername() {
    var id="username"
    var value=$("#"+id).val();
    $.ajax({
        url: "/bookstore/UserServlet",
        data: {username: value,method:"findUserByUsername"},
        async: false,
        caches: false,
        type: "POST",
        dataType: "json",
        success: function (result) {
            if (result==true) {
                flag = true;
            }
            else {
                $("#" + id + "Error").text("用户名不存在");
                showError($("#" + id + "Error"));
                flag = false;
            }
        }
    });
    return flag;
}
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
$("#registForm").submit(function () {
    var flag=true;
    if(!validusername()){
        flag=false;
    }
    if(!validpassword()){
        flag=false;
    }
    return flag;
})