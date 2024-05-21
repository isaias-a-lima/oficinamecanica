package com.ikservices.oficinamecanica.users.infra.persistence;

import com.ikservices.oficinamecanica.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepositoryJPA extends JpaRepository<UserEntity, Long> {
    public List<UserEntity> findAllByAtivoTrue();

    //@Query("from UserEntity where email = :email and senha = :senha and ativo = true")
    public UserEntity findByEmail(@Param("email") String email);

    public UserEntity findByCpf(Long cpf);
}
