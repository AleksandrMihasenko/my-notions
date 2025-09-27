import { useTranslation } from 'react-i18next';
import { classNames } from '@/shared';
import cls from './NotFoundPage.module.scss';

const NotFoundPage = () => {
	const { t } = useTranslation('translation');

	return (
		<div className={classNames(cls['not-found-page'], {}, [])}>
			{ t('notFoundPage') }
		</div>
	);
}

export default NotFoundPage;
