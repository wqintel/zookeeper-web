<#--
<#import "/common/mainContainer.ftl" as mc />
<@mc.container jsFiles=[] cssFiles=[] ssl=true>
 <div class="error-page gray-border">
   	<div class="error-page_con">
        <p><#if (exception.retMsg)??>${exception.retMsg}<#else>${(exception.message)!''}</#if></p>
        <p class="p_back"><a href="${basepath}/">返回首页</a></p>
		<p style="display:none">${stackTrace!}</p>
    </div>
 </div>
</@mc.container>
-->
error