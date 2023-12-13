import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import AuthProvider from './security/AuthContext';
import Header from './components/Header';

function App() {
  return (
    <div className="App">
      <AuthProvider>
        
        <BrowserRouter>
          <Header/>
          <Routes>
            
            <Route path="/" element={
              <div></div>
            }>

            </Route>
          </Routes>
        </BrowserRouter>
      </AuthProvider>
      
    </div>
  );
}

export default App;
