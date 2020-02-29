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
     * если задача выполнена то значение рано true
     */
    @Column(name = "done")
    private boolean done;

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
     * @param done        - статус выполнения задачи (true - если выполнена)
     */
    public Item(String description, LocalDate date, boolean done) {
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
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
}
