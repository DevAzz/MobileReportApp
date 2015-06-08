package ru.kpfu.mobilereportapp.utils.server.exeptions;

/** Исключения, возникающие при входе в систему */
public class LoginException extends MobileReportException {

    /** UID класса для сериализации */
    private static final long serialVersionUID = 7072852520857465341L;

    /**
     * Конструктор
     */
    public LoginException() {
        super();
    }

    /**
     * Конструктор
     *
     * @param aMessage сообщение об ошибке
     * @param aCause причина ошибки
     */
    public LoginException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }

    /**
     * Конструктор
     *
     * @param aMessage сообщение об ошибке
     */
    public LoginException(String aMessage) {
        super(aMessage);
    }

    /**
     * Конструктор
     *
     * @param aCause причина ошибки
     */
    public LoginException(Throwable aCause) {
        super(aCause);
    }

    /**
     * Конструктор
     *
     * @param aMessageFormat шаблон строки - причины ошибки
     * @param aArgs аргументы шаблона
     */
    public LoginException(String aMessageFormat, Object... aArgs) {
        this(String.format(aMessageFormat, aArgs));
    }

    /**
     * Конструктор
     *
     * @param aCause причина исключения
     * @param aMessageFormat шаблон строки - причины ошибки
     * @param aArgs аргументы шаблона
     */
    public LoginException(Exception aCause, String aMessageFormat,
                          Object... aArgs) {}

}
