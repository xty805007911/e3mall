<html>
<head>
<meta charset="UTF-8">
<title>freemarker测试</title>
</head>
<body>
	1.取map中的value:hello,${name}					<br/>
<hr/>
	2.取pojo:<br/>
	学生id:${stu.id},name:${stu.name},addr:${stu.addr}<br/>
<hr/>	
	3.取List<pojo>:<br/>
<table border=1>
	<tr>
		<td>序号</td><td>id</td><td>name</td><td>address</td>
	</tr>
	<#list studentList as student>
	<#if student_index % 2 == 0>
		<tr bgcolor='blue'>
	<#else>
		<tr bgcolor='red'>
	</#if>
	
		<td>${student_index+1}</td>
		<td>${student.id}</td>
		<td>${student.name}</td>
		<td>${student.addr}</td>
	</tr>
	</#list>
</table>
<hr/>
	4.时间类型格式化:<br/>
	当前日期：${date?date}	<br/>
	当前时间：${date?time}	<br/>
	当前日期和时间：${date?datetime}	<br/>
	自定义日期格式（推荐）：${date?string("yyyy/MM/dd HH:mm:ss")}	<br/>
<hr/>
	5.null的处理<br/>
	<!-- 如果val为空，则输出该字符串 -->
	${val!"val为null"}
<hr/>
	6.引入文件
	<#include "hello.ftl">


</body>
</html>