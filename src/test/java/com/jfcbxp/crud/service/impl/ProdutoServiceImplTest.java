package com.jfcbxp.crud.service.impl;

import com.jfcbxp.crud.domain.Produto;
import com.jfcbxp.crud.domain.dto.ProdutoDTO;
import com.jfcbxp.crud.repository.ProdutoRepository;
import com.jfcbxp.crud.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ProdutoServiceImplTest {

    public static final String PRODUTO_NOME = "produto teste";
    public static final int PRODUTO_ID = 1;
    public static final BigDecimal PRODUTO_ESTOQUE = BigDecimal.ONE;
    public static final BigDecimal PRODUTO_PRECO = BigDecimal.TEN;
    public static final int INDEX = 0;
    public static final String OBJETO_NAO_ENCONTRADO_PARA_ESSE_ID = "Objeto não encontrado para esse id";
    @InjectMocks
    private ProdutoServiceImpl service;

    @Mock
    private ProdutoRepository repository;

    @Mock
    private ModelMapper mapper;

    private Produto produto;

    private ProdutoDTO produtoDTO;

    private Optional<Produto> optionalProduto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startProduto();
    }

    @Test
    void whenCreateProdutoThenReturnSuccess() {
        when(repository.save(any())).thenReturn(produto);
        Produto response = service.createProduto(produtoDTO);
        assertNotNull(response);
        assertEquals(Produto.class, response.getClass());
        assertEquals(PRODUTO_ID, response.getId());
        assertEquals(PRODUTO_NOME, response.getName());
        assertEquals(PRODUTO_ESTOQUE, response.getEstoque());
        assertEquals(PRODUTO_PRECO, response.getPreco());
        assertEquals(produto, response);

    }

    @Test
    void whenFindAllProdutoThenReturnAnListOfPageProduto() {
        Pageable pageable = PageRequest.of(0, 12, Sort.by(Sort.Direction.ASC, "name"));
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(produto)));
        Page<Produto> response = service.findAllProduto(pageable);
        assertNotNull(response);
        assertEquals(Produto.class, response.getContent().get(INDEX).getClass());
        assertEquals(PRODUTO_ID, response.getContent().get(INDEX).getId());
        assertEquals(PRODUTO_NOME, response.getContent().get(INDEX).getName());
        assertEquals(PRODUTO_ESTOQUE, response.getContent().get(INDEX).getEstoque());
        assertEquals(PRODUTO_PRECO, response.getContent().get(INDEX).getPreco());

    }

    @Test
    void whenFindProdutoByIdThenReturnAnProdutoInstance() {
        when(repository.findById(anyInt())).thenReturn(optionalProduto);

        Produto response = service.findProdutoById(PRODUTO_ID);
        assertNotNull(response);
        assertEquals(Produto.class, response.getClass());
        assertEquals(PRODUTO_ID, response.getId());
        assertEquals(PRODUTO_NOME, response.getName());
        assertEquals(PRODUTO_ESTOQUE, response.getEstoque());
        assertEquals(PRODUTO_PRECO, response.getPreco());
        assertEquals(response,produto);



    }

    @Test
    void whenFindProdutoByIdThenReturnAnObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO_PARA_ESSE_ID));
        Throwable exception = Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            service.findProdutoById(PRODUTO_ID);
        });
        assertEquals(ObjectNotFoundException.class, exception.getClass());
        assertEquals(OBJETO_NAO_ENCONTRADO_PARA_ESSE_ID, exception.getMessage());


    }

    @Test
    void whenUpdateProdutoThenReturnSuccess() {
        when(repository.findById(anyInt())).thenReturn(optionalProduto);
        when(repository.save(mapper.map(produtoDTO, Produto.class))).thenReturn(produto);

        Produto response = service.updateProduto(produtoDTO);
        assertNotNull(response);
        assertEquals(Produto.class, response.getClass());
        assertEquals(PRODUTO_ID, response.getId());
        assertEquals(PRODUTO_NOME, response.getName());
        assertEquals(PRODUTO_ESTOQUE, response.getEstoque());
        assertEquals(PRODUTO_PRECO, response.getPreco());

    }

    @Test
    void whenDeleteProdutoThenReturnSuccess() {
        when(repository.findById(anyInt())).thenReturn(optionalProduto);
        doNothing().when(repository).deleteById(anyInt());
        service.deleteProduto(PRODUTO_ID);
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void whenDeleteProdutoWithObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO_PARA_ESSE_ID));
        Throwable exception = assertThrows(ObjectNotFoundException.class, () -> {
            service.deleteProduto(PRODUTO_ID);
        });
        assertEquals(ObjectNotFoundException.class, exception.getClass());
        assertEquals(OBJETO_NAO_ENCONTRADO_PARA_ESSE_ID, exception.getMessage());
    }

    private void startProduto() {
        produto = new Produto(PRODUTO_ID, PRODUTO_NOME, PRODUTO_ESTOQUE, PRODUTO_PRECO);
        produtoDTO = new ProdutoDTO(PRODUTO_ID, PRODUTO_NOME, PRODUTO_ESTOQUE, PRODUTO_PRECO);
        optionalProduto = Optional.of(new Produto(PRODUTO_ID, PRODUTO_NOME, PRODUTO_ESTOQUE, PRODUTO_PRECO));
    }
}