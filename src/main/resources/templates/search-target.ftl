<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Sending Email with Freemarker HTML Template Example</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <style>
        table.reactome, table.header {
            background-color: transparent;
            border-spacing: 0;
            border-collapse: collapse;
            max-width: 100%;
            width: 60%;
            margin: 0 6px;
        }
        table.reactome thead tr:first-child th {
            background-color: #2F9EC2;
            font-size: 14px;
            font-weight: bold;
            color: #ffffff;
        }

        table.reactome td, table.reactome th {
            padding: 6px;
            border: 1px solid #ccc;
            text-align: left;
        }

        table.reactome td {
            padding-left: 12px;
        }

        table.reactome tr:nth-of-type(odd) {
            background: #F6F6F6;
        }
    </style>
</head>
<body>


<table class="header" style="width: 620px; border-bottom: 1px solid #e9e9e9;">
    <tr>
        <td style="text-align: center;"><span style="font-size:1.4em; font-weight: bold; text-decoration:underline;">${title}</span></td>
        <td style="width: 180px; height:50px; vertical-align: middle;"><img src="${logo}" alt="https://reactome.org" width="168" height="42"/></td>
    </tr>
</table>

<div style="padding-left: 20px;">
<p>Targets - By Term</p>
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

<p>Targets - Hits by IP</p>
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
</div>


<div style="padding-left: 20px;">
    <p>Terms not found</p>
    <table class="reactome" style="width: 300px">
        <thead>
        <tr>
            <th style="width: 20px;">Term</th>
            <th style="width: 100px;">Hits</th>
        </tr>
        </thead>
        <tbody>
    <#list searchSummary as search>
    <tr>
        <td>${search.term}</td>
        <td>${search.count}</td>
    </tr>
    </#list>
        </tbody>
    </table>

    <p>Terms not found - Hits by IP</p>
    <table class="reactome" style="width: 580px">
        <thead>
        <tr>
            <th style="width: 300px;">Term</th>
            <th style="width: 200px;">IP Address</th>
            <th style="width: 80px;">Hits</th>
        </tr>
        </thead>
        <tbody>
    <#list searchByIp as search>
    <tr>
        <td>${search.term}</td>
        <td>${search.ip}</td>
        <td>${search.count}</td>
    </tr>
    </#list>
        </tbody>
    </table>
</div>



</body>
</html>