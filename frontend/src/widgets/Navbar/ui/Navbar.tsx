import React, { useCallback, useState } from 'react';
import { AppButton, classNames } from '@/shared';
import cls from './Navbar.module.scss';
import { AppLink } from '@/shared';
import { ThemeSwitcher } from '@/widgets/ThemeSwitcher';
import { useTranslation } from 'react-i18next';
import LoginModal from '@/features/AuthByUsername/ui/LoginModal/LoginModal';

interface NavbarProps {
	className?: string;
}

const Navbar = ({ className }: NavbarProps) => {
	const { t } = useTranslation('translation');
	const [isAuthModalOpen, setIsAuthModalOpen] = useState(false);

	const onShowModal = useCallback(() => {
		setIsAuthModalOpen(true);
	}, []);
	const onCloseModal = useCallback(() => {
		setIsAuthModalOpen(false);
	}, []);


	return (
		<div className={classNames(cls.navbar, {}, [className])}>
			<ThemeSwitcher />
			<AppButton onClick={onShowModal}>{t('links.login')}</AppButton>

			<LoginModal isOpen={isAuthModalOpen} onClose={onCloseModal} />

			<div className={cls.links}>
				<AppLink to={'/'}>{t('links.main')}</AppLink>
				<AppLink to={'/notes'}>{t('links.notes')}</AppLink>
			</div>
		</div>
	);
}

export default Navbar;