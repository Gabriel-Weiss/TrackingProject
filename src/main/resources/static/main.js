function LoadData() {
    this.source = null;
    this.start = function () {
        let dataTable = document.getElementById("usersTable");
        var url = 'http://localhost:8080/subscribe';
        this.source = new EventSource(url);
        this.source.addEventListener("user_dto", function (event) {
        let userDto = JSON.parse(event.data);

        let rowElement = dataTable.getElementsByTagName("tbody")[0].insertRow(0);
        let cell0 = rowElement.insertCell(0);
        let cell1 = rowElement.insertCell(1);

        cell0.innerHTML = userDto.userId;
        cell1.innerHTML = userDto.userStatus;
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