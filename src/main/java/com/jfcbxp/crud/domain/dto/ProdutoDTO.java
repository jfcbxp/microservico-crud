package com.jfcbxp.crud.domain.dto;

import com.jfcbxp.crud.validation.CreateProdutoValidation;
import com.jfcbxp.crud.validation.UpdateProdutoValidation;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProdutoDTO extends RepresentationModel<ProdutoDTO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 7069290981939274043L;

    @NotNull(groups = UpdateProdutoValidation.class,message = "id não pode ser nulo")
    @Null(groups = CreateProdutoValidation.class,message = "id deve ser nulo")
    private Integer id;
    private String name;
    private BigDecimal estoque;
    private BigDecimal preco;
}
