var ws;


function connect() {
   ws = new WebSocket("ws://localhost:8080/TemperaturService/test/");

   var xhttp = new XMLHttpRequest();
   
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("log1").innerHTML += this.responseText;
    }
  };
  xhttp.open("GET", "http://localhost:8080/TemperaturService/rest/TempService/GetTemp", true);
  xhttp.send();

    ws.onmessage = function(event) {
        var log = document.getElementById("temp");
        

        log.innerHTML = event.data;
    };
}
function send() {
    var content = document.getElementById("msg").value;
    var json = JSON.stringify({
        "content":content
    });

    ws.send(json);
}