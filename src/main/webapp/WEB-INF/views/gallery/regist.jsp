<%@ page contentType="text/html; charset=UTF-8"%>
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
	$("#bt_regist").click(function(){
		regist();
	});
	
	$("#bt_list").click(function(){
		location.href="/gallery/list";
	});
});
</script>
</head>
<body>

	<h3>Contact Form</h3>

	<div class="container">
		<form>
			<input type="text" name="title" placeholder="제목"> <input
				type="text" name="writer" placeholder="작성자">

			<textarea id="content" name="content" placeholder="Write something.."
				style="height: 200px"></textarea>

			<input type="file" name="photo"> <br> <input type="file"
				name="photo">
			<p>
				<input type="button" value="등록" id="bt_regist">
				<input type="button" value="목록" id="bt_list">
				
				
		</form>
	</div>

</body>
</html>
