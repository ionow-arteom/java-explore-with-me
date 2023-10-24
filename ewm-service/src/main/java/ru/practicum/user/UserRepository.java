package ru.practicum.user;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByIdInOrderByIdAsc(List<Long> ids, PageRequest pageRequest);
}
