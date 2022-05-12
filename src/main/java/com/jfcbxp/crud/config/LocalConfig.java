package com.jfcbxp.crud.config;

import com.jfcbxp.crud.domain.Produto;
import com.jfcbxp.crud.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private ProdutoRepository repository;

    @Bean
    public void startBD() {
        Produto p1 = new Produto(null,"cimento", BigDecimal.ONE,BigDecimal.TEN);
        Produto p2 = new Produto(null,"telha", BigDecimal.TEN,BigDecimal.ONE);
        repository.saveAll(List.of(p1,p2));

    }

}
