var ws;

function connect() {
    var host = document.location.host;
    var pathname = document.location.pathname;

    ws = new WebSocket(document.getElementById("wsURI").value);
    ws.binaryType = "arraybuffer";

    ws.onopen = function (evt) {
        console.log(evt);
        writeMessage("Connect to WSEndpoint.");
    };
    ws.onmessage = function (evt) {
        console.log(evt);

        if(typeof event.data === 'string') {
            writeMessage(evt.data);
        } else if (event.data instanceof ArrayBuffer) {
            writeBinaryMessage(evt.data);
        }
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

function writeBinaryMessage(pValue) {
    var canvas = document.createElement("canvas");
    canvas.width="200";
    canvas.height="200";
    var ctx = canvas.getContext('2d');

    var imageData = ctx.createImageData(200,200);
    var pixels = imageData.data;

    var buffer = new Uint8Array(pValue);
    for (var i=0; i < pixels.length; i++) {
        pixels[i] = buffer[i];
    }
    ctx.putImageData(imageData, 0, 0);
    var wsMessages = document.getElementById("wsMessages");

    var newElement = document.createElement("div");
    newElement.appendChild(canvas);
    wsMessages.appendChild(newElement);
    wsMessages.scrollTop = wsMessages.scrollHeight;
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

function sendImage() {
    var canvas = document.getElementById("whiteboardcanvas");
    var context = canvas.getContext('2d');

    var imagedata = context.getImageData(0, 0, canvas.width, canvas.height).data;
    ws.send(new Uint8Array(imagedata));
}
