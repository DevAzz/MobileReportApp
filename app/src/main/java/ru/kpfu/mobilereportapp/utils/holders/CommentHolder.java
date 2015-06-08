package ru.kpfu.mobilereportapp.utils.holders;

import java.util.ArrayList;
import java.util.List;

import ru.kpfu.mobilereportapp.entities.CommentEntity;

/**
 * Класс для работы с моделью комментария
 * Created by Azz on 01.04.2015.
 */
public class CommentHolder {

    /**
     * Метод, возвращающий список комментариев
     * @return список комментариев
     */
    public List<CommentEntity> getListModel() {
        List<CommentEntity> list = new ArrayList<>();
        //FIXME Временно
        list.add(getModel("Иванов Иван","Ололо", "1.04.2015"));
        list.add(getModel("Петров Женя","Ололо", "3.04.2015"));
        list.add(getModel("Иванов Иван","Ололо", "1.04.2015"));
        list.add(getModel("Петров Женя","Ололо", "3.04.2015"));
        list.add(getModel("Иванов Иван","Ололо", "1.04.2015"));
        list.add(getModel("Петров Женя","Ололо", "3.04.2015"));
        list.add(getModel("Иванов Иван","Ололо", "1.04.2015"));
        list.add(getModel("Петров Женя","Ололо", "3.04.2015"));
        list.add(getModel("Иванов Иван","Ололо", "1.04.2015"));
        list.add(getModel("Петров Женя","Ололо", "3.04.2015"));


        return list;
    }

    /**
     * Метод, возвращающий модель
     * @param aNameUser имя юзера
     * @param aDateComment дата комментария
     * @param aTextComment  текст Комметария
     * @return Модель комментария
     */
    private CommentEntity getModel(String aNameUser, String aDateComment, String aTextComment) {
        return new CommentEntity(aNameUser, aDateComment, aTextComment);
    }

}
