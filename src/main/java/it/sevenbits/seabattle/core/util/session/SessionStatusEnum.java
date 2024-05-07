package it.sevenbits.seabattle.core.util.session;

public enum SessionStatusEnum {
    STATUS_PENDING,
    STATUS_ARRANGEMENT,
    STATUS_GAME,
    STATUS_FINISH,
    STATUS_UNEXPECTED,
    STATUS_CANCELED;

    @Override
    public String toString() {
        return super.toString();
    }
}
