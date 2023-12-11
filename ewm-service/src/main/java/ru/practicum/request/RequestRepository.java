package ru.practicum.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.util.enumerated.Status;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long userId);

    List<Request> findAllByEventId(Long eventId);

    Optional<Request> findByRequesterIdAndEventId(Long userId, Long eventId);

    Long countAllByEventIdAndStatus(Long eventId, Status status);
}