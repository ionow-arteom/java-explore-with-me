package ru.practicum.util.enumerated;

public enum Status {

    PENDING,
    CONFIRMED,
    CANCELED,
    REJECTED;

    public static Status getStatusValue(String status) {
        try {
            return Status.valueOf(status);
        } catch (Exception e) {
            throw new IllegalStateException("Unknow status: " + status);
        }
    }
}
