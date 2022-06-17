import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import ServiceClass from '../service/ServiceClass';
import Client from './Client';

const Clients = () => {

    const [loaded, setLoaded] = useState();
    const [clients, setClients] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const getClients = async () => {
            setLoaded(true)
            try {
                const response = await ServiceClass.getClients();
                setClients(response.data);
            } catch (error) {
                console.log(error)
            }
            setLoaded(false);
        }
        getClients();
    }, [])

    const [listening, setListening] = useState(false);
    const url = 'http://localhost:8080';
    let source = undefined;

    useEffect(() => {
        if (!listening) {
            source = new EventSource(url + '/subscribe');
            const dataTable = document.getElementById("clientsTable");

            source.addEventListener('clients', (event) => {
                let clientDto = JSON.parse(event.data);
                // console.log(clientDto);

                let rowElement = dataTable.getElementsByTagName("tbody")[0].insertRow(0);
                let idCell = rowElement.insertCell(0);
                let phoneCell = rowElement.insertCell(1);
                let statusCell = rowElement.insertCell(2);
                let optionsCell = rowElement.insertCell(3);

                idCell.innerText = clientDto.clientId;
                phoneCell.innerText = clientDto.clientPhone;
                statusCell.setAttribute('class', 'text-center');
                statusCell.innerText = clientDto.clientStatus;

                let div = document.createElement('div');
                div.setAttribute('class', 'd-grid gap-2 d-md-flex justify-content-md-center');

                let btnUpd = document.createElement('a');
                btnUpd.setAttribute('class', 'btn btn-primary');
                btnUpd.innerText = 'Update';

                let btnDts = document.createElement('a');
                btnDts.setAttribute('class', 'btn btn-success');
                btnDts.innerText = 'Details';

                div.appendChild(btnUpd);
                div.appendChild(btnDts);
                optionsCell.appendChild(div);
            });

            source.onopen = (event) => {
                // console.log('connection opened')
            }

            source.onmessage = (event) => {
                // console.log('data received')
            }

            source.onerror = (event) => {
                if (event.target.readyState === EventSource.CLOSED) {
                    console.log('Stream: ' + event.target.readyState + ' closed')
                }
                source.close();
            }

            setListening(true);
        }

        return () => {
            source.close();
            console.log('event source closed')
        }

    }, [])
    
    const handleUpdateStatus = (id) => {
        ServiceClass.updateStatus(id);
    }

    const handleDelete = (e, id) => {
        e.preventDefault();
        ServiceClass.deleteClient(id).then(
            (response) => {
                if (clients) {
                    setClients((prevClient) => {
                        return prevClient.filter((client) => client.clientId !== id)
                    })
                }
            }
        );
    };

    return (
        <div className='container-md'>
            <h1>Lista utilizatorilor</h1>
            <table className="table table-hover" id="clientsTable">
                <thead>
                    <tr>
                        <th>Identificator</th>
                        <th>Telefon</th>
                        <th className='text-center'>Infectat</th>
                        <th className='text-center'>Options</th>
                    </tr>
                </thead>
                {!loaded && (
                    <tbody>
                        {clients.map((client) => (
                            <Client
                                client={client}
                                key={client.clientId}
                                handleDelete={handleDelete}
                                handleUpdateStatus={handleUpdateStatus} />
                        ))}
                    </tbody>
                )}
            </table>
        </div>
    )
}

export default Clients