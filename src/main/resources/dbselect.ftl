<html>
<head>
    <title>DB Selection</title>
</head>

<body>
    <form action="/choose_db", method="POST">
    <p>Which database would you choose?</p>
    <#list databases as db>
    <input type="radio" name="db" value="${db}">${db}</input>
    </#list>
    <input type="submit" value="submit"/>
    </form>
</body>
</html>