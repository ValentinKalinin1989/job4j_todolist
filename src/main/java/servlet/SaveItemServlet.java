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
import java.time.LocalDate;

/**
 * сервлет для сохранения задачи в бд
 */
public class SaveItemServlet extends HttpServlet {
    private final InteractionWithDB database = InteractionWithDB.getInstance();
    private static final Logger LOG = LogManager.getLogger(GetItemsServlet.class.getName());

    /**
     * сохраняет в бд переданную задачу(генерирует id для неё и сохраняет дату поступления)
     * и возвращает результат выполнения с информацией о id и дате поступления задачи
     *
     * @param request  задача для сохранения в формате json
     * @param response результат сохранения задачи и задача с информацией о id и дате поступления
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
        String desc = jsonNode.get("description").asText();
        ObjectNode responseNode = objectMapper.createObjectNode();
        LocalDate localDate = LocalDate.now();
        Item saveItem = new Item(desc, localDate, false);
        if (database.saveItem(saveItem)) {
            responseNode.put("success", true)
                    .put("id", saveItem.getId())
                    .put("description", desc)
                    .put("created", localDate.toString())
                    .put("done", false);
        } else {
            responseNode.put("success", false);
        }
        PrintWriter writer = response.getWriter();
        writer.append(objectMapper.writeValueAsString(responseNode));
        writer.flush();

    }
}
