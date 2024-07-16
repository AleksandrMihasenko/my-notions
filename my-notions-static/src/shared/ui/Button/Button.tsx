import React, { FC, ButtonHTMLAttributes } from 'react';
import { getClassNames } from '../../lib/classNames/classNames';
import * as classes from './Button.module.scss';

export enum ButtonTheme {
    CLEAR = 'clear',
}

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
    className?: string;
    children: React.ReactNode;
    theme?: ButtonTheme;
}

export const Button: FC<ButtonProps> = (props) => {
    const {
        children,
        className,
        theme,
        ...otherProps
    } = props;
    return (
        <button
            className={getClassNames(classes.Button, {}, [className, theme])}
            {...otherProps}
        >
            {children}
        </button>
    );
}

export default Button;