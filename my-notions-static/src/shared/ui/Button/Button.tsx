import React, { FC, ButtonHTMLAttributes } from 'react';
import { getClassNames } from '../../lib/classNames/classNames';
import * as classes from './Button.module.scss';

export enum ButtonTheme {
    CLEAR = 'clear',
    OUTLINE = 'outline',
    BACKGROUND = 'background',
    BACKGROUND_INVERTED = 'backgroundInverted',
}

export enum ButtonSize {
    M = 'size_m',
    L = 'size_l',
    XL = 'size_xl',
}

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
    className?: string;
    children: React.ReactNode;
    theme?: ButtonTheme;
    square?: boolean;
    size?: ButtonSize.M;
}

export const Button: FC<ButtonProps> = (props) => {
    const {
        children,
        className,
        theme,
        square,
        size,
        ...otherProps
    } = props;

    const mods: Record<string, boolean> = {
        [classes['square']]: square,
        [classes[size]]: true,
    }

    return (
        <button
            className={getClassNames(classes.Button, mods, [className, theme])}
            {...otherProps}
        >
            {children}
        </button>
    );
}

export default Button;