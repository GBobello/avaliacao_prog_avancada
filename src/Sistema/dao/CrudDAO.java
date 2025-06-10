package Sistema.dao;

import java.util.List;

public interface CrudDAO<T> {
    void inserir(T obj) throws Exception;
    void editar(T obj) throws Exception;
    T visualizar(int id) throws Exception;
    void deletar(int id) throws Exception;
    List<T> listar() throws Exception;
}