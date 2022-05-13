package com.jfcbxp.crud.resource;

import com.jfcbxp.crud.domain.Produto;
import com.jfcbxp.crud.domain.dto.ProdutoDTO;
import com.jfcbxp.crud.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ProdutoResourceTest {

    public static final String PRODUTO_NOME = "produto teste";
    public static final int PRODUTO_ID = 1;
    public static final BigDecimal PRODUTO_ESTOQUE = BigDecimal.ONE;
    public static final BigDecimal PRODUTO_PRECO = BigDecimal.TEN;
    public static final int INDEX = 0;

    private Produto produto;

    private ProdutoDTO produtoDTO;

    private Optional<Produto> optionalProduto;

    private Page<ProdutoDTO> produtosDTOPage;

    private Page<Produto> produtosPage;

    private PagedModel<EntityModel<ProdutoDTO>> produtosDTOPageModel;

    @InjectMocks
    private ProdutoResource resource;

    @Mock
    private ModelMapper mapper;

    @Mock
    private ProdutoService service;

    @Mock
    private PagedResourcesAssembler<ProdutoDTO> assembler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RequestAttributes mockRequest = new ServletWebRequest(new MockHttpServletRequest("GET", "/produto"));
        RequestContextHolder.setRequestAttributes(mockRequest);
        startProduto();

    }

    @Test
    void whenFindProdutoByIdThenReturnSuccess() {
        when(service.findProdutoById(anyInt())).thenReturn(produto);
        when(mapper.map(produto, ProdutoDTO.class)).thenReturn(produtoDTO);
        ProdutoDTO response = resource.findProdutoById(PRODUTO_ID);
        assertNotNull(response);
        assertEquals(ProdutoDTO.class, response.getClass());
        assertEquals(PRODUTO_ID, response.getId());
        assertEquals(PRODUTO_NOME, response.getName());
        assertEquals(PRODUTO_ESTOQUE, response.getEstoque());
        assertEquals(PRODUTO_PRECO, response.getPreco());
        assertEquals(produtoDTO,response);

    }

    @Test
    void whenFindAllProdutoThenReturnAListOfProdutoDTO() {
        Pageable pageable = PageRequest.of(0, 12, Sort.by(Sort.Direction.ASC, "name"));
        when(assembler.toModel(produtosDTOPage)).thenReturn(produtosDTOPageModel);
        when(mapper.map(produto, ProdutoDTO.class)).thenReturn(produtoDTO);
        when(service.findAllProduto(pageable)).thenReturn(produtosPage);
        ResponseEntity<?> response = resource.findAllProduto(0, 12, "asc");
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PagedModel.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void whenCreateProdutoThenReturnSuccess() {
        when(mapper.map(produto, ProdutoDTO.class)).thenReturn(produtoDTO);
        when(service.findProdutoById(anyInt())).thenReturn(produto);
        when(service.createProduto(produtoDTO)).thenReturn(produto);
        ProdutoDTO response = resource.createProduto(produtoDTO);
        assertNotNull(response);
        assertEquals(ProdutoDTO.class, response.getClass());
        assertEquals(PRODUTO_ID, response.getId());
        assertEquals(PRODUTO_NOME, response.getName());
        assertEquals(PRODUTO_ESTOQUE, response.getEstoque());
        assertEquals(PRODUTO_PRECO, response.getPreco());

    }

    @Test
    void whenUpdateProdutoThenReturnSuccess() {
        when(mapper.map(produto, ProdutoDTO.class)).thenReturn(produtoDTO);
        when(service.findProdutoById(anyInt())).thenReturn(produto);
        when(service.updateProduto(produtoDTO)).thenReturn(produto);
        ProdutoDTO response = resource.updateProduto(produtoDTO);
        assertNotNull(response);
        assertEquals(ProdutoDTO.class, response.getClass());
        assertEquals(PRODUTO_ID, response.getId());
        assertEquals(PRODUTO_NOME, response.getName());
        assertEquals(PRODUTO_ESTOQUE, response.getEstoque());
        assertEquals(PRODUTO_PRECO, response.getPreco());
    }

    @Test
    void whenDeleteProdutoThenReturnSuccess() {
        doNothing().when(service).deleteProduto(anyInt());
        ResponseEntity<ProdutoDTO> response = resource.deleteProduto(PRODUTO_ID);
        assertNotNull(response);
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    private void startProduto() {
        produto = new Produto(PRODUTO_ID, PRODUTO_NOME, PRODUTO_ESTOQUE, PRODUTO_PRECO);
        produtoDTO = new ProdutoDTO(PRODUTO_ID, PRODUTO_NOME, PRODUTO_ESTOQUE, PRODUTO_PRECO);
        optionalProduto = Optional.of(new Produto(PRODUTO_ID, PRODUTO_NOME, PRODUTO_ESTOQUE, PRODUTO_PRECO));
        produtosDTOPage = new PageImpl<>(List.of(produtoDTO));
        produtosPage = new PageImpl<>(List.of(produto));
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        PagedResourcesAssembler<ProdutoDTO> produtoDTOAssembler = new PagedResourcesAssembler<ProdutoDTO>(resolver, null);
        produtosDTOPageModel = produtoDTOAssembler.toModel(produtosDTOPage);

    }
}