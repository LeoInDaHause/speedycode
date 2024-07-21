<%-- 
    Document   : index
    Created on : Jul 21, 2024, 12:30:33 PM
    Author     : cesle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Formulario prueba</title>
    </head>
    <body>
        <h1>Diego es gey en HTML</h1>
        
        <p>Dato del usuraio</p>
        <form action="svUsuarios" method="POST"> 
            <p><lable>Correo : </lable> <input type="text" name="correo"></p>
            <p><lable>Password : </lable> <input type="text" name="password"></p>
            <button type="submit" >enviar</button>
        </form>
        
        <%String hola = "Diego es gey en java";
            String a = "Pagina 10/10";
            System.out.println(hola);
        %>
        
        <%=a
        %>
        
    </body>
</html>
