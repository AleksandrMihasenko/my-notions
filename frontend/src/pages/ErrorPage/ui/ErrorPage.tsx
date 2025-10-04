import { AppButton, classNames } from '@/shared';
import cls from './ErrorPage.module.scss';
import { useTranslation } from 'react-i18next';

const ErrorPage = () => {
	const { t } = useTranslation('translation');

	const reloadPage = () => {
		location.reload();
	}

	return (
		<div className={classNames(cls['error-page'], {}, [])}>
			<p>{ t('errorPage') }</p>

			<AppButton onClick={reloadPage}>
				{ t('actions.reload') }
			</AppButton>
		</div>
	);
}

export default ErrorPage;
