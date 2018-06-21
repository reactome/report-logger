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

    <h3>TARGETS (${targetTotal})</h3>
    <h5>Searched terms not found in Reactome but present in Uniprot</h5>
    <table style="background-color: transparent; border-spacing: 0; border-collapse: collapse; max-width: 100%; margin: 0 6px; width: 450px; border-bottom: 1px solid #e9e9e9;">
        <thead>
        <tr>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 275px; border: 1px solid #ccc; text-align: left;">Terms (${targetRelevantSummary?size})</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 75px; border: 1px solid #ccc; text-align: left;">Hits</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 100px; border: 1px solid #ccc; text-align: left;">Unique Users</th>
        </tr>
        </thead>
        <tbody>
        <#list targetRelevantSummary as target>
        <tr style="background-color: ${((target_index % 2)==0)?string("white", "#F6F6F6")}">
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;"><a href="http://www.uniprot.org/uniprot/?query=${target.term}" target="_blank">${target.term}</a></td>
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${target.count}</td>
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${target.uniqueIPs}</td>
        </tr>
        </#list>
        </tbody>
    </table>

    <h4>TARGETS queried ONCE by only ONE user - (${targetSingleSummary?size})</h4>
    <table style="background-color: transparent; border-spacing: 0; table-layout: fixed; border-collapse: collapse; max-width: 800px; margin: 0 6px; width: 800px; border-bottom: 1px solid #e9e9e9;">
        <tbody>
        <tr>
        <#assign color="white">
        <#list targetSingleSummary as singleTarget>
            <#if (singleTarget_index != 0 && singleTarget_index % 8 == 0)> <#if (singleTarget_index % 2)==0 && color =="white"><#assign color="#F6F6F6"><#else> <#assign color="white"></#if></tr><tr style="background-color: ${color}"></#if>
            <td style="padding: 10px; width: 100px; border: 1px solid #ccc; text-align: left; word-wrap:break-word;"><a href="http://www.uniprot.org/uniprot/?query=${singleTarget.term}" target="_blank">${singleTarget.term}</a></td>
        </#list>
        </tr>
        </tbody>
    </table>

    <div style="padding: 15px; border-bottom: 1px solid #e9e9e9"></div>

    <h3>TERMS not found (${searchSummaryTotal})</h3>
    <h5>Searched terms not found in Reactome</h5>
    <table style="background-color: transparent; border-spacing: 0; border-collapse: collapse; max-width: 100%; margin: 0 6px; width: 600px; border-bottom: 1px solid #e9e9e9;">
        <thead>
        <tr>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 425px; border: 1px solid #ccc; text-align: left;">Terms (${searchRelevantSummary?size})</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 75px; border: 1px solid #ccc; text-align: left;">Hits</th>
            <th style="background-color: #2F9EC2; padding: 6px; font-size: 14px; font-weight: bold; color: #ffffff; width: 100px; border: 1px solid #ccc; text-align: left;">Unique Users</th>
        </tr>
        </thead>
        <tbody>
        <#list searchRelevantSummary as search>
        <tr style="background-color: ${((search_index % 2)==0)?string("white", "#F6F6F6")}">
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${search.term}</td>
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${search.count}</td>
            <td style="padding: 12px; border: 1px solid #ccc; text-align: left;">${search.uniqueIPs}</td>
        </tr>
        </#list>
        </tbody>
    </table>

    <h4>TERMS queried ONCE by only ONE user - (${searchSingleUsersSummary?size})</h4>
    <table style="background-color: transparent; border-spacing: 0; table-layout: fixed; border-collapse: collapse; max-width: 800px; margin: 0 6px; width: 800px; border-bottom: 1px solid #e9e9e9;">
        <tbody>
        <tr>
        <#assign color="white">
        <#list searchSingleUsersSummary as search>
            <#if (search_index != 0 && search_index % 4 == 0)> <#if (search_index % 2)==0 && color =="white"><#assign color="#F6F6F6"><#else> <#assign color="white"></#if></tr><tr style="background-color: ${color}"></#if>
            <td style="padding 5px; width: 200px; border: 1px solid #ccc; text-align: left; word-wrap:break-word;">${search.term}</td>
        </#list>
        </tr>
        </tbody>
    </table>


</div>

</body>
</html>