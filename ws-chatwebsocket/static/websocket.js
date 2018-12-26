var ws;

function connect() {
    var host = document.location.host;
    var pathname = document.location.pathname;

    ws = new WebSocket(document.getElementById("wsURI").value);
    ws.onopen = function (evt) {
        console.log(evt);
        writeMessage("Connect to WSEndpoint.");
    };
    ws.onmessage = function (evt) {
        console.log(evt);
        writeMessage(evt.data);
    };
    ws.onerror = function (evt) {
        console.log(evt);
    };
    ws.onclose = function (evt) {
         writeMessage("Disconnect from WSEndpoint.");
    }
}

function disConnect() {
    ws.close();
}

function writeMessage(pValue) {
    var newElement = document.createElement("div");
    newElement.textContent = pValue;
    var wsMessages = document.getElementById("wsMessages");
    wsMessages.appendChild(newElement);
    wsMessages.scrollTop = wsMessages.scrollHeight;
}

function clearMessages() {
    var wsMessages = document.getElementById("wsMessages");
    while (wsMessages.firstChild) {
        wsMessages.removeChild(wsMessages.firstChild);
    }
}

function send() {
    ws.send(document.getElementById("wsMessage").value);
}
