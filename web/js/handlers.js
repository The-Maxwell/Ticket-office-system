function onAddButton(event) {
    let tableActive = document.getElementsByClassName("active")[0];
    let table = tableActive.getAttribute("title");
    let element = document.getElementsByClassName("containerAdd" + table)[0];
    element.style.display = "block";
    let elBlur = document.getElementsByClassName("wrapper")[0];
    elBlur.style.filter = "blur(2px)";
}

try{
    let buttonAdd = document.getElementById("add");
    buttonAdd.onclick = onAddButton;
}catch (e) {
    console.log(e);
}

function onDelete({target: el}) {
    let el2 = el.parentNode;
    let el3 = el2.parentElement;
    let formData = new FormData();
    formData.set('entityString', el3.children[0].textContent);
    let table = document.getElementsByClassName("active")[0];
    formData.append('table', table.getAttribute("title"));
    formData.append('act', 'Delete');
    let request = new XMLHttpRequest();
    request.open("POST", "/work_with_db");
    request.onreadystatechange = function () {
        onReadyStateChangeCRUD(request);
    };
    request.send(formData);
}

function onEdit({target: el}) {
    let el2 = el.parentNode;
    let el3 = el2.parentElement;
    let entityString = "";
    for (let i = 0; i < el3.children.length; i++) {
        entityString += el3.children[i].textContent;
        if (i + 1 !== el3.children.length) entityString += ",";
    }
    entityString = entityString.substr(0, entityString.length - 2);
    console.log(entityString);
    let formData = new FormData();
    formData.append('entityString', entityString);
    let table = document.getElementsByClassName("active")[0];
    formData.append('table', table.getAttribute("title"));
    formData.append('act', 'Update');
    let request = new XMLHttpRequest();
    request.open("POST", "/work_with_db");
    request.onreadystatechange = function () {
        onReadyStateChangeCRUD(request);
    };
    request.send(formData);
}

let deleteElems = document.getElementsByClassName("delete");
let editElems = document.getElementsByClassName("edit");
for (let i = 0; i < deleteElems.length; i++) {
    deleteElems[i].onclick = onDelete;
    editElems[i].onclick = onEdit;
}

function onSubmitReport(event) {
    event.preventDefault();
    let form = new FormData(event.target);
    let request = new XMLHttpRequest();
    request.open("POST", "/statistics");
    request.onreadystatechange = function () {
        onReadyStateChangeReports(request);
    };
    request.send(form);
    let loading = document.getElementById("loading");
    loading.style.display = "block";
    onResetSendEmail(event);
}
function onGenerate(event) {
    event.preventDefault();
    let el = event.target;
    console.log(el.getAttribute("id"));
    let form = new FormData();
    form.append('act', 'Generate');
    form.append('generateReport', el.getAttribute("id"));
    let request = new XMLHttpRequest();
    request.open("POST", "/statistics", true);
    request.onreadystatechange = function () {
        onReadyStateChangeReports(request);
    };
    request.send(form);
    let loading = document.getElementById("loading");
    loading.style.display = "block";
}

function onOpenSendForm(event) {
    let element = document.getElementsByClassName("containerSendEmail")[0];
    element.style.display = "block";
    let target = event.target;
    let el = document.getElementById("sendReport");
    el.value = target.id;
    let elBlur = document.getElementsByClassName("wrapper")[0];
    elBlur.style.filter = "blur(2px)";
}

function onAdd(event) {
    event.preventDefault();
    console.log("Submit")
    let form = new FormData(event.target);
    let table = document.getElementsByClassName("active")[0];
    form.append('table', table.getAttribute("title"));
    form.append('act', 'Add');
    let request = new XMLHttpRequest();
    request.open("POST", "/work_with_db");
    request.onreadystatechange = function () {
        onReadyStateChangeCRUD(request);
    };
    request.send(form);
    onReset(event);
}

function onReadyStateChangeCRUD(request) {
    if (request.readyState === 4) {
        var status = request.status;
        if (status === 200) {
            location.reload();
        }
        else if(status === 500){
            showResultLog(request);
        }
    }
}

function onReadyStateChangeReports(request) {
    if (request.readyState === 4) {
        var status = request.status;
        if (status === 200 || status === 500){
            let loading = document.getElementById("loading");
            loading.style.display = "none";
            showResultLog(request);
        }
    }
}

function showResultLog(request){
    let element1 = document.getElementsByClassName("result-log")[0];
    element1.style.display = "block";
    let element2 = document.getElementById("result-text");
    element2.textContent = request.responseText;
    let elBlur = document.getElementsByClassName("wrapper")[0];
    elBlur.style.filter = "blur(2px)";
}

function onReset(event) {
    let tableActive = document.getElementsByClassName("active")[0];
    let table = tableActive.getAttribute("title");
    let element = document.getElementsByClassName("containerAdd" + table)[0];
    element.style.display = "none";
    let elBlur = document.getElementsByClassName("wrapper")[0];
    elBlur.style.filter = "blur(0px)";
}

function onResetSendEmail(event) {
    let element = document.getElementsByClassName("containerSendEmail")[0];
    element.style.display = "none";
    let elBlur = document.getElementsByClassName("wrapper")[0];
    elBlur.style.filter = "blur(0px)";
}

function onOK(event) {
    let element = document.getElementsByClassName("result-log")[0];
    element.style.display = "none";
    let elBlur = document.getElementsByClassName("wrapper")[0];
    elBlur.style.filter = "blur(0px)";
}
