package com.cip.sale.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cip.sale.bean.Animal;

@Controller
public class HelloController {
  @RequestMapping("showInfo.do")
  public ModelAndView showInfo(){
	  Animal a1 = new Animal();
	  a1.setName("小狗");
	  a1.setPrice(88);
	 Animal a2 = new Animal();
	 a2.setName("小苗");
	 a2.setPrice(80);
	List<Animal> list = new ArrayList<Animal>();
	  list.add(a1);
	  list.add(a2);
	  ModelAndView mod = new ModelAndView("hello");
	  mod.addObject("name","冉冉");
	  mod.addObject("list",list);
	  return mod;
  }
}
