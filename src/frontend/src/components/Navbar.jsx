import React from 'react'

const Navbar = () => {
    return (


        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="container-fluid">
                <a className="navbar-brand" href="http://localhost:3000/">Navbar</a>
                <button className="navbar-toggler" type="button"
                    data-bs-toggle="collapse" data-bs-target="#superhero_navbar"
                    aria-controls="superhero_navbar" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="superhero_navbar">
                    <ul className="navbar-nav me-auto">
                        <li className="nav-item">
                            <a className="nav-link" href="http://localhost:3000/">Features</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="http://localhost:3000/">Home</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="http://localhost:3000/">About</a>
                        </li>
                    </ul>
                    <form className="d-flex">
                        <input className="form-control me-sm-2" type="text" placeholder="Search" />
                        <button className="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
                    </form>
                </div>
            </div>
        </nav>
    )
}

export default Navbar