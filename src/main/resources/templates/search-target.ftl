<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Sending Email with Freemarker HTML Template Example</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>


<table style="background-color: transparent; border-spacing: 0; border-collapse: collapse; max-width: 100%; margin: 0 6px; width: 620px; border-bottom: 1px solid #e9e9e9;">
    <tr>
        <td style="text-align: center;"><span style="font-size:1.4em; font-weight: bold; text-decoration:underline;">${title}</span></td>
        <td style="width: 180px; height:50px; vertical-align: middle;"><img src="${logo}" alt="https://reactome.org" width="168" height="42"/></td>
    </tr>
</table>

<div style="padding-left: 20px;">
    <p>Targets - By Term</p>
    <table style="background-color: transparent; border-spacing: 0; border-collapse: collapse; max-width: 100%; margin: 0 6px; width: 300px; border-bottom: 1px solid #e9e9e9;">
        <thead>
        <tr>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 20px; border: 1px solid #ccc; text-align: left;">Term</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 100px; border: 1px solid #ccc; text-align: left;">Hits</th>
        </tr>
        </thead>
        <tbody>
        <#list targetSummary as target>
        <tr style='background-color: ${((target_index % 2)==0)?string("white", "#F6F6F6")}'>
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${target.term}</td>
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${target.count}</td>
        </tr>
        </#list>
        </tbody>
    </table>

    <p>Terms not found</p>
    <table style="background-color: transparent; border-spacing: 0; border-collapse: collapse; max-width: 100%; margin: 0 6px; width: 300px; border-bottom: 1px solid #e9e9e9;">
        <thead>
        <tr>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 20px; border: 1px solid #ccc; text-align: left;">Term</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 100px; border: 1px solid #ccc; text-align: left;">Hits</th>
        </tr>
        </thead>
        <tbody>
    <#list searchSummary as search>
    <tr style='background-color: ${((search_index % 2)==0)?string("white", "#F6F6F6")}'>
        <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${search.term}</td>
        <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${search.count}</td>
    </tr>
    </#list>
        </tbody>
    </table>
</div>

<div style="padding-left: 20px;">
    <p>Targets - Hits by IP</p>
    <table style="background-color: transparent; border-spacing: 0; border-collapse: collapse; max-width: 100%; margin: 0 6px; width: 580px; border-bottom: 1px solid #e9e9e9;">
        <thead>
        <tr>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 300px; border: 1px solid #ccc; text-align: left;">Term</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 200px; border: 1px solid #ccc; text-align: left;">IP Address</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 80px; border: 1px solid #ccc; text-align: left;">Hits</th>
        </tr>
        </thead>
        <tbody>
    <#list targetsByIp as target>
    <tr style='background-color: ${((target_index % 2)==0)?string("white", "#F6F6F6")}'>
        <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${target.term}</td>
        <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${target.ip}</td>
        <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${target.count}</td>
    </tr>
    </#list>
        </tbody>
    </table>

    <p>Terms not found - Hits by IP</p>
    <table style="background-color: transparent; border-spacing: 0; border-collapse: collapse; max-width: 100%; margin: 0 6px; width: 580px; border-bottom: 1px solid #e9e9e9;">
        <thead>
        <tr>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 300px; border: 1px solid #ccc; text-align: left;">Term</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 200px; border: 1px solid #ccc; text-align: left;">IP Address</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 80px; border: 1px solid #ccc; text-align: left;">Hits</th>
        </tr>
        </thead>
        <tbody>
    <#list searchByIp as search>
    <tr style='background-color: ${((search_index % 2)==0)?string("white", "#F6F6F6")}'>
        <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${search.term}</td>
        <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${search.ip}</td>
        <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${search.count}</td>
    </tr>
    </#list>
        </tbody>
    </table>
</div>

</body>
</html>