import React from 'react'

const Locations = ({ location }) => {
    return (
        <ul key={location.time}>
            Time: {location.time}
            <br/>
            Coordinates:
            <ul>
                <li>Longitude:{location.longitude}</li>
                <li>Latitude:{location.latitude}</li>
                <li>Altitude:{location.altitude}</li>
            </ul>
        </ul>
    )
}

export default Locations