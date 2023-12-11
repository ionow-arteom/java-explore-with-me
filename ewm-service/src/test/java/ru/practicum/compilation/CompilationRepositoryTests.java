package ru.practicum.compilation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CompilationRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompilationRepository repository;

    @Test
    public void findByPinnedShouldReturnPinnedCompilations() {
        Compilation pinnedCompilation = new Compilation(null, true, "Pinned Compilation", null);
        entityManager.persistAndFlush(pinnedCompilation);

        Compilation notPinnedCompilation = new Compilation(null, false, "Not Pinned Compilation", null);
        entityManager.persistAndFlush(notPinnedCompilation);

        List<Compilation> pinnedCompilations = repository.findByPinned(true, PageRequest.of(0, 10));

        assertThat(pinnedCompilations).hasSize(1);
        assertThat(pinnedCompilations.get(0).getPinned()).isTrue();
        assertThat(pinnedCompilations.get(0).getTitle()).isEqualTo("Pinned Compilation");
    }
}