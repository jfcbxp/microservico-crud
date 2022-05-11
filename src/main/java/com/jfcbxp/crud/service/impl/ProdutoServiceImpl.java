package com.jfcbxp.crud.service.impl;

import com.jfcbxp.crud.domain.Produto;
import com.jfcbxp.crud.domain.dto.ProdutoDTO;
import com.jfcbxp.crud.repository.ProdutoRepository;
import com.jfcbxp.crud.service.ProdutoService;
import com.jfcbxp.crud.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    public static final String OBJETO_NAO_ENCONTRADO_POR_ESSE_ID = "Objeto não encontrado por esse id";
    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Produto createProduto(ProdutoDTO produtoDTO) {
        return repository.save(mapper.map(produtoDTO, Produto.class));
    }

    @Override
    public Page<Produto> findAllProduto(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Produto findProdutoById(Integer id) {
        Optional<Produto> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO_POR_ESSE_ID));
    }

    @Override
    public Produto updateProduto(ProdutoDTO produtoDTO) {
        return repository.save(findProdutoById(produtoDTO.getId()));
    }

    @Override
    public void deleteProduto(Integer id) {
        repository.deleteById(findProdutoById(id).getId());
    }


}
