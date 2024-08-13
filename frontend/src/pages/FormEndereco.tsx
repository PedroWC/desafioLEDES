import React, { useEffect } from 'react';
import { JsonForms } from '@jsonforms/react';
import { materialRenderers, materialCells } from '@jsonforms/material-renderers';
import serproApi from "../api/brasilApi";
import '../styles/InstituicaoForm.css';

type FormEnderecoProps = {
    schema: any;
    uiSchema: any;
    data: any;
    setData: React.Dispatch<React.SetStateAction<any>>;
    isDisabled: boolean;
};

const FormEndereco: React.FC<FormEnderecoProps> = ({ schema, uiSchema, data, setData, isDisabled }) => {
    useEffect(() => {
        const fetchCEPData = async (cep: string) => {
            try {
                const response = await serproApi.get(`/${cep}`);
                const cepData = response.data;

                // Atualizar os campos de endereço com os dados do CEP
                setData((prevData: any) => ({
                    ...prevData,
                    estado: cepData.state,
                    municipio: cepData.city,
                    logradouro: cepData.street,
                    bairro: cepData.neighborhood,
                }));
            } catch (error) {
                console.error('Erro ao buscar dados do CEP:', error);
            }
        };

        // Verificar se o país é "Brasil" e se o CEP tem 8 dígitos e é numérico
        if (data.pais === 'Brasil' && data.cep && data.cep.length === 8 && /^[0-9]+$/.test(data.cep)) {
            fetchCEPData(data.cep).then(() => {});
        }
    }, [data.cep, data.pais, setData]);

    return (
        <div style={{ marginTop: '20px' }}>
            <JsonForms
                schema={schema}  // Schema que define a estrutura de dados
                uischema={uiSchema}  // UI Schema que define como os dados são exibidos
                data={data}  // Dados do formulário
                renderers={materialRenderers}  // Renderers do Material UI
                cells={materialCells}  // Cells do Material UI
                onChange={({ data }) => setData(data)}  // Atualizar o estado quando os dados mudam
                readonly={isDisabled}  // Desabilitar campos se estiver em modo "view"
            />
        </div>
    );
};

export default FormEndereco;
