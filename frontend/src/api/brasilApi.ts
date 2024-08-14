import axios from 'axios';

const brasilApi = axios.create({
    baseURL: 'https://brasilapi.com.br/api/cep/v1',
    timeout: 5000,
});

export default brasilApi;
