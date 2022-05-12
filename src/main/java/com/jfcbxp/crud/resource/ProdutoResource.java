package com.jfcbxp.crud.resource;

import com.jfcbxp.crud.domain.dto.ProdutoDTO;
import com.jfcbxp.crud.service.ProdutoService;
import com.jfcbxp.crud.validation.CreateProdutoValidation;
import com.jfcbxp.crud.validation.UpdateProdutoValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/produto")
public class ProdutoResource {

    public static final String ID = "/{id}";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_X_YAML = "application/x-yaml";
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProdutoService service;
    @Autowired
    private PagedResourcesAssembler<ProdutoDTO> assembler;

    @GetMapping(value = ID, produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML})
    public ProdutoDTO findProdutoById(@PathVariable Integer id) {
        ProdutoDTO response = mapper.map(service.findProdutoById(id), ProdutoDTO.class);
        response.add(linkTo(methodOn(ProdutoResource.class).findProdutoById(id)).withSelfRel());
        return response;
    }

    @GetMapping(produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML})
    public ResponseEntity<?> findAllProduto(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "limit", defaultValue = "12") int limit,
                                            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));
        Page<ProdutoDTO> produtos = service.findAllProduto(pageable).map(p -> mapper.map(p, ProdutoDTO.class));

        produtos.stream()
                .forEach(p -> p.add(linkTo(methodOn(ProdutoResource.class).findProdutoById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<ProdutoDTO>> pagedModel = assembler.toModel(produtos);


        return ResponseEntity.ok().body(pagedModel);

    }

    @PostMapping(produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML},
            consumes = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML})
    public ProdutoDTO createProduto(@RequestBody @Validated(CreateProdutoValidation.class) ProdutoDTO produtoDTO) {
        ProdutoDTO response = mapper.map(service.createProduto(produtoDTO), ProdutoDTO.class);
        response.add(linkTo(methodOn(ProdutoResource.class).findProdutoById(response.getId())).withSelfRel());
        return response;
    }

    @PutMapping(produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML},
            consumes = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_X_YAML})
    public ProdutoDTO updateProduto(@RequestBody @Validated(UpdateProdutoValidation.class) ProdutoDTO produtoDTO) {
        ProdutoDTO response = mapper.map(service.updateProduto(produtoDTO), ProdutoDTO.class);
        response.add(linkTo(methodOn(ProdutoResource.class).findProdutoById(response.getId())).withSelfRel());
        return response;
    }

    @DeleteMapping(ID)
    public ResponseEntity<ProdutoDTO> deleteProduto(@PathVariable Integer id) {
        service.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }
}
