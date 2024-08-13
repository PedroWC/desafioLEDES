import React, { useEffect, useState } from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    TablePagination,
    IconButton,
    Button
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import VisibilityIcon from '@mui/icons-material/Visibility';
import EditIcon from '@mui/icons-material/Edit';
import BlockIcon from '@mui/icons-material/Block';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { useNavigate } from 'react-router-dom';
import api from '../api/axiosConfig';
import ibgeApi from '../api/ibgeApi';  // Importar sua instância da API do IBGE
import { Institution } from '../types/Institution';
import '../styles/InstitutionList.css';

const InstitutionList: React.FC = () => {
    const [instituicoes, setInstituicoes] = useState<Institution[]>([]);
    const [pagina, setPagina] = useState(0);
    const [linhasPorPagina, setLinhasPorPagina] = useState(5);
    const [countryFlagMap, setCountryFlagMap] = useState<{ [key: string]: string }>({});
    const navigate = useNavigate();

    useEffect(() => {
        api.get('/api/instituicao')
            .then(resposta => {
                setInstituicoes(resposta.data);
            })
            .catch(erro => {
                console.error('Houve um erro ao buscar as instituições!', erro);
            });

        // Requisição para obter o mapeamento de países e bandeiras
        ibgeApi.get('/')
            .then(resposta => {
                const countryMap: { [key: string]: string } = {};
                resposta.data.forEach((country: any) => {
                    countryMap[country.nome.abreviado.toLowerCase()] = country.id['ISO-3166-1-ALPHA-2'].toLowerCase();
                });
                setCountryFlagMap(countryMap);
            })
            .catch(erro => {
                console.error('Erro ao buscar os países do IBGE!', erro);
            });
    }, []);

    const obterCodigoBandeira = (pais: string) => {
        return countryFlagMap[pais.toLowerCase()] || '';
    };

    const ativarInstituicao = (id: number) => {
        if (window.confirm('Você realmente deseja ativar esta instituição?')) {
            api.put(`/api/instituicao/reativar/${id}`)
                .then(() => {
                    setInstituicoes(prevInstituicoes =>
                        prevInstituicoes.map(inst =>
                            inst.id === id ? { ...inst, status: true } : inst
                        )
                    );
                })
                .catch(erro => {
                    console.error('Houve um erro ao reativar a instituição!', erro);
                });
        }
    };

    const desativarInstituicao = (id: number) => {
        if (window.confirm('Você realmente deseja inativar esta instituição?')) {
            api.delete(`/api/instituicao/${id}`)
                .then(() => {
                    setInstituicoes(prevInstituicoes =>
                        prevInstituicoes.map(inst =>
                            inst.id === id ? { ...inst, status: false } : inst
                        )
                    );
                })
                .catch(erro => {
                    console.error('Houve um erro ao inativar a instituição!', erro);
                });
        }
    };

    const alterarPagina = (_: unknown, novaPagina: number) => {
        setPagina(novaPagina);
    };

    const alterarLinhasPorPagina = (evento: React.ChangeEvent<HTMLInputElement>) => {
        setLinhasPorPagina(parseInt(evento.target.value, 10));
        setPagina(0); // Volta para a primeira página
    };

    return (
        <Paper className="container">
            <div className="cabecalho">
                <h1 className="titulo">Instituição</h1>
                <Button
                    variant="text"
                    startIcon={<AddIcon />}
                    className="botao-adicionar"
                    onClick={() => navigate('/institutions/create')}
                >
                    ADICIONAR
                </Button>
            </div>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Nome</TableCell>
                            <TableCell>Sigla</TableCell>
                            <TableCell>País</TableCell>
                            <TableCell>Ações</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {Array.isArray(instituicoes) && instituicoes
                            .slice(pagina * linhasPorPagina, pagina * linhasPorPagina + linhasPorPagina)
                            .map((instituicao) => (
                                <TableRow key={instituicao.id} style={{ backgroundColor: instituicao.status ? '#fff' : '#f2f2f2' }}>
                                    <TableCell>{instituicao.nome}</TableCell>
                                    <TableCell>{instituicao.sigla || 'N/A'}</TableCell>
                                    <TableCell>
                                        <img
                                            src={`https://flagcdn.com/w20/${obterCodigoBandeira(instituicao.pais)}.png`}
                                            alt={instituicao.pais}
                                            className="icone-bandeira"
                                        />
                                        {instituicao.pais}
                                    </TableCell>
                                    <TableCell>
                                        {instituicao.status ? (
                                            <>
                                                <IconButton className="icones-acao" onClick={() => navigate(`/institutions/view/${instituicao.id}`)}>
                                                    <VisibilityIcon sx={{ color: '#6c757d' }} />
                                                </IconButton>
                                                <IconButton className="icones-acao" onClick={() => navigate(`/institutions/edit/${instituicao.id}`)}>
                                                    <EditIcon sx={{ color: '#1E90FF' }} />
                                                </IconButton>
                                                <IconButton className="icones-acao" onClick={() => desativarInstituicao(instituicao.id)}>
                                                    <BlockIcon sx={{ color: '#dc3545' }} />
                                                </IconButton>
                                            </>
                                        ) : (
                                            <IconButton className="icones-acao" onClick={() => ativarInstituicao(instituicao.id)}>
                                                <CheckCircleIcon sx={{ color: '#28a745' }} />
                                            </IconButton>
                                        )}
                                    </TableCell>
                                </TableRow>
                            ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[5, 10, 15]}
                component="div"
                count={instituicoes.length}
                rowsPerPage={linhasPorPagina}
                page={pagina}
                onPageChange={alterarPagina}
                onRowsPerPageChange={alterarLinhasPorPagina}
                labelRowsPerPage="Linhas por página"
                labelDisplayedRows={({ from, to, count }) => `${from}-${to} de ${count}`}
            />
        </Paper>
    );
};

export default InstitutionList;
