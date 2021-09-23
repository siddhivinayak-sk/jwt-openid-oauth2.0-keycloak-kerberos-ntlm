package com.sk.resource.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sk.resource.persistence.model.Foo;

public interface IFooRepository extends PagingAndSortingRepository<Foo, Long> {
}
