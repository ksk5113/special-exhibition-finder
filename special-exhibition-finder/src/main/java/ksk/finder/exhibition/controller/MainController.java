package ksk.finder.exhibition.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ksk.finder.exhibition.sevice.OngoingExhibitionService;

@Controller
public class MainController {
	@Autowired
	private OngoingExhibitionService service;

	@RequestMapping(value = "/")
	public String root() {
		return "index";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexController(Model model) {
		model.addAttribute("exhibitionList", service.getAllExhibition());

		return "index";
	}

	@RequestMapping(value = "/gallery", method = RequestMethod.GET)
	public String galleryController(Model model) {
		model.addAttribute("exhibitionList", service.getAllExhibition());

		return "gallery";
	}

	@RequestMapping(value = "/generic")
	public String generic() {
		return "generic";
	}
}
