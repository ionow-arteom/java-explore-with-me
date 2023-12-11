package ru.practicum.events.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.categories.Category;
import ru.practicum.user.User;
import ru.practicum.util.enumerated.State;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.dto.utilities.Constants.TIME_ORDER;

@Entity
@Table(name = "events", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Size(min = 20, max = 2000)
    @Column(name = "annotation", nullable = false)
    private String annotation;

    @Size(min = 20, max = 7000)
    @Column(name = "description")
    private String description;

    @Column(name = "event_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_ORDER)
    private LocalDateTime eventDate;

    @Column(name = "paid")
    private Boolean paid;

    @JoinColumn(name = "confirmed_requests")
    private Long confirmedRequests;

    @Column(name = "participant_limit")
    private Long participantLimit;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @Column(name = "published_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_ORDER)
    private LocalDateTime publishedOn;

    @Column(name = "created_on", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_ORDER)
    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "views")
    private Long views;
}