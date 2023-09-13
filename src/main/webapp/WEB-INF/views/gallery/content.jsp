<%@page import="org.sp.springborder.domain.GalleryImg"%>
<%@page import="org.sp.springborder.domain.Gallery"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Gallery gallery=(Gallery)request.getAttribute("gallery");
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

* {
	box-sizing: border-box;
}

input[type=text], select, textarea {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	margin-top: 6px;
	margin-bottom: 16px;
	resize: vertical;
}

input[type=button] {
	background-color: #04AA6D;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

input[type=button]:hover {
	background-color: #45a049;
}

.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}
</style>

<%@ include file="./inc/head_link.jsp" %>

<script type="text/javascript">
function regist(){
	$("form").attr({
		action:"/gallery/regist",
		method:"post",
		enctype:"multipart/form-data"
	});
	$("form").submit();
}
$(function(){
	$("#bt_edit").click(function(){
		
			$("form").attr({
				action:"/gallery/update",
				method:"post"
			});
			$("form").submit();
			
		
	});
	$("#bt_del").click(function(){
		if(confirm("삭제하시겠어요?")){
			//삭제요청시 from태그 안에 작성된 파라미터들을 한꺼번에 전송하자
			$("form").attr({
				action:"/gallery/delete",
				method:"post"
			});
			$("form").submit();
			
		}
	});
	
	$("#bt_list").click(function(){
		location.href="/gallery/list";
	});
});
</script>
</head>
<body>

	<h3>상세보기</h3>

	<div class="container">
		<form>
		<input type="hidden" name="gallery_idx" value="<%=gallery.getGallery_idx()%>">
		
			<input type="text" name="title" value="<%=gallery.getTitle()%>"> 
			<input type="text" name="writer" value="<%=gallery.getWriter()%>">

			<textarea id="content" name="content"
				style="height: 200px"><%=gallery.getContent() %></textarea>
	
		<%for(int i=0;i<gallery.getGalleryImgList().size();i++){ %>
		<%GalleryImg galleryImg=gallery.getGalleryImgList().get(i); %>
			<input type="hidden" name="filename" value="<%=galleryImg.getFilename()%>">
			<P>
				<img src="/static/data/<%=galleryImg.getFilename()%>" width="150px">
			</P>
			<%} %>
			
			<p>
				<input type="button" value="수정" id="bt_edit">
				<input type="button" value="삭제" id="bt_del">
				<input type="button" value="목록" id="bt_list">
				
				
		</form>
	</div>

</body>
</html>
