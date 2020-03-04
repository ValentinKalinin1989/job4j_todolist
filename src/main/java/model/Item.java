package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * класс для хранения информации о задачах (item)
 */
@Entity
@Table(name = "item")
public class Item {
    /**
     * используется для хранения id задачи в БД
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /**
     * хранит описание задачи
     */
    @Column(name = "description")
    private String description;
    /**
     * время создания задачи
     */
    @Column(name = "created")
    private LocalDate date;
    /**
     * значение выполнения задания,
     * done - если выполнена, undone - если нет
     */
    @Column(name = "done")
    private String done;
    /**
     * констркуктор без аргументов для JPA
     */
    public Item() {
    }
    /**
     * конструктор
     *
     * @param description - описание задачи
     * @param date        - дата создания задачи
     * @param done        - статус выполнения задачи (done - если выполнена, undone - если нет)
     */
    public Item(String description, LocalDate date, String done) {
        this.description = description;
        this.date = date;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String isDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return done == item.done &&
                description.equals(item.description) &&
                date.equals(item.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, date, done);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", done=" + done +
                '}';
    }

    /**
     * Создает строку с разметкой json-объекта
     * вывода для id = 1, description = "Find framework for UI", date = "2020-02-25", done = "undone" представлен ниже
     * {"id":1,"description":"Find framework for UI","date":"2020-02-25","done":"undone"}
     *
     * @return строка c json разметкой
     */
    public String toJsonString() {
        return "{" +
                "\"id\"" + ":" + id + "," +
                "\"description\"" + ":" + "\"" + description + "\"," +
                "\"date\"" + ":" + "\"" + date + "\"," +
                "\"done\"" + ":" + "\"" + done + "\"" +
                "}";
    }
}
