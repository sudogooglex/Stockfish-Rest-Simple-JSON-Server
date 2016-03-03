//(function () {
    var start = 0;
    var end = 0;
    var diff = 0;
    var timerID = 0;
    var chronotime = document.getElementById("chronotime");

    function chrono() {
        end = new Date();
        diff = end - start;
        diff = new Date(diff);
        var msec = diff.getMilliseconds();
        var sec = diff.getSeconds();
        var min = diff.getMinutes();
        var hr = diff.getHours() - 1;
        if (min < 10) {
            min = "0" + min;
        }
        if (sec < 10) {
            sec = "0" + sec;
        }
        if (msec < 10) {
            msec = "00" + msec;
        }
        else if (msec < 100) {
            msec = "0" + msec;
        }
        chronotime.innerHTML = min + ":" + sec + ":" + msec;
        timerID = setTimeout(chrono, 10);
    }
    function chronoStart() {
        start = new Date();
        chrono();
    }
    function chronoContinue() {
        start = new Date() - diff;
        start = new Date(start);
        chrono();
    }
    function chronoReset() {
        chronotime.innerHTML = "00:00:000";
        start = new Date();
    }
    function chronoStopReset() {
        chronotime.innerHTML = "00:00:000";
    }
    function chronoStop() {
        clearTimeout(timerID);
    }
//})();
