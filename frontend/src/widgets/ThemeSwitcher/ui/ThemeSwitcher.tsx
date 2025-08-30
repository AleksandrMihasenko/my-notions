import React from 'react';
import { Theme, useTheme } from '@/app/providers/ThemeProvider';
import { AppButton, classNames } from '@/shared';
import cls from './ThemeSwitcher.module.scss';
import Sun from '@/shared/assets/sun.svg?react';
import Moon from '@/shared/assets/moon.svg?react';

interface ThemeSwitcherProps {
	className?: string;
}

const ThemeSwitcher = ({ className }: ThemeSwitcherProps) => {
	const { theme, toggleTheme } = useTheme();

	return (
		<AppButton
			theme={'clear'}
			className={classNames(cls.ThemeSwitcher, {}, [className])}
			onClick={toggleTheme}
		>
			{theme === Theme.DARK ? <Moon /> : <Sun />}

		</AppButton>
	);
}

export default ThemeSwitcher;
