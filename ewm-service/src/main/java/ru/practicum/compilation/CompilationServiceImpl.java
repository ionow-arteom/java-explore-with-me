package ru.practicum.compilation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationNewDto;
import ru.practicum.compilation.dto.CompilationUpdateDto;
import ru.practicum.events.EventRepository;
import ru.practicum.util.UnionService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final UnionService unionService;

    @Override
    @Transactional
    public CompilationDto add(CompilationNewDto compilationNewDto) {
        Compilation compilation = CompilationMapper.returnCompilation(compilationNewDto);
        setCompilationDefaults(compilation, compilationNewDto.getEvents());
        log.info("Adding compilation with title: {}", compilation.getTitle());
        return CompilationMapper.returnCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        unionService.getCompilationOrNotFound(compId);
        compilationRepository.deleteById(compId);
        log.info("Deleted compilation with ID: {}", compId);
    }

    @Override
    @Transactional
    public CompilationDto update(Long compId, CompilationUpdateDto compilationUpdateDto) {
        Compilation compilation = unionService.getCompilationOrNotFound(compId);
        setCompilationDefaults(compilation, compilationUpdateDto.getEvents());
        if (compilationUpdateDto.getTitle() != null) {
            compilation.setTitle(compilationUpdateDto.getTitle());
        }
        log.info("Updating compilation with ID: {}", compId);
        return CompilationMapper.returnCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public List<CompilationDto> getList(Boolean pinned, Integer from, Integer size) {

        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Compilation> compilations;

        if (pinned) {
            compilations = compilationRepository.findByPinned(pinned, pageRequest);
        } else {
            compilations = compilationRepository.findAll(pageRequest).getContent();;
        }
        return new ArrayList<>(CompilationMapper.returnCompilationDtoSet(compilations));
    }

    @Override
    public CompilationDto getById(Long compId) {
        Compilation compilation = unionService.getCompilationOrNotFound(compId);
        log.info("Fetching compilation with ID: {}", compId);
        return CompilationMapper.returnCompilationDto(compilation);
    }

    private void setCompilationDefaults(Compilation compilation, Set<Long> eventIds) {
        if (compilation.getPinned() == null) {
            compilation.setPinned(false);
        }
        compilation.setEvents(eventIds == null || eventIds.isEmpty() ?
                Collections.emptySet() :
                eventRepository.findByIdIn(eventIds));
    }
}