package ru.kpfu.mobilereportapp.utils.server.exeptions;

/** Родитель иерархии исключений */
public class MobileReportException extends Exception {

    /** UID класса для сериализации */
    private static final long serialVersionUID = 7072852520857465341L;

    /**
     * Конструктор
     */
    public MobileReportException() {
        super();
    }

    /**
     * Конструктор
     *
     * @param aMessage сообщение об ошибке
     * @param aCause причина ошибки
     */
    public MobileReportException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }

    /**
     * Конструктор
     *
     * @param aMessage сообщение об ошибке
     */
    public MobileReportException(String aMessage) {
        super(aMessage);
    }

    /**
     * Конструктор
     *
     * @param aCause причина ошибки
     */
    public MobileReportException(Throwable aCause) {
        super(aCause);
    }

    /**
     * Конструктор
     *
     * @param aMessageFormat шаблон строки - причины ошибки
     * @param aArgs аргументы шаблона
     */
    public MobileReportException(String aMessageFormat, Object... aArgs) {
        this(String.format(aMessageFormat, aArgs));
    }

    /**
     * Конструктор
     *
     * @param aCause причина исключения
     * @param aMessageFormat шаблон строки - причины ошибки
     * @param aArgs аргументы шаблона
     */
    public MobileReportException(Exception aCause, String aMessageFormat,
                                 Object... aArgs) {}

}
