package com.loja.sagradoLunar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.loja.sagradoLunar.model.Produto;
import com.loja.sagradoLunar.repository.ProdutoRepository;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    // Faz busca de todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> findAllProduto() {
        return ResponseEntity.ok(repository.findAll());
    }

    // Faz busca de produtos por id
    @GetMapping("/produto/{id}")
    public ResponseEntity<Produto> findByIdProduto(@PathVariable long id) {
        return repository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    // Faz busca de produtos por nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> findByNomeProduto(@PathVariable String nome) {
        return ResponseEntity.ok(repository.findAllByNomeContainingIgnoreCase(nome));
    }

    // Busca valores menores ou iguais ao cadastrados na tabela
    @GetMapping("/preco-menor/{preco}")
    public ResponseEntity<List<Produto>> findByProdutoPrecoMenor(@PathVariable int preco) {
        return ResponseEntity.ok(repository.findAllByPrecoLessThanEquals(preco));
    }

    // Busca valores maiores ou iguais ao cadastrados na tabela
    @GetMapping("/preco-maior/{preco}")
    public ResponseEntity<List<Produto>> findByProdutoPrecoMaior(@PathVariable int preco) {
        return ResponseEntity.ok(repository.findAllByPrecoGreaterThanEquals(preco));
    }

    // Busca os produtos ativos
    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<List<Produto>> findAllByAtivo(@PathVariable boolean ativo) {
        return ResponseEntity.ok(repository.findAllByAtivo(ativo));
    }

    @PostMapping
    public ResponseEntity<Produto> postProduto(@RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
    }

    @PutMapping
    public ResponseEntity<Produto> putProduto(@RequestBody Produto produto) {
        return ResponseEntity.ok(repository.save(produto));
    }

    @DeleteMapping("/{id}")
    public void deleteProduto(@PathVariable long id) {
        repository.deleteById(id);
    }

}
