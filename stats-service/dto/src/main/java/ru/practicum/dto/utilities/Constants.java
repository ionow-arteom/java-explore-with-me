package ru.practicum.dto.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Constants {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String TIME_ORDER = "yyyy-MM-dd HH:mm:ss";

    public static final LocalDateTime START_TIME = LocalDateTime.of(1970,1,1,0,0);
}

