
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="./js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript">
	 		function messageHeader(){
			this.bizId = "34002219231202332X";
			this.picId = "";
			this.appId = "app01";
			this.bizType = "";
 		}
 		function messageBody(){
 			this.num = "1";
 			this.picId = "";
 		}
 		function response(){
 			this.version = "1.00";
 			this.header = new messageHeader();
 			this.body = new messageBody;
 		}
	
		function setshow(){  
			$.ajax( {   
			     type : "POST",   
			     url : "<%=request.getContextPath()%>/getPicId.do", 
			     data : JSON.stringify(new response()),  
			     dataType: "json", 
// 			     contentType: "application/json; charset=utf-8",
			     contentType:"application/json",
			     success : function(data) {   
			         if(data.success){   
			             alert("设置成功！");   
			              
			         }   
			         else{   
			             alert("设置失败！");   
			         }   
			     },   
			     error :function(){   
			         alert("网络连接出错！");   
			     }   
			 });   
 		}
	</script>
  </head>
  
  <body>
    <form action="testAjax.do">
    	获取的iD：<input id="picId" type="text">
    	<input id="click2" name="click2" type="button" value="获取PicId" onclick="setshow()">
    </form>
    <br>
    <br>
    <form action="file/upload.do" method="post" enctype="multipart/form-data">
    	选择文件<input type="file" name="file">
    	<input type="text" name="requestMessage">
    	<input type="submit" value="上传">
    </form>
    <form action="file/viewPic.do" method="post">
    	<input type="text" name="requestMessage">
    	<input type="submit" value="調閱">
    </form>
    <form action="file/delete.do" method="post">
    	<input type="text" name="requestMessage">
    	<input type="submit" value="删除">
    </form>
     <form action="getPicId.do" method="post">
     	<input type="text" name="requestMessage">
     	<img alt="测试图片1" src="http://127.0.0.1:8080/ImagePlatform/file/download.do?path=G9_vsolbg_1325_23_24_36-isd&zoomPic=true"> 
    	<input type="submit" value="获取picId">
    </form>
  </body>
</html>
