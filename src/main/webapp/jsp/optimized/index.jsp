<jsp:include page="../main/encoding.jsp" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../main/css.jsp" />
        <link rel="stylesheet" type="text/css" href="../../css/main/switch.css">
        <link rel="stylesheet" type="text/css" href="../../css/optimized/optimized.css">
        <title>StockfishRest Optimized</title>
    </head>
    <body>
        <jsp:include page="../main/menu/menu.jsp" />

        <div id='mainTitleDiv'><h1>StockfishRest Optimized</h1></div>
        <div id="curlCodeDiv" class="curlCode">
            <textarea id="userInputTextAreaDiv" class="js-copytextarea">
clear;curl http://localhost:8080/SR/wr/bot --data "{
        \"moves\" : \"g1f3 g8f6\",
        \"hashValue\" : 256,
        \"threadsValue\" : 1,
        \"movetime\" : 100,
        \"forsythEdwardsNotation\" : \"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1\",
        \"timeWaitUpdateEvaluationScore\" : 100
}" | prettyjson</textarea>
        </div>
        <div class="curlForm">
            <button class="js-postBtn">Post</button>
            <button class="js-addMoveBtn">Add Move</button>
            <button class="js-addBot1Btn">Bot 1 (Post=>Move)</button>
            <button class="js-addBot3Btn">Bot 3</button>
            <button class="js-prevBtn">Prev.</button>
            <button class="js-nextBtn">Next.</button>
            <button class="js-textareacopybtn">Copy Curl</button>
            <button class="js-resetTextAreaBtn">Reset</button>
            <div class="onoffswitch">
                <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch">
                <label class="onoffswitch-label" for="myonoffswitch"></label>
            </div>
        </div>

        <div class="chronometerDiv"><span id="chronotime">00:00:000</span></div>

        <div class="curlResponseTable">
            <table>
                <colgroup>
                    <col span="1" style="width: 15%;">
                    <col span="1" style="width: 70%;">
                </colgroup>
                <tr id="errorStringRowDiv">
                    <td>errorString</td>
                    <td><pre id="errorStringDiv"></pre></td>
                </tr>
                <tr>
                    <td>gameBoard</td>
                    <td><pre id="boardStateDiv"></pre></td>
                </tr>
                <tr>
                    <td>nextBestMooveDescription</td>
                    <td><pre id="nextBestMooveDescriptionDiv"></pre></td>
                </tr>
                <tr>
                    <td>nextBestMoove</td>
                    <td><pre id="nextBestMooveDiv"></pre></td>
                </tr>
                <tr>
                    <td>evaluationScoreDiv</td>
                    <td><pre id="evaluationScoreDiv"></pre></td>
                </tr>
                <tr>
                    <td>response</td>
                    <td><pre id="responseDiv"></pre></td>
                </tr>
                <tr>
                    <td>mozAnon</td>
                    <td><pre id="mozAnonDiv"></pre></td>
                </tr>
                <tr>
                    <td>mozSystem</td>
                    <td><pre id="mozSystemDiv"></pre></td>
                </tr>
                <tr>
                    <td>readyState</td>
                    <td><pre id="readyStateDiv"></pre></td>
                </tr>
                <tr>
                    <td>responseType</td>
                    <td><pre id="responseTypeDiv"></pre></td>
                </tr>
                <tr>
                    <td>responseURL</td>
                    <td><pre id="responseURLDiv"></pre></td>
                </tr>
                <tr>
                    <td>responseXML</td>
                    <td><pre id="responseXMLDiv"></pre></td>
                </tr>
                <tr>
                    <td>status</td>
                    <td><pre id="statusDiv"></pre></td>
                </tr>
                <tr>
                    <td>statusText</td>
                    <td><pre id="statusTextDiv"></pre></td>
                </tr>
                <tr>
                    <td>timeout</td>
                    <td><pre id="timeoutDiv"></pre></td>
                </tr>
                <tr>
                    <td>withCredentials</td>
                    <td><pre id="withCredentialsDiv"></pre></td>
                </tr>
            </table>
        </div>
        <jsp:include page="../main/licence.jsp" />

        <!--javascript-->
        <script type="text/javascript" src="../../js/main/conf.js" ></script>
        <script type="text/javascript" src="../../js/main/chronometer.js" ></script>
        <script type="text/javascript" src="../../js/optimized/optimized.js" ></script>
    </body>
</html>
