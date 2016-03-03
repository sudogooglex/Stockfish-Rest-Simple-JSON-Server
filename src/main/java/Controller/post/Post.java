package Controller.post;

import Controller.main.StockFishException;
import Model.Conf;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.Data;

//@AllArgsConstructor
@Data
public class Post {

    private InputBeanPost inputbean;

    /**
     * Parse JSON input.
     *
     * @param jsonContent
     * @throws java.io.IOException
     * @throws Controller.main.StockFishException
     */
    public Post(String jsonContent) throws IOException, StockFishException, IOException {
        inputbean = new InputBeanPost();
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        inputbean = mapper.readValue(jsonContent, InputBeanPost.class);
        updateMaxLimit();
    }

    /**
     * Post method activated when sending a POST content.
     *
     * @return
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws Controller.main.StockFishException
     */
    public OutputBeanPost runPost() throws IOException, InterruptedException, NumberFormatException, StockFishException {
        long startTime = System.currentTimeMillis();

        OutputBeanPost res;

        StockfishEnginePost se = new StockfishEnginePost(inputbean);
        se.init(); // 1-3
        se.updateNextBestMoove();// 4
        se.updateBoardState();// 5-6
        se.updateEvaluationScore();// 7
        se.stop();// 8

        long endTime = System.currentTimeMillis();
        se.getOutputBean().setTimeTotal((endTime - startTime));  //divide by 1000000 to get milliseconds.

        res = se.getOutputBean();

        return res;
    }

    /**
     * Prevent user from using bad values.
     */
    private void updateMaxLimit() throws StockFishException {
        if (inputbean.getHashValue() > Conf.MAXHASHVALUE || inputbean.getHashValue() < 0) {
            throw new StockFishException(
                    "updateMaxLimit: E: The hash value allowded must be between '0' and '"
                    + Conf.MAXHASHVALUE + "', but found '"
                    + inputbean.getHashValue() + "'.");
        }
        if (inputbean.getThreadsValue() > Conf.MAXTHREADSVALUE || inputbean.getThreadsValue() < 0) {
            throw new StockFishException(
                    "updateMaxLimit: E: The number of threads allowded must be between '0' and '"
                    + Conf.MAXTHREADSVALUE + "', but found '"
                    + inputbean.getThreadsValue() + "'.");
        }
        if (inputbean.getMovetime() > Conf.MAXMOVETIME || inputbean.getMovetime() < 0) {
            throw new StockFishException(
                    "updateMaxLimit: E: The move time allowded must be between '0' and '"
                    + Conf.MAXMOVETIME + "', but found '"
                    + inputbean.getMovetime() + "'.");
        }
        if (!inputbean.getForsythEdwardsNotation().matches(Conf.REGEXFORSYTHEDWARDSNOTATION)) {
            throw new StockFishException(
                    "updateMaxLimit: E: The ForsythEdwardsNotation must match with "
                    + Conf.REGEXFORSYTHEDWARDSNOTATION + ", but found '"
                    + inputbean.getForsythEdwardsNotation() + "'.");
        }
        if (inputbean.getTimeWaitUpdateEvaluationScore() > Conf.MAXTIMEWAITUPDATEEVALUATIONSCORE
                || inputbean.getTimeWaitUpdateEvaluationScore() < 0) {
            throw new StockFishException(
                    "updateMaxLimit: E: The move time allowded must be between '0' and '"
                    + Conf.MAXTIMEWAITUPDATEEVALUATIONSCORE + "', but found '"
                    + inputbean.getTimeWaitUpdateEvaluationScore() + "'.");
        }
    }
}
