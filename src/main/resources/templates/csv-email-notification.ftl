<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Reactome Report ${mailHeader} digester</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>

<h2 style="border-bottom: 1px solid #e9e9e9;">${mailHeader}</h2>

<div style="padding-left: 20px; padding-top: 15px;">
    <div>${label} reports are available to review, please visit the link with your credential below,</div>

    <h4>TARGETS</h4>
    <#list targetFiles>
        <ul>
            <#items as file>
                <li><a href="https://reactome.org/private/reports/${reportPath}/${file.getName()}">${file.getName()}</a>
                </li>
            </#items>
        </ul>
    </#list>

    <h4>TERMS not found</h4>
    <#list searchFiles>
        <ul>
            <#items as file>
                <li><a href="https://reactome.org/private/reports/${reportPath}/${file.getName()}">${file.getName()}</a>
                </li>
            </#items>
        </ul>
    </#list>

    <p>More reports are <a href="https://reactome.org/private/reports">here.</a></p>

</div>

</body>
</html>