<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Sending Email with Freemarker HTML Template Example</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <style>
        table {
            background-color: transparent;
            border-spacing: 0;
            border-collapse: collapse;
            max-width: 100%;
            width: 60%;
            margin: 0 6px;
        }
        table thead tr:first-child th {
            background-color: #2F9EC2;
            font-size: 14px;
            font-weight: bold;
            color: #ffffff;
        }

        table td, table.reactome th {
            padding: 6px;
            border: 1px solid #ccc;
            text-align: left;
        }

        table td {
            padding-left: 12px;
        }

        table tr:nth-of-type(odd) {
            background: #F6F6F6;
        }
    </style>
</head>
<body>

<#--<img src="${logo}" alt="https://reactome.org" />-->
<img src="http://reactome.org/templates/favourite/images/logo/logo.png" alt="https://reactome.org" width="268" height="63"/>

<h2>${title}</h2>

<p>By Term</p>
<table class="reactome" style="width: 300px">
    <thead>
    <tr>
        <th style="width: 20px;">Term</th>
        <th style="width: 100px;">Hits</th>
    </tr>
    </thead>
    <tbody>
    <#list targetSummary as target>
    <tr>
        <td>${target.term}</td>
        <td>${target.count}</td>
    </tr>
    </#list>
    </tbody>
</table>

<p>Hits by IP</p>
<table class="reactome" style="width: 580px">
    <thead>
    <tr>
        <th style="width: 300px;">Term</th>
        <th style="width: 200px;">IP Address</th>
        <th style="width: 80px;">Hits</th>
    </tr>
    </thead>
    <tbody>
    <#list targetsByIp as target>
    <tr>
        <td>${target.term}</td>
        <td>${target.ip}</td>
        <td>${target.count}</td>
    </tr>
    </#list>
    </tbody>
</table>

</body>
</html>