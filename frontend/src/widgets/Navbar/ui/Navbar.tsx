import React from 'react';
import { classNames } from '@/shared';
import cls from './Navbar.module.scss';
import { AppLink } from '@/shared';
import { ThemeSwitcher } from '@/widgets/ThemeSwitcher';
import { useTranslation } from 'react-i18next';

interface NavbarProps {
	className?: string;
}

const Navbar = ({ className }: NavbarProps) => {
	const { t } = useTranslation('translation');

	return (
		<div className={classNames(cls.navbar, {}, [ className ])}>
			<ThemeSwitcher />

			<div className={cls.links}>
				<AppLink to={"/"}>{ t('links.main') }</AppLink>
				<AppLink to={"/notes"}>{ t('links.notes') }</AppLink>
			</div>
		</div>
	);
}

export default Navbar;