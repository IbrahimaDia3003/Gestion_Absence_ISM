package sn.ism.gestion.Config.Impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import sn.ism.gestion.Config.Service;
import sn.ism.gestion.utils.exceptions.EntityNotFoundExecption;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public abstract class IService<T, R extends MongoRepository<T, String>> implements Service<T> {

    protected final R repository;

    @Override
    public T create(T object) {
        return repository.save(object);
    }

    @Override
    public T update(String id, T object) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundExecption("Entity not found with id: " + id);
        }
        // object.setId(id);
        return repository.save(object);
    }

    @Override
    public boolean delete(String id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public T findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundExecption("Entity not found with id: " + id));
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
