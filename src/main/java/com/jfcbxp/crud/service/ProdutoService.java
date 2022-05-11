package com.jfcbxp.crud.service;

import com.jfcbxp.crud.domain.Produto;
import com.jfcbxp.crud.domain.dto.ProdutoDTO;

import java.util.List;

public interface ProdutoService {
    Produto createProduto(ProdutoDTO produtoDTO);

    List<Produto> findAllProduto();

    Produto findById(Integer id);

    Produto update(ProdutoDTO produtoDTO);

    void delete(Integer id);
}
