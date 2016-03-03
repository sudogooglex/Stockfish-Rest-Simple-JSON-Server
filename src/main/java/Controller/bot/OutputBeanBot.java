package Controller.bot;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OutputBeanBot {

    private String configStockfish = "configStockfish Undefined";
    private String forsythEdwardsNotation = "forsythEdwardsNotation Undefined";
    private String stackStockfish = "nextBestMoove Undefined";
    private String nextBestMooveDescription = "nextBestMooveDescription Undefined";
    private String nextBestMoove = "nextBestMoove Undefined";
    private String boardState = "boardState Undefined";    
    private String errorString = "";
    private float evaluationScore = 0;

    private long time1Init = 0;
    private long time2UpdateNextBestMoove = 0;
    private long time3UpdateBoardState = 0;
    private long time4UpdateEvaluationScore = 0;
    private long timeTotal = 0;

    public OutputBeanBot(String fen) {
        forsythEdwardsNotation = fen;
    }

    /**
     * Clean to avoid memory leaks.
     */
    void clean() {
    configStockfish = null;
    forsythEdwardsNotation = null;
    stackStockfish = null;
    nextBestMooveDescription = null;
    nextBestMoove = null;
    boardState = null;    
    errorString = null;
    evaluationScore = 0;
    time1Init = 0;
    time2UpdateNextBestMoove = 0;
    time3UpdateBoardState = 0;
    time4UpdateEvaluationScore = 0;
    timeTotal = 0;
    }
}
