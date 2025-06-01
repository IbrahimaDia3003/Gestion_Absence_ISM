package sn.ism.gestion.Config;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Service<T> {

    T create(T object);
    T update(String id, T object);
    boolean delete(String id); 
    T findById(String id);
    List<T> findAll();
    Page<T> findAll(Pageable pageable);

}
