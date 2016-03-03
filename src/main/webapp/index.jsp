<jsp:include page="./jsp/main/encoding.jsp" />

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="./css/main/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="./css/main/main.css">
        <link rel="stylesheet" type="text/css" href="./css/main/menu/menu.css">
        <link rel="shortcut icon" type="image/png" href="./res/img/favicon.ico"/>
        <title>StockfishRest Home</title>
    </head>

    <jsp:include page="./jsp/main/menu/menu.jsp" />

    <div id='mainTitleDiv'><h1>StockfishRest Home</h1></div>
    <div class="mainContenair">
        <p>Welcome to the web gui of the JSON StockfishRest server. You can here navigate between all tabs.</p>
        <ul>
            <li>If you want to send a simple post request to the Stockfish server, visit the <a href="http://localhost:8080/SR/jsp/post/">WebPost</a> menu.</li>
            <li>If you want to see the Stockfish server fighting against himself, visit the <a href="http://localhost:8080/SR/jsp/bot/">BotFight</a> menu.</li>
            <li>If you want to send the default configuration with the Post method, visit the <a href="http://localhost:8080/SR/wr/post/">PostJSONData</a> menu.</li>
        </ul>
    </div>
        <div class="mainContenair">
            <p>Download the official <a href="./res/doc/engine-interface.txt">UCI protocol documentation (2006)</a>.</p>
    </div>
    <jsp:include page="./jsp/main/licence.jsp" />
</html>
