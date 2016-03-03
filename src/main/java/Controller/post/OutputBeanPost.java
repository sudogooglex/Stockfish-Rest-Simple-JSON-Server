package Controller.post;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OutputBeanPost {

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

    public OutputBeanPost(String fen) {
        forsythEdwardsNotation = fen;
    }
}
