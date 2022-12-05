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


//打开注册协议弹层
function alertBox(maskid,bosid){
	$("#"+maskid).show();
	$("#"+bosid).show();
}
//关闭注册协议弹层
function closeBox(maskid,bosid){
	$("#"+maskid).hide();
	$("#"+bosid).hide();
}

//注册协议确认
$(function() {
	$("#agree").click(function(){
		var ischeck = document.getElementById("agree").checked;
		if (ischeck) {
			$("#btnRegist").attr("disabled", false);
			$("#btnRegist").removeClass("fail");
		} else {
			$("#btnRegist").attr("disabled","disabled");
			$("#btnRegist").addClass("fail");
		}
	});

	$("#phone").blur(function (){
		var phone=$.trim($("#phone").val());
		if(phone == ""){
			showError("phone","请输入正确的手机号");
		}else if(!/^1(3\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\d|9[0-35-9])\d{8}$/.test(phone))
		{
			showError("phone","请输入正确的手机号")
		}else{
			//然后进入数据库查询手机号是否存在，存在则通过，否则则失败
			$.ajax({
				url: "/p2p/enroll/checkPhone",
				type:"post",
				data:{"phone":phone},
				success:function (data) {
					if(data.code==1){
						showSuccess("phone")
					}else {
						showError("phone",data.message);
					}
				},error:function () {
					showError("phone","系统繁忙");
				}
			})
		}
	})
 $("#loginPassword").blur(function (){
        var password = $.trim($("#loginPassword").val());
        if(password==""){
        	showError("loginPassword","密码不能为空");
		}else if(20<password.length||password.length<6){
        	showError("loginPassword","密码长度在6-20位");
		}else  if(!/^(?=\S*[a-z])(?=\S*\d)\S{8,}$/){
        	showError("loginPassword","请输入6-20位字母和数字混合密码");
		}else{
        	//然后就正确
			showSuccess("loginPassword");
		}



 })
	//验证码失去焦点
	$("#messageCode").blur(function (){
		var messageCode =$.trim($("#messageCode").val())
		if(messageCode==""){
			showError("messageCode","验证码不能为空")
		}else if(!/^\d{6}$/.test($("#messageCode").val())){
			showError("messageCode","请输入正确格式的验证码")
		}
		else hideError("messageCode")
	})
	//点击验证码
	$("#messageCodeBtn").click(function () {
  //判断手机号密码触发事件
		$("#phone").blur();
		$("#loginPassword").blur();
         hideError("messageCode");
		var erroText=$("div[id$='Err']").text();
		if(erroText==""){
			//没有错的时候进行发送短信
			var phone=$.trim($("#phone").val())
			$.ajax({
				url:"/p2p/user/Verifaction/",
				type:"post",
				data:{
					"phone":phone,
				},
				success:function (data) {
					if(data.code==1){
						//则发送成功开始倒计时
						alert(data.data);
						if(!$("#messageCodeBtn").hasClass("on")){
							$.leftTime(60,function (d) {
								//d:d.status=true,倒计时中
								if(d.status){
									$("#messageCodeBtn").text(d.s=='00' ? '60秒后获取':d.s+'秒后获取')
									$("#messageCodeBtn").addClass("on");
								}else{
									$("#messageCodeBtn").text('60秒后获取')
									$("#messageCodeBtn").removeClass("on");
								}
							})
						}
					}
				}

			})

		}


	})
	//点击注册
	$("#btnRegist").click(function (){
		$("#phone").blur();
		$("#loginPassword").blur();
		$("#messageCode").blur();
		//判断手机号和密码是都通过
		var erroText=$("div[id$='Err']").text();
		alert(erroText);
		if(erroText==""){
			//然后开始注册
			var phone=$.trim($("#phone").val());
			var password=$.trim($("#loginPassword").val());
			var messageCode =$.trim($("#messageCode").val());
			$("#loginPassword").val($.md5(password));
			$.ajax({
				url:"/p2p/enroll/register",
				type: "post",
				data: {"phone":phone,
					"password":$.md5(password),
					"messageCode":messageCode
				},
				success:function (data) {
					if(data.code==1){
						alert(data.message)
						window.location.href="/p2p/user/realName"
					}else{
						showError("messageCode","验证码错误")
					}
				},
				error:function () {
					alert("系统繁忙，请重新注册");
				}
			})
		}


	})

});



