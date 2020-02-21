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
