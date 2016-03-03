// -----------------------------------------------
// ---------------- 1. POST ----------------------
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
    var evaluationScoreDiv = document.getElementById("evaluationScoreDiv");
    var errorStringDiv = document.getElementById("errorStringDiv");
    var errorStringRowDiv = document.getElementById("errorStringRowDiv");
    var userInputTextAreaDiv = document.getElementById("userInputTextAreaDiv");
    var userInputTextAreaInit = userInputTextAreaDiv.value; // backup old value for reset
    var curlResponseTableDiv = document.getElementsByClassName("curlResponseTable")[0];

    curlResponseTableDiv.style.display = 'none'; // hide on start

    /**
     * Get the JSON String to send with the user input.
     * @returns {res} dataToSend
     */
    function getDataToSend() {
        var res = "{}";

        var userData = userInputTextAreaDiv.value;
        if (userData) {
            var n = userData.length;
            if (n >= USERDATAMINLENGTHPOST) {
                var userJsonToParse = userData.substring(USERSTARTLENGTHPOST, n - USERENDLENGTH);
                var userJson = userJsonToParse.replace(/\\/g, "");
                res = userJson;
            } else {
                console.log("getDataToSend: E: userData is too short. Expected length >= " +
                        USERDATAMINLENGTHPOST);
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
     * Print the response of the curl script
     * @returns {undefined}
     */
    function post() {
        // 1. Reset fields and reset/start chronometer
        chronoReset();
        chronoStart();
        //curlResponseText.innerHTML = "";
        mozAnonDiv.innerHTML = "";
        mozSystemDiv.innerHTML = "";
        readyStateDiv.innerHTML = "";
        responseTypeDiv.innerHTML = "";
        responseURLDiv.innerHTML = "";
        responseXMLDiv.innerHTML = "";
        responseDiv.innerHTML = "";
        statusDiv.innerHTML = "";
        statusTextDiv.innerHTML = "";
        timeoutDiv.innerHTML = "";
        withCredentialsDiv.innerHTML = "";
        boardStateDiv.innerHTML = "";
        nextBestMooveDiv.innerHTML = "";
        evaluationScoreDiv.innerHTML = "";
        errorStringDiv.innerHTML = "";
        curlResponseTableDiv.style.display = 'none'; // hide curlResponseTableDiv

        // 2. Prepare request
        var xhr = new XMLHttpRequest();
        xhr.open("POST", URLJSONPOST, true);
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
                    evaluationScoreDiv.innerHTML = parsed.evaluationScore;
                    if (parsed.errorString === "") {
                        errorStringRowDiv.style.display = 'none';
                    } else {
                        errorStringDiv.innerHTML = parsed.errorString;
                        errorStringRowDiv.style.display = '';
                    }
                }

                // Display Response
                curlResponseTableDiv.style.display = '';

                // Start chronometer
                chronoStop();
            } else {}
        };

        // 4. Send request
        var dataToSend = getDataToSend();
        xhr.send(dataToSend);
    }

    /**
     * Add event listeners
     * @returns {}
     */
    (function () {
        var postBtn = document.querySelector('.js-postBtn');
        postBtn.addEventListener('click', post);
        var resetTextAreaBtn = document.querySelector('.js-resetTextAreaBtn');
        resetTextAreaBtn.addEventListener('click', resetTextArea);
    })();
})();








// -----------------------------------------------
// -------------- 2. COPY TEXT -------------------
// -----------------------------------------------
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
