package com.example.demo.repo;

import com.example.demo.models.Employee;
import com.example.demo.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByNamesContains(String names);

    List<Product> findByNames(String names);

}
