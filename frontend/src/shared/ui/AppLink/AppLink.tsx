import React from 'react';
import { Link, type LinkProps } from 'react-router-dom';
import { classNames } from '@/shared';
import cls from './AppLink.module.scss'

enum AppLinkTheme {
	PRIMARY = 'primary',
	SECONDARY = 'secondary',
}

interface AppLinkProps extends LinkProps {
	className?: string;
	theme?: AppLinkTheme;
}

const AppLink = (props: AppLinkProps) => {
	const {
		to,
		className,
		children,
		theme = AppLinkTheme.PRIMARY,
		...other
	} = props;

	return (
		<Link
			to={to}
			className={classNames(cls.link, {}, [ className, cls[theme] ])}
			{...other}
		>
			{ children }
		</Link>
	);
}

export default AppLink;