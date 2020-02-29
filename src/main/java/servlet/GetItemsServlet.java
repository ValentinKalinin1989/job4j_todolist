package servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.InteractionWithDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * сервлет для получения списка задач с определенным статусом
 * и изменения статуса задачи с определенным id
 */
public class GetItemsServlet extends HttpServlet {
    private final InteractionWithDB database = InteractionWithDB.getInstance();
    private static final Logger LOG = LogManager.getLogger(GetItemsServlet.class.getName());

    /**
     * возвращает список задач с определенным статусом в виде json-объекта
     *
     * @param request  содержит параметр статуса выполнения задач
     * @param response возвращает задачи с выбранным статусом в виде json-объекта
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Item> itemList = new ArrayList<>();
        String done = request.getParameter("done");
        if (done.equals("true")) {
            itemList = database.getItemsWithTypeOfDone(true);
        } else if (done.equals("false")) {
            itemList = database.getItemsWithTypeOfDone(false);
        } else {
            itemList = database.getAllItem();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonFromList = objectMapper.writeValueAsString(itemList);
        PrintWriter writer = response.getWriter();
        writer.println(jsonFromList);
        writer.close();
    }

    /**
     * меняет статус задачи и возвращает результат выполнения операции
     *
     * @param request  содержит id и статус задачи, который нужно установить для с задачи с данным id
     * @param response возвращает json с результатом обновлениния статуса задачи
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        reader.lines().forEach(stringBuilder::append);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(stringBuilder.toString());
        int id = jsonNode.get("id").asInt();
        boolean done = jsonNode.get("done").asBoolean();
        LOG.info("id=" + id + " done=" + done);
        System.out.println("id=" + id + "  done=" + done);

        ObjectNode responseNode = objectMapper.createObjectNode();
        if (database.setDoneItem(id, done)) {
            responseNode.put("success", true).put("id", id).put("done", done);
        } else {
            responseNode.put("success", false);
        }
        PrintWriter writer = response.getWriter();
        writer.append(objectMapper.writeValueAsString(responseNode));
        writer.flush();
    }
}
