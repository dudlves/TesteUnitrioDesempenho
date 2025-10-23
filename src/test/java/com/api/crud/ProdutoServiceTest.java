package com.api.crud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;\n\nimport com.api.crud.model.Produto;\nimport com.api.crud.repository.ProdutoRepository;\nimport com.api.crud.service.ProdutoService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    private Produto produto1;
    private Produto produto2;

    @BeforeEach
    void setUp() {
        produto1 = new Produto(1L, "Notebook", "Laptop de alta performance", 5000.00, 10);
        produto2 = new Produto(2L, "Mouse", "Mouse sem fio ergonômico", 150.00, 50);
    }

    @Test
    void criar_DeveRetornarProdutoSalvo() {
        when(repository.save(any(Produto.class))).thenReturn(produto1);

        Produto resultado = service.criar(produto1);

        assertNotNull(resultado);
        assertEquals("Notebook", resultado.getNome());
        verify(repository, times(1)).save(produto1);
    }

    @Test
    void listarTodos_DeveRetornarListaDeProdutos() {
        List<Produto> lista = Arrays.asList(produto1, produto2);
        when(repository.findAll()).thenReturn(lista);

        List<Produto> resultado = service.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Mouse", resultado.get(1).getNome());
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_DeveRetornarOptionalComProduto_QuandoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(produto1));

        Optional<Produto> resultado = service.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Notebook", resultado.get().getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_DeveRetornarOptionalVazio_QuandoNaoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Produto> resultado = service.buscarPorId(99L);

        assertFalse(resultado.isPresent());
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void atualizar_DeveAtualizarProdutoExistente() {
        Produto produtoNovo = new Produto(1L, "Notebook Pro", "Laptop de altíssima performance", 6500.00, 5);
        when(repository.findById(1L)).thenReturn(Optional.of(produto1));
        when(repository.save(any(Produto.class))).thenReturn(produtoNovo);

        Produto resultado = service.atualizar(1L, produtoNovo);

        assertNotNull(resultado);
        assertEquals("Notebook Pro", resultado.getNome());
        assertEquals(6500.00, resultado.getPreco());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Produto.class));
    }

    @Test
    void atualizar_DeveLancarExcecao_QuandoProdutoNaoEncontrado() {
        Produto produtoNovo = new Produto(99L, "Inexistente", "Produto que não existe", 10.00, 1);
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.atualizar(99L, produtoNovo);
        });

        assertEquals("Produto não encontrado com ID: 99", exception.getMessage());
        verify(repository, times(1)).findById(99L);
        verify(repository, times(0)).save(any(Produto.class));
    }

    @Test
    void excluir_DeveExcluirProdutoExistente() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.excluir(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void excluir_DeveLancarExcecao_QuandoProdutoNaoEncontrado() {
        when(repository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.excluir(99L);
        });

        assertEquals("Produto não encontrado com ID: 99", exception.getMessage());
        verify(repository, times(1)).existsById(99L);
        verify(repository, times(0)).deleteById(99L);
    }
}
