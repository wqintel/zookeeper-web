<!DOCTYPE html>
<html>
	<head>
		<title>Zookeeper-Web</title>
		<script src="${host}/js/jquery.min.js" type="text/javascript"></script>
		<script src="${host}/js/bootstrap.min.js" type="text/javascript"></script>
		<link href="${host}/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="${host}/css/zk-web.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="container-full1">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<nav class="navbar navbar-default navbar-fixed-bottom" role="navigation">
						<div>
						<div class="navbar-header" style="margin-left: 35%;">
							 <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> 
							 <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
							 <span class="navbar-brand">管理工具</span>
						</div>
						
						<div class="collapse navbar-collapse  style="margin-left: 35%;"" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav">
								<li>
									<a data-toggle="modal" data-target="#createModal" href="#createModal">创建</a>
								</li>
								<li>
									<a data-toggle="modal" data-target="#editModal" href="#editModal">编辑</a>
								</li>
								<li>
									<a data-toggle="modal" data-target="#deleteModal" href="#deleteModal">删除</a>
								</li>
								<li>
									<a data-toggle="modal" data-target="#rmrModal" href="#rmrModal">级联删除</a>
								</li>
							</ul>
							<ul class="nav nav-pills navbar-right" style="margin-right: 8%;">
								<li>
									 <a>游客</a>
								</li>
								<li class="active">
									 <a href="${host}/login">登录</a>
								</li>
							</ul>
						</div>
						</div>
					</nav>
					<div class="page-header text-center">
						<a href="${host}" style="text-decoration : none"><h1>
							Zookeeper Web <#--<small>简单一点 方便一点</small>-->
						</h1></a>
					</div>
					<div class="text-center">
						<ul class="breadcrumb span12">
							<i class="icon-chevron-right"></i>
							<li><a href="${host}/read/addr?cxnstr=${cxnstr!''}">${cxnstr!''}</a></li>
							<#assign pathAppend = "/">
							<#if pathList??>
							<#list pathList as p>
							<#assign pathAppend=pathAppend+p+"/">
							<i class="icon-chevron-right"></i>
							<li><a href="${host}/read/node?path=${pathAppend?substring(0,pathAppend?length-1)}">${p!''}</a></li>
							</#list>
							</#if>
							<#--<#if pathAppend==""><#assign pathAppend="/"></#if>-->
						<#--<span>This is a template for a simple marketing or .</span>-->
						</ul>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-4 column">
					<h3>子节点<span class="label label-info fontsize11">${(children?size)!'0'}</span></h3>
					<table class="table table-bordered">
						<#if children?size gt 0>
						<#list children as c>
						<tr><td><a href="${host}/read/node?path=${pathAppend?substring(0,pathAppend?length-1)}/${c}">${c}</a></td></tr>
						</#list>
						<#else>
						<div class="alert"><h3 class="text-danger text-center">木有子节点</h3></div>
						</#if>
					</table>
				</div>
				<div class="col-md-4 column">
					<h3>节点状态</h3>
					<table class="table table-bordered">
						<#if stat??>
						<#list stat?keys as key>
						<tr>
							<td>${key}</td>
							<td>${stat[key]}</td>
						</tr>
						</#list>
						</#if>
					</table>
				</div>
				<div class="col-md-4 column">
					<h3>节点数据<span class="label label-info fontsize11">${dataSize} byte(s)</span></h3>
					<div class="well marginright50">
						<p style="word-break:break-all;">${data?replace("\n", "<br/>")}</p>
					</div>
				</div>
			</div>
			

<div class="modal fade" id="createModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">创建一个子节点</h4>
      </div>
      <form action="${host}/op/create" method="POST" class="form-horizontal">
      <div class="modal-body">
    	<div class="alert alert-info">从这个节点创建子节点: <strong>${pathAppend}</strong></div>
    	<div class="form-group">
	      <label for="inputName" class="col-lg-2 control-label">节点名称:</label>
	      <div class="col-lg-10">
	        <input class="form-control" required name="name" id="inputName" placeholder="Name of new node" type="text" />
	      </div>
	    </div>
	    <div class="form-group">
	      <label for="textAreaData" class="col-lg-2 control-label">数据:</label>
	      <div class="col-lg-10">
	        <textarea class="form-control" rows="10" name="data" id="textAreaData" placeholder="Data of new node" rows="6"></textarea>
	      </div>
	    </div>
		<input class="span8" name="parent" type="hidden" value="${pathAppend}" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" class="btn btn-primary">创建</button>
      </div>
      </form>
    </div>
  </div>
</div>

<div class="modal fade" id="editModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">编辑节点数据</h4>
      </div>
      <form action="${host}/op/edit" method="POST" class="form-horizontal">
      <div class="modal-body">
    	<div class="alert alert-info">编辑节点: <strong>${pathAppend}</strong></div>
	    <div class="form-group">
	      <label for="textAreaData" class="col-lg-2 control-label">数据:</label>
	      <div class="col-lg-10">
	        <textarea class="form-control" required rows="10" name="data" id="textAreaData" placeholder="Data of new node" rows="6">${data!''}</textarea>
	      </div>
	    </div>
		<input class="span8" name="path" type="hidden" value="${pathAppend}" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" class="btn btn-primary">保存</button>
      </div>
      </form>
    </div>
  </div>
</div>

<div class="modal fade" id="deleteModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">删除这个节点</h4>
      </div>
      <form action="${host}/op/delete" method="POST" class="form-horizontal">
      <div class="modal-body">
    	<div class="alert alert-info">确认删除节点: <strong>${pathAppend}</strong></div>
		<input class="span8" name="path" type="hidden" value="${pathAppend}" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" id="del-btn" class="btn btn-primary">删除</button>
      </div>
      </form>
    </div>
  </div>
</div>

<div class="modal fade" id="rmrModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">删除这个节点及子节点</h4>
      </div>
      <form action="${host}/op/rmrdel" method="POST" class="form-horizontal">
      <div class="modal-body">
    	<div class="alert alert-danger">
    		<h4>Danger!!</h4>
    		确定级联删除: <strong>${pathAppend}</strong>和所有的子节点???
    	</div>
		<input class="span8" name="path" type="hidden" value="${pathAppend}" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" id="del-btn" class="btn btn-primary">级联删除</button>
      </div>
      </form>
    </div>
  </div>
</div>

		</div>

	</body>
</html>