
//同意实名认证协议
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
	//验证手机号
	$("#phone").blur(function (){
		var phone =$.trim($("#phone").val());
		if(phone==""){
			showError("phone","请输入手机号")
		}else if(!/^1(3\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\d|9[0-35-9])\d{8}$/.test(phone)){
			showError("phone","请输入正确的手机号")
		}else  showSuccess("phone")
	})
	//验证姓名
	$("#realName").blur(function (){
		var realName =$.trim($("#realName").val())
		if(realName==""){
			showError("realName","请输入姓名")
		}else if(/[^\u4e00-\u9fa5]/.test(realName)){
			showError("realName","请输入正确的姓名")
		}else  showSuccess("realName")
	})
	//验证身份证号
	$("#idCard").blur(function (){
		var nameNumber =$.trim($("#idCard").val())
		if(nameNumber==""){
			showError("idCard","请输入身份证号")
		}else if(!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(nameNumber)){
			showError("idCard","请输入正确的身份证")
		}else showSuccess("idCard")
	})

	//验证验证码
	$("#messageCode").blur(function (){
			var messageCode = $.trim($("#messageCode").val())
			if (messageCode == "") {
				showError("messageCode", "请输入验证码")
			} else if (!/^\d{6}$/.test($("#messageCode").val())) {
				showError("messageCode", "请输入正确格式的验证码")
			}
		})


	//验证码点击事件
	$("#messageCodeBtn").click(function (){
		//让前几个失去焦点，并且隐藏验证码错误
		$("#phone").blur();
		$("#realName").blur();
		$("#idCard").blur();
		hideError("messageCode");
		var erroText=$("div[id$='Err']").text();
		if(erroText==""){
			//然后发送短信，成功之后倒计时
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
									alert(data.messageCode);
								}else{
									$("#messageCodeBtn").text('60秒后获取')
									$("#messageCodeBtn").removeClass("on");
								}
							})
						}
					}
				}

			})
		}else {
			showError("messageCode","系统繁忙")
		}


	})

	//点击认证
	$("#btnRegist").click(function (){
		//触发失去焦点事件
		$("#messageCode").blur();
		$("#phone").blur();
		$("#idCard").blur();
		$("#realName").blur();
		var erroText=$("div[id$='Err']").text();
		if(erroText==""){
			//通过之后然后拿到值
			var phone=$.trim($("#phone").val())
			var messageCode=$.trim($("#messageCode").val())
			var idCard=$.trim($("#idCard").val())
			var realName=$.trim($("#realName").val())
			$.ajax({
				url:"/p2p/user/certion",
				type:"post",
				data:{
					"messageCode":messageCode,
					"idCard":idCard,
					"realName":realName,
				},
				success:function (data) {
					if(data.code==1){
						//成功之后返回首页
						alert("实名成功")
						window.location.href="/p2p/loan/myCenter"
					}else {
						alert(data.message)
					}
				}
			})
		}
	})



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
});

