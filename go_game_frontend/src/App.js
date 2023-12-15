import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import AuthProvider from './security/AuthContext';
import Header from './components/Header';

import RegisterPage from './pages/RegisterPage';
import LoginPage from './pages/LoginPage';
import Board from './components/Board';
import RoomListPage from './pages/RoomListPage';
import CreateGamePage from './pages/CreateGamePage';
import MainPage from './pages/MainPage';
import GamePage from './pages/GamePage';

function App() {
  return (
    <div className="App">
      <AuthProvider>
        
        <BrowserRouter>
          <Header/>
          <Routes>
            
            <Route path="/" element={
              <MainPage/>
            }/>
            
            <Route path="/register" element={
              <RegisterPage/>
            }/>

            <Route path="/login" element={
              <LoginPage/>
            }/>

            <Route path="/rooms" element={
              <RoomListPage/>
            }/>

            <Route path="/create/game" element={
              <CreateGamePage/>
            }/>

            <Route path="/game/:roomId" element={
              <GamePage/>
            }/>


          </Routes>
          {/* <Board
            size={9}
            cellSize={30}
            boardMatrix={[
              [null, null, null, "WHITE", null, null, null, null, null],
              [null, null, null, null, null, null, null, null, null],
              [null, null, null, null, null, null, null, null, null],
              [null, null, null, null, null, null, null, null, null],
              [null, null, null, null, null, "BLACK", null, null, null],
              [null, null, null, null, null, null, null, null, null],
              [null, null, null, null, null, null, null, null, null],
              [null, null, null, null, null, null, null, null, null],
              [null, null, null, null, null, null, null, null, null]
            ]}
            color="WHITE"
          /> */}
        </BrowserRouter>
      </AuthProvider>
      
    </div>
  );
}

export default App;
