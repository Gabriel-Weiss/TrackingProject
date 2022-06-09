function LoadData() {
    this.source = null;
    this.start = function () {
        let dataTable = document.getElementById("usersTable");

        var url = 'http://localhost:8080';
        this.source = new EventSource(url+'/subscribe');


        this.source.addEventListener("user_dto", function (event) {
            let userDto = JSON.parse(event.data);
            console.log(userDto);

            let rowElement = dataTable.getElementsByTagName("tbody")[0].insertRow(0);
            let cell0 = rowElement.insertCell(0);
            let cell1 = rowElement.insertCell(1);
            let cell2 = rowElement.insertCell(2);

            cell0.innerHTML = userDto.userId;
            cell1.innerHTML = userDto.userStatus;

            let div = document.createElement('div');
            div.setAttribute('class', 'd-grid gap-2 d-md-block');

            let btnUpd = document.createElement('button');
            btnUpd.setAttribute('class','btn btn-primary');
            btnUpd.setAttribute('style', 'margin-right: 5px');
            btnUpd.addEventListener('click', function (x) {
                    location.href = url + '/update/' + userDto.userId;
                  });
            btnUpd.innerHTML = 'Update';

            let btnDts = document.createElement('button');
            btnDts.setAttribute('class','btn btn-success');
            btnDts.innerHTML = 'Details';

            div.appendChild(btnUpd);
            div.appendChild(btnDts);
            cell2.appendChild(div);
        });

        this.source.onerror = function () {
            this.close();
        };
    };

    this.stop = function () {
        this.source.close();
    };
}

loadData = new LoadData();

window.onload = function () {
    loadData.start();
};

window.onbeforeunload = function () {
    loadData.stop();
}