package com.ikservices.oficinamecanica.commons.generics;

import java.util.List;

/**
 * Converter pattern for Clean Architecture
 * @param <I> request
 * @param <D> domain
 * @param <E> entity
 * @param <O> response
 */
public abstract class IKConverter<I, D, E, O> {
    public abstract D parseRequestToDomain(I request);
    public abstract E parseDomainToEntity(D domain);
    public abstract D parseEntityToDomain(E entity);
    public abstract O parseDomainToResponse(D domain);
    public abstract List<D> parseRequestToDomainList(List<I> requestList);
    public abstract List<E> parseDomainToEntityList(List<D> domainList);
    public abstract List<D> parseEntityToDomainList(List<E> entityList);
    public abstract List<O> parseDomainToResponse(List<D> domainList);
}
