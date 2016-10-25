
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
	<script type="text/javascript" src="./js/ajaxfileupload.js"></script>
	<script type="text/javascript">
	 		function messageHeader(){
			this.bizId = "34002219231202332X";
			this.picId = document.getElementById('picId').value;
			this.appId = "app01";
			this.bizType = "";
 		}
 		function messageBody(){
 			this.base64Img = "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";
 			this.picType = "png";
 			this.expiryDate = "2067-12-21";
 		}
 		 function messageBody2(){
 			this.expiryDate = "2014-12-21";
 			this.ReservedValue1 = "身份证";
 		}
 		function response(){
 			this.version = "1.00";
 			this.header = new messageHeader();
 			this.body = new messageBody;
 		}
 		 function response2(){
 			this.version = "1.00";
 			this.header = new messageHeader();
 			this.body = new messageBody2;
 		}
	
		function setshow(){  
			$.ajax( {   
			     type : "POST",   
			     url : "<%=request.getContextPath()%>/file/base64Upload.do", 
			     data : JSON.stringify(new response()),  
			     dataType: "json", 
// 			     contentType: "application/json; charset=utf-8",
			     contentType:"application/json",
			     success : function(data) {   
			         if(data.success){ 
			         	 document.getElementById('picId').value= data.body.picId;
			         }   
			         else{   
			             alert("请求失败！");   
			         }   
			     },   
			     error :function(){   
			         alert("网络连接出错！");   
			     }   
			 });   
 		}
 		
 		function uploadFunc(){ 
 		 $.ajaxFileUpload
            (
                {
                    url:"<%=request.getContextPath()%>/file/upload2.do", 
                    secureuri: false, //是否需要安全协议，一般设置为false
                    fileElementId: 'fileUpload', //文件上传域的ID
                    data:{"requestMessage":JSON.stringify(new response2())},
                    dataType: 'json', //返回值类型 一般设置为json
                    success: function (data, status)  //服务器成功响应处理函数
                    {
						if(data.success){
						 alert("上传成功！"); 
						}else{
						  alert("失败！"); 
						}
                    },
                    error: function (data, status, e)//服务器响应失败处理函数
                    {
                        alert(e);
                    }
                }
            );
             return false;
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
<!--     	选择文件<input type="file" id="fileUpload" name="file"> -->
<!--     	<input type="text" name="requestMessage"> -->
    	<input type="button" value="上传" onclick="setshow()">
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
     	<img alt="测试图片1" src="http://127.0.0.1:8080/TestTransport/file/download.do?path=G9_vsolbg_1325_23_24_36-isd&zoomPic=true"> 
    	<input type="submit" value="获取picId">
    </form>
  </body>
</html>
