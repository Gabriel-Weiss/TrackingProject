import React from 'react'
import Locations from './Locations'

const Dates = ({ date }) => {
    return (
        <div>
            <ul key={date.date}>
                <li>date:<br/>{date.date}</li>
                <li>codes:{date.codes.map(code => (<span><br />- {code}</span>))}</li>
                <li>
                    locations:{date.locations.map(location => (<Locations location={location} key={location.time} />))}
                </li>
            </ul>
        </div>
    )
}

export default Dates