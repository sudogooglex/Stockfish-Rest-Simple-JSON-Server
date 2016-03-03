package Controller.optimized;

import Model.Conf;
import lombok.Data;

public @Data
class InputBeanOptimized {

    /**
     * Asks the engine to move. This string is limited by 50 moves.
     */
    private String moves;
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
     * Forsyth Edwards Notation to use.
     */
    private String forsythEdwardsNotation;
    /**
     * WaitTime in milliseconds after having asked to evaluate score. Seems
     * compulsary. Tests show tis must be less than 3000.
     */
    private int timeWaitUpdateEvaluationScore;

    public InputBeanOptimized() {
        moves = Conf.DEFAULTMOVES;
        hashValue = Conf.DEFAULTHASHVALUE;
        threadsValue = Conf.DEFAULTTHREADSVALUE;
        movetime = Conf.DEFAULTMOVETIME;
        forsythEdwardsNotation = Conf.DEFAULTFORSYTHEDWARDSNOTATION;
        timeWaitUpdateEvaluationScore = Conf.DEFAULTTIMEWAITUPDATEEVALUATIONSCORE;
    }

    /**
     * Clean to avoid memory leaks.
     */
    void clean() {
        moves = null;
        hashValue = 0;
        threadsValue = 0;
        movetime = 0;
        forsythEdwardsNotation = null;
        timeWaitUpdateEvaluationScore = 0;
    }
}
