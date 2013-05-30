<%--
  Created by IntelliJ IDEA.
  User: ajbrown
  Date: 5/29/13
  Time: 10:33 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
</head>
<body>

<h1>${pet.givenName}</h1>

${pet.color} ${pet.breed} ${pet.sex}

<g:each in="${pet.photos}">
    <img src="${it.standardUrl}" alt=""/>
</g:each>

<p>${pet.description}</p>

</body>
</html>