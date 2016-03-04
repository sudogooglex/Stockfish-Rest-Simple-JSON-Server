package Model;

public class Conf {

// ---------------------------- ALWAYS CHANGE THIS --------------------------------
    public static final String MAINDIRECTORY = "/home/a/Dropbox/Perso/1.Workspace/2.Web/1.Paris/Stockfish-Rest-Simple-JSON-Server/", // W: Change this if needed !!!
//            STOCKFISH6PATH = MAINDIRECTORY + "stockfish-6-linux/Linux/stockfish_6_x64_modern",
            STOCKFISH7PATH = MAINDIRECTORY + "stockfish-7-linux/Linux/stockfish_7_x64",
            STOCKFISHPATH = STOCKFISH7PATH;
// ---------------------------- GENERAL APP ---------------------------------------
    public static final String VERSION = "0.1",
            PATHIMG = "./img/",
            PATHTOFILEONDISK = PATHIMG + "icon.png",
            TITLE = "StockfishRest - Enter Forsyth Edwards Notation in JSON and "
            + "get next best move - v" + VERSION,
            FORSYTHEDWARDSNOTATIONFILE = MAINDIRECTORY + "res/ForsythEdwardsNotation.txt";

// ---------------------------- STOCKFISH ---------------------------------------
    public static final String REGEXFORSYTHEDWARDSNOTATION = "(([1-8BKNPQRbknpqr]{1,8}\\/){7}([1-8BKNPQRbknpqr]{8})(\\s[bw]\\s)([KQkq]{1,4}|[-])([\\s])([1-8abcdefgh]{2}|[-])(\\s[\\d]+\\s[\\d]+))",
            REGEXMOVES = "((([1-8abcdefgh]{4}[BNQRbnqr]{0,1}\\s)+([1-8abcdefgh]{4}[BNQRbnqr]{0,1}))|([1-8abcdefgh]{4}[BNQRbnqr]{0,1})|(^$))",
            DEFAULTFORSYTHEDWARDSNOTATION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            DEFAULTNEXTFEN = DEFAULTFORSYTHEDWARDSNOTATION,
            DEFAULTMOVES = "g1f3 g8f6";
    public static final String ACTIONSTART = "start",
            ACTIONREFRESH = "refresh",
            ACTIONNEXT = "next",
            ACTIONQUIT = "quit";
    public static final int DEFAULTTHREADSVALUE = 1,
            DEFAULTHASHVALUE = 1024 / 4,
            DEFAULTMOVETIME = 100,
            DEFAULTTIMEWAITUPDATEEVALUATIONSCORE = 100,
            MAXTHREADSVALUE = 8,
            MAXHASHVALUE = 1024 * 3, // 1024 => 1Go Hash
            MAXMOVETIME = 1000 * 60, // 1000 * 60 => 1 min
            MAXTIMEWAITUPDATEEVALUATIONSCORE = 3000;
    public static final String DEFAULTINPUT = "{"
            + "\"hashValue\" : " + DEFAULTHASHVALUE
            + ",\"threadsValue\" : " + DEFAULTTHREADSVALUE
            + ",\"movetime\" : " + DEFAULTMOVETIME
            + ",\"forsythEdwardsNotation\" : \"" + DEFAULTFORSYTHEDWARDSNOTATION + "\""
            + ",\"timeWaitUpdateEvaluationScore\" : " + DEFAULTTIMEWAITUPDATEEVALUATIONSCORE
            + "}";

// ---------------------------- WRITE LOGS ---------------------------------------
//    /**
//     * Get the ForsythEdwardsNotation from the file in the res directory.
//     *
//     * @return
//     */
//    public static String readForsythEdwardsNotation() {
//        String res = "";
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new FileReader(FORSYTHEDWARDSNOTATIONFILE));
//            String fileString;
//            byte[] encoded = Files.readAllBytes(Paths.get(FORSYTHEDWARDSNOTATIONFILE));
//            fileString = new String(encoded, StandardCharsets.UTF_8);
//            List allMatches = new ArrayList();
//            Matcher m = Pattern.compile("(\\'[^\\']*\\')")
//                    .matcher(fileString);
//            while (m.find()) {
//                allMatches.add(m.group());
//            }
//            if (allMatches.isEmpty()) {
//                System.out.println("readForsythEdwardsNotation: E: Can't find "
//                        + "any ForsythEdwardsNotation in the file " + FORSYTHEDWARDSNOTATIONFILE + " ...\n"
//                        + "Write '4k2q/6r1/1q6/8/8/8/8/4K3 w - - 0 1' for instance.");
//                br.close();
//                System.exit(0);
//            } else {
//                for (Object o : allMatches) {
//                    String s = (String) o;
//                    if (!s.equals("''")) {
//                        res = s.replace("'", "");
//                        break;
//                    }
//                }
//                if (res.equals("''") || res.equals("")) {
//                    System.out.println("readForsythEdwardsNotation: E: The file " + FORSYTHEDWARDSNOTATIONFILE
//                            + " only contains an empty '' ForsythEdwardsNotation. "
//                            + "Write '4k2q/6r1/1q6/8/8/8/8/4K3 w - - 0 1' for instance.");
//                    br.close();
//                    System.exit(0);
//                }
//            }
//            br.close();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Conf.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Conf.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (br == null) {
//                System.out.println("readForsythEdwardsNotation: E: null BufferedReader.");
//            } else {
//                try {
//                    br.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(Conf.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//
//        return res;
//    }
//
//    /**
//     * Save the ForsythEdwardsNotation at the beginning of the from the
//     * ForsythEdwardsNotation file.
//     *
//     * @param forsythEdwardsNotation The ForsythEdwardsNotation String to save
//     * to the file.
//     */
//    public static void saveForsythEdwardsNotation(String forsythEdwardsNotation) {
//        try {
//            // 1. Read and add
//            BufferedReader br = new BufferedReader(new FileReader(FORSYTHEDWARDSNOTATIONFILE));
//            byte[] encoded = Files.readAllBytes(Paths.get(FORSYTHEDWARDSNOTATIONFILE));
//            String originalString = new String(encoded, StandardCharsets.UTF_8);
//            br.close();
//            String finalString = "'" + forsythEdwardsNotation + "'\n" + originalString;
//
//            // 2. Write
//            PrintWriter out = new PrintWriter(FORSYTHEDWARDSNOTATIONFILE);
//            out.println(finalString);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Conf.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Conf.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
