import axios from "axios";

const BASE_URL = 'http://localhost:8080/'

class ServiceClass {

    getClients() {
        return axios.get(BASE_URL)
    }

    updateStatus(id) {
        return axios.put(BASE_URL + 'update/' + id)
    }

    getClient(id) {
        return axios.get(BASE_URL + 'get/' + id)
    }

    deleteClient(id) {
        return axios.delete(BASE_URL + 'delete/' + id)
    }
}

export default new ServiceClass();