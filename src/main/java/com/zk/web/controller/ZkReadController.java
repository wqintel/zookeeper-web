package com.zk.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.zk.ZkData;
import com.zk.read.ZkReader;

@Controller
@RequestMapping("/read")
@SessionAttributes("cxnstr")
public class ZkReadController {
   private static final Logger LOGGER = LoggerFactory.getLogger(ZkReadController.class);

   @RequestMapping("/addr")
   public String addr(HttpServletRequest request, @RequestParam(required = true) String cxnstr) {
      if (StringUtils.isBlank(cxnstr)) {
         return "redirect:/";
      }
      request.getSession().setAttribute("cxnstr", StringUtils.trimToEmpty(cxnstr));
      return "redirect:/read/node/";
   }

   @RequestMapping("/node")
   public String node(Model model, HttpServletRequest request, String path) {
      String cxnstr = (String) request.getSession().getAttribute("cxnstr");
      if (StringUtils.isBlank(cxnstr)) {
         return "redirect:/";
      }
      path = StringUtils.isBlank(path) ? "/" : StringUtils.trimToEmpty(path);
      model.addAttribute("pathList", Arrays.asList(StringUtils.split(path, "/")));

      ZkReader reader = new ZkReader(cxnstr);

      List<String> children = reader.getChildren(path);
      if(CollectionUtils.isNotEmpty(children)) {
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
