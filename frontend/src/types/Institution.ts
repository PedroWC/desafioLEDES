export interface Institution {
    id: number;
    nome: string;
    sigla: string;
    pais: string;
    status: boolean;
    cep: string;
    logradouro: string;
    complemento: string | null;
    estado: string;
    municipio: string;
    cnpj: string | null;
    bairro: string | null;
    numero: string | null;
}