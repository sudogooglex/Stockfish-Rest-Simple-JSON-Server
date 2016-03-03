package Model.Jersey;

import Controller.bot.Bot;
import Controller.bot.OutputBeanBot;
import Controller.main.StockFishException;
import Model.Conf;
import com.fasterxml.jackson.jaxrs.json.annotation.JSONP;
import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 */
@Path("bot")
public class BotResource {

    @POST
    @JSONP
    @Consumes("application/x-www-form-urlencoded")
    @Produces({"application/javascript", MediaType.APPLICATION_JSON})
    public OutputBeanBot postHandler(String jsonContent) {
        OutputBeanBot outputBeanBot = getOutputBot(jsonContent);

        return outputBeanBot;
    }

    @GET
    @JSONP
    @Consumes("application/x-www-form-urlencoded")
    @Produces({"application/javascript", MediaType.APPLICATION_JSON})
    public OutputBeanBot getSimpleBeanJSONP() {
        OutputBeanBot outputBeanBot = getOutputBot(Conf.DEFAULTINPUT);

        return outputBeanBot;
    }

    /**
     * Return the output with an error if any fail is detected.
     *
     * @param jsonContent The user input.
     * @return
     */
    private OutputBeanBot getOutputBot(String jsonContent) {
        OutputBeanBot res = new OutputBeanBot();

        // 1. Ensures user input is correct.
        try {
            Bot bot = new Bot(jsonContent);
            bot.runBot(); // main method
            res = bot.getStockfishEngineBot().getOutputBean();
            bot.clean();
        } catch (IOException e) {
            System.out.println("getOutput: E: IOException: " + e);
            res.setErrorString(e.toString());
        } catch (InterruptedException e) {
            System.out.println("getOutput: E: InterruptedException: " + e);
            res.setErrorString(e.toString());
        } catch (NumberFormatException e) {
            System.out.println("getOutput: E: NumberFormatException: " + e);
            res.setErrorString(e.toString());
        } catch (StockFishException e) {
            System.out.println("getOutput: E: StockFishException: " + e);
            res.setErrorString(e.toString());
        }

        return res;
    }
}
