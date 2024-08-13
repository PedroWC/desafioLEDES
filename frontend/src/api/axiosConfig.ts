import axios from 'axios';

const api = axios.create({
    baseURL: 'http://192.168.102.9:8080',
    timeout: 10000,
});

export default api;