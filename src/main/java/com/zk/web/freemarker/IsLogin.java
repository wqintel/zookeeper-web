package com.zk.web.freemarker;

import java.util.List;

import com.zk.web.util.AuthUtils;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class IsLogin implements TemplateMethodModelEx {

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arg0) throws TemplateModelException {
		return AuthUtils.isLogin();
	}

}
