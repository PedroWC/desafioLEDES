import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import InstitutionList from './pages/InstitutionList';
import BotaoVoltar from './components/BotaoVoltar';
import InstituicaoForm from './pages/InstituicaoForm';

const App: React.FC = () => {
    return (
        <Router>
            <div style={{ position: 'relative', width: '100%', height: '100vh', fontFamily: 'Arial, sans-serif' }}>
                <BotaoVoltar />
                <div
                    style={{
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                        height: '100%'
                    }}
                >
                    <Routes>
                        <Route path="/" element={
                            <div>
                                <h2 style={{ textAlign: 'center' }}>Bem-vindo ao sistema</h2>
                                <button
                                    style={{ marginTop: '20px', padding: '10px 20px', cursor: 'pointer' }}
                                    onClick={() => window.location.href = '/institutions'}
                                >
                                    Ver Lista de Instituições
                                </button>
                            </div>
                        } />
                        <Route path="/institutions" element={<InstitutionList />} />
                        <Route path="/institutions/create" element={<InstituicaoForm />} />
                    </Routes>
                </div>
            </div>
        </Router>
    );
};

export default App;