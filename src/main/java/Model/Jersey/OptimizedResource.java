package Model.Jersey;

import Controller.main.StockFishException;
import Controller.optimized.OutputBeanOptimized;
import Controller.optimized.Optimized;
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
@Path("optimized")
public class OptimizedResource {

    @POST
    @JSONP
    @Consumes("application/x-www-form-urlencoded")
    @Produces({"application/javascript", MediaType.APPLICATION_JSON})
    public OutputBeanOptimized postHandler(String jsonContent) {
        OutputBeanOptimized outputBeanOptimized = getOutputOptimized(jsonContent);

        return outputBeanOptimized;
    }

    @GET
    @JSONP
    @Consumes("application/x-www-form-urlencoded")
    @Produces({"application/javascript", MediaType.APPLICATION_JSON})
    public OutputBeanOptimized getSimpleBeanJSONP() {
        OutputBeanOptimized outputBeanOptimized = getOutputOptimized(Conf.DEFAULTINPUT);

        return outputBeanOptimized;
    }

    /**
     * Return the output with an error if any fail is detected.
     *
     * @param jsonContent The user input.
     * @return
     */
    private OutputBeanOptimized getOutputOptimized(String jsonContent) {
        OutputBeanOptimized res = new OutputBeanOptimized();

        // 1. Ensures user input is correct.
        try {
            Optimized optimized = new Optimized(jsonContent);
            optimized.runOptimized(); // main method
            res = optimized.getStockfishEngineOptimized().getOutputBean();
            optimized.clean();
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
        
//        System.out.println("getOutputOptimized: I: " + res);

        return res;
    }
}
