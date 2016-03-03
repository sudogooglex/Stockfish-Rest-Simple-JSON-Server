package Controller.optimized;

import Controller.main.StockFishException;
import Model.Conf;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import lombok.Data;

/**
 * A simple and efficient client to run Stockfish from Java
 */
@Data
public class StockfishEngineOptimized {

    private Process engineProcess;
    private BufferedReader processReader;
    private OutputStreamWriter processWriter;
// Server Input and Output
    private OutputBeanOptimized outputBean;
    private InputBeanOptimized inputBean;

    public StockfishEngineOptimized(InputBeanOptimized ib) {
        inputBean = ib;
        outputBean = new OutputBeanOptimized(ib.getForsythEdwardsNotation());
    }

    /**
     * Initialize Stockfish Engine.
     *
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws Controller.main.StockFishException
     */
    public void initPositionFen() throws IOException, InterruptedException, StockFishException {
        long startTime = System.currentTimeMillis();

// 1. Connect to engine
        String configStart = "Oops! Something went wrong ...\n";
        if (startEngine()) {
            configStart = inputBean.toString();
        }
        System.out.println("----------------------------------------------");
        System.out.println("1. Configuration : " + configStart);

// 2. Update Configuration
        sendCommand("uci"); // Send commands manually
        outputBean.setConfigStockfish(getOutput(0)); // Receive output dump
        sendCommand("setoption name Hash value " + inputBean.getHashValue());
        sendCommand("setoption name Threads value " + inputBean.getThreadsValue());
        String pos = "position fen " + inputBean.getForsythEdwardsNotation() + " moves " + inputBean.getMoves();
        sendCommand(pos);

        // Get time taken
        long endTime = System.currentTimeMillis();
        outputBean.setTime1Init((endTime - startTime));  //divide by 1000000 to get milliseconds.
    }

    /**
     * Update the next best moove.
     *
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws Controller.main.StockFishException
     */
    public void updateNextBestMooveGoMovetime() throws IOException, InterruptedException, StockFishException {
// 3. Get the best move for a position with a given think time
        long startTime = System.currentTimeMillis();

        sendCommand("go movetime " + inputBean.getMovetime());
        updateBestMove();
        System.out.println("2. Best move : " + outputBean.getNextBestMoove() + "\n");

        long endTime = System.currentTimeMillis();
        outputBean.setTime2UpdateNextBestMoove((endTime - startTime));  //divide by 1000000 to get milliseconds.
    }

    /**
     * Draws the current state of the chess board.
     *
     * @return
     */
    void updateBoardStateD() throws IOException, InterruptedException, StockFishException {
        String boardState;
        long startTime = System.currentTimeMillis();

// 4. Send debug Command
        sendCommand("d");

        // Split the debug output
        String[] rows = getOutput(0).split("\n");
        StringBuilder sb = new StringBuilder();
        String pos = " "; // left pos
        int nlignes = 18;

        for (int i = 1; i < nlignes; i++) {
            if (i % 2 == 0) {
                sb.append((nlignes - i) / 2); // Add left pos (1, ..., 8)
            } else {
                sb.append(pos);
            }
            sb.append(rows[i]);
            sb.append("\n");
        }

        // Add bottom pos
        sb.append("    a   b   c   d   e   f   g   h  \n");

        // Write result in the outputBean
        boardState = sb.toString();
        outputBean.setBoardState(boardState);
        System.out.println("3. BoardState : \n" + boardState + "\n");

        // Time statistics
        long endTime = System.currentTimeMillis();
        outputBean.setTime3UpdateBoardState((endTime - startTime));  //divide by 1000000 to get milliseconds.
    }

    /**
     * Update the evaluation score.
     *
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws Controller.main.StockFishException
     */
    public void updateEvaluationScore() throws IOException, InterruptedException, NumberFormatException, StockFishException {
// 5. Get the evaluation score of current position
        long startTime = System.currentTimeMillis();

        updateEvalScore();
        System.out.println("4. Evaluation score : " + outputBean.getEvaluationScore());
        System.out.println("----------------------------------------------");
        long endTime = System.currentTimeMillis();
        outputBean.setTime4UpdateEvaluationScore((endTime - startTime));  //divide by 1000000 to get milliseconds.
    }

    /**
     * Stop the engine.
     *
     * @throws java.io.IOException
     */
    public void stop() throws IOException {
// 6. Stop the engine
        // todo : generate logs
        sendCommand("quit");
        //Conf.saveForsythEdwardsNotation(outputBean.getForsythEdwardsNotation()); // save Forsyth Edwards Notation
    }

// -----------------------------------------------------
// DO NOT CHANGE BELLOW
// -----------------------------------------------------
    /**
     * Starts Stockfish engine as a process and initializes it
     *
     * @return True on success. False otherwise
     */
    private boolean startEngine() {
        try {
            engineProcess = Runtime.getRuntime().exec(Conf.STOCKFISHPATH);
            processReader = new BufferedReader(new InputStreamReader(
                    engineProcess.getInputStream()));
            processWriter = new OutputStreamWriter(
                    engineProcess.getOutputStream());
        } catch (Exception e) {
            System.out.println("startEngine: E: " + e);
            return false;
        }
        return true;
    }

    /**
     * Takes in any valid UCI command and executes it
     *
     * @param command
     */
    private void sendCommand(String command) throws IOException {
        processWriter.write(command + "\n");
        processWriter.flush();
    }

    /**
     * This is generally called right after 'sendCommand' for getting the raw
     * output from Stockfish
     *
     * @param waitTime Time in milliseconds for which the function waits before
     * reading the output. Useful when a long running command is executed
     * @return Raw output from Stockfish
     */
    private String getOutput(int waitTime) throws IOException, InterruptedException, StockFishException {
        StringBuilder buffer = new StringBuilder();
        Thread.sleep(waitTime);
        sendCommand("isready");
        while (true) {
            String text = processReader.readLine();
            if (text == null) {
                throw new StockFishException("getOutput: E: processReader.readLine() == null.");
            }
            if (text.equals("readyok")) {
                break;
            } else {
                buffer.append(text).append("\n");
            }
        }

        return buffer.toString();
    }

    /**
     * This function returns the best move for a given position after
     * calculating for 'timeWaitUpdateNextBestMoove' ms.
     *
     * @return Best Move in PGN format.
     */
    private void updateBestMove() throws IOException, InterruptedException, StockFishException {
        String nextBestMoveDescription, nextBestMove;

// 1. Find Stockfish results
//        sendCommand("position fen " + inputBean.getForsythEdwardsNotation());
//        sendCommand("go movetime " + inputBean.getMovetime());
        int tSplitPos1 = 1, tSplitPos2 = 0;
        String sendCommandResult = getOutput(inputBean.getMovetime() + 20);
        String splitter1 = "bestmove ";
        if (!sendCommandResult.contains(splitter1)) {
            throw new StockFishException("getBestMove: E: Splitter '" + splitter1
                    + "' can't be found in " + sendCommandResult + ".");
        }
        String[] tSplit1 = sendCommandResult.split(splitter1);
        int tSplit1Length = tSplit1.length;
        if (tSplit1Length < tSplitPos1) {
            throw new StockFishException(
                    "getBestMove: E: The string was splitted by '" + splitter1
                    + "' but the result is an array of length " + tSplit1Length
                    + " instead of " + tSplitPos1 + ".");
        }
        nextBestMoveDescription = tSplit1[tSplitPos1];
        nextBestMove = nextBestMoveDescription;

// 2. A Next best moove is found
        String splitter2 = " ";
        if (nextBestMoveDescription.contains(splitter2)) {
            String[] tSplit2 = nextBestMoveDescription.split(splitter2);
            int tSplit2Length = tSplit2.length;
            if (tSplit2Length >= tSplit2Length) {
                nextBestMove = tSplit2[tSplitPos2];
                String tmp = nextBestMoveDescription; // todo : remove
                System.out.println(tmp);
                nextBestMoveDescription = nextBestMove + " (" + tmp.replace("\n", "") + ")";
            }
        }

// 3. Update outputBean
        outputBean.setNextBestMoove(nextBestMove);
        outputBean.setNextBestMooveDescription(nextBestMoveDescription);
    }

    /**
     * Get a list of all legal moves from the given position
     *
     * @return String of moves
     */
    private String getLegalMoves() throws IOException, InterruptedException, StockFishException {
        String res;
//        sendCommand("position fen " + outputBean.getForsythEdwardsNotation());
        sendCommand("d");
        res = getOutput(0);
        // todo : .split("Legal moves: ")[1];
//        return getOutput(0).split("Legal moves: ")[1];
        return res;
    }

    /**
     * Get the evaluation score of a given board position
     *
     * @return evalScore
     */
    private void updateEvalScore() throws IOException, InterruptedException, NumberFormatException, StockFishException {
        sendCommand("go movetime " + inputBean.getTimeWaitUpdateEvaluationScore()); // MUST be set

        float evalScore = -100f;
        String dumpString = getOutput(inputBean.getTimeWaitUpdateEvaluationScore() + 20);
        String[] tDump = dumpString.split("\n");
        for (int i = tDump.length - 1; i >= 0; i--) {
            if (tDump[i].startsWith("info depth ")) {
                String[] t = tDump[i].split("score cp ");
                if (t.length <= 1) {
                    throw new StockFishException("getEvalScore: E: Can't evaluate score line " + i + ". Details :\n" + t[0]);
                } else {
                    if (t[1].split(" upperbound nodes").length < 0) {
                        throw new StockFishException("getEvalScore: E: Can't evaluate score. Found " + t[0]);
                    } else {
//                        try {
                        evalScore = Float.parseFloat(t[1]
                                .split(" nodes")[0]);
//                        } catch (Exception e) {
//                            evalScore = Float.parseFloat(t[1]
//                                    .split(" upperbound nodes")[0]);
//                        }
                    }
                }
            }
        }

        outputBean.setEvaluationScore(evalScore / 100);
        outputBean.setStackStockfish(dumpString);
    }

    /**
     * Clean to avoid memory leaks.
     */
    void clean() throws IOException {
        engineProcess.destroy();
        processReader.close();
        processWriter.close();
        inputBean.clean();

        engineProcess = null;
        processReader = null;
        processWriter = null;
        outputBean = null;
        inputBean = null;
    }
}
