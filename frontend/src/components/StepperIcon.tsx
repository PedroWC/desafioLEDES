import {StepIconProps} from "@mui/material";
import React from "react";
import {AccountCircle, LocationOn} from "@mui/icons-material";


const StepperIcon = (props: StepIconProps) => {
    const { active, completed, icon } = props;

    const icons: { [index: string]: React.ReactElement } = {
        1: <AccountCircle />,
        2: <LocationOn />
    };

    return (
        <div
            style={{
                backgroundColor: active || completed ? '#1976d2' : '#e0e0e0',
                color: active || completed ? 'white' : 'gray',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                borderRadius: '50%',
                width: 50,
                height: 50,
                zIndex: 1
            }}
        >
            {icons[String(icon)]}
        </div>
    );
};

export default StepperIcon;