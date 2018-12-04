var socket = null;


function connect() {

    socket = new WebSocket('ws://localhost:8080/messenger');

    socket.onopen = socket_onopen;

    socket.onmessage = socket_onevent;

    socket.onclose = socket_onclose;

    socket.onerror = socket_onerror;

}

function socket_onopen() {
    console.log('opened.');
    $("#connect").prop('disabled', true);
    $("#disconnect").prop('disabled', false);
    $("#send").prop('disabled', false);
}

function socket_onclose() {
    console.log('closed.');
    socket = null;
    $("#connect").prop('disabled', false);
    $("#disconnect").prop('disabled', true);
    $("#send").prop('disabled', true);

}

function socket_onerror(err) {
    console.log('ups, got an error.');
    console.log(err);

}

function socket_onevent(evt) {
    console.log('event received. ' + evt.data);
    showGreeting(evt.data);

}


function disconnect() {
    if (socket !== null) {
        socket.close();
    }

}

function sendName() {
    socket.send($("#name").val());
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});
