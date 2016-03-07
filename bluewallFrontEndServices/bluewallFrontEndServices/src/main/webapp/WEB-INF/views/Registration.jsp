<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration</title>
</head>
<body>
    <div align="center">
        <form:form action="register" method="post" commandName="userRegister">
            <table border="0">
                <tr>
                    <td colspan="2" align="center"><h2>Spring MVC Form Demo - Registration</h2></td>
                </tr>
                <tr>
                    <td>User Name:</td>
                    <td><form:input path="username" /></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><form:password path="password" /></td>
                </tr>
                <tr>
                    <td>E-mail:</td>
                    <td><form:input path="email" /></td>
                </tr>
                <tr>
                    <td>Contact Number:</td>
                    <td><form:input path="contactNumber" /></td>
                </tr>
                <tr>
                    <td>Age:</td>
                    <td><form:input path="age" /></td>
                </tr>
                <tr>
                    <td>Gender:</td>
                    <td><form:input path="gender" /></td>
                </tr>
                <tr>
                    <td>Height:</td>
                    <td><form:input path="height" /></td>
                </tr>
                <tr>
                    <td>Weight:</td>
                    <td><form:input path="weight" /></td>
                </tr>
                <tr>
                    <td>Activity Level:</td>
                    <td><form:select path="activity" items="${activityLevel}" /></td>
                </tr>
                <tr>
                    <td>Current Location:</td>
                    <td><form:input path="currentLocation" /></td>
                </tr>
                <tr>
                    <td>Goal Type:</td>
                    <td><form:input path="goalType" items="${goaltype}" /></td>
                </tr>
                <tr>
                    <td>Target Weight:</td>
                    <td><form:input path="targetWeight" /></td>
                </tr>
                <tr>
                    <td>Start Date:</td>
                    <td><form:input path="startDate" /></td>
                </tr>
                <tr>
                    <td>End Date:</td>
                    <td><form:input path="endDate" /></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Register" /></td>
                </tr>
            </table>
        </form:form>
    </div>
</body>
</html>