function openModel(modelName) {
    var i;
    var x = document.getElementsByClassName("model");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    document.getElementById(modelName).style.display = "block";
}

function testReq() {
    const xhr = new XMLHttpRequest();

    xhr.open("GET", "http://54.159.17.211:8080/WebApp1/player");
    xhr.send();
    xhr.onload = () => {
        returnData = xhr.responseText;

        document.write(returnData);

    }

}