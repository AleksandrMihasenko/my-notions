import { ButtonHTMLAttributes } from 'react';
import { classNames } from '@/shared';
import cls from './AppButton.module.scss';

enum ThemeAppButton {
	CLEAR = 'clear'
}

interface AppButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
	className?: string;
	theme?: ThemeAppButton;
}

const AppButton = (props: AppButtonProps) => {
	const { className, children, theme, ...otherProps } = props;

	return (
		<button
			className={classNames(cls.AppButton, {}, [className, theme])}
			{ ...otherProps }
		>
			{children}
		</button>
	);
}

export default AppButton;
