/* global USERDATAMINLENGTHBOT, REGEXMOVES, USERSTARTLENGTHBOT, USERENDLENGTH, URL_JSON_OPTIMIZED */

// -----------------------------------------------
// ----------------- 1. BOT ----------------------
// -----------------------------------------------
(function () {
    var mozAnonDiv = document.getElementById("mozAnonDiv");
    var mozSystemDiv = document.getElementById("mozSystemDiv");
    var readyStateDiv = document.getElementById("readyStateDiv");
    var responseTypeDiv = document.getElementById("responseTypeDiv");
    var responseURLDiv = document.getElementById("responseURLDiv");
    var responseXMLDiv = document.getElementById("responseXMLDiv");
    var responseDiv = document.getElementById("responseDiv");
    var statusDiv = document.getElementById("statusDiv");
    var statusTextDiv = document.getElementById("statusTextDiv");
    var timeoutDiv = document.getElementById("timeoutDiv");
    var withCredentialsDiv = document.getElementById("withCredentialsDiv");
    var boardStateDiv = document.getElementById("boardStateDiv");
    var nextBestMooveDiv = document.getElementById("nextBestMooveDiv");
    var nextBestMooveDescriptionDiv = document.getElementById("nextBestMooveDescriptionDiv");
    var evaluationScoreDiv = document.getElementById("evaluationScoreDiv");
    var errorStringDiv = document.getElementById("errorStringDiv");
    var errorStringRowDiv = document.getElementById("errorStringRowDiv");
    var userInputTextAreaDiv = document.getElementById("userInputTextAreaDiv");
    var userInputTextAreaInit = userInputTextAreaDiv.value; // backup old value for reset
    var curlResponseTableDiv = document.getElementsByClassName("curlResponseTable")[0];

    curlResponseTableDiv.style.display = 'none'; // hide on start

    var isContinueBotInfinite = false;

    /**
     * Get the JSON String to send with the user input.
     * @returns {res} dataToSend
     */
    function getDataToSend() {
        var res = "{}";

        var userData = userInputTextAreaDiv.value;
        if (userData) {
            var n = userData.length;
            if (n >= USERDATAMINLENGTHBOT) {
                var userJsonToParse = userData.substring(USERSTARTLENGTHBOT, n - USERENDLENGTH);
                var userJson = userJsonToParse.replace(/\\/g, "");
                res = userJson;
            } else {
                console.log("getDataToSend: E: userData is too short. Expected length >= " +
                        USERDATAMINLENGTHBOT);
            }
        } else {
            console.log("getDataToSend: E: userData is undefined.");
        }

        return res;
    }

    /**
     * Reset the text in the textArea.
     * @returns {}
     */
    function resetTextArea() {
        userInputTextAreaDiv.value = userInputTextAreaInit;
        chronoReset();
        curlResponseTableDiv.style.display = 'none'; // hide curlResponseTableDiv
    }

    /**
     * Add best moves to moves and to post.
     */
    function addMove() {
// 1. Check if nextBestMove is empty
        var nextBestMove = nextBestMooveDiv.innerHTML;
        if (nextBestMove === "") {
            console.log("addMove: W: nextBestMove is empty. Please run a Post before.");
        } else {

// 2. Run Regex
            var userString = userInputTextAreaDiv.value;
            var matcher = REGEXMOVES.exec(userString);

            if (matcher === null) {
                console.log("addMove: E: Can't add next best move to the moves because regex '"
                        + REGEXMOVES + "' has no result. Got null tMatch.");
            } else {
                var matchPos = matcher.index;
                if (matcher.length < 1) {
                    console.log("addMove: E: Can't add next best move to the moves because regex '"
                            + REGEXMOVES + "' has no result. Got too low tMatch length.");
                } else {
                    var match = matcher[1];
                    var beginning = userString.substring(0, matchPos);

// 3. If there is only one move, do not add any space at the end of the next best move.
                    var modified = match.substring(0, match.length - 2);
                    if (match.length > 17) { // 17 is the length of the string \"moves\" : \"\".
                        modified += " " + nextBestMove + "\\\",";
                        var a = matchPos + modified.length - 5, b = userString.length;

                    } else {
                        modified += nextBestMove + "\\\","; // skip 2 characters '\' and '"'.
                        var a = matchPos + modified.length - 4, b = userString.length;
                    }

// 4. Check length of modified string
                    if (a > b) {
                        console.log("addMove: E: The modified string with the next best pos is too long. Expected "
                                + a + " <= " + b + "'.");
                    } else {
                        var end = userString.substring(a, b);

// 5. Replace userInputTextAreaDiv
                        var replacedString = beginning + modified + end;
                        userInputTextAreaDiv.value = replacedString;
//                        console.log(beginning+"\n"+modified+"\n"+end)
                    }
                }
            }
        }
    }

    /**
     * Use this to end the loops of post();
     * @returns {0} n returns 0 to stop the loop.
     */
    function end() {
        isContinueBotInfinite = false;
        chronoStop();
        return 0;
    }

    /**
     * Print the response of the curl script.
     * Bot : Add Move and Post n time.
     * @param n Number of times to add best moves to moves and to post.
     * @param isBotMode Is in bot mode.
     * @returns {undefined}
     */
    function post(n, isBotMode) {
        console.log('>>>>>>>>>>>>>>>>>>');
        // 1. Reset fields and reset/start chronometer
        if (!isContinueBotInfinite) {
            chronoReset();
            chronoStart();
        }
        
        // 2. Prepare request
        var xhr = new XMLHttpRequest();
        xhr.open("POST", URL_JSON_OPTIMIZED, true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        // 3. Event : subscribe to this event before you send your request.
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var mozAnon = xhr.mozAnon;
                var mozSystem = xhr.mozSystem;
                var readyState = xhr.readyState;
                var responseType = xhr.responseType;
                var responseURL = xhr.responseURL;
                var responseXML = xhr.responseXML;
                var response = xhr.response;
                var status = xhr.status;
                var statusText = xhr.statusText;
                var timeout = xhr.timeout;
                var withCredentials = xhr.withCredentials;

                if (mozAnon) {
                    mozAnonDiv.innerHTML = mozAnon;
                }
                if (mozSystem) {
                    mozSystemDiv.innerHTML = mozSystem;
                }
                if (readyState) {
                    readyStateDiv.innerHTML = readyState;
                }
                if (responseType) {
                    responseTypeDiv.innerHTML = responseType;
                }
                if (responseURL) {
                    responseURLDiv.innerHTML = responseURL;
                }
                if (responseXML) {
                    responseXMLDiv.innerHTML = responseXML;
                }
                if (status) {
                    statusDiv.innerHTML = status;
                }
                if (statusText) {
                    statusTextDiv.innerHTML = statusText;
                }
                if (timeout) {
                    timeoutDiv.innerHTML = timeoutDiv;
                }
                if (withCredentials) {
                    withCredentialsDiv.innerHTML = withCredentials;
                }
                if (response) { // pretty print
                    var parsed = JSON.parse(response);
                    var final = JSON.stringify(parsed, undefined, 2);
                    responseDiv.innerHTML = final;
                    boardStateDiv.innerHTML = parsed.boardState;
                    nextBestMooveDiv.innerHTML = parsed.nextBestMoove;
                    nextBestMooveDescriptionDiv.innerHTML = parsed.nextBestMooveDescription;
                    evaluationScoreDiv.innerHTML = parsed.evaluationScore;

                    // The end
                    if (parsed.errorString === "") {
                        errorStringRowDiv.style.display = 'none';
                        if (nextBestMooveDiv.innerHTML === "(none)" ||
                                nextBestMooveDiv.innerHTML === "nextBestMoove Undefined") {
                            n = end();
                        }
                    } else {
                        errorStringDiv.innerHTML = parsed.errorString;
                        errorStringRowDiv.style.display = '';
                        n = end();
                    }
                }

                // Display Response
                curlResponseTableDiv.style.display = '';

                // Start chronometer
                if (!isContinueBotInfinite) {
                    chronoStop();
                }

                // Recursivity to call n times the post method
                if (isBotMode) {
                    addMove();
                }
                if (n >= 2 || isContinueBotInfinite) {
                    post(n - 1, isBotMode);
                }
            } else { // bad xhr status
            }
        };

        // 4. Send request
        var dataToSend = getDataToSend();
        console.log(dataToSend);
        xhr.send(dataToSend);
    }


    /**
     * Simple post
     */
    function post1() {
        post(1, false);
    }

    /**
     * Bot 1 : Add Move and Post 1 time.
     */
    function addBot1() {
        post(1, true);
    }

    /**
     * Bot 3 : Add Move and Post 3 time.
     */
    function addBot3() {
        post(3, true);
    }

    /**
     * Bot I : Add Move and Post 3 time.
     */
    function addBotI() {
        var addBotIBtn = document.querySelector('.onoffswitch-checkbox');
        if (addBotIBtn.checked === true) {
            chronoContinue();
            isContinueBotInfinite = true;
            post(1, true);
        } else {
            chronoStop();
            isContinueBotInfinite = false;
        }
    }



// -----------------------------------------------
// --------- 2. ADD EVENT LISTENERS --------------
// -----------------------------------------------
    /**
     * Buttons
     * @returns {}
     */
    (function () {
        var postBtn = document.querySelector('.js-postBtn');
        postBtn.addEventListener('click', post1);
        var resetTextAreaBtn = document.querySelector('.js-resetTextAreaBtn');
        resetTextAreaBtn.addEventListener('click', resetTextArea);
        var addMoveBtn = document.querySelector('.js-addMoveBtn');
        addMoveBtn.addEventListener('click', addMove);
        var addBot1Btn = document.querySelector('.js-addBot1Btn');
        addBot1Btn.addEventListener('click', addBot1);
        var addBot3Btn = document.querySelector('.js-addBot3Btn');
        addBot3Btn.addEventListener('click', addBot3);
        var addBotIBtn = document.querySelector('.onoffswitch-checkbox');
        addBotIBtn.addEventListener('click', addBotI);
    })();

    /**
     * Enable : copy past
     * @returns {}
     */
    (function () {
        var copyTextareaBtn = document.querySelector('.js-textareacopybtn');
        copyTextareaBtn.addEventListener('click', function (event) {
            var copyTextarea = document.querySelector('.js-copytextarea');
            copyTextarea.select();
            try {
                var successful = document.execCommand('copy');
                var msg = successful ? 'successful' : 'unsuccessful';
                console.log('Copying text command was ' + msg);
            } catch (err) {
                console.log('Oops, unable to copy');
            }
        });
    })();
})();
