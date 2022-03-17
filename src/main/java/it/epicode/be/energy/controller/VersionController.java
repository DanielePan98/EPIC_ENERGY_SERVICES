package it.epicode.be.energy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class VersionController {

	@GetMapping("/version")
	public String getVersion() {
		return "version: 3.0";
	}
	
}
