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
import api from '../api/axiosConfig';
import { Institution } from '../types/Institution';
import './InstitutionList.css';

const InstitutionList: React.FC = () => {
    const [instituicoes, setInstituicoes] = useState<Institution[]>([]);
    const [pagina, setPagina] = useState(0);
    const [linhasPorPagina, setLinhasPorPagina] = useState(5);

    useEffect(() => {
        api.get('/api/instituicao')
            .then(resposta => {
                setInstituicoes(resposta.data);
            })
            .catch(erro => {
                console.error('Houve um erro ao buscar as instituições!', erro);
            });
    }, []);

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

    const obterCodigoBandeira = (pais: string) => {
        switch (pais.toLowerCase()) {
            case 'brasil':
                return 'br';
            case 'estados unidos':
                return 'us';
            case 'reino unido':
                return 'gb';
            default:
                return '';
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
                    onClick={() => {
                        console.log('Adicionar nova instituição');
                    }}
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
                                        <img src={`https://flagcdn.com/w20/${obterCodigoBandeira(instituicao.pais)}.png`} alt={instituicao.pais} className="icone-bandeira" /> {instituicao.pais}
                                    </TableCell>
                                    <TableCell>
                                        {instituicao.status ? (
                                            <>
                                                <IconButton className="icones-acao">
                                                    <VisibilityIcon sx={{ color: '#6c757d' }} /> {/* Cinza para Visualizar */}
                                                </IconButton>
                                                <IconButton className="icones-acao">
                                                    <EditIcon sx={{ color: '#1E90FF' }} /> {/* Azul Claro para Editar */}
                                                </IconButton>
                                                <IconButton className="icones-acao" onClick={() => desativarInstituicao(instituicao.id)}>
                                                    <BlockIcon sx={{ color: '#dc3545' }} /> {/* Vermelho para Inativar */}
                                                </IconButton>
                                            </>
                                        ) : (
                                            <IconButton className="icones-acao" onClick={() => ativarInstituicao(instituicao.id)}>
                                                <CheckCircleIcon sx={{ color: '#28a745' }} /> {/* Verde para Ativar */}
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
