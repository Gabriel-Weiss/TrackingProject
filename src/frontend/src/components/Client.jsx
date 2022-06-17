import React from 'react'
import { useNavigate } from 'react-router-dom';

const Client = ({ client, handleDelete, handleUpdateStatus }) => {

    const navigateTo = useNavigate();

    const getClient = (e, clientId) => {
        e.preventDefault();
        navigateTo(`/get/${clientId}`);
    }

    return (
        <tr key={client.clientId}>
            <td>{client.clientId}</td>
            <td>{client.clientPhone}</td>
            <td className='text-center'>{client.clientStatus.toString()}</td>
            <td>
                <div className="d-grid gap-2 d-md-flex justify-content-md-center">
                    <button
                        onClick={(clientId) => handleUpdateStatus(client.clientId)}
                        className="btn btn-primary">
                        Update
                    </button>
                    <button 
                    onClick={(e, clientId) => getClient(e, client.clientId)}
                    className="btn btn-success">
                        Details
                    </button>
                    <button
                        onClick={(e, clientId) => handleDelete(e, client.clientId)}
                        className="btn btn-danger">
                        Delete
                    </button>
                </div>
            </td>
        </tr>
    )
}

export default Client