package ru.kpfu.mobilereportapp.utils.server.exeptions;

/** Исключения, возникающие при получении текущего пользователя */
public class CurrentUserException extends LoginException {

    /** UID класса для сериализации */
    private static final long serialVersionUID = 7072852520857465341L;

    /**
     * Конструктор
     */
    public CurrentUserException() {
        super();
    }

    /**
     * Конструктор
     *
     * @param aMessage сообщение об ошибке
     * @param aCause причина ошибки
     */
    public CurrentUserException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }

    /**
     * Конструктор
     *
     * @param aMessage сообщение об ошибке
     */
    public CurrentUserException(String aMessage) {
        super(aMessage);
    }

    /**
     * Конструктор
     *
     * @param aCause причина ошибки
     */
    public CurrentUserException(Throwable aCause) {
        super(aCause);
    }

    /**
     * Конструктор
     *
     * @param aMessageFormat шаблон строки - причины ошибки
     * @param aArgs аргументы шаблона
     */
    public CurrentUserException(String aMessageFormat, Object... aArgs) {
        this(String.format(aMessageFormat, aArgs));
    }

    /**
     * Конструктор
     *
     * @param aCause причина исключения
     * @param aMessageFormat шаблон строки - причины ошибки
     * @param aArgs аргументы шаблона
     */
    public CurrentUserException(Exception aCause, String aMessageFormat,
                                Object... aArgs) {}

}
