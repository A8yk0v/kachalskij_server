console.log("Hello! :)");

formFileSend.onsubmit = async (event) => {
    console.log("SendFilesToServer()");

    event.preventDefault();
    let response = await fetch('/api/file', {
        method: 'POST',
        body: new FormData(formFileSend)
    });
    let result = await response.json();

    console.log(result.toString());
    console.log("Sad: ",JSON.stringify(result));

    if (result.status === "ok")
    {
        document.getElementById('iconOk1').style.visibility = 'visible';
        document.getElementById('iconOk2').style.visibility = 'visible';
        document.getElementById('iconOk3').style.visibility = 'visible';
    }

    alert(result.status);
};

refreshClientsListButton.onclick = async (event) => {
    // ЗАПРОС СПИСКА КЛИЕНТОВ
    let response = await fetch('/api/refreshClientsList', {
        method: 'POST'
    });
    let result = await response.json();

    // ОББРАБОТКА ОТВЕТА
    let list = document.getElementById("listClients");
    // Очистим список
    while(list.lastChild) {
        list.lastChild.remove();
    }
    // Вставим новые значения в список
    for (i in result.clients) {
        let newLi = document.createElement('li');
        newLi.innerHTML = result.clients[i];
        list.appendChild(newLi);
    }
};

calculateButton.onclick = async (event) => {
    // ЗАПРОС на вычисление
    let response = await fetch('/api/calculate', {
        method: 'POST'
    });
    let result = await response.json();
    alert(result.status);
};