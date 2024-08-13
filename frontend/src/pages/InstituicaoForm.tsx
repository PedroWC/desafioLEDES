import React, { useState, useEffect } from 'react';
import { Stepper, Step, StepLabel, Button, Typography, StepConnector } from '@mui/material';
import { JsonForms } from '@jsonforms/react';
import { materialRenderers, materialCells } from '@jsonforms/material-renderers';
import { styled } from '@mui/system';
import { useParams } from 'react-router-dom';
import api from '../api/axiosConfig';
import Ajv from 'ajv';
import schema from '../schemas/instituicao.schema.json';
import uischema from '../schemas/instituicao.layout.json';
import brasilSchema from '../schemas/endereco.brasil.schema.json';
import brasilUiSchema from '../schemas/endereco.brasil.layout.json';
import estrangeiroSchema from '../schemas/endereco.estrangeiro.schema.json';
import estrangeiroUiSchema from '../schemas/endereco.estrangeiro.layout.json';
import StepperIcon from '../components/StepperIcon';
import ibgeApi from "../api/ibgeApi";
import { Institution } from '../types/Institution';
import FormEndereco from './FormEndereco';

interface InstituicaoFormProps {
    mode: 'create' | 'edit' | 'view';
    initialData?: Institution;
}

const steps = ['Dados da Instituição', 'Endereço'];
const ajv = new Ajv();

const CustomConnector = styled(StepConnector)(() => ({
    alternativeLabel: {
        top: 22,
    },
    active: {
        '& .MuiStepConnector-line': {
            borderColor: '#1976d2',
        },
    },
    completed: {
        '& .MuiStepConnector-line': {
            borderColor: '#1976d2',
        },
    },
    line: {
        borderColor: '#e0e0e0',
        borderTopWidth: 2,
        borderRadius: 1,
    },
}));

const InstituicaoForm: React.FC<InstituicaoFormProps> = ({ mode }) => {
    const [activeStep, setActiveStep] = useState(0);
    const [data, setData] = useState<Institution | Partial<Institution>>({});
    const [isBrazil, setIsBrazil] = useState(true);
    const [dynamicSchema, setDynamicSchema] = useState(schema);
    const [loaded, setLoaded] = useState(false);
    const { id } = useParams();

    useEffect(() => {
        if (mode !== 'create' && id) {
            api.get(`/api/instituicao/${id}`)
                .then(response => {
                    const institutionData: Institution = response.data;
                    setData(institutionData);
                    setIsBrazil(institutionData.pais === 'Brasil');
                    setLoaded(true); // Marcar como carregado após os dados serem carregados
                })
                .catch(error => {
                    console.error('Erro ao buscar dados da instituição:', error);
                    setLoaded(true); // Marcar como carregado mesmo em caso de erro para evitar loop infinito
                });
        } else {
            setLoaded(true); // Marcar como carregado imediatamente no modo 'create'
        }

        // Buscar a lista de países da API do IBGE
        ibgeApi.get('/')
            .then(response => {
                const countryNames = response.data.map((country: any) => country.nome.abreviado);

                // Remover duplicadas
                const countryOptions = Array.from(new Set(countryNames));

                // Atualizar o schema dinâmico com as opções carregadas
                const updatedSchema = {
                    ...schema,
                    properties: {
                        ...schema.properties,
                        pais: {
                            ...schema.properties.pais,
                            enum: countryOptions
                        }
                    }
                };
                setDynamicSchema(updatedSchema);
            })
            .catch(error => console.error('Erro ao buscar países:', error));
    }, [mode, id]);

    const handleNext = () => {
        if (activeStep === 0) {
            setIsBrazil(data.pais === 'Brasil');
        }
        setActiveStep((prevActiveStep) => prevActiveStep + 1);
    };

    const handleBack = () => {
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };

    const validateData = () => {
        const validate = ajv.compile(isBrazil ? brasilSchema : estrangeiroSchema);
        const valid = validate(data);
        if (!valid) {
            console.error(validate.errors);
            return false;
        }
        return true;
    };

    const handleSubmit = () => {
        if (!validateData()) {
            alert('Existem erros no formulário. Por favor, corrija antes de continuar.');
            return;
        }

        let institutionData;

        if (isBrazil) {
            // Estrutura de dados para uma instituição brasileira
            institutionData = {
                instituicao: {
                    nome: data.nome,
                    sigla: data.sigla,
                    status: true
                },
                pais: data.pais,
                cnpj: data.cnpj,
                cep: data.cep,
                logradouro: data.logradouro,
                bairro: data.bairro,
                estado: data.estado,
                municipio: data.municipio,
                numero: data.numero,
                complemento: data.complemento
            };
        } else {
            // Estrutura de dados para uma instituição estrangeira
            institutionData = {
                instituicao: {
                    nome: data.nome,
                    sigla: data.sigla,
                    status: true
                },
                pais: data.pais,
                cep: data.cep,
                logradouro: data.logradouro,
                complemento: data.complemento,
                estadoRegiao: data.estado,
                municipio: data.municipio
            };
        }

        const endpoint = isBrazil ? '/api/instituicao/brasileira' : '/api/instituicao/estrangeira';

        if (mode === 'edit') {
            api.put(`${endpoint}/${id}`, institutionData)
                .then(() => {
                    alert('Instituição editada com sucesso!');
                })
                .catch(error => {
                    alert('Erro ao editar instituição:');
                    console.error('Erro ao editar instituição:', error);
                });
        } else if (mode === 'create') {
            api.post(endpoint, institutionData)
                .then(() => {
                    alert('Instituição criada com sucesso!');
                })
                .catch(error => {
                    alert('Erro ao criar instituição:');
                    console.log('Erro ao criar instituição:', error);
                });
        }
    };

    const isDisabled = mode === 'view';

    // Verificar se os dados e as opções foram carregados antes de renderizar o formulário
    if (!loaded) {
        return <div>Carregando...</div>; // Mostrar uma mensagem de carregamento enquanto os dados e opções são carregadas
    }

    return (
        <div style={{ width: '50%', margin: 'auto', textAlign: 'center' }}>
            <Typography variant="h4" gutterBottom>
                Instituição
            </Typography>
            <Stepper activeStep={activeStep} alternativeLabel connector={<CustomConnector />}>
                {steps.map((label) => (
                    <Step key={label}>
                        <StepLabel StepIconComponent={StepperIcon}>{label}</StepLabel>
                    </Step>
                ))}
            </Stepper>

            {activeStep === steps.length ? (
                <div>
                    <Typography variant="h6" gutterBottom>
                        Cadastro completo!
                    </Typography>
                    <Button onClick={() => setActiveStep(0)}>Reiniciar</Button>
                </div>
            ) : (
                <div>
                    {activeStep === 1 ? (
                        <FormEndereco
                            schema={isBrazil ? brasilSchema : estrangeiroSchema}
                            uiSchema={isBrazil ? brasilUiSchema : estrangeiroUiSchema}
                            data={data}
                            setData={setData}
                            isDisabled={isDisabled}
                        />
                    ) : (
                        <JsonForms
                            schema={dynamicSchema}
                            uischema={uischema}
                            data={data}
                            renderers={materialRenderers}
                            cells={materialCells}
                            onChange={({ data }) => setData(data as Institution)}
                            readonly={isDisabled}
                        />
                    )}
                    <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: '20px' }}>
                        <Button
                            disabled={activeStep === 0}
                            onClick={handleBack}
                            variant="outlined"
                            color="inherit"
                        >
                            Anterior
                        </Button>
                        {activeStep < steps.length - 1 && (
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={handleNext}
                            >
                                Próximo
                            </Button>
                        )}
                        {activeStep === steps.length - 1 && mode !== 'view' && (
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={handleSubmit}
                            >
                                Finalizar
                            </Button>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
};

export default InstituicaoForm;
