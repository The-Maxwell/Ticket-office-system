function onAddButton(event) {
    var tableActive = document.getElementsByClassName("active")[0];
    var table = tableActive.getAttribute("title");
    var element = document.getElementsByClassName("containerAdd" + table)[0];
    element.style.display = "block";
    var elBlur = document.getElementsByClassName("wrapper")[0];
    elBlur.style.filter = "blur(2px)";
}

try{
    var buttonAdd = document.getElementById("add");
    buttonAdd.onclick = onAddButton;
}catch (e) {
    console.log(e);
}

function onDelete({target: el}) {
    var el2 = el.parentNode;
    var el3 = el2.parentElement;
    let formData = new FormData();
    formData.set('entityString', el3.children[0].textContent);
    var table = document.getElementsByClassName("active")[0];
    formData.append('table', table.getAttribute("title"));
    formData.append('act', 'Delete');
    var request = new XMLHttpRequest();
    request.open("POST", "http://localhost:8082/work_with_db");
    request.onreadystatechange = function () {
        onReadyStateChangeCRUD(request);
    };
    request.send(formData);
}

function onEdit({target: el}) {
    var el2 = el.parentNode;
    var el3 = el2.parentElement;
    var entityString = "";
    for (var i = 0; i < el3.children.length; i++) {
        entityString += el3.children[i].textContent;
        if (i + 1 !== el3.children.length) entityString += ",";
    }
    entityString = entityString.substr(0, entityString.length - 2);
    console.log(entityString);
    let formData = new FormData();
    formData.append('entityString', entityString);
    var table = document.getElementsByClassName("active")[0];
    formData.append('table', table.getAttribute("title"));
    formData.append('act', 'Update');
    var request = new XMLHttpRequest();
    request.open("POST", "http://localhost:8082/work_with_db");
    request.onreadystatechange = function () {
        onReadyStateChangeCRUD(request);
    };
    request.send(formData);
}

var deleteElems = document.getElementsByClassName("delete");
var editElems = document.getElementsByClassName("edit");
for (var i = 0; i < deleteElems.length; i++) {
    deleteElems[i].onclick = onDelete;
    editElems[i].onclick = onEdit;
}
function onSubmitReport(event) {
    event.preventDefault();
    let form = new FormData(event.target);
    var request = new XMLHttpRequest();
    request.open("POST", "/statistics");
    request.onreadystatechange = function () {
        onReadyStateChangeReports(request);
    };
    request.send(form);
    var loading = document.getElementById("loading");
    loading.style.display = "block";
    onResetSendEmail(event);
}
function onGenerate(event) {
    event.preventDefault();
    var el = event.target;
    console.log(el.getAttribute("id"));
    let form = new FormData();
    form.append('act', 'Generate');
    form.append('generateReport', el.getAttribute("id"));
    var request = new XMLHttpRequest();
    request.open("POST", "/statistics", true);
    request.onreadystatechange = function () {
        onReadyStateChangeReports(request);
    };
    request.send(form);
    var loading = document.getElementById("loading");
    loading.style.display = "block";
}

function onOpenSendForm(event) {
    var element = document.getElementsByClassName("containerSendEmail")[0];
    element.style.display = "block";
    var target = event.target;
    var el = document.getElementById("sendReport");
    el.value = target.id;
    var elBlur = document.getElementsByClassName("wrapper")[0];
    elBlur.style.filter = "blur(2px)";
}

function onAdd(event) {
    event.preventDefault();
    console.log("Submit")
    let form = new FormData(event.target);
    var table = document.getElementsByClassName("active")[0];
    form.append('table', table.getAttribute("title"));
    form.append('act', 'Add');
    var request = new XMLHttpRequest();
    request.open("POST", "/work_with_db");
    request.onreadystatechange = function () {
        onReadyStateChangeCRUD(request);
    };
    request.send(form);
    onReset(event);
}

function onReadyStateChangeCRUD(request) {
    if (request.readyState == 4) {
        var status = request.status;
        if (status == 200) {
            location.reload();
        }
        else if(status == 500){
            var element1 = document.getElementsByClassName("result-log")[0];
            element1.style.display = "block";
            var element2 = document.getElementById("result-text");
            element2.textContent = request.responseText;
            var elBlur = document.getElementsByClassName("wrapper")[0];
            elBlur.style.filter = "blur(2px)";
        }
    }
}

function onReadyStateChangeReports(request) {
    if (request.readyState == 4) {
        var status = request.status;
        if (status == 200 || status == 500){
            var loading = document.getElementById("loading");
            loading.style.display = "none";
            var element1 = document.getElementsByClassName("result-log")[0];
            element1.style.display = "block";
            var element2 = document.getElementById("result-text");
            element2.textContent = request.responseText;
            var elBlur = document.getElementsByClassName("wrapper")[0];
            elBlur.style.filter = "blur(2px)";
        }
    }
}
function onReset(event) {
    var tableActive = document.getElementsByClassName("active")[0];
    var table = tableActive.getAttribute("title");
    var element = document.getElementsByClassName("containerAdd" + table)[0];
    element.style.display = "none";
    var elBlur = document.getElementsByClassName("wrapper")[0];
    elBlur.style.filter = "blur(0px)";
}

function onResetSendEmail(event) {
    var element = document.getElementsByClassName("containerSendEmail")[0];
    element.style.display = "none";
    var elBlur = document.getElementsByClassName("wrapper")[0];
    elBlur.style.filter = "blur(0px)";
}

function onOK(event) {
    var element = document.getElementsByClassName("result-log")[0];
    element.style.display = "none";
    var elBlur = document.getElementsByClassName("wrapper")[0];
    elBlur.style.filter = "blur(0px)";
}
// function onShowReport(event, report){
//     event.preventDefault();
//     console.log("Show rep="+report);
//     let form = new FormData();
//     form.append('report', report);
//     var request = new XMLHttpRequest();
//     request.open("GET", "/statistics");
//     request.onreadystatechange = function () {
//         //onReadyStateChange(request);
//     };
//     request.send(form);
// }
