<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Reactome Report ${mailHeader} digester</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>

<h2 style="border-bottom: 1px solid #e9e9e9;">${mailHeader}</h2>

<div style="padding-left: 20px; padding-top: 15px;">

    <h3>${label} (${todayClaimed?size})</h3>
    <table style="background-color: transparent; border-spacing: 0; border-collapse: collapse; max-width: 100%; margin: 0 6px; width: 450px; border-bottom: 1px solid #e9e9e9;">
        <thead>
        <tr>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 250px; border: 1px solid #ccc; text-align: left;">Name</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 200px; border: 1px solid #ccc; text-align: left;">Orcid</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 100px; border: 1px solid #ccc; text-align: left;">Total</th>
        </tr>
        </thead>
        <tbody>
        <#list todayClaimed as today>
            <tr style="background-color: ${((today_index % 2)==0)?string("white", "#F6F6F6")}">
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${today.name}</td>
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${today.orcid}</td>
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${today.total}</td>
            </tr>
        </#list>
        </tbody>
    </table>


    <h3>${label} (${totalClaimed?size})</h3>
    <table style="background-color: transparent; border-spacing: 0; border-collapse: collapse; max-width: 100%; margin: 0 6px; width: 450px; border-bottom: 1px solid #e9e9e9;">
        <thead>
        <tr>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 250px; border: 1px solid #ccc; text-align: left;">Name</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 200px; border: 1px solid #ccc; text-align: left;">Orcid</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 100px; border: 1px solid #ccc; text-align: left;">Total</th>
        </tr>
        </thead>
        <tbody>
        <#list totalClaimed as total>
            <tr style="background-color: ${((total_index % 2)==0)?string("white", "#F6F6F6")}">
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${total.name}</td>
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${total.orcid}</td>
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${total.total}</td>
            </tr>
        </#list>
        </tbody>
    </table>

</div>

</body>
</html>