package ru.kpfu.mobilereportapp.utils.server.exeptions;

/** Исключения, возникающие в ходе регистрации */
public class RegistrationUserException extends LoginException{

    /** UID класса для сериализации */
    private static final long serialVersionUID = 7072852520857465341L;

    /**
     * Конструктор
     */
    public RegistrationUserException() {
        super();
    }

    /**
     * Конструктор
     *
     * @param aMessage сообщение об ошибке
     * @param aCause причина ошибки
     */
    public RegistrationUserException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }

    /**
     * Конструктор
     *
     * @param aMessage сообщение об ошибке
     */
    public RegistrationUserException(String aMessage) {
        super(aMessage);
    }

    /**
     * Конструктор
     *
     * @param aCause причина ошибки
     */
    public RegistrationUserException(Throwable aCause) {
        super(aCause);
    }

    /**
     * Конструктор
     *
     * @param aMessageFormat шаблон строки - причины ошибки
     * @param aArgs аргументы шаблона
     */
    public RegistrationUserException(String aMessageFormat, Object... aArgs) {
        this(String.format(aMessageFormat, aArgs));
    }

    /**
     * Конструктор
     *
     * @param aCause причина исключения
     * @param aMessageFormat шаблон строки - причины ошибки
     * @param aArgs аргументы шаблона
     */
    public RegistrationUserException(Exception aCause, String aMessageFormat,
                                     Object... aArgs) {}

}
