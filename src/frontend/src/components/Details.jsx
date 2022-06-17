import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import ServiceClass from '../service/ServiceClass';
import Dates from './Dates';

const Details = () => {

    const { id } = useParams();
    const [client, setClient] = useState({
        clientId: id,
        clientPhone: '',
        clientStatus: '',
        dates: []
    });

    useEffect(() => {
        const getClient = async () => {
            try {
                const response = await ServiceClass.getClient(id);
                console.log(response);
                setClient(response.data);
            } catch (error) {
                console.log(error);
            }
        }
        getClient();
    }, [])

    return (
        <div className='container-md'>
            Details:
            <ul>
                <li>ID: {client.clientId}</li>
                <li>Phone: {client.clientPhone}</li>
                <li>Infectat: {client.clientStatus ? 'pozitiv' : 'negativ'}</li>
                <li>dates:{client.dates.map((date) => (<Dates date = {date}/>))}</li>
            </ul>
        </div>
    )
}

export default Details