<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>
<head>
<title>starting page</title>
<script type="text/javascript" src="./dwr/engine.js"></script>
<script type="text/javascript" src="./dwr/util.js"></script>
<script type="text/javascript" src="./dwr/interface/Message.js"></script>
<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
<script type="text/javascript">
	var chatlog = "";
	function sendMessage() {
		var message = $("#message").val();
		var user = $("#user").val();
		Message.addMessage(user, message);
	}
	function receiveMessages(messages) {
		var lastMessage =  messages;
		chatlog = "<div>" + lastMessage + "</div>" + chatlog;

		dwr.util.setValue("list", chatlog, {
			escapeHtml : false
		});
	}
	
	//读取name值作为推送的唯一标示
    function onPageLoad(){
        var userId = getQueryString("name");
        $("#myName").html(userId);
        Message.onPageLoad(userId);
     }

    //获取url中的参数
    function getQueryString(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	    var r = window.location.search.substr(1).match(reg);
	    if (r != null) return unescape(r[2]); return null;
    }

    $(document).ready(function(){
    	dwr.engine.setActiveReverseAjax(true);
    	dwr.engine.setNotifyServerOnPageUnload(true);
    	onPageLoad();
    	}); 

</script>
</head>
<body>
         我是<span id="myName" style="color:red"></span><br/>
         推送给下面这位user<br/>    
	user:<br/>
	<input id="user" type="text" /><br/>
	推送信息为:<br/>
	<input id="message" type="text" value="hey" />
	<input type="button" value="send" onclick="sendMessage()" />
	<br>
	<div id="list"></div>
</body>
</html>