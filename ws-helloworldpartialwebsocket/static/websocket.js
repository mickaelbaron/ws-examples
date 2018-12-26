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

function sendTextDataSize() {
    var sizeValue = document.getElementById("wsTextDataSize").value;

    var payload = "";
    for (var i=0; i < sizeValue; i++) {
        payload += "x";
    }
    ws.send(payload);

    writeMessage("SENT (text) " + payload.length + " text bytes");
}

function sendBinaryDataSize() {
    var sizeValue = document.getElementById("wsBinaryDataSize").value;

    var buffer = new ArrayBuffer(sizeValue);
    var bytes = new Uint8Array(buffer);
    for (var i=0; i<bytes.length; i++) {
        bytes[i] = i;
    }
    ws.send(buffer);

    writeMessage("SENT (binary): " + buffer.byteLength + " bytes");
}
