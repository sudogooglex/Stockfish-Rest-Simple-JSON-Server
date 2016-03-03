package Controller.post;

import Model.Conf;
import lombok.Data;

public @Data
class InputBeanPost {

    /**
     * Set the hash size to 1 GB with 'setoption name Hash value 1024'.
     */
    private int hashValue;
    /**
     * This asks Stockfish to use 4 threads when analyzing with 'setoption name
     * Threads value 4'.
     */
    private int threadsValue;
    /**
     * Asks the engine to analyze for 5 seconds with 'go movetime 5000'.
     */
    private int movetime;
    /**
     * Default Forsyth Edwards Notation.
     */
    private String forsythEdwardsNotation;
    /**
     * WaitTime in milliseconds after having asked to evaluate score. Seems
     * compulsary. Tests show tis must be less than 3000.
     */
    private int timeWaitUpdateEvaluationScore;

    public InputBeanPost() {
        hashValue = Conf.DEFAULTHASHVALUE;
        threadsValue = Conf.DEFAULTTHREADSVALUE;
        movetime = Conf.DEFAULTMOVETIME;
        forsythEdwardsNotation = Conf.DEFAULTFORSYTHEDWARDSNOTATION;
        timeWaitUpdateEvaluationScore = Conf.DEFAULTTIMEWAITUPDATEEVALUATIONSCORE;
    }
}
