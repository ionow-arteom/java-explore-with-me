package ru.practicum.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.util.enumerated.Status;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class RequestTest {

    @Autowired
    private RequestRepository requestRepository;

    private Request request;

    @BeforeEach
    void setUp() {
        request = Request.builder()
                .created(LocalDateTime.now())
                .status(Status.PENDING)
                .build();
    }

    @Test
    @DisplayName("Create Request with Null Created Date: Should Throw DataIntegrityViolationException")
    void createRequestWithNullCreatedDate() {
        request.setCreated(null);
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> requestRepository.save(request));
    }

    @Test
    @DisplayName("Create Request with Null Status: Should Throw DataIntegrityViolationException")
    void createRequestWithNullStatus() {
        request.setStatus(null);
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> requestRepository.save(request));
    }
}