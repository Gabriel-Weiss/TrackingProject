import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import Clients from './components/Clients';
import Details from './components/Details';
import Navbar from './components/Navbar';

function App() {
  return (
    <>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route index element={<Clients />} />
          <Route path='/' element={<Clients />} />
          <Route path='/get/:id' element={<Details />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;