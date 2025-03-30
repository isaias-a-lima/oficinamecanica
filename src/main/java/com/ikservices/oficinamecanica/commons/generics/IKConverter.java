package com.ikservices.oficinamecanica.commons.generics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<D> parseRequestToDomainList(List<I> requestList) {
        List<D> domainList = new ArrayList<>();
        if (Objects.nonNull(requestList) && !requestList.isEmpty()) {
            for (I requestDTO : requestList) {
                domainList.add(this.parseRequestToDomain(requestDTO));
            }
        }
        return domainList;
    }

    public List<E> parseDomainToEntityList(List<D> domainList) {
        List<E> entityList = new ArrayList<>();
        if (Objects.nonNull(domainList) && !domainList.isEmpty()) {
            for (D domain : domainList) {
                entityList.add(this.parseDomainToEntity(domain));
            }
        }
        return entityList;
    }

    public List<D> parseEntityToDomainList(List<E> entityList) {
        List<D> domainList = new ArrayList<>();
        if (Objects.nonNull(entityList) && !entityList.isEmpty()) {
            for (E entity : entityList) {
                domainList.add(this.parseEntityToDomain(entity));
            }
        }
        return domainList;
    }

    public List<O> parseDomainToResponseList(List<D> domainList) {
        List<O> dtoList = new ArrayList<>();
        if (Objects.nonNull(domainList) && !domainList.isEmpty()) {
            for (D domain : domainList) {
                dtoList.add(this.parseDomainToResponse(domain));
            }
        }
        return dtoList;
    }
}
