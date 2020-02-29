package persistence;

import model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * класс для взаимодйствия с бд с использованием hibernate
 */
public class InteractionWithDB {
    /**
     * приватное поле для хранения экземпляра объекта
     */
    private static final InteractionWithDB INSTANCE = new InteractionWithDB();

    /**
     * приватный конструктор для синглтона
     */
    private InteractionWithDB() {
    }

    ;

    /**
     * возвращает экземпляр объекта
     *
     * @return экземляр объекта
     */
    public static InteractionWithDB getInstance() {
        return INSTANCE;
    }

    /**
     * создает новую фабрику для создания сессий hibernate
     *
     * @return фабрика hibernate
     */
    private SessionFactory createSessionFactory() {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Item.class)
                .buildSessionFactory();
    }

    /**
     * сохраняет задачу в бд
     *
     * @param item - задача
     * @return - результат сохранения
     */
    public boolean saveItem(Item item) {
        boolean result = false;
        try (Session session = createSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            result = true;
        }
        return result;
    }

    /**
     * получает сисок всех задач из бд
     *
     * @return - список задач
     */
    public List<Item> getAllItem() {
        List<Item> itemList = null;
        try (Session session = createSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            itemList = session.createQuery("FROM Item").getResultList();
        }
        return itemList;
    }

    /**
     * получает список задач в зависимости от статуса выполнения
     *
     * @param typeOfDone - статус выполнения задач
     * @return список задач с соответствующим статусом
     */
    public List<Item> getItemsWithTypeOfDone(boolean typeOfDone) {
        List<Item> itemList = null;
        try (Session session = createSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            String query = "FROM Item WHERE done = " + typeOfDone;
            itemList = session.createQuery(query).getResultList();
        }
        return itemList;
    }

    /**
     * @param id         - id задачи в базе данных
     * @param typeOfDone - статус выполнения задачи
     * @return - результат изменеия статуса задачи
     */
    public boolean setDoneItem(int id, boolean typeOfDone) {
        boolean result = false;
        try (Session session = createSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Item tempItem = session.get(Item.class, id);
            tempItem.setDone(typeOfDone);
            session.save(tempItem);
            session.getTransaction().commit();
            result = true;
        }
        return result;
    }
}
