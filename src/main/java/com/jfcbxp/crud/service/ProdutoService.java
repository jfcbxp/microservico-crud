package com.jfcbxp.crud.service;

import com.jfcbxp.crud.domain.Produto;
import com.jfcbxp.crud.domain.dto.ProdutoDTO;

import java.util.List;

public interface ProdutoService {
    Produto createProduto(ProdutoDTO produto);

    List<Produto> findAllProduto();
}
