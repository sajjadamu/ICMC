/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chest.currency.viewBean.FreshBean;

@org.springframework.stereotype.Controller
public class Controller {
	
	@RequestMapping("/TE")
	public ModelAndView TE2() {
		FreshBean fresh = new FreshBean();
		return new ModelAndView("TE", "user", fresh);
	}
}
