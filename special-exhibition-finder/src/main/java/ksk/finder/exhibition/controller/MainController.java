package ksk.finder.exhibition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping(value = "/")
	public String root() {
		return "index";
	}
	
	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/gallery")
	public String gallery() {
		return "gallery";
	}

	@RequestMapping(value = "/generic")
	public String generic() {
		return "generic";
	}
}
