package com.zk.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zk.entity.ZkData;
import com.zk.op.Zk;
import com.zk.web.constants.Constants;

@Controller
@RequestMapping("/read")
public class ZkReadController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZkReadController.class);

	@RequestMapping("/addr")
	public String addr(HttpServletRequest request, RedirectAttributes attr, @RequestParam(required = true) String cxnstr) {
		if (StringUtils.isBlank(cxnstr)) {
			return "redirect:/";
		}
		HttpSession session = request.getSession();
		session.setAttribute(Constants.CX_STR, cxnstr);
		attr.addFlashAttribute(Constants.CX_STR, StringUtils.trimToEmpty(cxnstr));
		return "redirect:/read/node/";
	}

	@RequestMapping("/node")
	public String node(HttpServletRequest request, Model model, String path) {
		HttpSession session = request.getSession();
		String cxnstr = (String) session.getAttribute(Constants.CX_STR);
		if (StringUtils.isBlank(cxnstr)) {
			return "redirect:/";
		}
		path = StringUtils.endsWith(path, "/") ? StringUtils.substring(path, 0, StringUtils.lastIndexOf(path, "/"))
				: path;
		path = StringUtils.isBlank(path) ? "/" : StringUtils.trimToEmpty(path);
		model.addAttribute("pathList", Arrays.asList(StringUtils.split(path, "/")));

		Zk reader = new Zk(cxnstr);

		List<String> children = reader.getChildren(path);
		if (CollectionUtils.isNotEmpty(children)) {
			Collections.sort(children);
		}
		model.addAttribute("children", children);

		ZkData zkData = reader.readData(path);

		model.addAttribute("data", zkData.getDataString());
		model.addAttribute("dataSize", zkData.getData().length);
		try {
			Map<String, Object> statMap = PropertyUtils.describe(zkData.getStat());
			statMap.remove("class");
			model.addAttribute("stat", statMap);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			LOGGER.error(e.getMessage(), e);
		}

		return "node";
	}

}
