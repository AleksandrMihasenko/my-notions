import React from 'react';
import { AppButton, AppInput, classNames } from '@/shared';
import cls from './LoginForm.module.scss'
import { useTranslation } from 'react-i18next';

interface LoginFormProps {
	className?: string;
}

const LoginForm = ({ className }: LoginFormProps) => {
	const { t } = useTranslation('translation');

	return (
		<div
			className={classNames(cls['login-form'], {}, [className])}
		>
			<AppInput id="login" type="text" placeholder={t('loginPlaceholder')} className={cls['input']} />
			<AppInput id="password" type="text" placeholder={t('passwordPlaceholder')} className={cls['input']} />

			<AppButton className={cls['login-button']}>
				{ t('actions.login') }
			</AppButton>
		</div>
	);
}

export default LoginForm;