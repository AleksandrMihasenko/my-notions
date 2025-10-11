import React, { useCallback, useState } from 'react';
import { AppButton, classNames, Modal } from '@/shared';
import cls from './Navbar.module.scss';
import { AppLink } from '@/shared';
import { ThemeSwitcher } from '@/widgets/ThemeSwitcher';
import { useTranslation } from 'react-i18next';

interface NavbarProps {
	className?: string;
}

const Navbar = ({ className }: NavbarProps) => {
	const { t } = useTranslation('translation');
	const [isAuthModalOpen, setIsAuthModalOpen] = useState(false);

	const onToggleModal = useCallback(() => {
		setIsAuthModalOpen((prevState) => !prevState);
	}, []);

	return (
		<div className={classNames(cls.navbar, {}, [className])}>
			<ThemeSwitcher />
			<AppButton onClick={onToggleModal}>{t('links.login')}</AppButton>

			<Modal isOpen={isAuthModalOpen} onClose={() => onToggleModal()}>

			</Modal>

			<div className={cls.links}>
				<AppLink to={'/'}>{t('links.main')}</AppLink>
				<AppLink to={'/notes'}>{t('links.notes')}</AppLink>
			</div>
		</div>
	);
}

export default Navbar;