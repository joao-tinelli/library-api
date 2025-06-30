package io.github.joao_tinelli.libraryapi.service;

import io.github.joao_tinelli.libraryapi.exception.RegistroDuplicadoException;
import io.github.joao_tinelli.libraryapi.model.Autor;
import io.github.joao_tinelli.libraryapi.repository.AutorRepository;
import io.github.joao_tinelli.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {
    private final AutorRepository repository;
    private final AutorValidator validator;
    public AutorService(AutorRepository repository, AutorValidator validator){
        this.repository = repository;
        this.validator = validator;
    }

    public Autor salvar(Autor autor) throws RegistroDuplicadoException {
        validator.validar(autor); // <------
        return repository.save(autor);
    }

    public void atualizar(Autor autor) throws RegistroDuplicadoException {
        if (autor.getId() == null){
            throw new IllegalArgumentException("Autor nao encontrado");
        }
        validator.validar(autor); // <------
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){ return repository.findById(id); }

    public void deletar(Autor autor){ repository.delete(autor); }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if (nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if (nome != null){
            return  repository.findByNome(nome);
        }

        if (nacionalidade != null){
            return  repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }
}
