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
        interactionWithDB.saveItem(new Item("Задача выбора фреймворка для работы", LocalDate.now(), false));
    }
    @Test
    public void testGetItemIsDone() {
        InteractionWithDB interactionWithDB = InteractionWithDB.getInstance();
        Item item = interactionWithDB.getItemsWithTypeOfDone(false).get(0);
        System.out.println(item);
    }
    @Test
    public void testAllItem() {
        InteractionWithDB interactionWithDB = InteractionWithDB.getInstance();
        List<Item> itemList = interactionWithDB.getItemsWithTypeOfDone(false);
        itemList.forEach(System.out::println);
    }
    @Test
    public void testChandeDone() {
        InteractionWithDB interactionWithDB = InteractionWithDB.getInstance();
        interactionWithDB.setDoneItem(2, false);
    }

}
