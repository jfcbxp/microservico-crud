package com.jfcbxp.crud.service;

import com.jfcbxp.crud.domain.Produto;
import com.jfcbxp.crud.domain.dto.ProdutoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoService {
    Produto createProduto(ProdutoDTO produtoDTO);

    Page<Produto> findAllProduto(Pageable pageable);

    Produto findProdutoById(Integer id);

    Produto updateProduto(ProdutoDTO produtoDTO);

    void deleteProduto(Integer id);
}
