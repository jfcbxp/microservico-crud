package com.jfcbxp.crud.domain.dto;

import com.jfcbxp.crud.domain.Produto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProdutoDTO {
    private Integer id;
    private String name;
    private BigDecimal estoque;
    private BigDecimal preco;

    public static ProdutoDTO create(Produto produto) {
        return new ModelMapper().map(produto, ProdutoDTO.class);
    }
}
