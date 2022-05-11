package com.jfcbxp.crud.service;

import com.jfcbxp.crud.domain.Produto;
import com.jfcbxp.crud.domain.dto.ProdutoDTO;

public interface ProdutoService {
    Produto createProduto(ProdutoDTO produto);
}
