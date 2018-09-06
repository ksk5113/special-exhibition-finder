package ksk.finder.exhibition.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ksk.finder.exhibition.sevice.Initializer;
import ksk.finder.exhibition.sevice.OngoingExhibitionService;

@Controller
public class MainController {
	@Autowired
	private Initializer initializer;

	@Autowired
	private OngoingExhibitionService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexDispatcher(Model model) {
		model.addAttribute("updated", initializer.getUpdated());
		model.addAttribute("exhibitionList", service.getSomeExhibitions());

		return "index";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexController(Model model) {
		model.addAttribute("updated", initializer.getUpdated());
		model.addAttribute("exhibitionList", service.getSomeExhibitions());

		return "index";
	}

	@RequestMapping(value = "/gallery", method = RequestMethod.GET)
	public String galleryController(Model model) {
		model.addAttribute("updated", initializer.getUpdated());
		model.addAttribute("exhibitionList", service.getAllExhibitions());

		return "gallery";
	}

	@RequestMapping(value = "/generic", method = RequestMethod.GET)
	public String genericController(Model model, @RequestParam(required = true) String exhibitionName) {
		if (exhibitionName != null) {
			model.addAttribute("exhibition", service.getAnExhibitionByName(exhibitionName));
		}

		return "generic";
	}
	
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String infoController(Model model) {
		model.addAttribute("updated", initializer.getUpdated());
		model.addAttribute("exhibitionList", service.getSomeExhibitions());

		return "index";
	}
}
