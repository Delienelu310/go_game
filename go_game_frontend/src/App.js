import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import AuthProvider from './security/AuthContext';
import Header from './components/Header';

import RegisterPage from './pages/RegisterPage';
import LoginPage from './pages/LoginPage';
import Board from './components/Board';

function App() {
  return (
    <div className="App">
      <AuthProvider>
        
        <BrowserRouter>
          <Header/>
          <Routes>
            
            <Route path="/" element={
              <div></div>
            }/>
            
            <Route path="/register" element={
              <RegisterPage/>
            }/>

            <Route path="/login" element={
              <LoginPage/>
            }/>

          </Routes>
          <Board
            size={9}
            cellSize={30}
            stones={[{row: 1, col: 2}]}
          />
        </BrowserRouter>
      </AuthProvider>
      
    </div>
  );
}

export default App;
