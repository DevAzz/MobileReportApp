package ru.kpfu.mobilereportapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с моделью комментария
 * Created by Azz on 01.04.2015.
 */
public class CommentHolder {

    /**
     * Метод, возвращающий список комментариев
     * @return список комментариев
     */
    public List<CommentModel> getListModel() {
        List<CommentModel> list = new ArrayList<>();
        //FIXME Временно
        list.add(getModel("Иванов Иван", "1.04.2015", "Ололо"));
        list.add(getModel("Петров Женя", "3.04.2015", "Ололо"));
        return list;
    }

    /**
     * Метод, возвращающий модель
     * @param aNameUser имя юзера
     * @param aDateComment дата комментария
     * @param aTextComment  текст Комметария
     * @return Модель комментария
     */
    private CommentModel getModel(String aNameUser, String aDateComment, String aTextComment) {
        return new CommentModel(aNameUser, aDateComment, aTextComment);
    }

}
