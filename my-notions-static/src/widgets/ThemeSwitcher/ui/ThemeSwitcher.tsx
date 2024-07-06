import React from 'react';
import Button, { ButtonTheme } from 'shared/ui/Button/Button';
import { useTheme, Theme } from 'app/providers/ThemeProvider';
import { getClassNames } from 'shared/lib/classNames/classNames';
import * as classes from './ThemeSwitcher.module.scss';
import Sun from 'shared/assets/icons/sun.svg'
import Moon from 'shared/assets/icons/moon.svg'

interface ThemeSwitcherProps {
    className?: string;
}

export const ThemeSwitcher = ({className}: ThemeSwitcherProps) => {
    const {theme, toggleTheme} = useTheme();

    return (
        <Button
            theme={ButtonTheme.CLEAR}
            className={getClassNames(classes.ThemeSwitcher, {}, [className])}
            onClick={toggleTheme}
        >
            {theme === Theme.DARK ? <Sun /> : <Moon />}
        </Button>
    );
}

export default ThemeSwitcher;