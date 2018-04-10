<#list statuses as s>
    <#if s.status==definitionApply.status>
        <#if s.status=="APPLY">
        <span class="label label-primary arrowed-right">${s.getName()}</span>
        <#elseif s.status=="BACK">
        <span class="label label-pink arrowed-right">${s.getName()}</span>
        <#elseif s.status=="REJECT">
        <span class="label label-danger arrowed-right">${s.getName()}</span>
        <#elseif s.status=="COMPLETE">
        <span class="label label-success arrowed-right">${s.getName()}</span>
        </#if>
    </#if>
</#list>
