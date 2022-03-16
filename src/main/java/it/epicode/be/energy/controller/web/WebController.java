package it.epicode.be.energy.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.service.ClienteService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/web")
@Slf4j
public class WebController {

	@Autowired
	ClienteService clienteService;

	@GetMapping("/mostraelenco")
	public ModelAndView findAllCliente(Pageable pageable) {
		log.info("*** Lista clienti ***");
		ModelAndView view = new ModelAndView("visualizzaClienti");
		Page<Cliente> clientiPage = clienteService.findAll(pageable);
		view.addObject("cliente", clientiPage);
		return view;
	}

	@GetMapping("/eliminaCliente/{id}")
	public ModelAndView eliminaCliente(@PathVariable Long id, Pageable pageable) {
		log.info("*** Cancellazione clienti in corso ***");
		clienteService.delete(id);
		log.info("*** Cliente cancellato ***");
		return findAllCliente(pageable);
	}
	
	@GetMapping("/mostraFormAggiungi")
	public ModelAndView mostraForm() {
		return new ModelAndView("aggiungiCliente","cliente",new Cliente());
	}
	
	@PostMapping("/aggiungiCliente")
	public ModelAndView aggiungiCliente(@ModelAttribute("cliente") Cliente cliente,Pageable pageable) {
		clienteService.save(cliente);
		return findAllCliente(pageable);
	}
	
	@GetMapping("/index")
	public ModelAndView homepage() {
		return new ModelAndView("index");
	}
	
	@PostMapping("/aggiornaCliente/{id}")
	public ModelAndView aggiornaCliente(@PathVariable Long id,@ModelAttribute("cliente") Cliente cliente,Pageable pageable) {
		clienteService.updateWeb(cliente,id);
		return findAllCliente(pageable);
	}
	
	@GetMapping("/mostraFormAggiorna/{id}")
	public ModelAndView mostraFormAggiorna(@PathVariable Long id) {
		ModelAndView view= new ModelAndView("aggiornaCliente","cliente",new Cliente());
		view.addObject("cliente",clienteService.findById(id).get());
		return view;
	}
	

}
