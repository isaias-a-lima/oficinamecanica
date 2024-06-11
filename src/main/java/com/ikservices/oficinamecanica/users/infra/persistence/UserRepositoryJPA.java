package com.ikservices.oficinamecanica.users.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepositoryJPA extends JpaRepository<UserEntity, Long> {
    public List<UserEntity> findAllByActiveTrue();

    public UserEntity findByUsername(@Param("username") String username);

    public UserEntity findByCpf(Long cpf);
}
