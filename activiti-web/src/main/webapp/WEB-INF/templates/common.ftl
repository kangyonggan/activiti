<#assign ctx="${(rca.contextPath)!''}">

<#--截取字符串, 超出部分用省略号代替-->
<#macro substring str len default=''>
    <#if str?? && str != ''>
        <#if str?length gt len>
        ${str?substring(0, len)}...
        <#else>
        ${str}
        </#if>
    <#else>
    ${default}
    </#if>
</#macro>

<#--分页组件-->
<#macro pagination url param="">
    <#if (page.list)?? && page.pages gt 1>
    <div class="pull-right">
        <ul class="pagination">
            <li><a href="javascript:" class="page-info">第 ${page.startRow}~${page.endRow} 条, 共 ${page.total} 条,
                第 ${page.pageNum} 页,
                共 ${page.pages} 页</a></li>
        </ul>
    </div>
    <div class="pull-left">
        <ul class="pagination">
            <#if page.hasPreviousPage>
                <li>
                    <a href="${url}?p=1<#if param?has_content>&${param}</#if>">
                        <i class="ace-icon fa fa-angle-double-left"></i>
                    </a>
                </li>
                <li>
                    <a href="${url}?p=${page.prePage}<#if param?has_content>&${param}</#if>">
                        <i class="ace-icon fa fa-angle-left"></i>
                    </a>
                </li>
            <#else>
                <li>
                    <a href="javascript:">
                        <i class="ace-icon fa fa-angle-double-left"></i>
                    </a>
                </li>
                <li>
                    <a href="javascript:">
                        <i class="ace-icon fa fa-angle-left"></i>
                    </a>
                </li>
            </#if>

            <#list page.navigatepageNums as nav>
                <#if nav == page.pageNum>
                    <li class="active">
                        <a href="javascript:">${nav}</a>
                    </li>
                <#else>
                    <li>
                        <a href="${url}?p=${nav}<#if param?has_content>&${param}</#if>">${nav}</a>
                    </li>
                </#if>
            </#list>

            <#if page.hasNextPage>
                <li>
                    <a href="${url}?p=${page.nextPage}<#if param?has_content>&${param}</#if>">
                        <i class="ace-icon fa fa-angle-right"></i>
                    </a>
                </li>

                <li>
                    <a href="${url}?p=${page.pages}<#if param?has_content>&${param}</#if>">
                        <i class="ace-icon fa fa-angle-double-right"></i>
                    </a>
                </li>
            <#else>
                <li>
                    <a href="javascript:">
                        <i class="ace-icon fa fa-angle-right"></i>
                    </a>
                </li>

                <li>
                    <a href="javascript:">
                        <i class="ace-icon fa fa-angle-double-right"></i>
                    </a>
                </li>
            </#if>
        </ul>
    </div>
    </#if>
</#macro>

<#--web分页组件-->
<#macro web_pagination url param="">
    <#if (page.list)?? && page.pages gt 1>
    <ul class="pagination">
        <#if page.hasPreviousPage>
            <li><a href="${url}?p=1<#if param?has_content>&${param}</#if>">首页</a></li>
            <li><a href="${url}?p=${page.prePage}<#if param?has_content>&${param}</#if>">上一页</a></li>
        <#else>
            <li><a href="javascript:">首页</a></li>
            <li><a href="javascript:">上一页</a></li>
        </#if>

        <#list page.navigatepageNums as nav>
            <#if nav == page.pageNum>
                <li class="active">
                    <a href="javascript:">${nav}</a>
                </li>
            <#else>
                <li>
                    <a href="${url}?p=${nav}<#if param?has_content>&${param}</#if>">${nav}</a>
                </li>
            </#if>
        </#list>

        <#if page.hasNextPage>
            <li><a href="${url}?p=${page.nextPage}<#if param?has_content>&${param}</#if>">下一页</a></li>
            <li><a href="${url}?p=${page.pages}<#if param?has_content>&${param}</#if>">尾页</a></li>
        <#else>
            <li><a href="javascript:">下一页</a></li>
            <li><a href="javascript:">尾页</a></li>
        </#if>
    </ul>
    </#if>
</#macro>

<#macro modal_form_tool>
<button class="btn btn-sm" data-dismiss="modal">
    <i class="ace-icon fa fa-times"></i>
    <@s.message "app.button.cancel"/>
</button>

<button class="btn btn-sm btn-success" id="submit"
        data-loading-text="正在<@s.message "app.button.save"/>..." data-toggle="form-submit" data-target="#modal-form">
    <i class="ace-icon fa fa-check"></i>
    <@s.message "app.button.save"/>
</button>
</#macro>

<#macro search_form_tool>
<button class="btn btn-sm btn-purple" data-toggle="search-submit">
    搜索
    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
</button>

<button class="btn btn-sm btn-danger" data-toggle="form-reset">
    清空
    <span class="ace-icon fa fa-undo icon-on-right bigger-110"></span>
</button>
</#macro>

<#--计算时间-->
<#macro relative_date datetime=.now>
    <#assign ct = (.now?long-datetime?long)/1000>
    <#if ct gte 31104000><#--n年前-->${(ct/31104000)?int}年前
        <#t><#elseif ct gte 2592000><#--n月前-->${(ct/2592000)?int}个月前
        <#t><#elseif ct gte 86400*2><#--n天前-->${(ct/86400)?int}天前
        <#t><#elseif ct gte 86400><#--1天前-->昨天
        <#t><#elseif ct gte 3600><#--n小时前-->${(ct/3600)?int}小时前
        <#t><#elseif ct gte 60><#--n分钟前-->${(ct/60)?int}分钟前
        <#t><#elseif ct gt 0><#--n秒前-->${ct?int}秒前
        <#t><#else>刚刚
    </#if>
</#macro>
