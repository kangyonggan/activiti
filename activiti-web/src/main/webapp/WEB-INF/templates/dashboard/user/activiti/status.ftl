<#list statuses as s>
    <#if s.status==definitionApply.status>
    ${s.getName()}[${s.getStatus()}]
    </#if>
</#list>
