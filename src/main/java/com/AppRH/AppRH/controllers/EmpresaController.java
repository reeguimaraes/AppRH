package com.AppRH.AppRH.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Candidato;
import com.AppRH.AppRH.models.Dependente;
import com.AppRH.AppRH.models.Empresa;
import com.AppRH.AppRH.models.Funcionario;
import com.AppRH.AppRH.models.Vaga;
import com.AppRH.AppRH.repository.EmpresaRepository;

@Controller
public class EmpresaController {

	@Autowired
	private EmpresaRepository er;
	
	
	// GET PARA O FORM QUE CADASTRA A EMPRESA
	@RequestMapping("/cadastrarEmpresa")
	public String form() {
		return "empresa/form-empresa";
	}
	
	// POST QUE CADASTRA EMPRESA
	@RequestMapping(value = "/cadastrarEmpresa", method = RequestMethod.POST)
	public String form(@Valid Empresa empresa, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem",  "Verifique os campos");
			return "redirect:/cadastrarEmpresa";
		}
		
		er.save(empresa);
		attributes.addFlashAttribute("mensagem", "Empresa adicionada com sucesso!");
		return "redirect:/empresas";
		
	}
	
	// GET QUE LISTA EMPRESAS
	@RequestMapping("/empresas")
	public ModelAndView listaEmpresas() {
		ModelAndView mv = new ModelAndView("empresa/lista-empresa");
		Iterable<Empresa> empresas = er.findAll();
		mv.addObject("empresas", empresas);
		return mv;
	}
	
	
	// GET QUE DELETA EMPRESA
	@RequestMapping("/deletarEmpresa")
	public String deletarEmpresa(long id) {
		Empresa empresa = er.findById(id);
		er.delete(empresa);
		return "redirect:/empresas";
	}
	
	
	// MÃ‰TODOS QUE ATUALIZAM A EMPRESA
	
	// GET QUE CHAMA O FORM DE EDIÃ‡ÃƒO DA EMPRESA
	@RequestMapping("/editar-empresa")
	public ModelAndView editarEmpresa(long id) {
		Empresa empresa = er.findById(id);
		ModelAndView mv = new ModelAndView("empresa/update-empresa");
		mv.addObject("empresa", empresa);
		return mv;
	}
	
	// POST QUE ATUALIZA A EMPRESA
	@RequestMapping(value="/editar-empresa", method = RequestMethod.POST)
	public String updateEmpresa(@Valid Empresa empresa, BindingResult result, RedirectAttributes attributes) {
		er.save(empresa);
		attributes.addFlashAttribute("success", "Funcionario alterado com sucesso!");
		return "redirect:/empresas";
	}
	
	// GET que lista dependentes e detalhes dos empresa
		@RequestMapping("/detalhes-empresa/{id}")
		public ModelAndView detalhesEmpresa(@PathVariable("id") long id) {
			Empresa empresa = er.findById(id);
			ModelAndView mv = new ModelAndView("empresa/detalhes-empresa");
			mv.addObject("empresas", empresa);

			

			return mv;

		}
	
	
}
