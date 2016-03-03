package Model.Jersey;

import Controller.post.OutputBeanPost;
import Controller.post.Post;
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
@Path("post")
public class PostResource {

    @POST
    @JSONP
    @Consumes("application/x-www-form-urlencoded")
    @Produces({"application/javascript", MediaType.APPLICATION_JSON})
    public OutputBeanPost postHandler(String jsonContent) {
        OutputBeanPost res = getOutputPost(jsonContent);

        return res;
    }

    @GET
    @JSONP
    @Consumes("application/x-www-form-urlencoded")
    @Produces({"application/javascript", MediaType.APPLICATION_JSON})
    public OutputBeanPost getSimpleBeanJSONP() {
        OutputBeanPost res = getOutputPost(Conf.DEFAULTINPUT);

        return res;
    }

    /**
     * Return the output with an error if any fail is detected.
     *
     * @param m
     * @return
     */
    private OutputBeanPost getOutputPost(String jsonContent) {
        OutputBeanPost res = new OutputBeanPost();

        try {
            Post post = new Post(jsonContent);
            res = post.runPost();
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
