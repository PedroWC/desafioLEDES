import axios from 'axios';

const ibgeApi = axios.create({
    baseURL: 'https://servicodados.ibge.gov.br/api/v1/paises/paises',
    headers: {
        'Content-Type': 'application/json',
    },
    timeout: 5000,
});

export default ibgeApi;
