package com.inteligente.ponto.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.inteligente.ponto.dto.EmpresaDTO;
import com.inteligente.ponto.entities.Empresa;
import com.inteligente.ponto.response.Response;
import com.inteligente.ponto.services.EmpresaService;

@RestController
@RequestMapping("/api/empresa")
@CrossOrigin(origins = "*")
public class EmpresaController {
	private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);

	@Autowired
	private EmpresaService empresaService;

	public EmpresaController() {

	}

	@GetMapping(value = "buscarPorCnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDTO>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
		log.info("Buscando empresa por CNPJ: {}", cnpj);
		Response<EmpresaDTO> response = new Response<EmpresaDTO>();
		Optional<Empresa> empresa = empresaService.buscarPorCnpj(cnpj);

		if (!empresa.isPresent()) {
			log.info("Empresa não encontrada para o CNPJ: {}", cnpj);
			response.getErrors().add("Empresa não encontrada para o CNPJ " + cnpj);

			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.ConverterEmpresaDTO(empresa.get()));
		return ResponseEntity.ok(response);
	}

	private EmpresaDTO ConverterEmpresaDTO(Empresa empresa) {
		EmpresaDTO empresaDTO = new EmpresaDTO();
		empresaDTO.setId(empresa.getId());
		empresaDTO.setCnpj(empresa.getCnpj());
		empresaDTO.setRazaoSocial(empresa.getRazaoSocial());

		return empresaDTO;
	}
}