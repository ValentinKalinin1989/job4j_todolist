package database;

import model.Item;
import org.junit.Test;
import persistence.InteractionWithDB;

import java.time.LocalDate;
import java.util.List;

public class hibernate_test {
    @Test
    public void testSaveItem() {
        InteractionWithDB interactionWithDB = InteractionWithDB.getInstance();
        interactionWithDB.saveItem(new Item("Задача выбора фреймворка для работы", LocalDate.now(), "undone"));
    }
    @Test
    public void testGetItemIsDone() {
        InteractionWithDB interactionWithDB = InteractionWithDB.getInstance();
        Item item = interactionWithDB.getItemsWithTypeOfDone("done").get(0);
        System.out.println(item);
    }
    @Test
    public void testAllItem() {
        InteractionWithDB interactionWithDB = InteractionWithDB.getInstance();
        List<Item> itemList = interactionWithDB.getAllItem();
        itemList.forEach(System.out::println);
    }
    @Test
    public void testChandeDone() {
        InteractionWithDB interactionWithDB = InteractionWithDB.getInstance();
        interactionWithDB.setDoneItem(2, "undone");
    }
    @Test
    public void testChangeDoneItem() {
        InteractionWithDB interactionWithDB = InteractionWithDB.getInstance();
        interactionWithDB.setDoneItem(1, "done");
    }
}
