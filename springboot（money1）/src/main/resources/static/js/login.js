var referrer = "";//登录后返回页面
referrer = document.referrer;
if (!referrer) {
	try {
		if (window.opener) {                
			// IE下如果跨域则抛出权限异常，Safari和Chrome下window.opener.location没有任何属性              
			referrer = window.opener.location.href;
		}  
	} catch (e) {
	}
}

//按键盘Enter键即可登录
$(document).keyup(function(event){
	if(event.keyCode == 13){
		login();
	}

});
//错误提示
function showError(id,msg) {
	$("#"+id+"Ok").hide();
	$("#"+id+"Err").html("<i></i><p>"+msg+"</p>");
	$("#"+id+"Err").show();
	$("#"+id).addClass("input-red");
}
//错误隐藏
function hideError(id) {
	$("#"+id+"Err").hide();
	$("#"+id+"Err").html("");
	$("#"+id).removeClass("input-red");
}
//显示成功
function showSuccess(id) {
	$("#"+id+"Err").hide();
	$("#"+id+"Err").html("");
	$("#"+id+"Ok").show();
	$("#"+id).removeClass("input-red");
}
$(function () {
//手机号失去焦点
	$("#phone").blur(function () {
		var phone = $.trim($("#phone").val());
		if (phone == "") {
			showError("phone", "请输入手机号");
		} else if (!/^1(3\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\d|9[0-35-9])\d{8}$/.test(phone)) {
			showError("phone", "请输入正确的手机号")
		}else{
			showSuccess("phone")
		}
	})
	//密码框失去焦点
	$("#loginPassword").blur(function () {
		var password = $.trim($("#loginPassword").val());
		if (password == "") {
			showError("loginPassword", "密码不能为空");
		} else if (20 < password.length || password.length < 6) {
			showError("loginPassword", "密码长度在6-20位");
		} else if (!/^(?=\S*[a-z])(?=\S*\d)\S{8,}$/) {
			showError("loginPassword", "请输入6-20位字母和数字混合密码");
		} else {
			//然后就正确
			showSuccess("loginPassword");
		}
	})
	$("#migtext").blur(function mig (){
		var message=$.trim($("#migtext").val());
		var ErroText=$("div[id$='Err']").text()
		if(ErroText==""){
			//然后进去验证
			if(message==""){
				showError("migtext","请输入验证码")
				//因为找不到原因的错误，这里错误只能保持五秒
				$.leftTime(5,function (d){
    if(d.status){
		showError("migtext","请输入验证码")
	}else {
    	hideError("migtext")
	}
	})
			}
		}

	})
	$("#loginBtn").click(function (){
		hideError("migtext")
       //触发失去焦点事件
		$("#loginPassword").blur();
		$("#phone").blur();
		$("#migtext").blur();
		var ErroText=$("div[id$='Err']").text()
		if(ErroText=="") {
			//然后进入后台验证登录
			//先验证验证码然后验证手机号
			var message=$.trim($("#migtext").val());
			$.ajax({
				url: "/p2p/Login_authentication",
				type: "post",
				data: {
					"message": message
				},
				success: function (data) {
					if (data.code == 1) {
						//然后进行手机号和密码判断
						var phone=$.trim($("#phone").val())
						var passWord=$.trim($("#loginPassword").val())
						$("#loginPassword").val($.md5(passWord));
                       $.ajax({
						   url:"/p2p/loan/login",
						   type: "post",
						   data:{
						   	"phone":phone,
							"passWord":$.md5(passWord),
						   },
						   success:function (d){
						   	 if(d.code==1){
						   	 	alert(d.message)
								 window.location.href="/p2p/index"
							 }
						   },error:function (){
							   alert("验证登录失败，系统繁忙");
						   }
					   })
					} else {
						showError("migtext", data.message)
					}
				},error:function (){
					alert("系统繁忙");
				}
			})
		}
	})


})




