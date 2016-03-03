package Controller.bot;

import Controller.main.StockFishException;
import Model.Conf;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.Data;

//@AllArgsConstructor
@Data
public class Bot {

//    private InputBeanBot inputbean;
    private StockfishEngineBot stockfishEngineBot;

    /**
     * Parse JSON input.
     *
     * @param jsonContent
     * @throws java.io.IOException
     * @throws Controller.main.StockFishException
     */
    public Bot(String jsonContent) throws IOException, StockFishException, IOException {
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        stockfishEngineBot = new StockfishEngineBot(mapper.readValue(jsonContent, InputBeanBot.class));
        updateMaxLimit();
    }

    /**
     * Main Post method activated when sending a POST content.
     *
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws Controller.main.StockFishException
     */
    public void runBot() throws IOException, InterruptedException, NumberFormatException, StockFishException {
        long startTime = System.currentTimeMillis();

        stockfishEngineBot.initPositionFen(); // 1-2
        stockfishEngineBot.updateNextBestMooveGoMovetime();// 3
        stockfishEngineBot.updateBoardStateD();// 4
//        se.updateEvaluationScore();// 5
        stockfishEngineBot.stop();// 6

        long endTime = System.currentTimeMillis();
        stockfishEngineBot.getOutputBean().setTimeTotal((endTime - startTime));
    }

    /**
     * Prevent user from using bad values.
     */
    private void updateMaxLimit() throws StockFishException {
        if (!stockfishEngineBot.getInputBean().getMoves().matches(Conf.REGEXMOVES)) {
            throw new StockFishException(
                    "updateMaxLimit: E: The Action must match with "
                    + Conf.REGEXMOVES + ", but found '"
                    + stockfishEngineBot.getInputBean().getMoves() + "'.");
        }
        if (stockfishEngineBot.getInputBean().getHashValue() > Conf.MAXHASHVALUE ||
                stockfishEngineBot.getInputBean().getHashValue() < 0) {
            throw new StockFishException(
                    "updateMaxLimit: E: The hash value allowded must be between '0' and '"
                    + Conf.MAXHASHVALUE + "', but found '"
                    + stockfishEngineBot.getInputBean().getHashValue() + "'.");
        }
        if (stockfishEngineBot.getInputBean().getThreadsValue() > Conf.MAXTHREADSVALUE || 
                stockfishEngineBot.getInputBean().getThreadsValue() < 0) {
            throw new StockFishException(
                    "updateMaxLimit: E: The number of threads allowded must be between '0' and '"
                    + Conf.MAXTHREADSVALUE + "', but found '"
                    + stockfishEngineBot.getInputBean().getThreadsValue() + "'.");
        }
        if (stockfishEngineBot.getInputBean().getMovetime() > Conf.MAXMOVETIME || 
                stockfishEngineBot.getInputBean().getMovetime() < 0) {
            throw new StockFishException(
                    "updateMaxLimit: E: The move time allowded must be between '0' and '"
                    + Conf.MAXMOVETIME + "', but found '"
                    + stockfishEngineBot.getInputBean().getMovetime() + "'.");
        }
        if (!stockfishEngineBot.getInputBean().getForsythEdwardsNotation().matches(Conf.REGEXFORSYTHEDWARDSNOTATION)) {
            throw new StockFishException(
                    "updateMaxLimit: E: The ForsythEdwardsNotation must match with "
                    + Conf.REGEXFORSYTHEDWARDSNOTATION + ", but found '"
                    + stockfishEngineBot.getInputBean().getForsythEdwardsNotation() + "'.");
        }
        if (stockfishEngineBot.getInputBean().getTimeWaitUpdateEvaluationScore() > Conf.MAXTIMEWAITUPDATEEVALUATIONSCORE
                || stockfishEngineBot.getInputBean().getTimeWaitUpdateEvaluationScore() < 0) {
            throw new StockFishException(
                    "updateMaxLimit: E: The move time allowded must be between '0' and '"
                    + Conf.MAXTIMEWAITUPDATEEVALUATIONSCORE + "', but found '"
                    + stockfishEngineBot.getInputBean().getTimeWaitUpdateEvaluationScore() + "'.");
        }
    }

    /**
     * Clean to avoid memory leaks.
     *
     * @throws java.io.IOException
     */
    public void clean() throws IOException {
        stockfishEngineBot.clean();
        stockfishEngineBot = null;
    }
}
