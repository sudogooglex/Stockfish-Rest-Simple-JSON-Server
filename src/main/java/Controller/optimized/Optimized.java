package Controller.optimized;

import Controller.main.StockFishException;
import Model.Conf;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.Data;

//@AllArgsConstructor
@Data
public class Optimized {

    private StockfishEngineOptimized stockfishEngineOptimized;

    /**
     * Parse JSON input.
     *
     * @param jsonContent
     * @throws java.io.IOException
     * @throws Controller.main.StockFishException
     */
    public Optimized(String jsonContent) throws IOException, StockFishException, IOException {
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        stockfishEngineOptimized = new StockfishEngineOptimized(mapper.readValue(jsonContent, InputBeanOptimized.class));
        updateMaxLimit();
    }

    /**
     * Main Post method activated when sending a POST content.
     *
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws Controller.main.StockFishException
     */
    public void runOptimized() throws IOException, InterruptedException, NumberFormatException, StockFishException {
//        long startTime = System.currentTimeMillis();

        stockfishEngineOptimized.initPositionFen(); // 1-2
        stockfishEngineOptimized.updateNextBestMooveGoMovetime();// 3
        stockfishEngineOptimized.updateBoardStateD();// 4
//        se.updateEvaluationScore();// 5
        stockfishEngineOptimized.stop();// 6
//        long endTime = System.currentTimeMillis();
//        stockfishEngineOptimized.getOutputBean().setTimeTotal((endTime - startTime));
    }

    /**
     * Prevent user from using bad values.
     */
    private void updateMaxLimit() throws StockFishException {
        if (!stockfishEngineOptimized.getInputBean().getMoves().matches(Conf.REGEXMOVES)) {
            throw new StockFishException(
                    "updateMaxLimit: E: The Action must match with "
                    + Conf.REGEXMOVES + ", but found '"
                    + stockfishEngineOptimized.getInputBean().getMoves() + "'.");
        }
        if (stockfishEngineOptimized.getInputBean().getHashValue() > Conf.MAXHASHVALUE
                || stockfishEngineOptimized.getInputBean().getHashValue() < 0) {
            throw new StockFishException(
                    "updateMaxLimit: E: The hash value allowded must be between '0' and '"
                    + Conf.MAXHASHVALUE + "', but found '"
                    + stockfishEngineOptimized.getInputBean().getHashValue() + "'.");
        }
        if (stockfishEngineOptimized.getInputBean().getThreadsValue() > Conf.MAXTHREADSVALUE
                || stockfishEngineOptimized.getInputBean().getThreadsValue() < 0) {
            throw new StockFishException(
                    "updateMaxLimit: E: The number of threads allowded must be between '0' and '"
                    + Conf.MAXTHREADSVALUE + "', but found '"
                    + stockfishEngineOptimized.getInputBean().getThreadsValue() + "'.");
        }
        if (stockfishEngineOptimized.getInputBean().getMovetime() > Conf.MAXMOVETIME
                || stockfishEngineOptimized.getInputBean().getMovetime() < 0) {
            throw new StockFishException(
                    "updateMaxLimit: E: The move time allowded must be between '0' and '"
                    + Conf.MAXMOVETIME + "', but found '"
                    + stockfishEngineOptimized.getInputBean().getMovetime() + "'.");
        }
        if (!stockfishEngineOptimized.getInputBean().getForsythEdwardsNotation().matches(Conf.REGEXFORSYTHEDWARDSNOTATION)) {
            throw new StockFishException(
                    "updateMaxLimit: E: The ForsythEdwardsNotation must match with "
                    + Conf.REGEXFORSYTHEDWARDSNOTATION + ", but found '"
                    + stockfishEngineOptimized.getInputBean().getForsythEdwardsNotation() + "'.");
        }
        if (stockfishEngineOptimized.getInputBean().getTimeWaitUpdateEvaluationScore() > Conf.MAXTIMEWAITUPDATEEVALUATIONSCORE
                || stockfishEngineOptimized.getInputBean().getTimeWaitUpdateEvaluationScore() < 0) {
            throw new StockFishException(
                    "updateMaxLimit: E: The move time allowded must be between '0' and '"
                    + Conf.MAXTIMEWAITUPDATEEVALUATIONSCORE + "', but found '"
                    + stockfishEngineOptimized.getInputBean().getTimeWaitUpdateEvaluationScore() + "'.");
        }
    }

    /**
     * Clean to avoid memory leaks.
     *
     * @throws java.io.IOException
     */
    public void clean() throws IOException {
        stockfishEngineOptimized.clean();
        stockfishEngineOptimized = null;
    }
}
