package com.nisum.apirest.repository;

import com.nisum.apirest.model.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone,Long> {

    Optional<List<Phone>> findByUserId(long userId);
}
