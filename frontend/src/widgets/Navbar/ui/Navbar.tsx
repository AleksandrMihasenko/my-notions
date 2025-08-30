import React from 'react';
import { classNames } from '@/shared';
import cls from './Navbar.module.scss';
import { AppLink } from '@/shared';
import { ThemeSwitcher } from '@/widgets/ThemeSwitcher';

interface NavbarProps {
	className?: string;
}

const Navbar = ({ className }: NavbarProps) => {
	return (
		<div className={classNames(cls.navbar, {}, [ className ])}>
			<ThemeSwitcher />

			<div className={cls.links}>
				<AppLink to={"/"}>Main</AppLink>
				<AppLink to={"/notes"}>Notes List</AppLink>
			</div>
		</div>
	);
}

export default Navbar;