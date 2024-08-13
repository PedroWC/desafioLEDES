import React from 'react';
import { useNavigate } from 'react-router-dom';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import '../styles/BotaoVoltar.css';

const BotaoVoltar: React.FC = () => {
    const navigate = useNavigate();

    return (
        <button
            className="botao-voltar"
            onClick={() => navigate(-1)}
        >
            <ArrowBackIcon className="icone-voltar" />
            VOLTAR
        </button>
    );
};

export default BotaoVoltar;
