package com.jfcbxp.crud.service.impl;

import com.jfcbxp.crud.domain.Produto;
import com.jfcbxp.crud.domain.dto.ProdutoDTO;
import com.jfcbxp.crud.repository.ProdutoRepository;
import com.jfcbxp.crud.service.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Produto createProduto(ProdutoDTO produto) {
        return repository.save(mapper.map(produto,Produto.class));
    }

    @Override
    public List<Produto> findAllProduto() {
        return repository.findAll();
    }


}
