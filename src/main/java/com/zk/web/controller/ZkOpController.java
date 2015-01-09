package com.zk.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//TODO
@Controller
@RequestMapping("/op")
public class ZkOpController {
   private static final Logger LOGGER = LoggerFactory.getLogger(ZkOpController.class);

   @RequestMapping("/create")
   public String create() {
      return "redirect:/read/node/";
   }

   @RequestMapping("/edit")
   public String edit() {
      return "redirect:/read/node/";
   }

   @RequestMapping("/delete")
   public String delete() {
      return "redirect:/read/node/";
   }

   @RequestMapping("/rmrdel")
   public String rmrdel() {
      return "redirect:/read/node/";
   }

}
