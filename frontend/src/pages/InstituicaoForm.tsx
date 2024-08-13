import React, { useState } from 'react';
import {
    Stepper,
    Step,
    StepLabel,
    Button,
    Typography,
    StepConnector
} from '@mui/material';
import { JsonForms } from '@jsonforms/react';
import { materialRenderers, materialCells } from '@jsonforms/material-renderers';
import { styled } from '@mui/system';

import schema from '../schemas/instituicao.schema.json';
import uischema from '../schemas/instituicao.layout.json';
import '../styles/InstituicaoForm.css';
import StepperIcon from '../components/StepperIcon';

const steps = ['Dados da Instituição', 'Endereço'];

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

const InstituicaoForm: React.FC = () => {
    const [activeStep, setActiveStep] = useState(0);
    const [data, setData] = useState({});

    const handleNext = () => {
        setActiveStep((prevActiveStep) => prevActiveStep + 1);
    };

    const handleBack = () => {
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };

    const handleSubmit = () => {
        console.log('Finalizando o cadastro com os dados:', data);
    };

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
                    <JsonForms
                        schema={schema}
                        uischema={uischema}
                        data={data}
                        renderers={materialRenderers}
                        cells={materialCells}
                        onChange={({ data }) => setData(data)}
                    />
                    <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: '20px' }}>
                        <Button
                            disabled={activeStep === 0}
                            onClick={handleBack}
                            variant="outlined"
                            color="inherit"
                        >
                            Anterior
                        </Button>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={activeStep === steps.length - 1 ? handleSubmit : handleNext}
                        >
                            {activeStep === steps.length - 1 ? 'Finalizar' : 'Próximo'}
                        </Button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default InstituicaoForm;